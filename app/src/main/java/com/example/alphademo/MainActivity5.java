package com.example.alphademo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alphademo.dummy.MainActivity4;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity5 extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private SignaturePad signature_pad;
    private Button clear;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);


        // initiate variable for signature pad
        signature_pad = (SignaturePad) findViewById(R.id.signature_pad);
        clear = (Button) findViewById(R.id.clearSign);
        save = (Button) findViewById(R.id.saveSign);

        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(MainActivity5.this, "Start Signing", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                save.setEnabled(true);
                clear.setEnabled(true);
            }

            @Override
            public void onClear() {
                save.setEnabled(false);
                clear.setEnabled(false);
            }

        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signature_pad.clear();
            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = signature_pad.getSignatureBitmap();
                Toast.makeText(MainActivity5.this, "Signature Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

}