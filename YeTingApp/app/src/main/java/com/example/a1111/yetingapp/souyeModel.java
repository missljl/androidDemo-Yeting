package com.example.a1111.yetingapp;

import java.io.Serializable;

/**
 * Created by a1111 on 2017/10/27.
 */

public class souyeModel implements Serializable{


    private String aid;
    private String aName;
    private String aspeak;
    private String aicon;
private  String coverMiddle;

    public boolean isCheck;

    public souyeModel (String aid,String aName, String aspeak, String aicon,String coverMiddle) {
        this.aid = aid;
        this.aName = aName;
        this.aspeak = aspeak;
        this.aicon = aicon;
        this.coverMiddle =  coverMiddle;

    }
    public boolean getisCheck(){

        return isCheck;
    }
    public void setCheck(boolean isCheck){

        this.isCheck = isCheck;
    }

    public String getaName() {

        return aName;

    }

    public String getaspeak() {

        return aspeak;


    }

    public String getaicon() {

        return aicon;

    }
    public  String getCoverMiddle(){

        return  coverMiddle;

    }

    public String getAid(){

        return aid;
    }

    public void setaName(String aName) {

        this.aName = aName;

    }

    public void setaspeak(String aspeak) {

        this.aspeak = aspeak;


    }

    public void setaicon(String aicon) {

        this.aicon = aicon;

    }

    public  void setCoverMiddle(String coverMiddle){
        this.coverMiddle = coverMiddle;

    }

    public void setAid(String aid) {

        this.aid = aid;

    }
}
