package com.example.edu.model;

import java.util.HashMap;
import java.util.Map;

public class BoardModel {
    public String uid;
    public String groupName;
    public String groupShortTitle;
    public int groupLimit;
    public int groupCurrentMemebers;
    public String groupStyle;
    public String groupTopic;
    public String groupExplain;
    public Map<String, Boolean> favorites = new HashMap<>();
}
