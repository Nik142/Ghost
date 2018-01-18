package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    private int lowerindex, upperindex;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if (prefix == null || prefix.trim().equals("")) {
            Random random = new Random();
            return words.get(random.nextInt(words.size()));
        } else {
            int index = binarySearch(prefix);
            if (index == -1) {
                return null;
            } else {
                String newWord = words.get(index);
                return newWord;
            }
        }
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        Random random = new Random();
        if (prefix == null || prefix.trim().equals("")) {
            return words.get(random.nextInt(words.size()));
        } else {

            binarySearchWithUpperOffset(0, words.size() - 1, prefix);
            binarySearchWithLowerOffset(0, words.size() - 1, prefix);
            if (lowerindex == -1 || upperindex == -1) {
                return null;
            }
            int index = random.nextInt(upperindex - lowerindex + 1) + lowerindex;
            return words.get(index);
        }

    }

    private int binarySearch(String word) {
        int initial = 0;
        int finalindex = words.size() - 1;
        int mid = 0, compareInt = 0;

        do {
            if (initial > finalindex) {
                break;
            }
            mid = (initial + finalindex) / 2;
            String midStr = words.get(mid);
            if (midStr.length() > word.length()) {
                midStr = midStr.substring(0, word.length());
            }
            compareInt = word.compareToIgnoreCase(midStr);
            if (compareInt == 0) {
                break;
            } else if (compareInt < 0) {
                finalindex = mid - 1;
            } else {
                initial = mid + 1;
            }
        } while (initial != finalindex);

        if (compareInt == 0) {
            return mid;
        } else {
            return -1;
        }
    }

    private void binarySearchWithLowerOffset(int initial, int finalindex, String word) {
        int mid, compareInt;
        if (initial > finalindex) {
            lowerindex = -1;
        } else {
            mid = (initial + finalindex) / 2;
            String midStr = words.get(mid);
            if (midStr.length() > word.length()) {
                midStr = midStr.substring(0, word.length());
            }
            compareInt = word.compareToIgnoreCase(midStr);
            if (compareInt == 0) {
                int justBeforemid = mid - 1;
                if(mid>initial){
                String justBeforeMid = words.get(justBeforemid);
                if (justBeforeMid.startsWith(word)) {
                    binarySearchWithLowerOffset(initial, justBeforemid, word);
                } else {
                    lowerindex = mid;
                }}
                else{
                    lowerindex = mid;
                }
            } else if (compareInt < 0) {
                binarySearchWithLowerOffset(initial, mid - 1, word);
            } else if (compareInt > 0) {
                binarySearchWithLowerOffset(mid + 1, finalindex, word);
            }
        }

    }

    private void binarySearchWithUpperOffset(int initial, int finalindex, String word) {
        int mid, compareInt;
        if (initial > finalindex) {
            upperindex = -1;
        } else {
            mid = (initial + finalindex) / 2;
            String midStr = words.get(mid);
            if (midStr.length() > word.length()) {
                midStr = midStr.substring(0, word.length());
            }
            compareInt = word.compareToIgnoreCase(midStr);
            if (compareInt == 0) {
                int justAftermid = mid + 1;
                if(mid<finalindex){
                String justAfterMid = words.get(justAftermid);
                if (justAfterMid.startsWith(word)) {
                    binarySearchWithUpperOffset(justAftermid, finalindex, word);
                } else {
                    upperindex = mid;
                }}
                else {
                    upperindex = mid;
                }
            } else if (compareInt < 0) {
                binarySearchWithUpperOffset(initial, mid - 1, word);
            } else if (compareInt > 0) {
                binarySearchWithUpperOffset(mid + 1, finalindex, word);
            }
        }
    }
}


