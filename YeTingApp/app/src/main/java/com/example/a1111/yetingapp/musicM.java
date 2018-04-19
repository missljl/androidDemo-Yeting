package com.example.a1111.yetingapp;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by a1111 on 2017/11/14.
 */
@Entity
public class musicM {
    @Id
    private  String id;

    private String name;
    private String imagename;
    private String musicplayerurl;
    public String getMusicplayerurl() {
        return this.musicplayerurl;
    }
    public void setMusicplayerurl(String musicplayerurl) {
        this.musicplayerurl = musicplayerurl;
    }
    public String getImagename() {
        return this.imagename;
    }
    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Generated(hash = 1971353396)
    public musicM(String id, String name, String imagename, String musicplayerurl) {
        this.id = id;
        this.name = name;
        this.imagename = imagename;
        this.musicplayerurl = musicplayerurl;
    }
    @Generated(hash = 362574140)
    public musicM() {
    }

}
