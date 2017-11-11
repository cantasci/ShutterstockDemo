package com.cantasci.shutterstock.pojos;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class Error {
    @SerializedName("code")     private String mCode;
    @SerializedName("data")     private String mData;
    @SerializedName("message")  private String mMessage;
    @SerializedName("path")     private String mPath;
    @SerializedName("items")    private Object[] mItems;

}