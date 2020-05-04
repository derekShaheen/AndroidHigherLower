package com.shaheen.higherlower;

/**
 * @author Derek Shaheen
 * @date: Apr 17, 2020
 * @version: 1.1
 */
public class HigherLowerModel {
    ScoreTracker score = new ScoreTracker();

    private int random = -1;
    private int randomPrevious = -1;

    public HigherLowerModel() {
        gen();
    }

    public void gen() {
        randomPrevious = random;
        random = (int) (Math.random() * 100);
    }

    public int get() {
        return random;
    }

    public String getString() {
        return random + "";
    }

    public int getPrevious() {
        return randomPrevious;
    }

    public int checkChoice(int choice) {
        if ((get() > getPrevious()) && (choice == 1)) { // higher choice
            score.increaseScore();
            return 1; // correct
        } else if ((get() < getPrevious()) && (choice == 0)) { // lower choice
            score.increaseScore();
            return 1; // correct
        } else { // incorrect choice
            score.decreaseScore();
            return -1; // incorrect
        }
    }

    public String getScore() {
        return score.printScore();
    }

    public void reset() {
        score.reset();
    }

}
