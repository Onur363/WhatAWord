package com.example.onur.whataword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Onur on 10.11.2017.
 */

public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE="Kelimeler.db";
    private static final int VERSION=1;


    public DataBase(Context context){
        super(context,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL="CREATE TABLE "+WordContent.WordEntity.Table+"("+WordContent.WordEntity.ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                WordContent.WordEntity.EN+" TEXT,"+
                WordContent.WordEntity.TR+" TEXT,"+  WordContent.WordEntity.CEN+" TEXT,"+ WordContent.WordEntity.CTR+" TEXT)";
        db.execSQL(SQL);
    }

    public void KelimeEkle(String EN,String TR,String CEN,String CTR){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(WordContent.WordEntity.EN,EN);
        contentValues.put(WordContent.WordEntity.TR,TR);
        contentValues.put(WordContent.WordEntity.CEN,CEN);
        contentValues.put(WordContent.WordEntity.CTR,CTR);

        db.insert(WordContent.WordEntity.Table,null,contentValues);
        db.close();
    }

    public HashMap<String,String> kelimedetay(int id){

        HashMap<String,String> maps=new HashMap<String, String>();
        String sql="SELECT * FROM "+WordContent.WordEntity.Table+" WHERE "+ WordContent.WordEntity.ID+"="+id;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql,null);

        cursor.moveToFirst();
        if(cursor.getCount()>0){
            maps.put(WordContent.WordEntity.EN,cursor.getString(1));
            maps.put(WordContent.WordEntity.TR,cursor.getString(2));
            maps.put(WordContent.WordEntity.CEN,cursor.getString(3));
            maps.put(WordContent.WordEntity.CTR,cursor.getString(4));
        }

        return maps;

    }

    public ArrayList<HashMap<String,String>> kelimler(){

        ArrayList<HashMap<String,String>> words=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db=getReadableDatabase();
        String sorgu="SELECT * FROM "+ WordContent.WordEntity.Table+" ORDER BY "+ WordContent.WordEntity.EN+" ASC";

        Cursor cursor=db.rawQuery(sorgu,null);

        if(cursor.moveToFirst())
        {
            do{
                HashMap<String,String> map=new HashMap<String, String>();
                for(int i=0;i<cursor.getColumnCount();i++){
                    map.put(cursor.getColumnName(i),cursor.getString(i));
                }
                words.add(map);
            }while (cursor.moveToNext());

        }

        return words;

    }
    public void Update(String en,String Tr,String Cen, String Ctr,int id){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(WordContent.WordEntity.EN,en);
        contentValues.put(WordContent.WordEntity.TR,Tr);
        contentValues.put(WordContent.WordEntity.CEN,Cen);
        contentValues.put(WordContent.WordEntity.CTR,Ctr);

        db.update(WordContent.WordEntity.Table,contentValues, WordContent.WordEntity.ID+"=?",new String[]{String.valueOf(id)});

    }
    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(WordContent.WordEntity.Table, WordContent.WordEntity.ID+"=?",new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<HashMap<String,String>> sorting(String en){
        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String, String>>();
        SQLiteDatabase db=getReadableDatabase();
        String sorgu="SELECT * FROM "+ WordContent.WordEntity.Table +" WHERE "+ WordContent.WordEntity.EN+" like '"+en+"%'";

        Cursor cursor=db.rawQuery(sorgu,null);

        if(cursor.moveToFirst())
        {
            do{
                HashMap<String,String> map=new HashMap<String, String>();
                for(int i=0;i<cursor.getColumnCount();i++){
                    map.put(cursor.getColumnName(i),cursor.getString(i));
                }
                list.add(map);
            }while (cursor.moveToNext());

        }

        return list;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
