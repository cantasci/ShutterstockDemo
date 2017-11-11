package com.cantasci.shutterstock.pojos;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class Contributor {
    @SerializedName("id")    private String mId;

    public String getId() {
        return mId;
    }
}