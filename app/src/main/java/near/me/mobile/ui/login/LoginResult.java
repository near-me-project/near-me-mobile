package near.me.mobile.ui.login;

import androidx.annotation.Nullable;

public class LoginResult {
    @Nullable
    private String loggedInUser;
    @Nullable
    private Integer error;

    public LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    public LoginResult(@Nullable String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    @Nullable
    public String getLoggedInUser() {
        return loggedInUser;
    }

    @Nullable
    public Integer getError() {
        return error;
    }
}