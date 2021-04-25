package com.example.alphademo.views.setting;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alphademo.MainActivity;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseJson;
import com.example.alphademo.database.DatabaseProfile;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SettingFragment extends Fragment
{
    private Button button;
    private Button logoutBtn;
    private ImageView setting;
    DatabaseProfile db;
    EditText editemail, editPhone, editAddress, editName;
    RadioGroup radioGender;
    RadioButton radiobtn;
    TextView textEmail, textPhone, textAddress, textGender, textName;
    String name, email, address, gender, phone;
    String updateName, updateEmail, updatePhone, updateAddress, updateGender;
    Button save;


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
        Log.d("TAG", "onErrorResponse1: ");
        setting = (ImageView)getView().findViewById(R.id.settingIcon);
        editemail = (EditText) getView().findViewById(R.id.edittextemail);
        editPhone = (EditText) getView().findViewById(R.id.editPhone);
        editAddress= (EditText) getView().findViewById(R.id.editAddress);
        editName = (EditText) getView().findViewById(R.id.editName);

        radioGender = (RadioGroup) getView().findViewById(R.id.radioGrp);

        textEmail = (TextView) getView().findViewById(R.id.textviewemail);
        textPhone = (TextView) getView().findViewById(R.id.textPhone);
        textAddress = (TextView) getView().findViewById(R.id.textAddress);
        textGender = (TextView) getView().findViewById(R.id.textGender);
        textName = (TextView) getView().findViewById(R.id.textName);
        phone = textPhone.getText().toString();

        save = (Button) getView().findViewById((R.id.saveButton));





         db =  new DatabaseProfile(getContext());
         //db.addData(1, "jhhjhj", "", "", "","");
        //name = "ABCDEFGH";
        //db.getData(2, "EMAIL");
        Toast.makeText(getContext(),db.getData(1, "NAME").toString(), Toast.LENGTH_SHORT).show();



      /*  address = textAddress.getText().toString();
        gender = textGender.getText().toString();
        email  = textEmail.getText().toString();
        gender = textGender.getText().toString();


        db.addData(1, name, address, email, phone, gender);*/

        textEmail.setText(db.getData(1, "EMAIL"));
        textName.setText(db.getData(1, "NAME"));
        textAddress.setText(db.getData(1, "ADDRESS"));
        textPhone.setText(db.getData(1, "PHONE"));
        textGender.setText(db.getData(1, "GENDER"));




        //Toast.makeText(getContext(),db.getData(1, "EMAIL").toString(), Toast.LENGTH_SHORT).show();






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
                    editemail.setVisibility(View.VISIBLE);
                    editPhone.setVisibility(View.VISIBLE);
                    editAddress.setVisibility(View.VISIBLE);
                    editName.setVisibility(View.VISIBLE);
                    radioGender.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);


                    textEmail.setVisibility(View.GONE);
                    textPhone.setVisibility(View.GONE);
                    textAddress.setVisibility(View.GONE);
                    textGender.setVisibility(View.GONE);
                    textName.setVisibility(View.GONE);


                    Toast.makeText(getContext(), "Pressed Edit Profile Button", Toast.LENGTH_SHORT).show();

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
    public void saveFunction(String updateAddress1){
        updateAddress = editAddress.getText().toString();
        updateName = editName.getText().toString();
        updateEmail = editemail.getText().toString();
        updatePhone = editPhone.getText().toString();
        int selectedId = radioGender.getCheckedRadioButtonId();
        radiobtn = (RadioButton) getView().findViewById(selectedId);
        updateGender = radiobtn.getText().toString();
        db.addData(1, updateName, updateAddress,  updateEmail, updatePhone, updateGender);
        db.updateInfo(1, updateName, updateAddress,  updateEmail, updatePhone, updateGender);

        textPhone.setText(updatePhone);
        textEmail.setText(updateEmail);
        textAddress.setText(updateAddress);
        textGender.setText(updateGender);
        textName.setText(updateName);

        editemail.setVisibility(View.GONE);
        editPhone.setVisibility(View.GONE);
        editAddress.setVisibility(View.GONE);
        radioGender.setVisibility(View.GONE);
        editName.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        textEmail.setVisibility(View.VISIBLE);
        textPhone.setVisibility(View.VISIBLE);
        textAddress.setVisibility(View.VISIBLE);
        textGender.setVisibility(View.VISIBLE);
        textName.setVisibility(View.VISIBLE);





    }
}
/*
package com.example.alphademo.views.setting;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.alphademo.MainActivity;
import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseJson;
import com.example.alphademo.database.DatabaseProfile;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SettingFragment extends Fragment
{
    private Button button;
    private Button logoutBtn;
    private ImageView setting;
    DatabaseProfile db;
    EditText editemail, editPhone, editAddress;
    RadioGroup radioGender;
    RadioButton radiobtn;
    TextView textEmail, textPhone, textAddress, textGender;
    String name, email, address, gender, phone;
    String updateName, updateEmail, updatePhone, updateAddress, updateGender;
    Button save;


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
        });



        return view;
                }

@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TAG", "onErrorResponse1: ");
        setting = (ImageView)getView().findViewById(R.id.settingIcon);
        editemail = (EditText) getView().findViewById(R.id.edittextemail);
        editPhone = (EditText) getView().findViewById(R.id.editPhone);
        editAddress= (EditText) getView().findViewById(R.id.editAddress);

        radioGender = (RadioGroup) getView().findViewById(R.id.radioGrp);

        textEmail = (TextView) getView().findViewById(R.id.textviewemail);
        textPhone = (TextView) getView().findViewById(R.id.textPhone);
        textAddress = (TextView) getView().findViewById(R.id.textAddress);
        textGender = (TextView) getView().findViewById(R.id.textGender);
        phone = textPhone.getText().toString();

        save = (Button) getView().findViewById((R.id.saveButton));





        db =  new DatabaseProfile(getContext());;
        name = "ABCDEFGH";


      /*  address = textAddress.getText().toString();
        gender = textGender.getText().toString();
        email  = textEmail.getText().toString();
        gender = textGender.getText().toString();


        db.addData(1, name, address, email, phone, gender);

        textEmail.setText(db.getData(1, "EMAIL"));
        textAddress.setText(db.getData(1, "ADDRESS"));
        textPhone.setText(db.getData(1, "PHONE"));
        textGender.setText(db.getData(1, "GENDER"));




        Toast.makeText(getContext(),db.getData(1, "EMAIL").toString(), Toast.LENGTH_SHORT).show();






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
        editemail.setVisibility(View.VISIBLE);
        editPhone.setVisibility(View.VISIBLE);
        editAddress.setVisibility(View.VISIBLE);
        radioGender.setVisibility(View.VISIBLE);
        save.setVisibility(View.VISIBLE);


        textEmail.setVisibility(View.GONE);
        textPhone.setVisibility(View.GONE);
        textAddress.setVisibility(View.GONE);
        textGender.setVisibility(View.GONE);


        Toast.makeText(getContext(), "Pressed Edit Profile Button", Toast.LENGTH_SHORT).show();

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
public void saveFunction(String updateAddress1){
        updateAddress = editAddress.getText().toString();
        updateName = "fvdndf";
        updateEmail = editemail.getText().toString();
        updatePhone = editPhone.getText().toString();
        int selectedId = radioGender.getCheckedRadioButtonId();
        radiobtn = (RadioButton) getView().findViewById(selectedId);
        updateGender = radiobtn.getText().toString();
        db.addData(1, updateName, updateAddress,  updateEmail, updatePhone, updateGender);
        db.updateInfo(1, updateName, updateAddress,  updateEmail, updatePhone, updateGender);

        textPhone.setText(updatePhone);
        textEmail.setText(updateEmail);
        textAddress.setText(updateAddress);
        textGender.setText(updateGender);

        editemail.setVisibility(View.GONE);
        editPhone.setVisibility(View.GONE);
        editAddress.setVisibility(View.GONE);
        radioGender.setVisibility(View.GONE);
        save.setVisibility(View.GONE);

        textEmail.setVisibility(View.VISIBLE);
        textPhone.setVisibility(View.VISIBLE);
        textAddress.setVisibility(View.VISIBLE);
        textGender.setVisibility(View.VISIBLE);





        }
        }
 */