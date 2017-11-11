package com.cantasci.shutterstock.pojos;

public class GeneralItem extends ListItem {
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImages(Image image) {
        this.image = image;
    }

    @Override
    public int getType() {
        return TYPE_GENERAL;
    }
}
