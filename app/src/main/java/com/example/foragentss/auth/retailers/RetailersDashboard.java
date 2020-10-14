package com.example.foragentss.auth.retailers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.FloatingButtonActivity;
import com.example.foragentss.auth.retailers.adapter.TabAdapter;
import com.example.foragentss.auth.retailers.fragments.AgentsFragment;
import com.example.foragentss.auth.retailers.fragments.DashboardFragment;
import com.example.foragentss.auth.retailers.fragments.DownloadsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class RetailersDashboard extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailers_dashboard);
        getSupportActionBar().setTitle("ECard Retailers");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardFragment(), "Dashboard");
        adapter.addFragment(new AgentsFragment(),"My agents");
        adapter.addFragment(new DownloadsFragment(), "Downloads");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FloatingButtonActivity.class);
                startActivity(intent);
            }
        });

    }
}
