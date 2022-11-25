package com.bashirli.alsat.model;

import com.google.firebase.firestore.FieldValue;

import java.io.Serializable;

public class Data implements Serializable {
    private String email;

    private String name;
    private String value;
    private String about;
    private String connection;
    private String product;
    private String image;

    public Data(String email, String name, String value, String about, String connection,String product,String image) {
        this.email = email;

        this.name = name;
        this.value = value;
        this.about = about;
        this.connection = connection;
        this.product=product;
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public String getProduct(){
        return product;
    }


    public String getEmail() {
        return email;
    }



    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getAbout() {
        return about;
    }

    public String getConnection() {
        return connection;
    }
}
