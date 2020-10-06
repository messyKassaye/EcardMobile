package com.example.foragentss.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegistrationActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private LinearLayout mainLayout,confirmationLayout;
    private ProgressBar loading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mainLayout = findViewById(R.id.mainLayout);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        EditText fullNameEdit = findViewById(R.id.full_name);
        EditText phoneEdit = findViewById(R.id.inputPhone);
        EditText emailEdit = findViewById(R.id.email);
        EditText passwordEdit = findViewById(R.id.input_password);
        Button registerBtn = findViewById(R.id.registerBtn);
        TextView errorShower = findViewById(R.id.errorShower);
        RadioGroup radioGroup = findViewById(R.id.roleSelection);
        loading = findViewById(R.id.signUPLoading);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEdit.getText().toString();
                String phoneNumber = phoneEdit.getText().toString();
                String emailAddress = emailEdit.getText().toString();
                int selectedRoleId = radioGroup.getCheckedRadioButtonId();
                String password = passwordEdit.getText().toString();

                if (fullName.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your name");
                }else if(phoneNumber.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your phone number");
                }else if(emailAddress.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your email address");
                }else if(selectedRoleId<=0){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please select your registration type. are you our agent or retailer?");
                }else if(password.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your password");
                } else if(!fullName.contains(" ")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("You haven't enter your full name. E.g Mahder Girma");
                } else {
                    registerBtn.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Registering...");
                    errorShower.setTextColor(Color.GREEN);
                    int role_id =0;
                    if(selectedRoleId==R.id.agent){
                        role_id = 3;
                    }else if (selectedRoleId==R.id.retailer){
                        role_id = 4;
                    }
                    String first_name = fullName.substring(0,fullName.lastIndexOf(" ")+1);
                    String last_name = fullName.substring(fullName.lastIndexOf(" ")+1,fullName.length());
                    User user =new User(first_name,last_name,emailAddress,phoneNumber,password,role_id);

                    Retrofit retrofit= MainHttpAdapter.getAuthApi();
                    LoginService loginService = retrofit.create(LoginService.class);
                    loginService.signUP(user)
                            .enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    if (response.isSuccessful()){
                                        setToken(response.body().getToken());
                                        int loginRoleId = response.body().getRole().getId();
                                        if (loginRoleId==3){
                                            Intent intent = new Intent(getApplicationContext(), AgentsDashboard.class);
                                            startActivity(intent);
                                        }else if (loginRoleId==4){

                                        }
                                    }else {

                                        if (response.code()==409){
                                            errorShower.setText("Some is register by your email or phone number. please change your email or phone number");
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });

                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


    public void setToken(String token){
        SharedPreferences preferences = getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
}
