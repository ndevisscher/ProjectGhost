package nl.mprog.Ghost.projectghost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.devisscher.niek.projectghost.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class highScore extends AppCompatActivity {

    //Initializing all the needed variables
    String player1;
    String player2;
    String langChoice;
    Set<String> highScores = new HashSet<String>();
    List<String> data = new ArrayList<>();
    private TextView explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Getting the data that was passed allong from other activities
        Bundle b = getIntent().getExtras();
        player1 = b.getString("player1");
        player2 = b.getString("player2");
        langChoice = prefs.getString("language",langChoice);
        highScores = prefs.getStringSet("highScores",highScores);
        //Changing the highscores set to a list so we can use it in a listview
        for (String h : highScores) {
            data.add(h);
        }
        //Making the listview adapter and filling our listview with the highscores
        ArrayAdapter<String> highscoreAddapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, (List<String>) data);
        ListView listView = (ListView) findViewById(R.id.scores);
        listView.setAdapter(highscoreAddapter);

    }

    //Starting an entirely new game
    public void newGame (View n) {
        Intent startGame = new Intent (highScore.this,gameStart.class);
        startActivity(startGame);
        finish();
    }

    //Used for rematching the last played game with the same players and dictionary
    public void rematch (View r) {
        Intent rematch = new Intent (highScore.this,MainActivity.class);
        Bundle c = new Bundle();
        c.putString("player1",player1);
        c.putString("player2", player2);
        rematch.putExtras(c);
        startActivity(rematch);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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
