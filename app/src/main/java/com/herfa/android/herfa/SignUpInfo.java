package com.herfa.android.herfa;

/**
 * Created by Shaima on 26/04/2018.
 */

public class SignUpInfo {
    private String uid;
    private String username;
    private String password;
    private String email;
    private String userImageURL;
    private Boolean isCraftmanUser;
    private Boolean isBuyerUser;
    private Boolean isDeliveryUser;

    public SignUpInfo() {
    }

    public SignUpInfo(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SignUpInfo(String username, String password, String email, String userimageURL, Boolean isCraftmanUser, Boolean isBuyerUser, Boolean isDeliveryUser) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userImageURL = userimageURL;
        this.isCraftmanUser = isCraftmanUser;
        this.isBuyerUser = isBuyerUser;
        this.isDeliveryUser = isDeliveryUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public Boolean getCraftmanUser() {
        return isCraftmanUser;
    }

    public void setCraftmanUser(Boolean craftmanUser) {
        isCraftmanUser = craftmanUser;
    }

    public Boolean getBuyerUser() {
        return isBuyerUser;
    }

    public void setBuyerUser(Boolean buyerUser) {
        isBuyerUser = buyerUser;
    }

    public Boolean getDeliveryUser() {
        return isDeliveryUser;
    }

    public void setDeliveryUser(Boolean deliveryUser) {
        isDeliveryUser = deliveryUser;
    }
}
