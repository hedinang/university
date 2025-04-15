package com.example.university.controller;

import com.example.university.mapper.UserMapper;
import com.example.university.model.dto.UserDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.ChangePasswordRequest;
import com.example.university.model.request.LoginRequest;
import com.example.university.service.UserService;
import com.example.university.util.exception.ServiceException;
import com.example.university.util.response.BaseResponse;
import com.example.university.util.response.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public BaseResponse<Map<String, Object>> login(@RequestBody @Valid LoginRequest req) {
        Map<String, Object> response = userService.loginUser(req);

        if (response.isEmpty()) {
            return new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), "사용자 ID 또는 비빌번호가 틀렸습니다.다시 입력해 주세요", null);
        }

        return Response.toData(response);
    }

    @PostMapping("/verify")
    public BaseResponse<UserDto> verify(@AuthenticationPrincipal User user) {
        if (Objects.isNull(user)) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        UserDto dto = userMapper.toDto(user);

        return new BaseResponse<>(HttpStatus.OK.value(), "verify successfully", dto);
    }

    @PostMapping("/change-password")
    public BaseResponse<Object> changePasswordUser(@AuthenticationPrincipal User user, @RequestBody @Valid ChangePasswordRequest request) {
        try {
            return Response.toData(userService.changePassword(user.getUserId(), request));
        } catch (ServiceException e) {
            return Response.toError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        } catch (Exception e) {
            return Response.toError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
        }
    }
}
