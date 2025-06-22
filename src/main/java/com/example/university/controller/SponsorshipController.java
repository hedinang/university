package com.example.university.controller;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreSponsorshipRequest;
import com.example.university.model.request.search.SponsorshipSearch;
import com.example.university.service.SponsorshipService;
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
@RequestMapping("/api/sponsorship")
public class SponsorshipController {

    private final SponsorshipService sponsorshipService;

    @PostMapping("/page")
    public BaseResponse<Page<SponsorshipDto>> getList(@RequestBody PageRequest<SponsorshipSearch> request, @AuthenticationPrincipal User user) {
        if (Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", sponsorshipService.getPage(request, user));
    }

    @PostMapping("/store")
    public BaseResponse<SponsorshipDto> getList(@RequestBody StoreSponsorshipRequest request, @AuthenticationPrincipal User user) {
        if (Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", sponsorshipService.store(request, user));
    }
}
