package com.tst.concatenated;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        //SpringApplication.run(Application.class, args);


        Concatenate findLongest = new Concatenate();
        long start=System.currentTimeMillis();
        try {
            findLongest.findConcatenetedWord("words.txt");

            System.out.printf("Concatenated Words:");

            System.out.println("The longest concatenated word is '" + findLongest.getFirstLongestConcatenatedWord() +
                    "', length: " + findLongest.getFirstLongestConcatenatedWord().length());

            System.out.println("The 2nd longest concatenated word is '" + findLongest.getSecondLongestConcatenatedWord());


            System.out.println("The total count of all the concatenated words in the file: " + findLongest.getTotalCountWordsConcatenate());

            long end=System.currentTimeMillis();
            System.out.println("time ="+(end-start)+" ms");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
