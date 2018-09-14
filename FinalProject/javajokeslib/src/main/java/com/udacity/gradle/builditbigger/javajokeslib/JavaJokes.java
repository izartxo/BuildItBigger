package com.udacity.gradle.builditbigger.javajokeslib;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class JavaJokes {

    String[] jokes = {
            "- Can a kangaroo jump higher than a house?\r\n" +

                    "- Of course, a house doesn’t jump at all.",
            "- Anton, do you think I’m a bad mother?\n" +

                    "- My name is Paul.\n",

            "- What is the difference between a snowman and a snowwoman?\n" +

                    "- Snowballs.\n",

            // Basque Country selection jokes for all you ;)
            "- Zer esaten du E.T.k limoia jaterakoan:\n" +

                    "- \"Mikaaatzaaa\"",
            "- Nola esaten da \"maquillaje\" euskaraz:\n" +

                    "- Harry Potter"
    };

    ArrayList<String> jokeList;


    public JavaJokes(){
        jokeList = new ArrayList<>();

        for (int num = 0; num < jokes.length; num++)
            jokeList.add(jokes[num]);

    }

    public String getJoke(){
        Random randomNumber = new Random();

        return jokeList.get(randomNumber.nextInt(jokes.length));
    }
}
