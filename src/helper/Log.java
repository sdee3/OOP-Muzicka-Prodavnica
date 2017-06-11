package helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Log {

    private static String putanja = "C:\\Users\\Stefan\\IdeaProjects\\OOP-Muzicka-Prodavnica\\src\\helper\\aktivnosti.log";
    private static FileWriter fileWriter;

    public static void init(){
        try {
            fileWriter = new FileWriter(putanja, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static void unesiTekst(String text){
        try {
            fileWriter.write(text + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zatvori() {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}