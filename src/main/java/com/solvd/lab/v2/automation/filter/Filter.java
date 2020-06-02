package com.solvd.lab.v2.automation.filter;


import com.solvd.lab.v2.automation.classes.c10.Server;

import java.util.logging.Logger;

public class Filter {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());


    public static EmojiFilter emojiFilter = new EmojiFilter();
    public static OffensesFilter offensesFilter = new OffensesFilter();
    public static NamesFilter nameFilter = new NamesFilter();
    public static FirstWordFilter firstWordFilter = new FirstWordFilter();

    public String apply(String message) {

        String symbols = ".,?! ";
        String filteredMessage = "";
        String word = "";

        message = message + " ";

        char[] characters = message.toCharArray();
        for(int i = 0; i < characters.length; i++){
            if (symbols.indexOf(characters[i]) == -1) {
                word = word + characters[i];
            }
            else {
                word = nameFilter.apply(word);
                word = offensesFilter.apply(word);
                word = emojiFilter.apply(word);
                filteredMessage = filteredMessage + word + characters[i];
                word = "";
            }
        }

        filteredMessage = firstWordFilter.apply(filteredMessage);
        return filteredMessage;
    }


}
