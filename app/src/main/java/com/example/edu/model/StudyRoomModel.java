package com.example.edu.model;

import java.util.HashMap;
import java.util.Map;

public class StudyRoomModel {

    public Map<String,StudyRoomModel.Day> day = new HashMap<>();
//    public Map<String,StudyRoomModel.R101.Tuesday> tuesdayMap = new HashMap<>();

    public static class Day {
        public String uid;
        public int time;
        public boolean reservation;
    }
}
