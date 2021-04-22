package com.example.alphademo.dummy;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.alphademo.R;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignaturePop extends MainActivity4 {

    private SignaturePad signature_pad;
    private Button clear;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popsignaturepad);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6));

        // initiate variable for signature pad
        signature_pad = (SignaturePad) findViewById(R.id.signature_pad);
        clear = (Button) findViewById(R.id.clearSign);
        save = (Button) findViewById(R.id.saveSign);

        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(SignaturePop.this, "Start Signing", Toast.LENGTH_SHORT).show();
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
                //signature_pad.getSignatureBitmap();
                Bitmap bitmap = signature_pad.getDrawingCache();
                Toast.makeText(SignaturePop.this, "Signature Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

}