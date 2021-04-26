package com.example.alphademo.views.triplist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alphademo.R;
import com.example.alphademo.views.triplist.SiteDeliveryFormFragment;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignaturePop extends Fragment {

    private SignaturePad signature_pad;
    private Button clear;
    private Button save;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.popsignaturepad, container, false);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getActivity().getWindow().setLayout((int)(width*.8),(int)(height*.6));

        // initiate variable for signature pad
        signature_pad = (SignaturePad) view.findViewById(R.id.signature_pad);
        clear = (Button) view.findViewById(R.id.clearSign);
        save = (Button) view.findViewById(R.id.saveSign);

        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(getActivity(), "Start Signing", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity(), "Signature Saved", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}