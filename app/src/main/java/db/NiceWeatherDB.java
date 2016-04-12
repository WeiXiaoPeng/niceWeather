package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.City;
import model.County;
import model.Province;

/**
 * Created by Administrator on 2016/4/12.
 */
public class NiceWeatherDB {

    public static  final String DB_NAME = "nice_weather";
    public static final int VERSION = 1;
    private static NiceWeatherDB niceWeatherDB;
    private SQLiteDatabase db;
    private NiceWeatherDB(Context context){
        NiceWeatherOpenHelper dbHelper = new NiceWeatherOpenHelper(context,
                DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
    public synchronized static  NiceWeatherDB getInstance(Context context){
        if(niceWeatherDB == null){
            niceWeatherDB = new NiceWeatherDB(context);
        }
        return niceWeatherDB;
    }
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
              Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getcityName());
            values.put("city_code",city.getcityCode());
            values.put("province_id",city.getprovinceId());
            db.insert("City",null,values);
        }
    }
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null
                ,"province_id = ?"
                ,new String[]{String.valueOf(provinceId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setcityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setcityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setprovinceId(provinceId);
                list.add(city);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    public void saveCounty(County county){
        if(county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getcountyName());
            values.put("county_code",county.getcountyCode());
            values.put("city_id",county.getcityId());
            db.insert("county",null,values);
        }
    }
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null
                ,"city_id = ?"
                ,new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setcountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setcountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setcityId(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
}
