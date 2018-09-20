package com.example.edu.model;

import java.util.HashMap;
import java.util.Map;

public class StudyRoomModel {

    public Map<String,Day> day = new HashMap<>();

    public static class Day {
        public String uid;
        public String time;
        public boolean reservation;
    }
}
