package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.AgentAndPartner;
import com.example.foragentss.rooms.respository.AgentPartnerRepository;

import java.util.List;

public class AgentPartnerViewModel extends AndroidViewModel {
    private AgentPartnerRepository agentPartnerRepository;
    public AgentPartnerViewModel(@NonNull Application application) {
        super(application);

        agentPartnerRepository = new AgentPartnerRepository(application);
    }

    public void store(AgentAndPartner agentAndPartner){
        agentPartnerRepository.store(agentAndPartner);
    }
    public LiveData<List<AgentAndPartner>> index(){
        return agentPartnerRepository.index();
    }
}
