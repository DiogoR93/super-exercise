package com.drapps.ms.superexercise;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.drapps.ms.superexercise.realmobjects.Locations;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Diogo on 18/10/2017.
 */
public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder>{

    Fragment fragment;
    Realm realm;
    String[] query = null;
    RealmResults<Locations> locations;


    public LocationsAdapter(Fragment fragment, Realm realm) {
        this.fragment = fragment;
        this.realm = realm;

    }

    public LocationsAdapter(Fragment fragment, Realm realm, RealmResults<Locations> realmResults) {
        this.fragment = fragment;
        this.realm = realm;
        locations = realmResults;
    }

    public void setRealmResults(RealmResults<Locations> realmResults){
        locations = realmResults;
        notifyDataSetChanged();
    }

    @Override
    public LocationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(fragment.getContext()).inflate(R.layout.adapter_locations_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationsAdapter.ViewHolder holder, int position) {
        try {
            holder.tvPostalCode.setText(locations.get(position).getCodigoPostal() + " , " + locations.get(position).getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return realm.where(Locations.class).findAll().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPostalCode;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPostalCode = (TextView) itemView.findViewById(R.id.postal_code_and_name);
        }


    }
}
