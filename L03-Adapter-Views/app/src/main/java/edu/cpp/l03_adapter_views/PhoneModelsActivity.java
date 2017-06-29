package edu.cpp.l03_adapter_views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import edu.cpp.l03_adapter_views.model.LocalManualPhoneDataProvider;
import edu.cpp.l03_adapter_views.model.PhoneDataProvider;

/**
 * Created by yusun on 6/28/17.
 */

public class PhoneModelsActivity extends AppCompatActivity {

    private ListView phoneModelsListView;
    private PhoneDataProvider phoneDataProvider;

    private PhoneModelDataAdapter phoneModelDataAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //
        phoneDataProvider = new LocalManualPhoneDataProvider();


        phoneModelsListView = (ListView) findViewById(R.id.phoneModelsListView);

        phoneModelDataAdapter = new PhoneModelDataAdapter(this, R.layout.listview_item_phone_model,
                phoneDataProvider.listAllPhoneModelsCompatibility());

        phoneModelsListView.setAdapter(phoneModelDataAdapter);

        phoneModelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(PhoneModelsActivity.this, "List View Clicked: "
                        + phoneModelDataAdapter.getItem(i).getPhoneModel(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
