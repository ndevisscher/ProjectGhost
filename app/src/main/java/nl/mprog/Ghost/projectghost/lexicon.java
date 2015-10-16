package nl.mprog.Ghost.projectghost;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Niek on 9-10-2015.
 */
public class lexicon {
    //Initializing all the needed variables
    List<String> currentFilter = new ArrayList();
    HashSet<String> dictionary;

    //The initializer for our class, here we pass along what dictionary we use to create our lexicon
    public lexicon(Context context, String sourcePath) {
        /*
        We create a hashSet dictionary and read all the words from the chosen dictionary, we add
        those words to our dictionary hashSet and we fill our currentfilter we use to play the game
        */
        dictionary = new HashSet<>();
        String word = "";
        Scanner dutch;
        try {
            dutch = new Scanner(context.getAssets().open(sourcePath));
            while (dutch.hasNext()) {
                //process line
                word = dutch.next();
                dictionary.add(word);
                currentFilter.add(word);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Our function for filtering the current word in our filter
    public List filter(String input,List<String> currentFilter) {
        //Creating a pseudo list to fill from our entire list, this so we dont have to delete which is more timeconsuming
        List filtered = new ArrayList();
        String check;
        //Iterating over our filter
        for (int i =0; i<currentFilter.size(); i++){
            boolean same = true;
            check = currentFilter.get(i);
            /*
            If the word in the filter is longer then our current word we check
            if that word can be formed from our current word, if so we add it to
            our pseudo list. This check is done by comparing the letters from the input
            against the letters from the word out of our filter
             */
            if (check.length() >= input.length()) {
                for (int x = 0; x < input.length(); x++) {
                    char a_char = input.charAt(x);
                    char check_char = check.charAt(x);

                    if (a_char != check_char) {
                        same = false;
                    }
                }
                if (same) {
                    filtered.add(check);
                }
            }
        }
        return filtered;
    }

    //This returns the size of our filterlist
    public int count(List filtered) {
        // body
        int count = filtered.size();
        return count;
    }

    //This returns the last remaining word in the filter
    public String result(int count,List filtered, String currentWord) {
        String result="";
        //If it is the last remaining word it will return that word from the filter
        if (count == 1) {
            result = (String) filtered.get(0);
        }
        /*
        We also check if the word that is currently formed is in the filter,
        since the game ends once a word is formed
        */
        else if(filtered.contains(currentWord)){
            result = currentWord;
        }
        //If there is no result yet it will return an empty string
        return result;
    }

    //Here we can reset our filterlist
    public List reset() {
        List<String> resetlist = new ArrayList<String>(dictionary);
        return resetlist;
    }

}
