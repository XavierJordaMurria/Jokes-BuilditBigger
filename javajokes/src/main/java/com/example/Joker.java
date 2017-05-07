package com.example;

public class Joker
{

    private static String joke1 = "Can a kangaroo jump higher than a house? Of course, a house does not jump at all.";
    private static String joke2 = "Dentist: \"You need a crown.\"\n" +
            "-\n" +
            "Patient: \"Finally someone who understands me.\"";
    private static String joke3 = "I heard women love a man in uniform. Can not wait to start working at McDonalds.";

    public static String getJoke()
    {
        int random = (int) (Math.random() * 15);
        switch (random) {
            case 0:
                return joke1;
            case 1:
                return joke2;
            case 2:
                return joke3;
            default:
                return joke3;
        }
    }
}

