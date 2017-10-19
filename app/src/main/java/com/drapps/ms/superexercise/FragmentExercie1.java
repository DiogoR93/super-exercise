package com.drapps.ms.superexercise;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.drapps.ms.superexercise.realmobjects.Locations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Diogo on 18/10/2017.
 */
public class FragmentExercie1 extends Fragment {

    RecyclerView rvPostalCodes;
    LocationsAdapter adapter;
    EditText etSearch;
    Fragment fragment;
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercie_1, container ,false);
        adapter = new LocationsAdapter(this, Realm.getDefaultInstance(),Realm.getDefaultInstance().where(Locations.class).findAll());
        rvPostalCodes = (RecyclerView) view.findViewById(R.id.rv_postal_codes);
        etSearch = (EditText) view.findViewById(R.id.et_search);
        fragment = this;

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterRecyvlerView();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvPostalCodes.setLayoutManager(mLayoutManager);
        rvPostalCodes.setAdapter(adapter);
        getPostalCodesFromAPI();

        return view;
    }

    private void filterRecyvlerView() { //need to be improved (not dynamic, need better study of realm query)
        String query = etSearch.getText().toString();
        String[] querySplited = query.split(" ");

        if(query.equals("")){
            rvPostalCodes.removeAllViews();
            adapter.setRealmResults(Realm.getDefaultInstance().where(Locations.class).findAll());
        }else{rvPostalCodes.removeAllViews();
            if(querySplited.length == 1) {
                adapter = new LocationsAdapter(fragment, Realm.getDefaultInstance(), Realm.getDefaultInstance().where(Locations.class)
                        .contains("postalCode", querySplited[0], Case.INSENSITIVE).or()
                        .contains("name", querySplited[0], Case.INSENSITIVE).findAll());
                rvPostalCodes.setAdapter(adapter);


            }else if(querySplited.length == 2){
                adapter = new LocationsAdapter(fragment, Realm.getDefaultInstance(), Realm.getDefaultInstance().where(Locations.class)
                        .contains("postalCode", querySplited[0], Case.INSENSITIVE).or()
                        .contains("name", querySplited[0],Case.INSENSITIVE)
                        .contains("postalCode", querySplited[1],Case.INSENSITIVE).or()
                        .contains("name", querySplited[1],Case.INSENSITIVE).or()
                        .findAll());
                rvPostalCodes.setAdapter(adapter);
            }else if(querySplited.length == 3){
                adapter = new LocationsAdapter(getTargetFragment(), Realm.getDefaultInstance(), Realm.getDefaultInstance().where(Locations.class)
                        .contains("postalCode", querySplited[0], Case.INSENSITIVE).or()
                        .contains("name", querySplited[0],Case.INSENSITIVE)
                        .contains("postalCode", querySplited[1],Case.INSENSITIVE).or()
                        .contains("name", querySplited[1],Case.INSENSITIVE)
                        .contains("postalCode", querySplited[2],Case.INSENSITIVE).or()
                        .contains("name", querySplited[2],Case.INSENSITIVE).or()
                        .findAll());
                rvPostalCodes.setAdapter(adapter);
            }
        }

    }

    public void getPostalCodesFromAPI(){
        if(Realm.getDefaultInstance().where(Locations.class).findAll().isEmpty()) {
            progressDialog = ProgressDialog.show(getActivity(),"Loading...","");
            rx.Observable<ResponseBody> observable = ((MainActivity) getActivity()).getApi().getString();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ResponseBody>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ResponseBody response) {
                            try {
                                createList(response.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

    private void createList(String string) {
        int i = -1;
        List<Locations> locationsList = new ArrayList<>();

        Scanner scanner = new Scanner(string);
        String[] lineSplited;
        String line;
        while(scanner.hasNext()){
            if(i < 0){
                scanner.nextLine();
                i++;
            }else{
                line = scanner.nextLine();
                lineSplited = line.split(",");
                Log.i("LINE", i+" "+lineSplited[0]);
                locationsList.add(new Locations(i, lineSplited[11] + " - " + lineSplited[12], lineSplited[0]));
                i++;
            }
        }
            scanner.close();

            //Save list in local database
            Realm.getDefaultInstance().beginTransaction();
            Realm.getDefaultInstance().deleteAll();
            Realm.getDefaultInstance().insert(locationsList);
            Realm.getDefaultInstance().commitTransaction();

            progressDialog.dismiss();
            locationsList.clear();
            adapter.notifyDataSetChanged();
    }
}
