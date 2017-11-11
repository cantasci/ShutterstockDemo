package com.cantasci.shutterstock.pojos;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(prefix = "m")
public class BasePagedResponse<T> extends BaseResponse {
    @SerializedName("per_page")         private int mPerPage;
    @SerializedName("page")             private int mPage;
    @SerializedName("total_count")      private int mTotalCount;
    @SerializedName("data")             private T  mData;
}
