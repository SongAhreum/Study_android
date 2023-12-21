package com.example.myapplication;

public class UserVO {
    private String uid;
    private String upass;
    private String uname;
    private String address1;
    private String address2;
    private String phone;
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUpass() {
        return upass;
    }
    public void setUpass(String upass) {
        this.upass = upass;
    }
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String adrress1) {
        this.address1 = adrress1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Override
    public String toString() {
        return "UserVO [uid=" + uid + ", upass=" + upass + ", uname=" + uname + ", address1=" + address1 + ", address2="
                + address2 + ", phone=" + phone + "]";
    }
}
