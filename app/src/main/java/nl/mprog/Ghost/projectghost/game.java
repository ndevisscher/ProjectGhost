package nl.mprog.Ghost.projectghost;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Niek on 9-10-2015.
 */
//This class needs to be accessible in all other activities so it is public
public class game {

    //Initializing all the needed variables
    lexicon lexicon;
    String gameWord = "";
    //The initializer for our class, here we pass along what lexicon we are going to use when we create a game
    public game(lexicon lexicon){
        this.lexicon = lexicon;
    }

    //The function that is used to see what word will be formed with a given letter
    public String guess(String input, String currentWord){

        gameWord = currentWord;
        //If no letter is put in or the input is too long we return the original word
        if (input == " " || input.length() > 1 ){
            return gameWord;
        }
        //If the input was valid we return the newly formed word
        else{
            gameWord += input;
            return gameWord;
        }
    }

    //The function to indicate which turn it is, we can use this to see who is playing
    public boolean turn (boolean turncount){
        if (turncount){
            return false;
        }
        else {
            return true;
        }
    }

    boolean ended = false;
    //This function is used to check if the game has ended
    public boolean ended (String currentWord, String result){
        //The game will end if the word is longer then 3 letters
        if (result.equals(currentWord) && currentWord.length() > 3 ){
            ended = true;
            return ended;
        }
        //The game has also ended if no valid words can be formed from the input
        else if(lexicon.count(lexicon.filter(currentWord,lexicon.currentFilter)) == 0 ){
            ended = true;
            return ended;
        }
        //If the game has not ended we return false
        else {
            ended = false;
        }
        return ended;
    }

    //If the game has ended there is a winner
    public boolean winner (boolean ended){
        if (ended){
            return true;
        }
        return false;
    }

}
