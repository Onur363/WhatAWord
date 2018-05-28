package com.example.onur.whataword;

/**
 * Created by Onur on 10.11.2017.
 */

//WORDS sınıf ile burada kullanıcının yazdığı verileri words tipinden listelemek için veriyi gösterme ve veriyi kaydetme işlemi için kullanıyoruz


public class Words {

    private String en,tr,cen,ctr;
    private int id;


    public Words(String en){
        this.en=en;
    }

    public Words(int id, String en,String tr, String cen,String ctr){

        this.id=id; this.en=en; this.tr=tr; this.cen=cen; this.ctr=ctr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /****************************************/

    public void setEn(String en) {
        this.en = en;
    }

    public String getEn() {
        return en;
    }
    /*******************************************************/

    public void setTr(String tr) {
        this.tr = tr;
    }

    public String getTr() {
        return tr;
    }



    /**********************************************************/

    public void setCen(String cen) {
        this.cen = cen;
    }

    public String getCen() {
        return cen;
    }

    /***********************************************************/

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr;
    }
    /********************************************************/
}
