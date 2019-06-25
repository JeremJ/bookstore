package com.bs.library.user;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum RoleType {
    ADMIN("admin"),CUSTOMER("customer");

    private String displayName;

    RoleType(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}