package com.example.chhaya_pc.billscanner;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.List;

public class CamActivity extends Activity{

    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    final int requestCameraPermissionId = 1001;
    boolean flag = false;
    static String total;
    SparseArray<TextBlock> items = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_layout);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        textView = (TextView) findViewById(R.id.text_view);


        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.d("MainActivity", "Text Recognition dependencies not available");
        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer).setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1080, 1024)
                    .setRequestedFps(10.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            SurfaceHolder surfaceHolder = cameraView.getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                            ActivityCompat.requestPermissions(CamActivity.this,new String[] {Manifest.permission.CAMERA},requestCameraPermissionId);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });
            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    items = detections.getDetectedItems();

                    if (items.size() != 0) {

                        textView.post(new Runnable() {

                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < items.size(); i++) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());


                                   if (item.getValue().contains("Total") || item.getValue().contains("TOTAL") || item.getValue().contains("NET") || item.getValue().contains("Net") || item.getValue().contains("Amt") || item.getValue().contains("Amount") || item.getValue().contains("AMOUNT") ||flag) {
                                        flag = true;


                                        item.getValue().replaceAll("[^\\d.]", "");
                                        if (item.getValue().matches("[0-9]+.[0-9]*")) {
                                            Log.d("found total!", item.getValue());
                                           // Toast.makeText(getApplicationContext(), "Total " + item.getValue(), Toast.LENGTH_SHORT).show();
                                            total = item.getValue();
                                            Intent intent = new Intent(CamActivity.this,MainActivity.class);
                                            intent.putExtra("total",total.toString());
                                            startActivity(intent);
                                            stopLockTask();
                                            flag = false;

                                            finish();
                                            flag = false;


                                        }
                                    }


                                        stringBuilder.append("\n");

                                    }
                                    //  textView.setText(stringBuilder.toString());
                                    //if(stringBuilder.toString().contains("TOTAL")||stringBuilder.toString().contains("Total")){
                                    //  AlertDialog.Builder(this).build();
                                }
                            });
                        }
                    }
                });
            }
        }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case requestCameraPermissionId:
            {
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                        return;

                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

