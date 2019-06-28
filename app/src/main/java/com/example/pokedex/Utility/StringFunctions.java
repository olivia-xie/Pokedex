package com.example.pokedex.Utility;

import java.util.ArrayList;

public class StringFunctions {

    // formatting list
    public static String formatList (ArrayList<String> list, String string) {

        for (String item : list) {

            if (list.indexOf(item) == (list.size() - 1)) {
                string += item.toUpperCase();
            } else {
                string += item.toUpperCase() + ", ";
            }
        }

        return string;
    }

    // formatting index number
    public static String formatIndexNum (String number) {

        String indexNum = String.format("%3s", number);
        indexNum = indexNum.replace(' ', '0');
        return indexNum;

    }

}
