package com.example.foragentss.auth.agents.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.commons.fragments.PaymentTransactionFragment;
import com.example.foragentss.auth.commons.fragments.ShowConnectionFragment;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CardRequestActivity extends AppCompatActivity {

    private MeViewModel meViewModel;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_request);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Send new card request");
        meViewModel = ViewModelProviders.of(this).get(MeViewModel.class);

        String role = getIntent().getStringExtra("Role");
        user = (User)getIntent().getSerializableExtra("user_id");
        showCardRequest(user);


    }

    public void showCardRequest(User userId){
        Fragment fragment = new CardRequestCardSelectionFragment(userId,"card_request_fragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_request_content_frame,fragment);
        transaction.commit();
    }

    public void showPaymentTransaction(User selectedUser,ArrayList<CardRequest> cardRequests,ArrayList<Integer> sendedCardRequest){
        Fragment fragment = new PaymentTransactionFragment(selectedUser,cardRequests,sendedCardRequest);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_request_content_frame,fragment);
        transaction.commit();
    }

    public void showLogin(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.login_dialog);
        ((TextView)dialog.findViewById(R.id.loginInfo)).setText("Login is required to send card request");
        ((Button)dialog.findViewById(R.id.dialogLoginBtn))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView errorShower =dialog.findViewById(R.id.dialogErrorShower);
                        String email =((EditText)dialog.findViewById(R.id.dialogInputPhone))
                                .getText().toString();
                        String password = ((EditText)dialog.findViewById(R.id.dialogInputpassword))
                                .getText().toString();
                        if (email.equalsIgnoreCase("")){
                            errorShower.setText("Please enter your email");
                        }else if (password.equalsIgnoreCase("")){
                            errorShower.setText("Please enter your password");
                        }else {
                                login(email,password,dialog);
                        }
                    }
                });
        dialog.show();
    }


    public void login(String email,String password,Dialog dialog){
        ProgressBar loginLoading = dialog.findViewById(R.id.dialogLoginLoading);
        Button loginBtn =dialog.findViewById(R.id.dialogLoginBtn);
        TextView errorShower = dialog.findViewById(R.id.dialogErrorShower);
        loginBtn.setVisibility(View.GONE);
        loginLoading.setVisibility(View.VISIBLE);

        Retrofit retrofit= MainHttpAdapter.getAuthApi();
        LoginService loginService = retrofit.create(LoginService.class);

        Call<LoginResponse> call = loginService.login(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code()==403){
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("Incorrect email or password is used.");
                }else if(response.code()==200){

                    if(response.body().getRole().getId()==4){
                        setToken(response.body().getToken());

                        meViewModel.me().observe(CardRequestActivity.this, meResponse -> {
                            List<Device> devices = meResponse.getData().getRelations().getDevice();
                            String thisDeviceSerailNumber = Build.SERIAL;
                            if (devices.size()>0&&devices.get(0).getSerial_number().equalsIgnoreCase(thisDeviceSerailNumber)){
                                showCardRequest(user);
                                dialog.dismiss();
                            }else {
                                loginLoading.setVisibility(View.GONE);
                                loginBtn.setVisibility(View.VISIBLE);
                                errorShower.setTextColor(Color.RED);
                                errorShower.setText("This phone is not registered by you. Use your phone to send card request");
                            }
                        });

                    }

                }else {
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("Something is not Good. This is not your mistake please get support from http://ecard.com/support");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }



    public void setToken(String token){
        SharedPreferences preferences = getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }


}
