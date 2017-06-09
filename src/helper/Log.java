package helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Log {

    private static String putanja = "C:\\Users\\Stefan\\IdeaProjects\\OOP-Muzicka-Prodavnica\\src\\helper\\aktivnosti.log";

    public static ArrayList<String> read() throws FileNotFoundException{

        FileReader fileReader = new FileReader(putanja);
        Scanner citac = new Scanner(fileReader);
        ArrayList<String> redovi = new ArrayList<>();

        while(citac.hasNext())
            redovi.add(citac.nextLine());

        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return redovi;

    }

    public static void insert(String text){
        try {
            FileWriter fileWriter = new FileWriter(putanja);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}