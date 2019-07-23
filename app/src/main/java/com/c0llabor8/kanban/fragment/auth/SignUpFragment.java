package com.c0llabor8.kanban.fragment.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import com.c0llabor8.kanban.R;
import com.c0llabor8.kanban.databinding.FragmentSignupBinding;
import com.c0llabor8.kanban.fragment.base.BaseAuthFragment;

public class SignUpFragment extends BaseAuthFragment {

  private FragmentSignupBinding binding;
  //assigns each component to the user input
  private OnClickListener signUpOnClick = (View view) -> {
    final String username = binding.etUsername.getText().toString();
    final String password = binding.etPassword.getText().toString();
    final String email = binding.etEmail.getText().toString();
    //registers user with the given information
    registerUser(username, email, password);
  };

  //creates new fragment for sign up
  public static SignUpFragment newInstance() {
    Bundle args = new Bundle();

    SignUpFragment fragment = new SignUpFragment();
    fragment.setArguments(args);
    return fragment;
  }

  /**
   * inflates the fragment for signUp to display
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    //takes user back to login screen
    binding.loginCard.setOnClickListener((View v) -> listener.toLogin());
    binding.authButton.setOnClickListener(signUpOnClick);
  }

  /**
   * displays message when the signup failed
   */
  @Override
  protected void postAuthFailure(String msg) {
    binding.authButton.setEnabled(true);
    binding.authMessage.setText(msg);
    binding.authMessage.setVisibility(View.VISIBLE);
  }

  /**
   * when signup is successful, message will not display and lets user sign up
   */
  @Override
  protected void postAuthSuccess() {
    binding.authButton.setEnabled(true);
    binding.authMessage.setVisibility(View.GONE);
  }

  /**
   *  disable the button and sets text to empty string
   */
  @Override
  public void preAuthAction() {
    binding.authMessage.setText("");
    binding.authMessage.setVisibility(View.GONE);
    binding.authButton.setEnabled(false);
  }
}
