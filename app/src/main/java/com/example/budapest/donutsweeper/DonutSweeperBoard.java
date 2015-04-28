package com.example.budapest.donutsweeper;

import android.util.Log;

import java.util.Random;

/**
 * Created by Della on 25/02/2015.
 */
public class DonutSweeperBoard {

    private static DonutSweeperBoard instance = null;

    public boolean isFlagging;

    private Square[][] model;
    private int MIN_X = 0;
    private int MIN_Y = 0;
    private int MAX_X = 4;
    private int MAX_Y = 4;

    private DonutSweeperBoard() {
        model = new Square[5][5];
        resetModel();
    }

    public static DonutSweeperBoard getInstance() {
        if (instance == null) {
            instance = new DonutSweeperBoard();
            Log.d("tag_", "creates new instance");
        }
        return instance;
    }

    public void showNeighbors(int thisPosX, int thisPosY) {
        int startPosX = (thisPosX - 1 < MIN_X) ? thisPosX : thisPosX-1;
        int startPosY = (thisPosY - 1 < MIN_Y) ? thisPosY : thisPosY-1;
        int endPosX =   (thisPosX + 1 > MAX_X) ? thisPosX : thisPosX+1;
        int endPosY =   (thisPosY + 1 > MAX_Y) ? thisPosY : thisPosY+1;

        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                // All the neighbors will be grid[rowNum][colNum]
                if (findSprouts(rowNum, colNum) == 0) {
                    DonutSweeperBoard.getInstance().getFieldContent(rowNum, colNum).isHidden = false;
                    showNeighbors(rowNum, colNum);
                }
            }
        }
    }


    public int findSprouts(int thisPosX, int thisPosY) {
        int counter = 0;
        int startPosX = (thisPosX - 1 < MIN_X) ? thisPosX : thisPosX-1;
        int startPosY = (thisPosY - 1 < MIN_Y) ? thisPosY : thisPosY-1;
        int endPosX =   (thisPosX + 1 > MAX_X) ? thisPosX : thisPosX+1;
        int endPosY =   (thisPosY + 1 > MAX_Y) ? thisPosY : thisPosY+1;

        for (int rowNum=startPosX; rowNum<=endPosX; rowNum++) {
            for (int colNum=startPosY; colNum<=endPosY; colNum++) {
                // All the neighbors will be grid[rowNum][colNum]
                if (DonutSweeperBoard.getInstance().getFieldContent(rowNum, colNum).isSprout == true) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public short totalFlags() {
        short counter = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                if (DonutSweeperBoard.getInstance().getFieldContent(i, j).isDonut == true) {
                    counter++;
                }
            }
        return counter;
    }
    public void uncoverAll() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                DonutSweeperBoard.getInstance().getFieldContent(i, j).isHidden = false;
            }
    }
    public short totalSprouts() {
        short counter = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                if (DonutSweeperBoard.getInstance().getFieldContent(i, j).isSprout == true) {
                    counter++;
                }
            }
        return counter;
    }

    public Square getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void resetModel() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j] = new Square();
            }
        }
        plantSprouts();
        isFlagging = false;
    }
    public void plantSprouts() {
        // number of bombs that is planned to be generated
        int sproutsNum = 3;
        int sproutsAdded = 0;
        Random random;
        random = new Random(System.currentTimeMillis());

        for (int k = 0; k < sproutsNum; k++) {
            // generate random x and y coordinate
            int x = random.nextInt(5);
            int y = random.nextInt(5);

            if (!model[x][y].isSprout) {
                model[x][y].isSprout = true;
                sproutsAdded++;
            }
        }
    }

}
