package com.shaheen.higherlower;

/**
 * @author Derek Shaheen
 * @date: Apr 17, 2020
 * @version: 1.1
 */
public class ScoreTracker {
    private int score = 0;

    public ScoreTracker(){
    }

    public void increaseScore(){
        score++;
    }

    public void decreaseScore(){
        score--;
    }

    public int getScore() {
        return score;
    }

    public String printScore() {
        return score + "";
    }

    public void reset() {
        score = 0;
    }

}
