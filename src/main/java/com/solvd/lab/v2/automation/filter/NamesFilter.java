package com.solvd.lab.v2.automation.filter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NamesFilter implements MessageFilter {

    @Override
    public String apply(String str) {

        StringBuffer sb = new StringBuffer(str);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return new String(sb);
    }
}
