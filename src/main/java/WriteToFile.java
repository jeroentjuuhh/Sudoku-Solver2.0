import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by jeroe on 6-3-2017.
 */
public class WriteToFile {

    public static void write(Sudoku s, String name, String difficulty) throws IOException{

        JSONObject obj = new JSONObject();
        obj.put("Name", name);
        obj.put("difficulty", difficulty);
        System.out.println("begin of the write function");

        for(int i = 0; i < 81; i++) {
            JSONArray cell = new JSONArray();
            Cell cellIn = s.getCell(i);

            cell.add("value: " + cellIn.getValue());
            cell.add("column: " + cellIn.getColumn());
            cell.add("row: " + cellIn.getRow());
            cell.add("index: " + cellIn.getIndex());

            obj.put("cell" + i, cell);

        }

        // try-with-resources statement based on post comment below
        try (
                FileWriter file = new FileWriter("/Users/jeroe/Documents/Sudoku/sudokus.txt")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }
    }

    public static Sudoku read(String name) {

        JSONParser parser = new JSONParser();
        int[] result = {    0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,1,0,0,0,0,
                0,0,0,0,2,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0};

        Object obj = null;

        try {

            obj = parser.parse(new FileReader(
                    "/Users/jeroe/Documents/file1.txt"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        JSONObject jsonObject = (JSONObject) obj;

        for(int i = 0; i < 81; i++) {
            JSONArray cell = (JSONArray) jsonObject.get("cell" + i);
            String valueS = (String) cell.get(0);
            valueS = valueS.replace("value: " , "");
            int value = Integer.parseInt(valueS);
            result[i] = value;
        }

        Sudoku sudReesult = new Sudoku(result);
        return sudReesult;

    }
}


