package com.udacity.gradle.builditbigger.javajokeslib;


import java.util.ArrayList;
import java.util.Random;

public class JavaJokes {

    ArrayList<String> jokeList;

    public JavaJokes(){
        jokeList = new ArrayList<>();
        for (int num = 0; num < 10; num++)
            jokeList.add("Joke number " + String.valueOf(num));

    }

    public String getJoke(){
        Random randomNumber = new Random();

        return jokeList.get(randomNumber.nextInt(10));
    }
}
