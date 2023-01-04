package agh.cs.po.Classes;

import java.io.FileWriter;
import java.io.PrintWriter;

public class WriteToCSV extends AbstractWorldMap{


    public void save(String data) {
        String fileName = "data.csv";

        try {
            FileWriter fw = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(data);

            pw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}