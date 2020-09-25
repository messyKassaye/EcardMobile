package com.example.foragentss.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.dashboard.DashboardActivity;
import com.example.foragentss.rooms.entity.User;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import javax.crypto.SecretKey;


public class RegistrationFragment extends Fragment {
    private UserViewModel userViewModel;
    private LinearLayout mainLayout,confirmationLayout;
    public RegistrationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);

        mainLayout = view.findViewById(R.id.mainLayout);
        confirmationLayout = view.findViewById(R.id.confirmationLayout);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        EditText fullNameEdit = view.findViewById(R.id.full_name);
        EditText phoneEdit = view.findViewById(R.id.inputPhone);
        EditText passwordEdit = view.findViewById(R.id.input_password);
        Button registerBtn = view.findViewById(R.id.registerBtn);
        TextView errorShower = view.findViewById(R.id.errorShower);

        TextView confirmationText = view.findViewById(R.id.confirmationText);
        Button nextButton = view.findViewById(R.id.gotoDashboardBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEdit.getText().toString();
                String phoneNumber = phoneEdit.getText().toString();
                String password = encryptPassword(passwordEdit.getText().toString());
                if (fullName.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your name");
                }else if(phoneNumber.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your phone number");
                }else if(password.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your password");
                }else {
                    User user =new User();
                    user.setFull_name(fullName);
                    user.setPhone(phoneNumber);
                    user.setPassword(password);
                    userViewModel.store(user);
                    Intent intent =new Intent(getActivity(), DashboardActivity.class);
                    intent.putExtra("phone",phoneNumber);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }

            }
        });
        return view;
    }


    public String encryptPassword(String password){
        MainActivity mainActivity =new MainActivity();
        String stringkey = mainActivity.getNativeKey();
        SecretKey secretKey = KeyCastor.convertIntoSecretKey(stringkey);

        return CardEncryptor.crypto(secretKey,password,false);
    }
}
