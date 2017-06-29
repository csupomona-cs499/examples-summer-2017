package edu.cpp.l03_adapter_views.model;

import java.util.List;

/**
 * Created by yusun on 6/28/17.
 */

public interface PhoneDataProvider {

    public List<PhoneCompatibility> listAllPhoneModelsCompatibility();

    public void addPhoneModel(String name, boolean isCom);

}
