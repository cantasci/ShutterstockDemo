package com.cantasci.shutterstock.pojos;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class Category {
    @SerializedName("id")       private String mId;
    @SerializedName("name")     private String mName;

    public String getId() {
        return mId;
    }
    public String getName() {
        return mName;
    }
}