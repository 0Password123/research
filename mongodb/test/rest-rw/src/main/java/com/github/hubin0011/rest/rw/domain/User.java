package com.github.hubin0011.rest.rw.domain;


import com.github.hubin0011.rest.rw.ICachableDomain;

public class User implements ICachableDomain {

    private String id;

    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
