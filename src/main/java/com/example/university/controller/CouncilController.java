package com.example.university.controller;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.Page;
import com.example.university.model.entity.Council;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CouncilSearch;
import com.example.university.service.CouncilService;
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
@RequestMapping("/api/council")
public class CouncilController {

    private final CouncilService councilService;

    @PostMapping("/page")
    public BaseResponse<Page<CouncilDto>> getList(@RequestBody PageRequest<CouncilSearch> request, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", councilService.getPage(request));
    }
}
