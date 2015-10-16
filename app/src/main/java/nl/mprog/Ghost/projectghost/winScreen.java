package nl.mprog.Ghost.projectghost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.devisscher.niek.projectghost.R;

import java.util.HashSet;
import java.util.Set;

public class winScreen extends AppCompatActivity {

    //Initializing all the needed variables
    String player1;
    String player2;
    String langChoice;
    Set<String> highScores = new HashSet<String>();
    Set<String> playerNames = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win_screen);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Getting the passed data from previous activities
        Bundle b = getIntent().getExtras();
        String winningplayer = b.getString("winningplayer");
        String winningword = b.getString("currentword");
        player1 = b.getString("player1");
        player2 = b.getString("player2");
        langChoice = prefs.getString("language",langChoice);

        //Finding the output textfields and filling them with the right data
        TextView endWord = (TextView) findViewById(R.id.winningWord);
        TextView player = (TextView) findViewById(R.id.winningPlayer);
        endWord.setText("the word that was formed: " + winningword);
        player.setText("the player that won: " + winningplayer);

        //Variables for updating or creating the highscores
        String update="";
        String previous="";
        Integer score;


        //If there are already highscores in the shared preferences we want to update them
        if (prefs.contains("highScores")){
            //Getting the current highscores
            highScores = prefs.getStringSet("highScores",highScores);
            //Parsing through the highscores and updating them if a player that won before won again
            for (String h : highScores){
                if (h.startsWith(winningplayer + ":")){
                    String[] split = h.split(":");
                    score = Integer.valueOf(split[1]);
                    score += 1;
                    update = winningplayer + ":" + score;
                    previous = h;
                }
            }
            //Removing the old score for the player and adding the new score for that player
            if (update!=""){
                highScores.remove(previous);
                highScores.add(update);
            }
            //If the player was not in the highscores he is added here
            else{
                highScores.add(winningplayer+":1");
            }
        }
        //Creating the highscores set if it did not yet exist
        else{
            highScores.add(winningplayer+":1");
        }
        //Adding the players that played in the game to the playerlist, both winners and losers
        if (prefs.contains("names")){
            playerNames = prefs.getStringSet("names",playerNames);
            System.out.println("hooizoie");
        }
        else{
            playerNames.add(player1);
            playerNames.add(player2);
        }
        if (playerNames.contains(player1)==false){
            playerNames.add(player1);
        }
        if (playerNames.contains(player2)==false){
            playerNames.add(player2);
        }
        //
        System.out.println(playerNames);
        //Updating the highscores and playerlist in shared preferences
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putStringSet("highScores", highScores);
        editor.putStringSet("names",playerNames);
        editor.putString("language",langChoice);
        editor.commit();
    }

    //Starting an entirely new game
    public void newGame (View n) {
        Intent startGame = new Intent (winScreen.this,gameStart.class);
        startActivity(startGame);
        finish();
    }

    //Rematching against your previous opponent with the same dictionary
    public void rematch (View r) {
        Intent rematch = new Intent (winScreen.this,MainActivity.class);
        Bundle c = new Bundle();
        c.putString("player1",player1);
        c.putString("player2",player2);
        rematch.putExtras(c);
        startActivity(rematch);
        finish();
    }

    //This is for the highscores button, which will lead you to the highscores
    public void highscores (View h) {
        Intent highscore = new Intent (winScreen.this,highScore.class);
        Bundle hi = new Bundle();
        hi.putString("player1",player1);
        hi.putString("player2",player2);
        highscore.putExtras(hi);
        startActivity(highscore);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_win_screen, menu);
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
