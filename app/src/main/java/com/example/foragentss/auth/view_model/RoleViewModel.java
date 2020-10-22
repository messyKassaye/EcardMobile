package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.Role;
import com.example.foragentss.auth.repository.RoleRepository;
import com.example.foragentss.auth.response.RoleResponse;

import retrofit2.Call;

public class RoleViewModel extends AndroidViewModel {
    private RoleRepository roleRepository;
    public RoleViewModel(@NonNull Application application) {
        super(application);

        roleRepository = new RoleRepository();
    }

    public Call<RoleResponse> show(int id){
        return roleRepository.show(id);
    }
}
