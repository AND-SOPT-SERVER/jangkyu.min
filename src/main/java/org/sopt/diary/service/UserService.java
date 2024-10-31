package org.sopt.diary.service;

import jakarta.persistence.EntityNotFoundException;
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

    public User signIn(String userId, String password) {
        UserEntity userEntity = userRepository.findUserEntityByLoginIdAndPassword(userId, password)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 유저 입니다."));

        return User.fromUserEntity(userEntity);
    }
}
