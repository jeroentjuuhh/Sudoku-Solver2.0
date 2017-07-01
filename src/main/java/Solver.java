import java.util.List;

/**
 * Static class solve a specific sudoku
 */
public class Solver {

    public static void solve(Sudoku sudoku) {

        long startTime = System.currentTimeMillis();

        while (sudoku.isCanbeSolved()) {
            sudoku.setCanbeSolved(false);

            for (int index = 0; index < 81; index++) {
                //for(int index = 39; index < 40; index++){
                /*als er nog geen oplossing is*/
                if (sudoku.getSolution().get(index).getValue() == 0) {
                    System.out.println("--------------------------------------------------");
                    System.out.println("this index has no solution yet: " + index);

                    solveCell(sudoku, index);

                    if(!sudoku.isCellSolved()){
                        solveCellByBlock(sudoku, index);
                    }
                }
            }
        }

        sudoku.setCanbeSolved(true);

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
        double elapsed = (endTime - startTime)/1000;
        System.out.println("Time it took to solve this sudoku is: " + elapsed + "s");
    }

    private static void solveCellByBlock(Sudoku sudoku, int index){
        //this are all options where a specific value can come

        List<Cell> block = sudoku.getCelltoBlock(sudoku.getSolution().get(index));

        //check all numbers
        for(int i = 1; i <= 9; i++) {

            sudoku.cellOptions.clear();

            //check all options where this input can come
            for (Cell c : block) {

                if(c.getValue() == 0){
                    System.out.println("\ntrying number:" + i + " at " + c.getIndex() + " ------------------------------- ");
                    if(sudoku.isOptionCell(sudoku.solution.get(c.getIndex()), i)){

                        System.out.println("this is a option for " + i + "at index " + c.getIndex());
                        Cell temp = new Cell(c.getIndex(), i, c.getRow(), c.getColumn());
                        temp.setValue(i);
                        sudoku.cellOptions.add(temp);
                    }
                }
            }

            //if there is only one position where it can be
            if(sudoku.cellOptions.size() == 1){
                System.out.println("this value " + sudoku.cellOptions.get(0).getValue() + " can be only be on " + sudoku.cellOptions.get(0).getIndex());
                sudoku.getSolution().set(sudoku.cellOptions.get(0).getIndex(), sudoku.cellOptions.get(0));
            } else{
                System.out.println("more than one options left");
            }
        }
    }

    private static void solveCell(Sudoku sudoku, int index){
        sudoku.cellOptions.clear();

        for (int j = 1; j <= 9; j++) {

            /*checken of dit cijfer er kan*/
            if (sudoku.isOptionCell(sudoku.getSolution().get(index), j)) {
                Cell temp = new Cell(index,j , sudoku.getSolution().get(index).getRow(), sudoku.getSolution().get(index).getColumn());
                sudoku.cellOptions.add(temp);

                System.out.println("option added, now size is: " + sudoku.cellOptions.size());
            }
        }

        System.out.println("after forloop cellOptions size: " + sudoku.cellOptions.size());


                    /*checken hoeveel nummers er zijn*/
        if (sudoku.cellOptions.size() == 1) {
                        /*substitute number for index*/
            sudoku.getSolution().get(index).setValue(sudoku.cellOptions.get(0).getValue());
            sudoku.setCanbeSolved(true);
            sudoku.setCellSolved(true);

            System.out.println("solution added");
        } else if (sudoku.cellOptions.size() == 0) {
            System.out.println("an error has occurred!");
        } else {
            System.out.println("no solution yet for this cell");
            sudoku.setCellSolved(false);
        }

        sudoku.cellOptions.clear();
    }

}
