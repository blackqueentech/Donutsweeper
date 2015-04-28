package com.example.budapest.donutsweeper;

/**
 * Created by Della on 28/02/2015.
 */
public class Square {

    public boolean isSprout;
    public boolean isDonut;
    public boolean isHidden;
    public short sproutCount;

    public Square() {
        isSprout = false;
        isDonut = false;
        isHidden = true;
        sproutCount = 0;
    }
}
