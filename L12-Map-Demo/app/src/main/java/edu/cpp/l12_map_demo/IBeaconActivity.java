package edu.cpp.l12_map_demo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IBeaconActivity extends AppCompatActivity implements BeaconConsumer {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 555;
    private BeaconManager beaconManager;


    private WebView webview;
    private String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibeacon);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        beaconManager.bind(this);


        webview = (WebView) findViewById(R.id.webview);

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    List<Beacon> beaconList = new ArrayList<Beacon>();
                    for(Beacon b : beacons) {
                        //Log.i("TEST", "Beacon: " + b.getId1() + " " + b.getId2() + " " + b.getId3() + " " + b.getDistance());
                        beaconList.add(b);
                    }
                    Collections.sort(beaconList, new Comparator<Beacon>() {
                        @Override
                        public int compare(Beacon beacon, Beacon t1) {
                            return (int)(beacon.getDistance() - t1.getDistance());
                        }
                    });
                    for(Beacon b : beaconList) {
                        Log.i("TEST", "Beacon: " + " " + b.getId2() + " " + b.getId3() + " " + b.getDistance());
                    }
                    //Log.i("TEST", beaconList.toString());
                    if (!beaconList.get(0).getId3().toString().equals(currentId)) {
                        currentId = beaconList.get(0).getId3().toString();
                        Log.i("TEST", "Cloest: " + currentId);
                        loadUrl();
                    }
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }

    private void loadUrl() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (currentId.equals("1")) {
                    webview.loadUrl("http://www.cpp.edu");
                } else {
                    webview.loadUrl("http://www.cnn.com");
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("TEST", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }
        }
    }
}
