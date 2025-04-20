package com.example.university.controller;

import com.example.university.model.entity.User;
import com.example.university.model.request.search.FullUserSearch;
import com.example.university.repository.UserRepository;
import com.example.university.service.UserService;
import com.example.university.util.response.BaseResponse;
import com.example.university.util.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;
    //    private final LanguageService languageService;
    private final UserService userService;
//    private final ResourceService resourceService;
//    private final UserMapper userMapper;

    @GetMapping("/existed-user/{userId}")
    public BaseResponse<Boolean> checkExistedUser(@PathVariable("userId") String userId, @AuthenticationPrincipal User user) {
        if (Objects.isNull(user)) {
            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return Response.toData(userRepository.existsByUserId(userId.toUpperCase()));
    }
//
//    @GetMapping("/userInfo")
//    public BaseResponse<UserDto> userInfo(@AuthenticationPrincipal User user) {
//        if (Objects.isNull(user)) {
//            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//        }
//
//        UserDto dto = userMapper.toDto(user);
//        return Response.toData(dto);
//    }
//
//    @PostMapping("/list")
//    public BaseResponse<List<FullUserDto>> list(@RequestBody List<String> userIds, @AuthenticationPrincipal User user) {
//        if (Objects.isNull(user)) {
//            return new BaseResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
//        }
//
//        List<User> users = userService.list(userIds);
//
//        if (users != null && !users.isEmpty()) {
//            return Response.toData(users.stream().map(userMapper::toFullDto).toList());
//        }
//
//        return new BaseResponse<>(HttpStatus.OK.value(), "No data found");
//    }
//
//    @PostMapping("/profile")
//    public BaseResponse<User> updateProfile(@AuthenticationPrincipal User user, @RequestBody UserRequest req) {
//        return Response.toData(userService.updateUser(user, req));
//    }

    @GetMapping("/getMe")
    public BaseResponse<Object> getMe(@AuthenticationPrincipal User user) {
        return Response.toData(userService.getMe(user.getUserId()));
    }

//    @PostMapping("/logout")
//    public BaseResponse<Object> logoutUser(@RequestBody LogoutRequest request, @AuthenticationPrincipal User user) {
//        if (userService.logout(user.getUserId(), request)) {
//            return Response.toData(user.getUserId());
//        }
//        return Response.toError(HttpStatus.BAD_REQUEST.value(), "logout fail");
//    }
//
//    @PostMapping("/save-me")
//    public BaseResponse<Object> saveMe(@AuthenticationPrincipal User user, @RequestBody UserRequest userRequest) {
//        User currentUser = userRepository.findByUserId(user.getUserId()).orElse(null);
//        if (currentUser == null) {
//            return Response.toError(404, "user not found");
//        }
//        if (userRequest != null) {
//            User isUpdated = userService.updateUser(currentUser, userRequest);
//
//            if (isUpdated == null) {
//                return Response.toError(404, "user not saved");
//            }
//
//            currentUser.setLanguage(userRequest.getLanguage());
//
//            Me me = new Me();
//            me.setUser(currentUser);
//            String language = currentUser.getLanguage() != null ? currentUser.getLanguage() : "kr";
//            me.setLanguageMap(languageService.getAll(language));
//            return Response.toData(me);
//        } else {
//            return Response.toError(400, "Bad Request");
//        }
//    }
//
//    @PostMapping("/forget-id")
//    public BaseResponse<String> forgetUserId(@RequestBody UserSearch forgetUserRequest) {
//        String userId = userService.forgetUserId(forgetUserRequest);
//
//        if (userId == null) {
//            return new BaseResponse<>(403, "Your information is not correct!");
//        } else {
//            return new BaseResponse<>(200, "Get successfully", userId);
//        }
//    }
//
//    @PostMapping("/forget-password")
//    public BaseResponse<String> forgetUserPassword(@RequestBody UserSearch forgetUserRequest) {
//        String password = userService.forgetUserPassword(forgetUserRequest);
//
//        if (password == null) {
//            return new BaseResponse<>(403, "Your information is not correct!");
//        } else {
//            return new BaseResponse<>(200, "Get successfully", password);
//        }
//    }
//
//    @PostMapping("/upload-profile-image")
//    public BaseResponse<Object> upload(@RequestBody UploadFileRequest req, @AuthenticationPrincipal User user) {
//        String url = resourceService.uploadProfileImage(req, user);
//
//        if (url == null) {
//            return new BaseResponse<>(403, "pls update an image");
//        } else {
//            return Response.toData(url);
//        }
//    }
//
//    @PostMapping("/remove-profile-image")
//    public BaseResponse<Object> remove(@AuthenticationPrincipal User user) {
//        resourceService.removeFile(user);
//        return new BaseResponse<>(200, "Remove avatar successfully", null);
//    }
//
//    @PostMapping("/info-user-list")
//    public BaseResponse<List<BasicUserInfo>> basicUserInfoList(@RequestBody List<String> userCodes) {
//        List<BasicUserInfo> basicUserInfos = userService.findBasicUserInfoByUserCodeIn(userCodes);
//
//        if (basicUserInfos != null && !basicUserInfos.isEmpty()) {
//            return new BaseResponse<>(200, "Get data successfully", basicUserInfos);
//        } else {
//            return new BaseResponse<>(400, "Get data unsuccessfully", null);
//        }
//    }
//
    @PostMapping("/admin/page")
    public BaseResponse<List<User>> basicUserInfoList(@RequestBody FullUserSearch request, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", userService.getPage(request));
    }

    @PostMapping("/admin/create")
    public BaseResponse<User> createUser(@RequestBody User request, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", userService.create(request, user));
    }

    @PostMapping("/admin/update")
    public BaseResponse<User> updateUser(@RequestBody User request, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", userService.update(request, user));
    }
//
//    @PostMapping("/admin/remove/{userId}")
//    public BaseResponse<String> removeUser(@PathVariable String userId, @AuthenticationPrincipal User user) {
//        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);
//
//        Boolean rs = userService.remove(userId, user);
//
//        if (rs) {
//            return new BaseResponse<>(200, "Remove successfully", null);
//        } else {
//            return new BaseResponse<>(400, "Remove unsuccessfully", null);
//        }
//    }
//
//    @PostMapping("/admin/reset-password/{userId}")
//    public BaseResponse<String> resetPassword(@PathVariable String userId, @AuthenticationPrincipal User user) {
//        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);
//
//        userService.resetPassword(userId, user);
//        return new BaseResponse<>(200, "Delete user successfully", null);
//    }
}
