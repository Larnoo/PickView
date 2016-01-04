package com.larno.pickview.demo.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.larno.pickview.demo.App;
import com.larno.pickview.demo.bean.City;
import com.larno.pickview.demo.db.DbManager;

import java.util.ArrayList;

/**
 * Created by sks on 2016/1/4.
 */
public class CityDao {

    private static final String dbfile = "default.db";

    public ArrayList<City> getProvinces(){
        SQLiteDatabase database = DbManager.getDatabase(App.app);
        String[] columns = {"AREA_CODE", "AREA_NAME", "TYPE", "PARENT_ID"};
        String[] where = {"1"};
        Cursor cursor = database.query("area", columns, "TYPE=?", where, null, null, null);
        ArrayList<City> beans = new ArrayList<City>();
        while(cursor.moveToNext()){
            City bean = new City();
            bean.area_code = cursor.getInt(cursor.getColumnIndex("AREA_CODE"));
            bean.area_name = cursor.getString(cursor.getColumnIndex("AREA_NAME"));
            bean.type = cursor.getInt(cursor.getColumnIndex("TYPE"));
            bean.parent_id = cursor.getInt(cursor.getColumnIndex("PARENT_ID"));
            beans.add(bean);
        }
        cursor.close();
        database.close();
        return beans;
    }

    public ArrayList<City> getCities(int parentId){
        SQLiteDatabase database = DbManager.getDatabase(App.app);
        String[] columns = {"AREA_CODE", "AREA_NAME", "TYPE", "PARENT_ID"};
        String[] where = {parentId+""};
        Cursor cursor = database.query("area", columns, "PARENT_ID=?", where, null, null, null);
        ArrayList<City> beans = new ArrayList<City>();
        while(cursor.moveToNext()){
            City bean = new City();
            bean.area_code = cursor.getInt(cursor.getColumnIndex("AREA_CODE"));
            bean.area_name = cursor.getString(cursor.getColumnIndex("AREA_NAME"));
            bean.type = cursor.getInt(cursor.getColumnIndex("TYPE"));
            bean.parent_id = cursor.getInt(cursor.getColumnIndex("PARENT_ID"));
            beans.add(bean);
        }
        cursor.close();
        database.close();
        return beans;
    }

    public ArrayList<City> getCounties(int parentId){
        SQLiteDatabase database = DbManager.getDatabase(App.app);
        String[] columns = {"AREA_CODE", "AREA_NAME", "TYPE", "PARENT_ID"};
        String[] where = {parentId+""};
        Cursor cursor = database.query("area", columns, "PARENT_ID=?", where, null, null, null);
        ArrayList<City> beans = new ArrayList<City>();
        while(cursor.moveToNext()){
            City bean = new City();
            bean.area_code = cursor.getInt(cursor.getColumnIndex("AREA_CODE"));
            bean.area_name = cursor.getString(cursor.getColumnIndex("AREA_NAME"));
            bean.type = cursor.getInt(cursor.getColumnIndex("TYPE"));
            bean.parent_id = cursor.getInt(cursor.getColumnIndex("PARENT_ID"));
            beans.add(bean);
        }
        cursor.close();
        database.close();
        return beans;
    }
}
