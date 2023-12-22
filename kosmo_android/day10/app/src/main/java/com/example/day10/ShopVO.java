package com.example.day10;

public class ShopVO {
    private int pid;
    private String title;
    private String image;
    private int lprice;
    private String regdate;
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public int getLprice() {
        return lprice;
    }
    public void setLprice(int lprice) {
        this.lprice = lprice;
    }
    public String getRegdate() {
        return regdate;
    }
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }
    @Override
    public String toString() {
        return "ShopVO [pid=" + pid + ", title=" + title + ", image=" + image + ", lprice=" + lprice + ", regdate="
                + regdate + "]";
    }



}

