package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.DeviceRoom;
import java.util.List;

@Dao
public interface DeviceDAO {

    @Query("select * from devices where serial_number=:serialNumber")
    public LiveData<List<DeviceRoom>> index(String serialNumber);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(DeviceRoom deviceRoom);
}
