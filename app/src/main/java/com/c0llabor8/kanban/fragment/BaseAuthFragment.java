package com.c0llabor8.kanban.fragment;

import androidx.fragment.app.Fragment;
import com.parse.ParseException;
import com.parse.ParseUser;

public abstract class BaseAuthFragment extends Fragment {

  // Interface to communicate authentication status
  AuthenticationFragmentListener listener;

  public void setOnAuthenticationListener(AuthenticationFragmentListener listener) {
    this.listener = listener;
  }

  /*
   * Method called once authentication is unsuccessful
   * */
  abstract void postAuthFailure(String msg);

  /*
   * Method called once authentication is successful
   * */
  abstract void postAuthSuccess();

  /*
   * Method called before authentication
   * */
  abstract void preAuthAction();


  /*
   * Method to login an existing user and call all proper pre and post hooks
   * @param username username of the account being logged in
   * @param password password of the user being logged in
   * */
  void loginUser(String username, String password) {

    preAuthAction();

    ParseUser.logInInBackground(username, password, (ParseUser user, ParseException e) -> {
      if (e != null || user == null) {
        postAuthFailure(e.getMessage());
      } else {
        postAuthSuccess();
        listener.onAuthenticated();
      }
    });
  }

  /*
   * Method to register a new user and call all proper pre and post hooks
   * @param username username of the account being created
   * @param email    email of the account being created
   * @param password password of the user being created
   * */
  void registerUser(String username, String email, String password) {

    preAuthAction();

    ParseUser user = new ParseUser();

    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(password);

    user.signUpInBackground((ParseException e) -> {
      if (e != null) {
        postAuthFailure(e.getMessage());
      } else {
        postAuthSuccess();
        listener.onAuthenticated();
      }
    });
  }

  /*
   * Interface implemented so parent Activity to be notified on authentication status
   * */
  public interface AuthenticationFragmentListener {

    void onAuthenticated();

    void toSignUp();

    void toLogin();
  }
}