package org.sopt.diary.api;

import org.sopt.diary.api.dto.user.request.SignInRequest;
import org.sopt.diary.api.dto.user.request.SignUpRequest;
import org.sopt.diary.api.dto.user.response.SignInResponse;
import org.sopt.diary.service.User;
import org.sopt.diary.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.signUp(
                new User(
                        null,
                        signUpRequest.getLoginId(),
                        signUpRequest.getPassword(),
                        signUpRequest.getNickname()
                )
        );
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest signInRequest) {
        User user = userService.signIn(
                signInRequest.getLoginId(),
                signInRequest.getPassword()
        );

        SignInResponse signInResponse = new SignInResponse(user.getId());

        return ResponseEntity.ok(signInResponse);
    }
}