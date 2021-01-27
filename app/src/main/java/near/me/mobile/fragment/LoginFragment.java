package near.me.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import near.me.mobile.R;
import near.me.mobile.activity.MainActivity;
import near.me.mobile.ui.login.LoginFormState;
import near.me.mobile.ui.login.LoginResult;
import near.me.mobile.ui.login.LoginViewModel;

public class LoginFragment extends Fragment {

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private ProgressBar loadingProgressBar;
    private LoginViewModel loginViewModel = new LoginViewModel();

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        emailField = getView().findViewById(R.id.et_email);
        passwordField = getView().findViewById(R.id.et_password);
        loginButton = getView().findViewById(R.id.btn_login);
        loadingProgressBar = getView().findViewById(R.id.loading);

        emailField.addTextChangedListener(afterTextChangedListener);
        passwordField.addTextChangedListener(afterTextChangedListener);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(LoginFormState loginForm) {
                loginButton.setEnabled(loginForm.isDataValid());
                if (loginForm.getEmailError() != null) {
                    emailField.setError(getString(loginForm.getEmailError()));
                }
                if (loginForm.getPasswordError() != null) {
                    passwordField.setError(getString(loginForm.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {

                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    Toast.makeText(getContext(), loginResult.getError(), Toast.LENGTH_LONG).show();
                }
                if (loginResult.getLoggedInUser() != null) {
                    Toast.makeText(getContext(), "Login success", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener( v -> {
            loadingProgressBar.setVisibility(View.VISIBLE);
            loginViewModel.login(emailField.getText().toString(), passwordField.getText().toString());
        });

    }

    TextWatcher afterTextChangedListener = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // ignore
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // ignore
        }
        public void afterTextChanged(Editable s) {
            loginViewModel.loginDataChanged(emailField.getText().toString(), passwordField.getText().toString());
        }
    };
}