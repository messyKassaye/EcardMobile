package com.example.foragentss.auth.agents;

import android.content.Intent;
import android.os.Bundle;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.FloatingButtonActivity;
import com.example.foragentss.auth.agents.fragments.AllCardRequestFragment;
import com.example.foragentss.auth.agents.ui.home.HomeFragment;
import com.example.foragentss.auth.commons.fragments.MyCardRequestFragment;
import com.example.foragentss.auth.commons.fragments.MyCardsFragment;
import com.example.foragentss.auth.commons.fragments.NotificationFragment;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.home.fragments.AddressFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentsDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MeViewModel viewModel;
    private TextView fullName,email;
    private ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agents_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FloatingButtonActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        View headerView = navigationView.getHeaderView(0);
        fullName = (TextView) headerView.findViewById(R.id.fullName);
        email = (TextView)headerView.findViewById(R.id.email);
        profileImage = headerView.findViewById(R.id.profileImage);
        viewModel = ViewModelProviders.of(this).get(MeViewModel.class);
        showHome();
        //set view model value
        this.setView();
    }

    public void  showHome(){
        getSupportActionBar().setTitle("Agents home");
        Fragment fragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    public void showAddress(String info){
        getSupportActionBar().setTitle("Address setting");
        Fragment fragment = new AddressFragment(3,info,"dashboard");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    public void setView(){
        viewModel.me().observe(this,meResponse->{
            if(meResponse!=null){
                fullName.setText(meResponse.getData().getAttribute().getFirst_name()+" "
                        +meResponse.getData().getAttribute().getLast_name());
                email.setText(meResponse.getData().getAttribute().getEmail());
                if (meResponse.getData().getAttribute().getAvator().equals("letter")){
                    profileImage.setImageResource(R.drawable.ic_person_black_24dp);
                }
            }
        });
    }


    public  void showCardRequest(){
        Fragment fragment = new AllCardRequestFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.agents_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_home){
            this.showHome();
        }else if(id==R.id.action_notification){
            this.showNotifications();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id==R.id.nav_home){
            fragment = new HomeFragment();
            getSupportActionBar().setTitle("Agents home");
        }else if (id==R.id.card_request){
            fragment = new MyCardRequestFragment();
            getSupportActionBar().setTitle("My card requests");
        }else if (id==R.id.nav_my_cards){
            fragment = new MyCardsFragment();
            getSupportActionBar().setTitle("My cards");
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void showNotifications(){
        Fragment fragment = new NotificationFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
