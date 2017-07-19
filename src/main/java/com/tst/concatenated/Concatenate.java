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
        listConcatenateWords = getConcatWordsList(lengthMap);
    }

    public String getFirstLongestConcatenatedWord() {

        listConcatenateWords.sort(new StringLengthListSort());

        return listConcatenateWords.get(getListConcatenateWords().size() - 1);
    }

    public String getSecondLongestConcatenatedWord() {

        return listConcatenateWords.get(getListConcatenateWords().size() - 2);
    }

    public void findConcatenetedWord(String fileName) throws IOException {

        String[] strWords = readFile(fileName).trim().split("[\\s']");

        for (String key : strWords) {
            listWordsForCompare.add(key);
        }

        findLongestConcatinationWord(listWordsForCompare);
    }

    private boolean isContainsWord(String word, Map<String, Boolean> wordsList, Set<String> unicWords) {
        if (wordsList.containsKey(word)) {
            return wordsList.get(word);
        }
        for (int i = 1; i <= word.length(); i++) {
            String pre = word.substring(0, i);

            if (unicWords.contains(pre)) {
                String w = word.substring(i);

                if (unicWords.contains(w) || isContainsWord(w, wordsList, unicWords)) {
                    wordsList.put(word, true);
                    return true;
                }
            }
        }
        wordsList.put(word, false);
        return false;
    }

    private List<String> getConcatWordsList(List<String> words) {
        Set<String> unicWords = new HashSet<>(words);
        Map<String, Boolean> wordsHolder = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (String word : words) {
            if (isContainsWord(word, wordsHolder, unicWords)) {
                result.add(word);
            }
        }
        return result;
    }

    class StringLengthListSort implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.length() - s2.length();
        }
    }

}
