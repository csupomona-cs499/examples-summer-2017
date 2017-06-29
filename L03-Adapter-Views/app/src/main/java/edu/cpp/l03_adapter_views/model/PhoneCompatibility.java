package edu.cpp.l03_adapter_views.model;

/**
 * Created by yusun on 6/28/17.
 */

public class PhoneCompatibility {

    private String phoneModel;
    private boolean isCompatible;

    public PhoneCompatibility() {
    }

    public PhoneCompatibility(String phoneModel, boolean isCompatible) {
        this.phoneModel = phoneModel;
        this.isCompatible = isCompatible;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public boolean isCompatible() {
        return isCompatible;
    }

    public void setCompatible(boolean compatible) {
        isCompatible = compatible;
    }
}
