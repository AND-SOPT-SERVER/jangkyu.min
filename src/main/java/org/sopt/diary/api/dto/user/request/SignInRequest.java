package org.sopt.diary.api.dto.user.request;

public class SignInRequest {
    private final String loginId;
    private final String password;

    public SignInRequest(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
}
