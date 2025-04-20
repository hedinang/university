package com.example.university.service.custom;

import com.example.university.model.dto.CustomUserDetails;
import com.example.university.model.entity.User;
import com.example.university.model.request.ChangePasswordRequest;
import com.example.university.model.request.LoginRequest;
import com.example.university.model.request.UserRequest;
import com.example.university.model.request.search.FullUserSearch;
import com.example.university.model.request.search.UserSearch;
import com.example.university.repository.UserRepository;
import com.example.university.service.UserService;
import com.example.university.util.StringUtil;
import com.example.university.util.constant.EntityStatus;
import com.example.university.util.constant.ErrorCode;
import com.example.university.util.date.DateUtil;
import com.example.university.util.exception.ServiceException;
import com.example.university.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    public Map<String, Object> loginUser(LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.getUserId(), request.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            User user = userDetails.getUser();
            user.setUserId(userDetails.getUser().getUserId());
            String jwt = tokenProvider.generateAccessToken(user);
//            user = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new ServiceException("user not found"));
//            String accessToken = user.getAccessToken();
//
//
//
//            if (!tokenProvider.validateToken(user.getAccessToken())) {
//                user.setAccessToken(jwt);
//                userRepository.save(user);
//                accessToken = jwt;
//            }

            Map<String, Object> response = new HashMap<>();
            response.put("id", user.getUserId());
            response.put("token", jwt);
            return response;
        } catch (Exception e) {
            log.error("login with email {} error: {}", request.getUserId(), e.getMessage());
            return Collections.emptyMap();
        }
    }

    @Override
    public boolean logout(String userId) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAccessToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new ServiceException(ErrorCode.E404.code(), "User not found"));
        return new CustomUserDetails(user);
    }

    @Override
    public List<User> createUser(List<User> users) {
        return userRepository.saveAll(users);
    }

    @Override
    public User updateUser(User user, UserRequest userRequest) {
        user.setLanguage(userRequest.getLanguage());
        return userRepository.save(user);
    }

    @Override
    public String forgetUserId(UserSearch userSearch) {
        // verify request
        if (userSearch.getName().isEmpty() || userSearch.getPhone().isEmpty()
                || userSearch.getEmail().isEmpty() || userSearch.getBirthday() == null) {
            return null;
        }
        return null;

//        List<User> results = userRepository.getUserList(userSearch).getMappedResults();
//
//        if (results.isEmpty()) {
//            return null;
//        } else {
//            return results.get(0).getUserId();
//        }
    }

    @Override
    public String forgetUserPassword(UserSearch userSearch) {
        if (Objects.equals(userSearch.getName(), "dung")) {
            return "dung";
        } else {
            return null;
        }
    }

    @Override
    public List<User> list(List<String> userIds) {
        return userRepository.findByUserIdIn(userIds);
    }

    @Override
    public List<User> getPage(FullUserSearch request) {
//        return userRepository.getPage(request);
        return userRepository.findAll();
    }

    @Override
    public User changePassword(String userId, ChangePasswordRequest request) {
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            throw new ServiceException("User not found");
        }
        User user = userOptional.get();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new ServiceException("Current password is incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new ServiceException("New password cannot be the same as the old password");
        }

        String newEncodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newEncodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User getMe(String userId) {
        return userRepository.findByUserId(userId).get();
    }

    @Override
    public User create(User request, User user) {
        if (request.getUsername() == null || request.getUsername().isEmpty() || request.getName() == null || request.getName().isEmpty()) {
            throw new ServiceException(ErrorCode.E401.code(), "UserId or username must be not null");
        }

//        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d-]+$";
//
//        if (!request.getUserId().trim().matches(regex)) {
//            throw new ServiceException(ErrorCode.E401.code(), "UserId must contain both letters and numbers");
//        }

        request.setUsername(request.getUsername().toUpperCase());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("Qwer123!");
        request.setPassword(encodedPassword);

        Optional<User> existUserOptional = userRepository.findByUsername(request.getUsername().toUpperCase());

//        if (existUserOptional.isPresent()) {
//            if (Boolean.FALSE.equals(existUserOptional.get().getIsActive())) {
//                updateUserStatus(List.of(request.getUserId()), EventType.UPDATE.toString(), user.getAccessToken());
//            }
//
//            existUserOptional.get().setUserCode(request.getUserId().toUpperCase());
//            existUserOptional.get().setName(request.getName().toUpperCase());
//            existUserOptional.get().setUserStatusCode(EntityStatus.ACTIVE.toString());
//            existUserOptional.get().setModifiedBy(user.getUserId());
//            existUserOptional.get().setModifiedAt(DateUtil.dateToString(new Date()));
//            existUserOptional.get().setIsActive(true);
//
//            try {
//                return userRepository.save(existUserOptional.get());
//            } catch (Exception e) {
//                log.error("Service error while saving existed user: {}", e.getMessage());
//                return null;
//            }
//        }

        request.setUserId(StringUtil.generateId());
        request.setName(request.getName().toUpperCase());
        request.setStatus(EntityStatus.ACTIVE.toString());
//        request.setBirthday(DateUtil.dateToString(new Date()));
        request.setCreatedBy(user.getUserId());
        request.setCreatedAt(DateUtil.dateToString(new Date()));
        request.setUpdatedBy(user.getUserId());
        request.setUpdatedAt(DateUtil.dateToString(new Date()));

        try {
            return userRepository.save(request);
        } catch (Exception e) {
            log.error("Service error while saving user: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public User update(User request, User user) {
        if (request.getUsername() == null || request.getUsername().isEmpty() || request.getName() == null || request.getName().isEmpty()) {
            throw new ServiceException(ErrorCode.E401.code(), "UserId or username must be not null");
        }

        User oldUser = userRepository.findByUsername(request.getUsername().toUpperCase()).orElse(null);

        if (oldUser == null) {
            throw new ServiceException(ErrorCode.E401.code(), String.format("UserId %s is not existed", request.getUserId()));
        }

        oldUser.setStatus(request.getStatus());
//        oldUser.setBirthday(request.getBirthday());
        oldUser.setRoleCode(request.getRoleCode());
        oldUser.setName(request.getName());
        oldUser.setEmail(request.getEmail());
        oldUser.setPhone(request.getPhone());
        oldUser.setUpdatedBy(user.getUserId());
        oldUser.setUpdatedAt(DateUtil.dateToString(new Date()));
        return userRepository.save(oldUser);
    }
}