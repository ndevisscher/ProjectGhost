package nl.mprog.Ghost.projectghost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.devisscher.niek.projectghost.R;

public class MainActivity extends AppCompatActivity {

    //Initializing all the needed variables
    String gameWord = "";
    String player1 = "";
    String player2 = "";
    String startPlayer;
    String currentword;
    String currentPlayer ="";
    String letterInput = "";
    String language = "dutch.txt";
    String langChoice = "";
    boolean turn = true;
    game game;
    lexicon lexicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        TextView turnIndicator = (TextView) findViewById(R.id.turnIndicator);
        //Getting the data passed allong from other activities
        Bundle d = getIntent().getExtras();
        player1 = d.getString("player1");
        player2 = d.getString("player2");
        langChoice = prefs.getString("language",langChoice);

        //If you do not have players you will be redirected to the start screen
        if (player1 == null && player2 == null){
            Intent newstart = new Intent (MainActivity.this,gameStart.class);
            startActivity(newstart);
            finish();
        }
        //If there is no language selected you get send back to the start screen
        else if (langChoice == null){
            Intent newstart = new Intent (MainActivity.this,gameStart.class);
            startActivity(newstart);
            finish();
        }
        //Creating the lexicon and game if you did select a language
        if (langChoice != "") {
            language = langChoice;
            lexicon = new lexicon(this, language);
            game = new game(lexicon);
        }
        //In case all else fails you will use the default dutch dictionary
        else{
            lexicon = new lexicon(this, language);
            game = new game(lexicon);
        }
        //Setting the starting player and showing it on screen
        startPlayer = player1;
        turnIndicator.setText(startPlayer + " " + "is playing");
    }

    //To start an entirely new game
    public void newGame(View v) {
        Intent restart = new Intent (MainActivity.this,gameStart.class);
        startActivity(restart);
        finish();
    }

    //To restart this specific game with the same players and dictionary
    public void restart(View v) {
        Intent restart = new Intent (MainActivity.this,MainActivity.class);
        Bundle r = new Bundle();
        r.putString("player1",player1);
        r.putString("player2", player2);
        restart.putExtras(r);
        startActivity(restart);
        finish();
    }

    //After adding a letter to the game word
    public void buttonOnClick(View v) {
        //Getting the different input and output ids from the app
        EditText editInput = (EditText) findViewById(R.id.editText);
        TextView currentWord = (TextView) findViewById(R.id.wordDisplay);
        Button inputButton = (Button) findViewById(R.id.button);
        TextView turnIndicator = (TextView) findViewById(R.id.turnIndicator);

        //Getting the input from the player
        letterInput = editInput.getText().toString();
        //We only want something to happen when we have input from the player
        System.out.println(letterInput);
        if (letterInput.equals("")){
            Toast.makeText(getApplicationContext(), "Please enter a letter.", Toast.LENGTH_LONG).show();
        }
        else {
            //The current word that has been formed
            currentword = currentWord.getText().toString();
            //Executing the game with the new guessed letter
            currentword = game.guess(letterInput, currentword);

            //If the game did not end with the letter the player played we change the turn and add it to the word
            if (!game.ended(currentword, lexicon.result(lexicon.count(lexicon.filter(currentword, lexicon.currentFilter)), lexicon.filter(currentword, lexicon.currentFilter), currentword))) {
                if (game.turn(turn) == false) {
                    turn = false;
                    turnIndicator.setText(player2 + " " + "is playing");
                    currentPlayer = player1;
                } else {
                    turn = true;
                    turnIndicator.setText(player1 + " " + "is playing");
                    currentPlayer = player2;
                }

                //Getting the current word from the game and adding the guessed letter to it
                gameWord = currentWord.getText().toString();
                gameWord += letterInput;

                //Setting the word on screen so the player can see it and emptying the input field
                currentWord.setText(gameWord);
                editInput.setText("");
            }
            //If the game did end we get the player who won by seeing who played last
            else {
                //The currentplayer will not be filled if the first letter cannot form a word, this will almost never happen
                if (currentPlayer == "") {
                    currentPlayer = player2;
                }
            /*This will send us to the winscreen where the winning player and the word that was played
            is displayed, we also pass allong the players that played so they can rematch
            */
                Intent winScreen = new Intent(MainActivity.this, winScreen.class);
                Bundle b = new Bundle();
                b.putString("winningplayer", currentPlayer);
                b.putString("currentword", currentword);
                b.putString("player1", player1);
                b.putString("player2", player2);
                winScreen.putExtras(b);
                startActivity(winScreen);
                finish();
            }
        }
    }
    //If the app crashes or is closed we want to restore it, here we set what values we restore


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
