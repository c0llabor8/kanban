package com.c0llabor8.kanban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity {

  ActivityLoginBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser != null) {
      final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
    }

    binding.authButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        final String username = binding.etUsername.getText().toString();
        final String password = binding.etPassword.getText().toString();
        login(username, password);
      }
    });

    binding.authMessage.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        final Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }

  private void login(String username, String password) {
    ParseUser.logInInBackground(username, password, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if (e == null) {
          Log.d("LoginActivity", "Login successful!");
          final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Log.e("LoginActivity", "Login failure.", e);
          e.printStackTrace();
        }
      }
    });

    binding.tvSignUp.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
      }
    });
  }
}
