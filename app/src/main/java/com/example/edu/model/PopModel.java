package com.example.edu.model;

import java.io.Serializable;

public class PopModel implements Serializable {

    public String groupName;
    public String groupShortTitle;
    public int groupLimit;
    public int groupCurrentMemebers;
    public String groupStyle;
    public String groupTopic;
    public String groupExplain;


    public PopModel() {

    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupShortTitle() {
        return groupShortTitle;
    }

    public void setGroupShortTitle(String groupShortTitle) {
        this.groupShortTitle = groupShortTitle;
    }

    public int getGroupLimit() {
        return groupLimit;
    }

    public void setGroupLimit(int groupLimit) {
        this.groupLimit = groupLimit;
    }

    public int getGroupCurrentMemebers() {
        return groupCurrentMemebers;
    }

    public void setGroupCurrentMemebers(int groupCurrentMemebers) {
        this.groupCurrentMemebers = groupCurrentMemebers;
    }

    public String getGroupStyle() {
        return groupStyle;
    }

    public void setGroupStyle(String groupStyle) {
        this.groupStyle = groupStyle;
    }

    public String getGroupTopic() {
        return groupTopic;
    }

    public void setGroupTopic(String groupTopic) {
        this.groupTopic = groupTopic;
    }

    public String getGroupExplain() {
        return groupExplain;
    }

    public void setGroupExplain(String groupExplain) {
        this.groupExplain = groupExplain;
    }
}
