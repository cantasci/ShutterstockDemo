package com.cantasci.shutterstock.pojos;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data @Accessors(prefix = "m")
public class BaseSearchResponse<T> extends BasePagedResponse<T> {
    @SerializedName("spellcheck_info")  private Object mSellcheckInfo;
    @SerializedName("search_id")        private String mSearchId;
}
