package com.cantasci.shutterstock.pojos;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class Thumbnail {
    @SerializedName("url")    private String mUrl;

    public String getTumbnailUrl() {
        return mUrl;
    }
}