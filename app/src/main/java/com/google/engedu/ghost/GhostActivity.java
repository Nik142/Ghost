package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private String wordFragment;
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private Button challenge,restart;
    private TextView text,label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        challenge=(Button) findViewById(R.id.challenge_buttonview);
        restart = (Button) findViewById(R.id.restart_buttonview);
        text = (TextView) findViewById(R.id.ghostText);
        label = (TextView) findViewById(R.id.gameStatus);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        wordFragment="";
        userTurn = random.nextBoolean();
        text.setText("");
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode>=KeyEvent.KEYCODE_A && keyCode<=KeyEvent.KEYCODE_Z)
        {
            wordFragment = wordFragment.concat(String.valueOf(event.getDisplayLabel()));
            wordFragment = wordFragment.toLowerCase();
            text.setText(wordFragment);
            computerTurn();
        }
        return super.onKeyUp(keyCode, event);
    }

    public void challenge(View view){
        if(wordFragment.length()>=4 && dictionary.isWord(wordFragment)){
            label.setText("You win as " + wordFragment + " is a valid word");
        }
        else if(wordFragment.length()<4){
            label.setText("Word length is small for a challenge");
        }
        else if(wordFragment.length()>=4){
            String nextWord = dictionary.getGoodWordStartingWith(wordFragment);
            if(nextWord==null){
                label.setText("You win as no more words can be formed from " + wordFragment);
            }
            else{
                label.setText("Computer win as " + nextWord + " can be formed");
            }
        }
    }


    private void computerTurn() {
        // Do computer turn stuff then make it the user's turn again
        userTurn = false;
        if(wordFragment.length()>=4 && dictionary.isWord(wordFragment)){
            label.setText("Computer wins");

        }
        else {
            String nextWord = dictionary.getGoodWordStartingWith(wordFragment);
            if(nextWord==null){
                label.setText("Computer challenged you. Computer Wins");

            }
            else{
            wordFragment = nextWord.substring(0,wordFragment.length()+1);
            text.setText(wordFragment);
            userTurn = true;
            label.setText(USER_TURN);}
        }
    }
}
