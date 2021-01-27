package near.me.mobile.ui.login;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import org.springframework.http.ResponseEntity;

import near.me.mobile.shared.Constants;
import near.me.mobile.shared.RestClient;

public class LoginService {
    private MutableLiveData<LoginResult> loginResult;

    public LoginService(MutableLiveData<LoginResult> loginResult) {
        this.loginResult = loginResult;
    }

    public void login(String username, String password) {
        new AuthenticateUserTask(loginResult).execute(new LoginRequestModel(username, password));
    }
}

class AuthenticateUserTask extends AsyncTask<LoginRequestModel, Void, LoginResponseModel> {

    private RestClient restClient;
    private MutableLiveData<LoginResult> loginResult;

    public AuthenticateUserTask(MutableLiveData<LoginResult> loginResult) {
        this.loginResult = loginResult;
        restClient = new RestClient();
    }

    @Override
    protected LoginResponseModel doInBackground(LoginRequestModel... loginRequestModels) {
        LoginRequestModel loginRequestModel = loginRequestModels[0];
        System.out.println("[LoginService] outcome request >>> " + loginRequestModel);
        ResponseEntity<String> responseEntity = restClient.executePostRequest(Constants.URL.USER_WS_LOGIN, loginRequestModel, String.class);
        String token = responseEntity.getHeaders().get("Token").get(0);
        String userId = responseEntity.getHeaders().get("UserId").get(0);
        return new LoginResponseModel(userId, token);
    }

    @Override
    protected void onPostExecute(LoginResponseModel loginResponseModel) {
        super.onPostExecute(loginResponseModel);
        System.out.println("Response: " + loginResponseModel);
        loginResult.setValue(new LoginResult(loginResponseModel.toString()));
    }
}

class LoginResponseModel {
    private String userId;
    private String token;

    public LoginResponseModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public LoginResponseModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}

class LoginRequestModel {

    private String email;
    private String password;

    public LoginRequestModel() {
    }

    public LoginRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
