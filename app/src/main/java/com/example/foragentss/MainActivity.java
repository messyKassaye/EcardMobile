package com.example.foragentss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foragentss.home.LoginActivity;
import com.example.foragentss.home.RegistrationActivity;
import com.example.foragentss.rooms.view_model.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static {
        System.loadLibrary("keys");
    }
    private UserViewModel userViewModel;
    private Button startSellingCard,login,signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startSellingCard = findViewById(R.id.start_selling_card);
        startSellingCard.setOnClickListener(this::onClick);

        login = findViewById(R.id.loginBtn);
        login.setOnClickListener(this::onClick);

        signUpBtn = findViewById(R.id.signUP);
        signUpBtn.setOnClickListener(this::onClick);


    }



    public  native String getNativeKey();

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.start_selling_card||view.getId()==R.id.loginBtn){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
            startActivity(intent);
        }
    }
}
