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
        if(offenses.contains(swearing)) {
            characters[rand] = '*';
        }
        return new String(characters);
    }
}

/*
с е г о д н я   п о г о д а   д е р ь м о
        новое предожение = пкстой строке
от 1 до доина массива
если символ не равен  , . ! ? " " тогда
слово = слово + символ
иначе
        еслм в jffense  есть слово
        тоогда
          int rand = (int)(Math.random()*слово.length());
          слово [ранд] = *
новое предложение= новое предложение+слово+символ[и]
        слово = ""
се*одня погодп*/
