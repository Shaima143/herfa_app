package com.herfa.android.herfa;

import android.net.Uri;

public class Introduction {

    private String desc;
    private String video;
    private Equipements Equipments;

    public Introduction() {
    }

    public Introduction(String desc, String video, Equipements equipments) {
        this.desc = desc;
        this.video = video;
        Equipments = equipments;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Equipements getEquipments() {
        return Equipments;
    }

    public void setEquipments(Equipements equipments) {
        Equipments = equipments;
    }
}
