package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.AgentPartnerDAO;
import com.example.foragentss.rooms.entity.AgentAndPartner;

import java.util.List;

public class AgentPartnerRepository {
    private AgentPartnerDAO agentPartnerDAO;

    public AgentPartnerRepository(Application application){

        agentPartnerDAO = ApplicationRoomDatabase.getDatabase(application).agentPartnerDAO();

    }

    public void store(AgentAndPartner agentAndPartner){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            agentPartnerDAO.store(agentAndPartner);
        });
    }
    public LiveData<List<AgentAndPartner>> index(){
        return  agentPartnerDAO.index();
    }
}
