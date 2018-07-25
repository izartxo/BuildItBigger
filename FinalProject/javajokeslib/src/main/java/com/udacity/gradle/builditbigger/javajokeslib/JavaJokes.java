package com.udacity.gradle.builditbigger.javajokeslib;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JavaJokes {

    String[] jokes = {
            "Txiste 1",
            "Txiste 2",
            "Txiste 3"
    };

    ArrayList<String> jokeList;


    public JavaJokes(){
        jokeList = new ArrayList<>();

        for (int num = 0; num < jokes.length; num++)
            jokeList.add(jokes[num]);

    }

    public String getJoke(){
        Random randomNumber = new Random();

        return jokeList.get(randomNumber.nextInt(3));
    }
}
