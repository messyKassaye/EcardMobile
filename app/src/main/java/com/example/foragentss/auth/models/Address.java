package com.example.foragentss.auth.models;

import java.io.Serializable;

public class Address implements Serializable {
    private int region_id;
    private int city_id;
    private String specific_name;
    private String building_name;
    private String floor_no;

    public Address() {
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getSpecific_name() {
        return specific_name;
    }

    public void setSpecific_name(String specific_name) {
        this.specific_name = specific_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }
}
