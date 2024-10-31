package org.sopt.diary.service;

import org.sopt.diary.repository.UserEntity;

public class User {
    private Long id;
    private String loginId;
    private String password;
    private String nickname;

    public User (
            Long id, String loginId, String password, String nickname
    ) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }

    public static User fromUserEntity(UserEntity userEntity) {
        return new User(
                userEntity.getId(),
                userEntity.getLoginId(),
                userEntity.getPassword(),
                userEntity.getNickname()
        );
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
