package com.solvd.lab.v2.automation.filter;

public interface MessageFilter extends Filter {
    String apply(String message);
}
