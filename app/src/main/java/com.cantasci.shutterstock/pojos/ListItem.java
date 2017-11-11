package com.cantasci.shutterstock.pojos;

public abstract class ListItem {
    public static final int TYPE_CATEGORY = 0;
    public static final int TYPE_GENERAL = 1;

    abstract public int getType();
}
