package ru.netology;

public class Validator {

    public boolean validator(String validName) {
        for (int i = 0; i < validName.length(); i ++) {
            if (Character.UnicodeBlock.of(validName.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC) ||
                    validName.charAt(i) == '-') {
                System.out.println("Имя содержит латинские буквы");
                return true;

            }

        }
        return false;
    }
}

