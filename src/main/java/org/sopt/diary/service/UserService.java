package org.sopt.diary.service;

import jakarta.persistence.EntityNotFoundException;
import org.sopt.diary.api.dto.user.request.SignInRequest;
import org.sopt.diary.api.dto.user.request.SignUpRequest;
import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(SignUpRequest signUpRequest) {
        userRepository.save(
                new UserEntity(
                        signUpRequest.getLoginId(),
                        signUpRequest.getPassword(),
                        signUpRequest.getNickname()
                )
        );
    }

    public UserEntity signIn(SignInRequest signInRequest) {
        return userRepository.findUserEntityByLoginIdAndPassword(
                signInRequest.getLoginId(), signInRequest.getPassword()
                ).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 입니다."));
    }
}
