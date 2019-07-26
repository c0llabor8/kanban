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

/*
 * This class is the host activity for the sign up and login fragments
 * */

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

    // Create new instances of the login and signup fragments
    loginFragment = LoginFragment.newInstance();
    signUpFragment = SignUpFragment.newInstance();

    // Start by displaying the login form fragment
    setFragment(loginFragment);
  }

  /*
   * This method is used to switch the active fragment within the activity
   * @param fragment The fragment we are switching to
   * */
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

  /*
   * Have this class listen to the currently attached BaseAuthFragment
   * */
  @Override
  public void onAttachFragment(@NonNull Fragment fragment) {
    if (fragment instanceof BaseAuthFragment) {
      BaseAuthFragment authFragment = (BaseAuthFragment) fragment;
      authFragment.setOnAuthenticationListener(this);
    }
  }

  /*
   * This method is called once the user is successfully authenticated
   * */
  @Override
  public void onAuthenticated() {
    startMainAndFinish();
  }

  /*
   * This method switches the current fragment to the sign up form
   * */
  @Override
  public void toSignUp() {
    setFragment(signUpFragment);
  }

  /*
   * This method switches the current fragment to the login form
   * */
  @Override
  public void toLogin() {
    setFragment(loginFragment);
  }
}
