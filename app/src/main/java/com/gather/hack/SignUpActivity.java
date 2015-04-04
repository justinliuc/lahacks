package com.gather.hack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.*;

import java.text.ParseException;


public class SignUpActivity extends ActionBarActivity {

    public String uname;
    public String pw;
    public Context thisContext = this;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = pref.edit();
    }

    public void register(View view)
    {
        EditText usernameField = (EditText) findViewById(R.id.username);
        EditText passwordField = (EditText) findViewById(R.id.password1);
        EditText passwordConfirmField = (EditText) findViewById(R.id.password2);
        EditText phoneNumField = (EditText) findViewById(R.id.phonenum);

        ParseUser user = new ParseUser();
        uname = usernameField.getText().toString();
        pw = passwordField.getText().toString();
        user.setUsername(uname);
        user.setPassword(pw);
        // other fields can be set just like with ParseObject
        user.put("phone", phoneNumField.getText().toString());
        user.signUpInBackground(new SignUpCallback() {
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    editor.putString("username", uname);
                    editor.putString("password", pw);
                    editor.commit();

                    Intent intent = new Intent(thisContext, EventsActivity.class);
                    startActivity(intent);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
