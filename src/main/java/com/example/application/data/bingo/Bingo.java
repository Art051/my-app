package com.example.application.data.bingo;

import com.sun.xml.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;

public class Bingo {



    private int learningNum;
    private int minMulti;
    private int maxMulti;

    private List<String[]> timesTable = new ArrayList<>();

    public Bingo() {
    }

    public Bingo(int learningNum, int minMulti, int maxMulti) {
        this.learningNum = learningNum;
        this.minMulti = minMulti;
        this.maxMulti = maxMulti;
        setTimesTable(timesTable);
    }

    public int getLearningNum() {
        return learningNum;
    }

    public void setLearningNum(int learningNum) {
        this.learningNum = learningNum;
    }

    public int getMinMulti() {
        return minMulti;
    }

    public void setMinMulti(int minMulti) {
        this.minMulti = minMulti;
    }

    public int getMaxMulti() {
        return maxMulti;
    }

    public void setMaxMulti(int maxMulti) {
        this.maxMulti = maxMulti;
    }

    public List<String[]> getTimesTable() {
        return timesTable;
    }

    public void setTimesTable(List<String[]> timesTable) {
        int currentMulti = minMulti;
        for(int i = 0; i < maxMulti - minMulti; i++){
            timesTable.add(new String[]{learningNum + " X " + currentMulti, String.valueOf(learningNum * currentMulti)});
        }
        this.timesTable = timesTable;
    }
}
