package com.drapps.ms.superexercise.realmobjects;

import io.realm.RealmObject;

/**
 * Created by Diogo on 17/10/2017.
 */

public class Locations extends RealmObject {

    private String postalCode;
    private String name;

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
