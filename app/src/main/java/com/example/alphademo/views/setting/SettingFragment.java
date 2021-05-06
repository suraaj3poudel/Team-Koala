package com.example.alphademo.views.setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alphademo.activities.MainActivity;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseProfile;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SettingFragment extends Fragment {
    private Button button;
    private Button logoutBtn;
    private ImageView setting;
    DatabaseProfile db;
    EditText editemail, editPhone, editAddress, editName;
    RadioGroup radioGender;
    RadioButton radiobtn;
    TextView textEmail, textPhone, textAddress, textGender, textName, tv_address, displayName;
    String name, email, address, gender, phone;
    String updateName, updateEmail, updatePhone, updateAddress, updateGender;
    ImageView imageEmail, imagePhone, imageAddress, imageGender, imageName;
    Button save;
    RelativeLayout topSection;
    public static final String NAME = "MyPrefs" ;
    SharedPreferences sharedPreferencesName ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", "onErrorResponse1: ");
        sharedPreferencesName = getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        setting = (ImageView) getView().findViewById(R.id.settingIcon);
        editemail = (EditText) getView().findViewById(R.id.edittextemail);
        editPhone = (EditText) getView().findViewById(R.id.editPhone);
        editAddress = (EditText) getView().findViewById(R.id.editAddress);
        editName = (EditText) getView().findViewById(R.id.editName);

        radioGender = (RadioGroup) getView().findViewById(R.id.radioGrp);
        imageEmail = (ImageView) getView().findViewById(R.id.imageEmail);
        imagePhone = (ImageView) getView().findViewById(R.id.imagePhone);
        imageAddress = (ImageView) getView().findViewById(R.id.imageAddress);
        imageGender = (ImageView) getView().findViewById(R.id.imageGender);
        imageName = (ImageView) getView().findViewById(R.id.imageName);

        textEmail = (TextView) getView().findViewById(R.id.textviewemail);
        textPhone = (TextView) getView().findViewById(R.id.textPhone);
        textAddress = (TextView) getView().findViewById(R.id.textAddress);
        textGender = (TextView) getView().findViewById(R.id.textGender);
        textName = (TextView) getView().findViewById(R.id.textName);
        tv_address = (TextView) getView().findViewById(R.id.tv_address);
        displayName = (TextView) getView().findViewById(R.id.displayName);


        topSection = getView().findViewById(R.id.topSection);

        phone = textPhone.getText().toString();

        save = (Button) getView().findViewById((R.id.saveButton));


        db = new DatabaseProfile(getContext());


        textEmail.setText(db.getData(1, "EMAIL"));

        textAddress.setText(db.getData(1, "ADDRESS"));
        textPhone.setText(db.getData(1, "PHONE"));
        textGender.setText(db.getData(1, "GENDER"));
        tv_address.setText(db.getData(1, "ADDRESS"));

        if(db.getData(1, "NAME")==""){
            displayName.setText(sharedPreferencesName.getString("dname","Name"));
            editName.setText(sharedPreferencesName.getString("dname","Name"));
            textName.setText(sharedPreferencesName.getString("dname","Name"));
        }
        else{
            displayName.setText(db.getData(1, "NAME"));
            editName.setText(db.getData(1, "NAME"));
            textName.setText(db.getData(1, "NAME"));
        }

        editemail.setText(db.getData(1, "EMAIL"));
        editPhone.setText(db.getData(1, "PHONE"));
        editAddress.setText(db.getData(1, "ADDRESS"));



        if (db.getData(1, "GENDER").equals("Female"))
            radioGender.check(R.id.radioF);
        else
            radioGender.check(R.id.radioM);


        //Toast.makeText(getContext(),db.getData(1, "EMAIL").toString(), Toast.LENGTH_SHORT).show();


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });


    }

    private void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.menu_settings, popupMenu.getMenu());
        Boolean is = true;


        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    Toast.makeText(getContext(), "Pressed Log Out Button", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.putBoolean("loggedIn",false);
                    editor.commit();
                    goToMainActivity();
                }
                if (item.getItemId() == R.id.editProfile) {


                    save.setVisibility(View.VISIBLE);
                    imageEmail.setVisibility(View.VISIBLE);
                    imageAddress.setVisibility(View.VISIBLE);
                    imageGender.setVisibility(View.VISIBLE);
                    imagePhone.setVisibility(View.VISIBLE);


                    backgroundAndPadding(textEmail);
                    backgroundAndPadding(textAddress);
                    backgroundAndPadding(textPhone);
                    backgroundAndPadding(textGender);


                    topSection.setVisibility(View.GONE);



                    imageEmail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appears(editemail, textEmail, imageEmail, "EMAIL");
                        }
                    });
                    imagePhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appears(editPhone, textPhone, imagePhone, "PHONE");
                        }
                    });
                    imageAddress.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            appears(editAddress, textAddress, imageAddress, "ADDRESS");
                        }
                    });
                    imageGender.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imageGender.setVisibility(View.GONE);
                            radioGender.setVisibility(View.VISIBLE);
                            textGender.setVisibility(View.GONE);
                            if (db.getData(1, "GENDER").toString().equals("Female"))
                                radioGender.check(R.id.radioF);
                            else
                                radioGender.check(R.id.radioM);
                        }
                    });

                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveFunction(updateAddress);
                        }
                    });
                }
                return false;
            }
        });
        if (is) {
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

    public void goToMainActivity(){
        Intent i = new Intent(getContext(),MainActivity.class);
        startActivity(i);
    }

    public void openDialog() {
        DialogFragment dialog = new DialogFragment();
        dialog.show(getChildFragmentManager(), "example ");
    }

    public void saveFunction(String updateAddress1) {
        updateAddress = editAddress.getText().toString();
        updateName = editName.getText().toString();
        updateEmail = editemail.getText().toString();
        updatePhone = editPhone.getText().toString();
        int selectedId = radioGender.getCheckedRadioButtonId();
        radiobtn = (RadioButton) getView().findViewById(selectedId);
        updateGender = radiobtn.getText().toString();
        db.addData(1, updateName, updateAddress, updateEmail, updatePhone, updateGender);
        db.updateInfo(1, updateName, updateAddress, updateEmail, updatePhone, updateGender);

        textPhone.setText(updatePhone);
        textEmail.setText(updateEmail);
        textAddress.setText(updateAddress);
        textGender.setText(updateGender);
        textName.setText(updateName);
        tv_address.setText(updateAddress);
        displayName.setText(updateName);

        editemail.setVisibility(View.GONE);
        editPhone.setVisibility(View.GONE);
        editAddress.setVisibility(View.GONE);
        radioGender.setVisibility(View.GONE);
        editName.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        imageEmail.setVisibility(View.GONE);
        imageAddress.setVisibility(View.GONE);
        imageGender.setVisibility(View.GONE);
        imagePhone.setVisibility(View.GONE);
        imageName.setVisibility(View.GONE);

        textEmail.setVisibility(View.VISIBLE);
        textPhone.setVisibility(View.VISIBLE);
        textAddress.setVisibility(View.VISIBLE);
        textGender.setVisibility(View.VISIBLE);
        textName.setVisibility(View.VISIBLE);
        tv_address.setVisibility(View.VISIBLE);
        topSection.setVisibility(View.VISIBLE);

        textName.setBackground(null);
        textGender.setBackground(null);
        textPhone.setBackground(null);
        textAddress.setBackground(null);
        textEmail.setBackground(null);

        textName.setPadding(0, 0, 0, 0);
        textPhone.setPadding(0, 0, 0, 0);
        textAddress.setPadding(0, 0, 0, 0);
        textEmail.setPadding(0, 0, 0, 0);
        textGender.setPadding(0, 0, 0, 0);


    }

    public void appears(EditText appear, TextView disappear, ImageView image, String field) {
        appear.setVisibility(View.VISIBLE);
        appear.setText(db.getData(1, field));
        disappear.setVisibility(View.GONE);
        image.setVisibility(View.GONE);
    }

    public void backgroundAndPadding(TextView newAppearance){
        newAppearance.setBackgroundResource(R.drawable.rectangled);
        newAppearance.setPadding(50,50,50, 50);
    }
}