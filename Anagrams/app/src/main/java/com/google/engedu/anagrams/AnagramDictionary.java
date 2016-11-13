package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
//    private ArrayList<String> wordList;

    private     HashSet wordSet;
    private     HashMap<String,ArrayList<String>> lettersToWord;


    private String Letter_Sort(String word){
//        StringBuilder
        char[] word_Array = word.toCharArray();
        char[] letter = word.toCharArray();
        ArrayList<Character> letters = new ArrayList<Character>();
        for(char c : word_Array){
            letters.add(c);
        }

        Collections.sort(letters);

        int index = 0;
        for(char c: letters){
            letter[index] = c;
            index++;
        }
        return new String(letter);
    }

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
//        this.wordList = new ArrayList<String>();
        wordSet = new HashSet();
        lettersToWord = new HashMap();

        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            String letter = Letter_Sort(word);
            if(!lettersToWord.containsKey(letter)) lettersToWord.put(letter, new ArrayList<String>());
            lettersToWord.get(letter).add(word);
        }
    }

    private boolean contain_Sub(char[] word, char[] base) {
        int size_w = word.length;
        int size_b= base.length;
        boolean contain = true;

        int index = 0;
        int sub_i = 0;
        while(index < size_w - size_b+1){

            contain =word[index+sub_i] == base[sub_i];
            while (sub_i < size_b && contain ){
//                Log.d("Comparsion: ",word[index+sub_i] + " vs " + base[sub_i]);
                contain =word[index+sub_i] == base[sub_i];
                if(contain && sub_i == size_b -1) return contain;
                sub_i++;
            }
            sub_i = 0;


            index++;
        }
        return contain;

    }

    public boolean isGoodWord(String word, String base) {
        boolean good = true;
        good &= wordSet.contains(word);
        good &= !contain_Sub(word.toCharArray(),base.toCharArray());
//        String gb = (good)? "GooD":"BaD";
//        Log.d(word,gb);
        return good;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String letter = Letter_Sort(targetWord);
        result.addAll(getAnagramsWithOneMoreLetter(letter));
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char it = 'a';
        int index = 1;
        char[] new_word = new char[word.length() + 1];
        char[] old_word = word.toCharArray();

        while(index < word.length()+1){
            new_word[index] = old_word[index-1];
            index++;
        }
        index = 0;


        while(index < 26){
            new_word[0] = it;
            result.addAll(lettersToWord.get(letter)) ;
            it++;
        }

        return result;
    }

    public String pickGoodStarterWord() {
        return "post";
    }
}
