package com.solvd.lab.v2.automation.filter;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class OffensesFilter implements MessageFilter{


    public static ArrayList<String> offenses = new ArrayList<>();


    OffensesFilter() {

        try {
            FileReader reader = new FileReader("src/main/resources/offenses.txt");
            Scanner scanner = new Scanner(reader);

            while(scanner.hasNextLine()){
                offenses.add(String.valueOf(scanner.nextLine()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String apply(String swearing) {
        char[] characters = swearing.toCharArray();
        int rand = (int)(Math.random()*swearing.length());
        if (offenses.contains(swearing)) {
            characters[rand] = '*';
        }
        return new String(characters);
    }
}