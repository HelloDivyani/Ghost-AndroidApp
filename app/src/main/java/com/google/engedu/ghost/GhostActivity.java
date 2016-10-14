package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity {
    TextView gameText;
    TextView label;
    TextView status;
    String store;
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private SimpleDictionary dictionary;
    private boolean userTurn = false;
    String text;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);


        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        gameText = (TextView) findViewById(R.id.ghostText);
        label = (TextView) findViewById(R.id.gameStatus);
        text = gameText.getText().toString();
        text = dictionary.getAnyWordStartingWith("");
        gameText.setText(text);
        onStart(null);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //SimpleDictionary simpleCheck= new SimpleDictionary();
        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            text = gameText.getText().toString();
            text = text.concat(String.valueOf(event.getDisplayLabel()).toLowerCase());
            gameText.setText(text);
            computerTurn();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);

        }


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

    private void computerTurn() {
        // Collections.binarySearch(prefix,comparator);

        TextView label = (TextView) findViewById(R.id.gameStatus);
        text = gameText.getText().toString();
        int len = text.length();
        // SimpleDictionary simplecheck=new SimpleDictionary();
        if (dictionary.isWord(text) && len >= 4) {
            label.setText("Computer's Victory,You Lost!!!");

        } else {

            store = dictionary.getAnyWordStartingWith(text);
            if (store == null) {
                label.setText("Do not bluff the computer");

            } else {
                text = store.substring(0, text.length() + 1);
                gameText.setText(text);
            }
            userTurn = true;
            label.setText(USER_TURN);
        }

    }

    public boolean Challenge(View v) {
        int len = text.length();
        text = gameText.getText().toString();
        String s = dictionary.getAnyWordStartingWith(text);
        if (dictionary.isWord(text) && len >= 4) {
            label.setText("you won computer lost!");
        } else {
            if(s == null){
                label.setText("You lost no such word exists");
            }else {
                label.setText("computer victory" + "actual word is:" + s );
            }
//            gameText.setText(dictionary.getAnyWordStartingWith(text));

            //find all possible words from dictionary

        }
        return true;
    }


    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = true;
        TextView label = (TextView) findViewById(R.id.gameStatus);
        String s = dictionary.getAnyWordStartingWith("");
        gameText.setText(s);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
}
