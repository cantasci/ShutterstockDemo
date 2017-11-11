package com.cantasci.shutterstock.pojos;


import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class ImageAssets{
    @SerializedName("preview")
    private Thumbnail mPreview;

    @SerializedName("small_thumb")
    private Thumbnail mSmallThumb;

    @SerializedName("large_thumb")
    private Thumbnail mLargeThumb;
}
