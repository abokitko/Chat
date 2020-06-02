package com.solvd.lab.v2.automation.filter;

public class Filter {

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
            if (symbols.indexOf(characters[i]) == -1){
                word = word + characters[i];
            }
            else{
                word = emojiFilter.apply(word);
                word = offensesFilter.apply(word);
                word = nameFilter.apply(word);
                filteredMessage = filteredMessage + word + characters[i];
                word = "";
            }
        }
        filteredMessage = firstWordFilter.apply(filteredMessage);

        return filteredMessage;
    }


}
