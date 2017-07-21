package edu.cpp.l12_map_demo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class SensorActivity extends AppCompatActivity implements BeaconConsumer {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 555;
    private BeaconManager beaconManager;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temple_run);

        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser()
                        .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));

        beaconManager.bind(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        imageView = (ImageView) findViewById(R.id.imageView);

        final int imageWidth = imageView.getWidth();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final float width = size.x;

        int height = size.y;

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);


        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            // Success! There's a magnetometer.
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mSensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float axisX = event.values[0];
                    float axisY = event.values[1];
                    float axisZ = event.values[2];
                    //Log.i("TEST", "X: " + axisX + " Y: " + axisY + " Z: " + axisZ + " width: " + width);
                    float ratio =
                            axisX >= 0 ?
                            (((float)Math.min(axisX, 5)) / (float) 5)
                            : (((float)Math.max(axisX, -5)) / (float) 5);
                    //Log.i("TEST", "ratio: " + ratio);
                    float center = width / 2 * (1 - ratio) - imageWidth / 2;
                    //Log.i("TEST", "center: " + center);
                    imageView.setX(center);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            // Failure! No magnetometer.
        }

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    for(Beacon b : beacons) {
                        Log.i("TEST", "Beacon: " + b.getId1() + " " + b.getId2() + " " + b.getId3() + " " + b.getDistance());
                    }
                    Log.i("test", "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }
            }
        });

        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
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
