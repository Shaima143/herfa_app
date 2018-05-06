package com.herfa.android.herfa;

public class LevelsDetails {
    private int level;
    private int image;

    public LevelsDetails() {
    }

    public LevelsDetails(int level, int image) {
        this.level = level;
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
