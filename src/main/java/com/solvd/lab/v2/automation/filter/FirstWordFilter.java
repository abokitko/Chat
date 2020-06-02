package com.solvd.lab.v2.automation.filter;

import java.util.List;

public class FirstWordFilter extends Filter{

    public String apply(String message) {
        message = message.substring(0,1).toUpperCase() + message.substring(1);
        return message;
    }
}
