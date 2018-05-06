package com.herfa.android.herfa;

public class LevelDetails {
    private int equipment_img;
    private int descInfo;
    private int vedio;

    public LevelDetails() {
    }

    public LevelDetails(int descInfo, int vedio) {
        this.descInfo = descInfo;
        this.vedio = vedio;
    }

    public int getEquipment_img() {
        return equipment_img;
    }

    public void setEquipment_img(int equipment_img) {
        this.equipment_img = equipment_img;
    }

    public int getDescInfo() {
        return descInfo;
    }

    public void setDescInfo(int descInfo) {
        this.descInfo = descInfo;
    }

    public int getVedio() {
        return vedio;
    }

    public void setVedio(int vedio) {
        this.vedio = vedio;
    }
}
