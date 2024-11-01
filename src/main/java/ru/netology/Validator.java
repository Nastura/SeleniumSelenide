package ru.netology;

public class Validator {

    public boolean validator(String validName) throws RuntimeException {
        if (validName.matches("^[a-zA-Z0-9]+$")) {
            return true;

        } else {
            return false;
        }
    }
}

