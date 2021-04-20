package com.example.alphademo.views.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alphademo.MainActivity;
import com.example.alphademo.R;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SettingFragment extends Fragment
{
    private Button button;
    private Button logoutBtn;
    private ImageView setting;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
       /* button = (Button) view.findViewById(R.id.about_btn);
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
        });*/



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setting = (ImageView)getView().findViewById(R.id.settingIcon);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });

    }

    private void showMenu(View v){
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_settings,popupMenu.getMenu());
        Boolean is = true;





        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.logout){
                    Toast.makeText(getContext(), "Pressed Log Out Button", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId() == R.id.editProfile){
                    Toast.makeText(getContext(), "Pressed Edit Profile Button", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        if(is){
            try {
                Field[] fields = popupMenu.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if ("mPopup".equals(field.getName())) {
                        field.setAccessible(true);
                        Object menuPopupHelper = field.get(popupMenu);
                        Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                        Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                        setForceIcons.invoke(menuPopupHelper, true);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }



        popupMenu.show();

    }
    public void openDialog(){
        DialogFragment dialog =new DialogFragment();
        dialog.show(getChildFragmentManager(),"example ");
    }
}
