package com.gather.hack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;


public class MainActivity extends ActionBarActivity {

    public Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "rlHChGDkkSHCUy9E4rLL7P064XpQm4iaHG2tWdub", "WJKWdbLyVXhT8aGBcIoz8eaagx3PQ7C9gFRPJZHX");
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        if(userInfo.contains("username"))
        {
            Log.w("mainActivity", "usernameExists");
            //that means account already created, load the info and go to event page
            String name = userInfo.getString("username", "no name");
            String pw = userInfo.getString("password", "no pw");
            if ( pw.equals("no pw") || name.equals("no name") ) // someone fucked up
            {
                // go to new account intent
            }
            ParseUser.logInInBackground(name, pw, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if ( parseUser != null ) {
                        Intent intent = new Intent(thisContext, EventsActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(thisContext, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        else
        {
            // files does not already exist, that means account never created go to pick between
            // sign up and login
            Log.w("mainActivity", "no info stored on account");
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }



    }


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
