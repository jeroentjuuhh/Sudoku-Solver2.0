package org.openjfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.util.Observable;
import java.util.Observer;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class controllerSudoku implements Observer{

    @FXML Label label1;
    @FXML GridPane gridPaneSudoku;

    Font f;
    Font fBold;

    public void initialize(){
        f = new Font(30);
        //t.setFont(Font.font("Verdana", FontWeight.BOLD, 70));

        gridPaneSudoku.getRowConstraints().clear();

        loadSudoku();

        gridPaneSudoku.setGridLinesVisible(true);
        System.out.println("initialized");
    }

    public static boolean shouldBeGrey(Cell c){
        int row = c.getRow();
        int column = c.getColumn();

        if(row == 0 || row== 1 || row == 2){
            if(column == 0 || column == 1 || column == 2 ||
                    column == 6 || column == 7 || column == 8) {
                return true;
            }
        }

        if(row == 3 || row == 4 || row == 5){
            if(column == 3 || column == 4 || column == 5){
                return true;
            }
        }

        if(row == 6 || row == 7 || row == 8){
            if(column == 0 || column == 1 || column == 2 ||
                    column == 6 || column == 7 || column == 8) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void update(Observable o, Object obj) {
        System.out.println("update called!");

        if(obj instanceof Cell){
            Cell c = (Cell)obj;

            System.out.println("Model.org.openjfx.Cell update!");
        }


        int size = gridPaneSudoku.getRowConstraints().size();

        //gridPaneSudoku.getChildren().retainAll(gridPaneSudoku.getChildren().get(1));

        //gridPaneSudoku.getChildren().clear();
        //gridPaneSudoku.getRowConstraints().remove(0, size);

        loadSudoku();

        //gridPaneSudoku.setGridLinesVisible(true);
    }

    public void loadSudoku(){
        gridPaneSudoku.getChildren().clear();

        int size = gridPaneSudoku.getRowConstraints().size();
        gridPaneSudoku.getRowConstraints().remove(0, size);

        for(int i = 0; i < 81; i++){
            Cell c = App.sudoku.getCell(i);
            Label value = new Label("");

            if(c.getValue() != 0) {
                value.setText("" + c.getValue());
            }

            value.setPrefWidth(200);
            value.setPrefHeight(200);

            //only if
            if(shouldBeGrey(c)) {
                value.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: #898989;");
            }else{
                value.setStyle("-fx-border-color: black; -fx-border-width: 2px");
            }

            value.setFont(f);
            value.setAlignment(Pos.CENTER);


            gridPaneSudoku.add(value, c.getColumn(), c.getRow());

            if(i % 9 == 0) {
                RowConstraints rowConstraints = new RowConstraints(50, 100, USE_COMPUTED_SIZE);
                rowConstraints.setValignment(VPos.CENTER);
                gridPaneSudoku.getRowConstraints().add(rowConstraints);
            }
        }

        gridPaneSudoku.setGridLinesVisible(true);

    }

    public void buttonClicked(ActionEvent actionEvent) {
        System.out.println("clicked!");
        Solver.solve(App.sudoku);
        this.update(App.sudoku, null);
    }


    public void buttonImport(ActionEvent actionEvent) {
        System.out.println("clicked!");
        App.mainWindow.getScene().setRoot(App.sudokuImportRoot);
    }
}


