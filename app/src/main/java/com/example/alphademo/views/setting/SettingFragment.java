package com.example.alphademo.views.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alphademo.MainActivity;
import com.example.alphademo.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingFragment extends Fragment
{
    private Button button;
    private Button logoutBtn;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        button = (Button) view.findViewById(R.id.about_btn);
        logoutBtn=(Button)view.findViewById(R.id.logout_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplication(), MainActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }
    public void openDialog(){
        DialogFragment dialog =new DialogFragment();
        dialog.show(getChildFragmentManager(),"example ");
    }
}
