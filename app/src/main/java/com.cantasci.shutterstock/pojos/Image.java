package com.cantasci.shutterstock.pojos;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class Image {
    @SerializedName("id")               private String  mId;
    @SerializedName("description")      private String  mDescription;
    @SerializedName("added_date")       private String  mAddedDate;
    @SerializedName("media_type")       private String  mMediaType;
    @SerializedName("contributor")      private Contributor  mContributor;
    @SerializedName("aspect")           private double  mAspect;
    @SerializedName("image_type")       private String  mImageType;
    @SerializedName("is_editorial")     private boolean  mIsEditorial;
    @SerializedName("is_adult")         private boolean  mIsAdult;
    @SerializedName("is_illustration")  private boolean  mIllustration;
    @SerializedName("releases")         private String  mReleases;
    @SerializedName("categories")       private Category[]  mCategories;
    @SerializedName("keywords")         private String[]  mKeywords;
    @SerializedName("assets")           private ImageAssets    mAssets;
    @SerializedName("models")           private Model[]      mModels;


    public String getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getLargeThumbnail(){
        return mAssets.getLargeThumb().getUrl();
    }





}