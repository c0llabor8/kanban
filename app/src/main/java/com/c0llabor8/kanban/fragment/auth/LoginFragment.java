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
import com.c0llabor8.kanban.databinding.FragmentLoginBinding;
import com.c0llabor8.kanban.fragment.base.BaseAuthFragment;

public class LoginFragment extends BaseAuthFragment {

  private FragmentLoginBinding binding;

  private OnClickListener loginOnClick = (View view) -> {
    final String username = binding.etUsername.getText().toString();
    final String password = binding.etPassword.getText().toString();
    loginUser(username, password);
  };

  /*
   *  Constructs a new Bundle to pass into a new fragment and returns that same fragment
   */
  public static LoginFragment newInstance() {
    Bundle args = new Bundle();

    LoginFragment fragment = new LoginFragment();
    fragment.setArguments(args);
    return fragment;
  }

  /*
   * Inflates the view into the fragment and returns the root
   */
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
    return binding.getRoot();
  }


  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    binding.signupCard.setOnClickListener((View v) -> listener.toSignUp());
    binding.authButton.setOnClickListener(loginOnClick);
  }

  /*
   * Shows message for the user when login fails
   */
  @Override
  public void postAuthFailure(String msg) {
    binding.authButton.setEnabled(true);
    binding.authMessage.setText(msg);
    binding.authMessage.setVisibility(View.VISIBLE);
  }

  /*
   * Enables the login button
   */
  @Override
  public void postAuthSuccess() {
    binding.authButton.setEnabled(true);
    binding.authMessage.setVisibility(View.GONE);
  }

  /*
   * Clears the authentication message and disables the login button
   */
  @Override
  public void preAuthAction() {
    binding.authMessage.setText("");
    binding.authMessage.setVisibility(View.GONE);
    binding.authButton.setEnabled(false);
  }
}
