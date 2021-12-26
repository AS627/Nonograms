package com.example.nonograms;

public class Game {
    private Puzzle puzzle;
    private int lives;
    private int mode;
    private boolean status;
    private int count;

    public Game(Puzzle setPuzzle) {
        puzzle = setPuzzle;
        lives = 3;
        mode = 1;
        count = 0;
        status = true;
    }
    public Puzzle getPuzzle() {
        return puzzle;
    }
    public void toggleMode() {
        mode *= -1;
    }
    public boolean getStatus() { return status; }
    public int getMode() {
        return mode;
    }
    public void setMode(int i) { mode = i; }
    public int getLives() {
        return lives;
    }
    public void tryPlace(int x, int y) {
        boolean attempt = puzzle.place(mode, x, y);
        if (!attempt) {
            lives--;
            if (lives != 0) {
                puzzle.place(-mode, x, y);
                if (mode == -1) {
                    count++;
                }
            } else {
                status = false;
            }
        } else if (mode == 1) {
            count++;
        }
    }
    public boolean testWin() {
        if (count == puzzle.getCount()) {
            status = false;
            return true;
        }
        return false;
    }
    public int getCount() {
        return count;
    }
}