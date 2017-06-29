package edu.cpp.l03_adapter_views.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yusun on 6/28/17.
 */

public class LocalManualPhoneDataProvider implements PhoneDataProvider {

    private List<PhoneCompatibility> phoneModels;

    public LocalManualPhoneDataProvider() {
        phoneModels = new ArrayList<>();
    }

    public List<PhoneCompatibility> listAllPhoneModelsCompatibility() {
        return phoneModels;
    }

    public void addPhoneModel(String name, boolean isCom) {
        phoneModels.add(new PhoneCompatibility(name, isCom));
    }
}
