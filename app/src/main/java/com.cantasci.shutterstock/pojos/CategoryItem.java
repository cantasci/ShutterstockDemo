package com.cantasci.shutterstock.pojos;


public class CategoryItem extends ListItem {
    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @Override
    public int getType() {
        return TYPE_CATEGORY;
    }
}
