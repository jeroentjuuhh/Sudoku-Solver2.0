package org.openjfx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeroe on 15-2-2017.
 */
public class Cell {

    int index;
    int value;
    int row;
    int column;
    List<Integer> options;

    public Cell(int index, int value, int row, int column){
        this.index = index;
        this.value = value;
        this.row = row;
        this.column = column;
        options = new ArrayList<>();
        for(int i = 1; i <=9; i++) {
            options.add(i);
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getIndex() {
        return index;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
