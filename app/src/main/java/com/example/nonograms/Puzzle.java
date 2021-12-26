package com.example.nonograms;

import java.util.ArrayList;

public class Puzzle {
    private int[][] solution;
    private int[][] board;
    private int count;
    public Puzzle(int id) {
        if (id == 1) {
            solution = new int[15][15];
            board = new int[15][15];
            // Generating chance of a filled cell between 0.5 and 0.7.
            double chance = (4 + (int) Math.ceil(3 * Math.random())) / 10.0;
            System.out.println("Chance: " + chance);
            for (int x = 0; x < solution.length; x++) {
                for (int y = 0; y < solution[0].length; y++) {
                    if (Math.random() <= chance) {
                        solution[x][y] = 1;
                        count++;
                    } else {
                        solution[x][y] = -1;
                    }
                    board[x][y] = 0;
                }
            }
        }
    }
    public int[][] getSolution() {
        return solution;
    }
    public int[][] getBoard() {
        return board;
    }
    public int getCount() { return count; }
    public boolean place(int value, int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board.length) {
            return false;
        }
        board[x][y] = value;
        if (board[x][y] == solution[x][y]) {
            return true;
        } else {
            //board[x][y] = -1 * value;
            return false;
        }
    }
    public int getValue(int x, int y) {
        return board[x][y];
    }
    public boolean isComplete() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    public ArrayList<Integer> getRowList(int y) {
        ArrayList<Integer> list = new ArrayList<>();
        int streak = 0;
        for (int x = 0; x < solution.length; x++) {
            if (solution[x][y] == 1) {
                streak++;
            } else if (streak != 0) {
                list.add(streak);
                streak = 0;
            }
        }
        if (streak != 0) {
            list.add(streak);
        }
        if (list.size() == 0) {
            list.add(0);
        }
        return list;
    }
    public ArrayList<Integer> getColList(int x) {
        ArrayList<Integer> list = new ArrayList<>();
        int streak = 0;
        for (int y = 0; y < solution.length; y++) {
            if (solution[x][y] == 1) {
                streak++;
            } else if (streak != 0) {
                list.add(streak);
                streak = 0;
            }
        }
        if (streak != 0) {
            list.add(streak);
        }
        if (list.size() == 0) {
            list.add(0);
        }
        return list;
    }
}
