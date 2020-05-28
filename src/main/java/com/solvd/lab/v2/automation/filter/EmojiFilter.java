package com.solvd.lab.v2.automation.filter;

import com.vdurmont.emoji.EmojiParser;

public class EmojiFilter implements MessageFilter {
    public String apply(String message){
        return EmojiParser.parseToUnicode(message);
    }
}
