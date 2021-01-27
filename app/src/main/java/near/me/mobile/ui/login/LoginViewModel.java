package near.me.mobile.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import near.me.mobile.R;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginService loginService = new LoginService(loginResult);

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    public LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        loginService.login(username, password);
    }

    public void loginDataChanged(String email, String password) {
        if (!emailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!passwordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    private boolean passwordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean emailValid(String email) {
        if (email == null || email.isEmpty()) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
