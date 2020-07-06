package org.openjfx;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * org.openjfx.Sudoku class
 */
public class Sudoku extends Observable {
    List<Cell> solution;
    private int attemps;
    private boolean canbeSolved;
    private boolean cellSolved;
    List<Cell> cellOptions = new ArrayList<>();

    public Sudoku(int begin[]) {
        solution = new ArrayList<>();
        attemps = 0;
        int index = 0;
        canbeSolved = true;
        cellSolved = false;

        //initialize cells
        int row = 0;
        int column = 0;
        BigDecimal bg2 = new BigDecimal(9);

        for (int i = 0; i < 81; i++) {
            column = i % 9;

            BigDecimal bg = new BigDecimal(index);

            BigInteger temp = (bg.divide(bg2, BigDecimal.ROUND_DOWN).toBigInteger());
            row = temp.intValue();

            Cell cell = new Cell(index, begin[index], row, column);
            solution.add(cell);
            index++;
        }
    }

    public List<Cell> getSolution() {
        return solution;
    }

    public void setSolution(List<Cell> solution) {
        this.solution = solution;
    }

    public boolean isCanbeSolved() {
        return canbeSolved;
    }

    public void setCanbeSolved(boolean canbeSolved) {
        this.canbeSolved = canbeSolved;
    }

    public boolean isCellSolved() {
        return cellSolved;
    }

    public void setCellSolved(boolean cellSolved) {
        this.cellSolved = cellSolved;
    }

    public boolean canbesolved() { /*just for now*/
        if (canbeSolved == false) {
            attemps++;
        } else {
            return true;
        }
        //solved or it cant be solved anymore
        if (attemps > 2) {
            setChanged();
            notifyObservers();
            return false;
        }
        return true;
    }

    public boolean isOptionCell(Cell c, int input) {

        //check column
        List<Cell> columnCheck = getColumnAtIndex(c.getIndex() % 9);

        //System.out.println("COLUMN: " + columnCheck.toString());

        for (int i = 0; i < columnCheck.size(); i++) {
            if (input == columnCheck.get(i).getValue()) {
                System.out.println(input + " is not an option according to column");
                return false;
            }
        }

        List<Cell> rowCheck = getRowAtIndex(c.getRow());
        //System.out.println("ROW " + rowCheck.toString());

        for (int i = 0; i < rowCheck.size(); i++) {
            if (input == rowCheck.get(i).getValue()) {
                System.out.println(input + " is not an option according to row");
                return false;
            }
        }

        /*check block*/
        List<Cell> blockCheck = getCelltoBlock(solution.get(c.getIndex()));
        for (int i = 0; i < blockCheck.size(); i++) {
            if (input == blockCheck.get(i).getValue()) {
                System.out.println(input + " is not an option according to block");
                return false;
            }
        }

        return true;
    }

    public List<Cell> getRowAtIndex(int index) {
        List<Cell> temp = new ArrayList<>();
        index = index * 9;
        for (int i = index; i < index + 9; i++) {
            temp.add(solution.get(i));
        }
        return temp;
    }

    private List<Cell> getHor(Cell c) {
        List<Cell> result = new ArrayList<>();

        int mod = c.getColumn() % 3;

        switch (mod) {
            case 0:
                result.add(solution.get(c.getIndex()));
                result.add(solution.get(c.getIndex() + 1));
                result.add(solution.get(c.getIndex() + 2));
                break;
            case 1:
                result.add(solution.get(c.getIndex() - 1));
                result.add(solution.get(c.getIndex()));
                result.add(solution.get(c.getIndex() + 1));
                break;
            case 2:
                result.add(solution.get(c.getIndex() - 2));
                result.add(solution.get(c.getIndex() - 1));
                result.add(solution.get(c.getIndex()));
                break;
            default:
                System.out.println("an error has occurred in getHor!");
                break;
        }

        return result;
    }

    public List<Cell> getCelltoBlock(Cell c) {

        List<Cell> block = new ArrayList<>();
        //row
        int mod = c.getRow() % 3;

        switch (mod) {
            case 0:
                block.addAll(getHor(c));
                block.addAll(getHor(solution.get(c.getIndex() + 9)));
                block.addAll(getHor(solution.get(c.getIndex() + 18)));
                break;
            case 1:
                block.addAll(getHor(solution.get(c.getIndex() - 9)));
                block.addAll(getHor(c));
                block.addAll(getHor(solution.get(c.getIndex() + 9)));
                break;
            case 2:
                block.addAll(getHor(solution.get(c.getIndex() - 18)));
                block.addAll(getHor(solution.get(c.getIndex() - 9)));
                block.addAll(getHor(c));
                break;
            default:
                System.out.println("There has a problem occurred!");
                break;
        }
        return block;
    }

    public List<Cell> getColumnAtIndex(int index) {
        List<Cell> tempList = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            if (i % 9 == 0) {
                Cell temp = solution.get(i + index);
                tempList.add(temp);
            }
        }

        return tempList;
    }

//    public boolean isCorrect() {
//        return false;
//    }

    /**
     * Get the cell at a specific index
     * @param index integer of the index
     * @return The cell at the index
     */
    public Cell getCell(int index) {
        if(index < 0 || index > 81){
            throw new ArrayIndexOutOfBoundsException();
        }
        return solution.get(index);
    }

    /**
     * Set cell at a specific index
     * @param c The cell that you want to set
     * @param index integer of the index
     */
    public void setCell(Cell c, int index){
        solution.set(index, c);
        setChanged();
        notifyObservers();
    }

    public String toString() {
        String result = "";

        for (int i = 0; i < solution.size(); i++) {
            if (i % 3 == 0 && !(result.equals(""))) {
                if (i % 9 == 0) {
                    result += "\n";
                    if (i % 27 == 0) {
                        result += "\n";
                    }
                } else {
                    result += "\t";
                }
            }

            result += solution.get(i).getValue() + "\t";
        }
        return result;
    }

    public List<Cell> getList(){
        return solution;
    }

    public void setArray(List<Cell> newArray){
        //initialize cells

        this.solution = newArray;

        setChanged();
        notifyObservers();
    }

}
