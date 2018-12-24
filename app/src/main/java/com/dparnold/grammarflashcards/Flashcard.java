package com.dparnold.grammarflashcards;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Flashcard {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name="packageName")
    private String packageName;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private String answer;

    @ColumnInfo(name= "ignored")
    private boolean ignored = false;

    @ColumnInfo(name= "learning")
    private boolean learning = false;

    @ColumnInfo(name = "timesstudied")
    private int timesStudied = 0;

    @ColumnInfo(name = "level")
    private int level = 0;

    @ColumnInfo(name = "tostudy")
    private boolean tostudy = true;

    @ColumnInfo(name = "score")
    private int score=0;

    @ColumnInfo(name = "learnNextTime")
    private long learnNextTime;


    public Flashcard(int ID, String title, String question, String answer) {
        this.ID = ID;
        this.title=title;
        this.question=question;
        this.answer = answer;
    }

    public Flashcard(){}; // empty constructor



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getTimesStudied() {
        return timesStudied;
    }

    public void setTimesStudied(int timesStudied) {
        this.timesStudied = timesStudied;
    }

    public boolean isTostudy() {
        return tostudy;
    }

    public void setTostudy(boolean tostudy) {
        this.tostudy = tostudy;
    }

    public int getScore() {
        return score;
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    public boolean isLearning() {
        return learning;
    }

    public void setLearning(boolean learning) {
        this.learning = learning;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getLearnNextTime() {
        return learnNextTime;
    }

    public void setLearnNextTime(long learnNextTime) {
        this.learnNextTime = learnNextTime;
    }
}