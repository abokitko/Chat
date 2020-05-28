package com.solvd.lab.v2.automation.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class OffensesFilter implements MessageFilter{

    @Override
    public String apply(String str) {
        char[] characters = str.toCharArray();
        int rand = (int)(Math.random()*str.length());
        characters[rand] = '*';
        return new String(characters);
    }


}
