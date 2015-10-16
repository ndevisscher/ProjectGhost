package nl.mprog.Ghost.projectghost;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.devisscher.niek.projectghost.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class gameStart extends AppCompatActivity {

    //Initializing all the needed variables
    String name1;
    String name2;
    String langSelected;
    String langChoice="dutch.txt";
    Set<String> playerNames = new HashSet<String>();
    List<String> data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_start);

        //calling the shared preferences and getting the last used language from it
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        langSelected = prefs.getString("language",langSelected);
        if (langSelected==null){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("language", langChoice);
            editor.commit();
        }
        //Getting all the names previously used from shared preferences so you can choose from them
        playerNames = prefs.getStringSet("names",playerNames);
        System.out.println(playerNames);
        for (String name:playerNames){
            data.add(name);
        }

        //Making a dropdown list from which you can select you name
        Spinner dropdown1 = (Spinner)findViewById(R.id.select1);
        Spinner dropdown2 = (Spinner)findViewById(R.id.select2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
    }

    //This is for selecting you language in the app, can be expanded for more languages
    public void languageSelect(View v) {
        //Finding the button for the language and getting its text
        Button language = (Button) findViewById(R.id.language);
        langSelected = language.getText().toString();
        final String status = (String) v.getTag();
        //Calling the shared preferences and initializing its editor
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        /*
        A switch to choose between the available languages,
        extra languages can easily be added via an extra case
         */
        switch (status) {
            case "0":
                language.setText("English");
                langChoice = "english.txt";
                editor.putString("language", langChoice);
                v.setTag("1"); //pause
                break;
            case "1":
                language.setText("Dutch");
                langChoice = "dutch.txt";
                editor.putString("language", langChoice);
                v.setTag("0"); //pause
                break;
        }
        //Commiting the data to the shared preferences so it is available in other screens
        editor.commit();
    }

    //This happens when u press the start game button, it starts the actual game
    public void buttonOnClick(View v) {
        //Getting the input from the textinput fields in the app
        EditText player1 = (EditText) findViewById(R.id.player1input);
        EditText player2 = (EditText) findViewById(R.id.player2input);
        name1 = player1.getText().toString();
        name2 = player2.getText().toString();
        //Getting the input from the dropdown lists
        Spinner dropdown1 = (Spinner)findViewById(R.id.select1);
        Spinner dropdown2 = (Spinner)findViewById(R.id.select2);

        //If the input field is empty we take the name from the dropdown list
        if (name1.equals("")){
            name1 = dropdown1.getSelectedItem().toString();
        }
        if (name2.equals("")){
            name2 = dropdown2.getSelectedItem().toString();
        }
        //Passing allong the names and language choice to the actual game
        Intent startGame = new Intent (gameStart.this,MainActivity.class);
        Bundle s = new Bundle();
        s.putString("player1",name1);
        s.putString("player2",name2);
        startGame.putExtras(s);
        startActivity(startGame);
        finish();
    }

    //This is for the highscores button, which leads you directly to the highscores
    public void highscores(View v) {
        //Passing allong the needed variables to the highscore page
        Intent highscore = new Intent (gameStart.this,highScore.class);
        Bundle h = new Bundle();
        h.putString("player1", null);
        h.putString("player2", null);
        highscore.putExtras(h);
        startActivity(highscore);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_start, menu);
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
