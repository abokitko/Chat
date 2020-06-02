package com.solvd.lab.v2.automation.filter;

public class FirstWordFilter extends Filter{

    public String apply(String message) {
        String capitalazedMessage = message.substring(0, 1).toUpperCase();
        capitalazedMessage += message.substring(1);
        return capitalazedMessage;
    }
}
