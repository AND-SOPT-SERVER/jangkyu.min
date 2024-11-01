package org.sopt.diary.api.dto.user.response;

public class SignInResponse {
    private final Long userId;

    // 필드가 하나인 DTO 클래스는 기본생성자가 있어야 한다.
    public SignInResponse() {
        userId = null;
    }

    public SignInResponse(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
