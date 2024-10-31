package ru.netology;

public class Validator {

    public String validator(String validName) {
        if (validName.matches("^[a-zA-Z0-9]+$")) {
            throw new LatinException(
                    "Имя и Фамилия указаные неверно. " +
                            "Допустимы только русские буквы, пробелы и дефисы."
            );

        } else {
            return validName;
        }
    }

}
