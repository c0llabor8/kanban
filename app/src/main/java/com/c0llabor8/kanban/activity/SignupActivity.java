package com.c0llabor8.kanban.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ActivitySignupBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {

  ActivitySignupBinding binding;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);

    binding.authButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        final String username = binding.etUsername.getText().toString();
        final String password = binding.etPassword.getText().toString();
        signUp(username, password);
      }
    });

  }

  private void signUp(String username, String password) {
    ParseUser user = new ParseUser();
    user.setUsername(username);
    user.setPassword(password);

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if (e == null) {
          Log.d("SignupActivity", "sign up successful");
          final Intent intent = new Intent(SignupActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Log.d("SignupActivity", "sign up fail");
        }
      }
    });
  }
}
