package org.sopt.diary.service;

import org.sopt.diary.repository.UserEntity;
import org.sopt.diary.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void signUp(User user) {
        userRepository.save(
                new UserEntity(
                        user.getLoginId(),
                        user.getPassword(),
                        user.getNickname()
                )
        );
    }
}
