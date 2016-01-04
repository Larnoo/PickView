package com.larno.pickview.demo.bean;

/**
 * Created by sks on 2016/1/4.
 */
public class City {
    public int type;
    public int parent_id;
    public int area_code;
    public String area_name;

    @Override
    public String toString() {
       return area_name;
    }
}
