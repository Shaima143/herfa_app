package com.herfa.android.herfa;

public class CraftsDetails {
    private int craftsImageOne;
    private int craftsImageTwo;
    private int craftNameOne;
    private int craftNameTwo;

    public CraftsDetails() {
    }

    public CraftsDetails(int craftsImageOne, int craftsImageTwo, int craftNameOne, int craftNameTwo) {
        this.craftsImageOne = craftsImageOne;
        this.craftsImageTwo = craftsImageTwo;
        this.craftNameOne = craftNameOne;
        this.craftNameTwo = craftNameTwo;
    }

    public int getCraftsImageOne() {
        return craftsImageOne;
    }

    public void setCraftsImageOne(int craftsImageOne) {
        this.craftsImageOne = craftsImageOne;
    }

    public int getCraftsImageTwo() {
        return craftsImageTwo;
    }

    public void setCraftsImageTwo(int craftsImageTwo) {
        this.craftsImageTwo = craftsImageTwo;
    }

    public int getCraftNameOne() {
        return craftNameOne;
    }

    public void setCraftNameOne(int craftNameOne) {
        this.craftNameOne = craftNameOne;
    }

    public int getCraftNameTwo() {
        return craftNameTwo;
    }

    public void setCraftNameTwo(int craftNameTwo) {
        this.craftNameTwo = craftNameTwo;
    }
}
