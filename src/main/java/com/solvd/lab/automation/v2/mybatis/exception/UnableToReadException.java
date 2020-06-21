package com.solvd.lab.automation.v2.mybatis.exception;

import java.io.IOException;

public class UnableToReadException extends IOException {
    public UnableToReadException(String message) {
        super(message);
    }
}
