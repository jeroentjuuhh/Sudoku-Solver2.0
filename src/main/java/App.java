import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    static List<Integer> solution;
    static Sudoku sudoku;
    static Stage mainWindow;
    static Parent sudokuRoot, sudokuImportRoot;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainWindow = primaryStage;


        FXMLLoader loaderSudoku = new FXMLLoader(getClass().getResource("sudokuView.fxml"));
        sudokuRoot = loaderSudoku.load();

        FXMLLoader loaderImportSudoku = new FXMLLoader((getClass().getResource("sudokuImportView.fxml")));
        sudokuImportRoot = loaderImportSudoku.load();

        //adding observers
        controllerSudoku sudokuController = (controllerSudoku) loaderSudoku.getController();
        App.sudoku.addObserver(sudokuController);
        sudokuImportController sudokuImport = (sudokuImportController) loaderImportSudoku.getController();
        App.sudoku.addObserver(sudokuImport);


        mainWindow.setTitle("Sudoku Solver");
        mainWindow.setScene(new Scene(sudokuRoot, 603, 603));
        mainWindow.show();
        sudokuController.update(App.sudoku, null);


        WriteToFile.write(sudoku, "sudoku1", "medium");
    }

    public static void main(String[] args) {

        int sudokuArray[] = {   9,0,0,0,3,0,0,0,4,
                0,3,1,4,0,8,6,7,0,
                0,4,0,0,0,0,0,1,0,
                0,2,0,3,0,6,0,8,0,
                4,0,0,0,2,0,0,0,9,
                0,5,0,7,0,9,0,6,0,
                0,6,0,0,0,0,0,2,0,
                0,8,5,1,0,4,3,9,0,
                1,0,0,0,8,0,0,0,6};

        int sudokuArray2[] = {  7,6,0,0,5,0,0,8,0,
                4,0,0,0,3,0,2,0,0,
                0,3,2,0,0,0,4,0,6,
                0,0,0,0,0,8,0,0,0,
                0,0,9,0,6,0,8,0,0,
                0,0,0,2,0,0,0,0,0,
                6,0,8,0,0,0,9,1,0,
                0,0,7,0,4,0,0,0,5,
                0,2,0,0,9,0,0,3,8};

        int medium[] =      {   0,8,0,1,0,0,7,0,0,
                0,0,9,0,3,0,0,2,0,
                2,0,0,5,0,0,0,0,6,
                0,7,0,0,0,9,0,0,0,
                5,0,0,0,1,0,0,0,9,
                0,0,0,4,0,0,0,7,0,
                9,0,0,0,0,8,0,0,1,
                0,3,0,0,4,0,5,0,0,
                0,0,7,0,0,2,0,8,0};

        sudoku = new Sudoku(sudokuArray2);

        launch(args);

        //sudoku.solve();

    }
}
