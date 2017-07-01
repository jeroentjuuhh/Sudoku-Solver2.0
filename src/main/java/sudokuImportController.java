import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 * Created by jeroe on 20-2-2017.
 */
public class sudokuImportController implements Observer {

    @FXML TextField textFieldIn;
    @FXML GridPane gridPaneImport;
    //@FXML TextField textFieldInd;
    Font f;
    Cell current;
    List<Cell> newArray;
    private Sudoku newOne;

    public void initialize(){
        System.out.println("initialized");
        Label l = new Label("hello");
        f = new Font(30);
        //current = 0;

        int[] newArray = new int[81];
        for(int i = 0; i < 81; i++){
            newArray[i] = 0;
        }

        newOne = new Sudoku(newArray);
        current = newOne.getCell(0);



        System.out.println(newOne.toString());

        load();
    }

    public void button(ActionEvent actionEvent) {
        System.out.println("clicked");
    }

    public void buttonBack(ActionEvent actionEvent) {
        System.out.println("clicked!");
        App.sudoku.setArray(newOne.getList());
        current = newOne.getCell(0);
        App.mainWindow.getScene().setRoot(App.sudokuRoot);
    }

    private void load(){
        gridPaneImport.getChildren().clear();

        int size = gridPaneImport.getRowConstraints().size();
        gridPaneImport.getRowConstraints().remove(0, size);

        for(int i = 0; i < 81; i++){
            Cell c = newOne.getCell(i);
            Label value = new Label("");

            if(c.getValue() != 0) {
                value.setText("" + c.getValue()); // + c.getValue());
            }

            value.setPrefWidth(200);
            value.setPrefHeight(200);

            //only if
            if(controllerSudoku.shouldBeGrey(newOne.getCell(i))){
                //System.out.println("this one should be grey");
                if(current.equals(newOne.getCell(i))) {
                    System.out.println("this is the current cell");
                    value.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-background-color: #898989;");
                }else{
                    value.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-background-color: #898989;");
                }
            }else{
                if(current.equals(newOne.getCell(i))) {
                    System.out.println("this is the current cell");
                    value.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                }
                else{
                    value.setStyle("-fx-border-color: black; -fx-border-width: 2px");
                }
            }

            value.setFont(f);
            value.setAlignment(Pos.CENTER);

            value.setOnMousePressed((MouseEvent) -> {
                System.out.println("clicked a label!");
                current = c;
                load();
                textFieldIn.requestFocus();
            });


            gridPaneImport.add(value, c.getColumn(), c.getRow());

            if(i % 9 == 0) {
                RowConstraints rowConstraints = new RowConstraints(50, 100, USE_COMPUTED_SIZE);
                rowConstraints.setValignment(VPos.CENTER);
                gridPaneImport.getRowConstraints().add(rowConstraints);
            }
        }

        System.out.println(newOne.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("update of sudoku import called");
        load();
    }

    public void buttonNext(ActionEvent actionEvent) {
        System.out.println("next called!");
        for(int i = 0; i < 81; i++){
            newOne.getCell(i).setValue(0);
        }
        load();
    }

    public void onEnter(ActionEvent actionEvent) {
        setCell();
    }

    public void setCell(){
        //set cell
        System.out.println("input: " + textFieldIn.getText());
        String text = textFieldIn.getText();

        if (text.matches("[0-9]+") && text.length() < 2){
            System.out.println("text matches!");
            int tempInt = Integer.parseInt(text);
            //current.setValue(tempInt);
            if(tempInt >= 0 && tempInt < 10) {
//                newArray[current] = tempInt;
//                current++;
                System.out.println("index was" + current.getIndex());
                current.setValue(tempInt);
                newOne.setCell(current, current.getIndex());
                if(current.getIndex() == 80) {
                    current = newOne.getCell(0);
                } else {
                    current = newOne.getCell(current.getIndex() + 1);
                }
                textFieldIn.setText("");
                System.out.println("index is now" + current.getIndex());
            }
        }


        load();
        textFieldIn.setText("");
    }

    public void input(KeyEvent keyEvent) {
        System.out.println("typed!");
        setCell();
    }

    public void buttonWrite(ActionEvent actionEvent) {
        System.out.println("write called");
        try{
            WriteToFile.write(App.sudoku, "sudoku1", "medium");
        } catch (IOException e){
            System.out.println("An error occurred!");
            System.err.println(e.getMessage());
        }
    }

    public void buttonImport(ActionEvent actionEvent) {
        Sudoku newS = WriteToFile.read("sudoku1");
        //App.sudoku = newS;
        load();
    }
}
