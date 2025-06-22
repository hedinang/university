package com.example.university.controller;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.ResetUnreadRequest;
import com.example.university.service.SeenService;
import com.example.university.util.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/seen")
public class SeenController {
    private final SeenService seenService;

    @PostMapping("/reset-unread")
    public BaseResponse<Page<SponsorshipDto>> resetUnread(@RequestBody ResetUnreadRequest request, @AuthenticationPrincipal User user) {
        if (Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        seenService.resetUnread(request, user);
        return new BaseResponse<>(200, "Get data successfully", null);
    }
}
