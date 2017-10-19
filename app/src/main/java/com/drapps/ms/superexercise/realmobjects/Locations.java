package com.drapps.ms.superexercise.realmobjects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Diogo on 17/10/2017.
 */

public class Locations extends RealmObject {

    @PrimaryKey
    private int id; //to set a position in realm
    private String postalCode;
    private String name;

    public Locations() {
    }

    public Locations(int id, String postalCode, String name) {
        this.id = id;
        this.postalCode = postalCode;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoPostal() {
        return postalCode;
    }

    public void setCodigoPostal(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
