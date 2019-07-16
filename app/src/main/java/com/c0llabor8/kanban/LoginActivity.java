package com.c0llabor8.kanban;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.etUsername);
        passwordInput = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.btnLogin);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                //login(username, password);
            }
        });

    }


    /**private void login (String username, String password) {
     if (ParseUser.getCurrentUser() != null) {
     final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
     startActivity(intent);
     finish();
     } else {
     ParseUser.logInInBackground(username, password, new LogInCallback() {
    @Override public void done(ParseUser user, ParseException e) {
    if (e == null) {
    Log.d("LoginActivity", "login successful!");
    final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
    finish();
    }  else {
    Log.e("LoginActivity", "Login failure");
    e.printStackTrace();
    }
    }
    });
     }
     }
     **/
}
