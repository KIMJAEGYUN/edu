package com.example.edu;

/**
 * Created by 장병호 on 2018-08-30.
 */

public class Items { //아이디와 이름을 담아둘 클래스를 정의


    int id;
    String title;
    String members;
    String limitMemb;// 받아올 데이터 정보

    public Items(int id, String title, String members, String limitMemb) {
        this.id = id;
        this.title = title;
        this.members = members;
        this.limitMemb = limitMemb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getLimitMemb() {
        return limitMemb;
    }

    public void setLimitMemb(String limitMemb) {
        this.limitMemb = limitMemb;
    }
}
