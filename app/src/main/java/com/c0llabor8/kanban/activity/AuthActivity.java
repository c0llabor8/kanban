package com.c0llabor8.kanban.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.ActivityAuthBinding;
import com.c0llabor8.kanban.fragment.auth.LoginFragment;
import com.c0llabor8.kanban.fragment.auth.SignUpFragment;
import com.c0llabor8.kanban.fragment.base.BaseAuthFragment;
import com.parse.ParseUser;

public class AuthActivity extends AppCompatActivity implements
    BaseAuthFragment.AuthenticationFragmentListener {

  ActivityAuthBinding binding;
  LoginFragment loginFragment;
  SignUpFragment signUpFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);

    // Start MainActivity if our user is logged in
    if (ParseUser.getCurrentUser() != null) {
      startMainAndFinish();
    }

    loginFragment = LoginFragment.newInstance();
    signUpFragment = SignUpFragment.newInstance();

    setFragment(loginFragment);
  }

  private void setFragment(BaseAuthFragment fragment) {

    getSupportFragmentManager()
        .beginTransaction()
        .replace(binding.content.getId(), fragment)
        .commit();
  }

  /*
   * Method to start MainActivity and finish
   * */
  private void startMainAndFinish() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
    finish();
  }

  @Override
  public void onAttachFragment(@NonNull Fragment fragment) {
    if (fragment instanceof BaseAuthFragment) {
      BaseAuthFragment authFragment = (BaseAuthFragment) fragment;
      authFragment.setOnAuthenticationListener(this);
    }
  }

  @Override
  public void onAuthenticated() {
    startMainAndFinish();
  }

  @Override
  public void toSignUp() {
    setFragment(signUpFragment);
  }

  @Override
  public void toLogin() {
    setFragment(loginFragment);
  }
}
