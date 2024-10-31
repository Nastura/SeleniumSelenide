package ru.netology;

public class LatinException extends RuntimeException{

    public LatinException(String s) {
        super(s);
        System.out.println(s);
    }
}
