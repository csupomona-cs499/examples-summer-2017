package edu.cpp.l03_adapter_views.model;

import java.util.List;

/**
 * Created by yusun on 6/28/17.
 */

public class RemotePhoneModelDataProvider implements PhoneDataProvider {

    @Override
    public List<PhoneCompatibility> listAllPhoneModelsCompatibility() {
        // make an HTTP request

        return null;
    }

    @Override
    public void addPhoneModel(String name, boolean isCom) {
        // make an HTTP request

    }
}
