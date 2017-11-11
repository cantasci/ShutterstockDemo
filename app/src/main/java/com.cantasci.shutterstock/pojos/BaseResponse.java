package com.cantasci.shutterstock.pojos;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(prefix = "m")
public class BaseResponse{
    @SerializedName("message") private String  mMessage;
    @SerializedName("errors") private  Error   mErrors;
}
