import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by jeroe on 5-3-2017.
 */
public class SudokuTest {

    int sudokuArray[] = {   9,0,0,0,3,0,0,0,4,
            0,3,1,4,0,8,6,7,0,
            0,4,0,0,0,0,0,1,0,
            0,2,0,3,0,6,0,8,0,
            4,0,0,0,2,0,0,0,9,
            0,5,0,7,0,9,0,6,0,
            0,6,0,0,0,0,0,2,0,
            0,8,5,1,0,4,3,9,0,
            1,0,0,0,8,0,0,0,6};

    Sudoku solution;

    @Test
    public void constructor(){
        solution = new Sudoku(sudokuArray);

        for(int i = 0; i < 81; i++) {

            assertThat(i).isEqualTo(solution.getCell(i).getIndex());
            //assertEquals(i, solution.getCell(i).getIndex());
        }
    }

    @Test
    public void column(){
        solution  = new Sudoku(sudokuArray);

        //column 0
//        assertEquals(0, solution.getCell(0).getColumn());

    }

    @Test
    public void exceptionTest(){
        solution  = new Sudoku(sudokuArray);

        IndexOutOfBoundsException ex = null;

        try{
            solution.getCell(82);
        } catch (IndexOutOfBoundsException e) {
            ex = e;
        }

//        assertNotEquals(ex, null);
    }
}
