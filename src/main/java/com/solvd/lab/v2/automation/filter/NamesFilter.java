package com.solvd.lab.v2.automation.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NamesFilter implements MessageFilter {
    public static ArrayList<String> names = new ArrayList<>();


    NamesFilter() {

        try {
            FileReader reader = new FileReader("src/main/resources/names.txt");
            Scanner scanner = new Scanner(reader);

            while(scanner.hasNextLine()){
                names.add(String.valueOf(scanner.nextLine()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String apply(String word) {
        String capitalizedName = "";
        if (names.contains(word)) {
            capitalizedName = word.substring(0, 1).toUpperCase();
            capitalizedName = capitalizedName + word.substring(1);
            return capitalizedName;
        } else return word;
    }
}
