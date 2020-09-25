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
import android.widget.TextView;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.dashboard.DashboardActivity;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import javax.crypto.SecretKey;


public class LoginFragment extends Fragment {

    private EditText phone,passowrd;
    private TextView errorShower;
    private Button loginBtn;
    private UserViewModel userViewModel;
    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        phone = view.findViewById(R.id.inputPhone);
        passowrd = view.findViewById(R.id.input_password);
        errorShower = view.findViewById(R.id.errorShower);
        loginBtn = view.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneText = phone.getText().toString();
                String passwordText = passowrd.getText().toString();
                if (phoneText.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your phone");
                }else if (passwordText.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your password");
                }else {
                    login(phoneText,encryptPassword(passwordText));
                }
            }
        });
        return view;
    }

    public void login(String phoneNumber,String password){

        userViewModel.showUser(phoneNumber,password).observe(getActivity(),users -> {
            if (users.size()<=0){
                errorShower.setVisibility(View.VISIBLE);
                errorShower.setText("Incorrect phone or password is used. Please try again");
            }else {
                Intent intent =new Intent(getActivity(), DashboardActivity.class);
                intent.putExtra("phone",phoneNumber);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }

    public String encryptPassword(String password){
        MainActivity mainActivity =new MainActivity();
        String stringkey = mainActivity.getNativeKey();
        SecretKey secretKey = KeyCastor.convertIntoSecretKey(stringkey);

        return CardEncryptor.crypto(secretKey,password,false);
    }
}
