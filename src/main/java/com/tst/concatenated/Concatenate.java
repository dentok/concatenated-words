package com.tst.concatenated;

import lombok.Data;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by denis on 18.07.17.
 */
@Data
public class Concatenate {

    private int countWordsInConcat;
    private int totalCountWordsConcatenate;
    private List<String> listWordsForCompare = new ArrayList<>();
    private List<String> listConcatenateWords = new ArrayList<>();
    private HashMap<String, Boolean> wordsMap = new HashMap<>();


    public static String readFile(String name) throws IOException {

        String filePath = new ClassPathResource(name).getFile().toString();
        FileInputStream f = new FileInputStream(filePath);
        byte[] buffer = new byte[(int) new File(filePath).length()];
        f.read(buffer);
        return new String(buffer);

    }

    public void findLongestConcatinationWord(List<String> lengthMap) {

        List<String> elementList = new ArrayList(lengthMap);

        for (int i = elementList.size() - 1; i >= 0; i--) {
            countWordsInConcat = 0;
            if (isContainsWord(elementList.get(i), true)) {
                listConcatenateWords.add(elementList.get(i));
            }
        }
    }

    public String getFirstLongestConcatenatedWord() {

        getListConcatenateWords().sort(new StringLengthListSort());

        return getListConcatenateWords().get(getListConcatenateWords().size() - 1);
    }

    public String getSecondLongestConcatenatedWord() {

        return getListConcatenateWords().get(getListConcatenateWords().size() - 2);
    }

    boolean isContainsWord(String word, boolean first) {
        if (!first && wordsMap.containsKey(word)) {
            return wordsMap.get(word);
        }
        if (first) {
            listWordsForCompare.remove(word);
        }
        for (int i = word.length() - 1; i >= 0; i--) {
            if (listWordsForCompare.contains(word.substring(0, i + 1))) {
                totalCountWordsConcatenate++;
                if ((i == word.length() - 1) || isContainsWord(word.substring(i + 1, word.length()), false)) {
                    countWordsInConcat += 1;
                    wordsMap.put(word, true);
                    if (first) listWordsForCompare.add(word);
                    return true;

                }
            }
        }

        wordsMap.put(word, false);
        if (first) listWordsForCompare.add(word);

        return false;
    }


    public void findConcatenetedWord(String fileName) throws IOException {

        String[] strWords = readFile(fileName).trim().split("[\\s']");

        for (String key : strWords) {
            listWordsForCompare.add(key);
        }

        findLongestConcatinationWord(listWordsForCompare);
    }

    class StringLengthListSort implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

}
