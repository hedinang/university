package com.example.university.controller;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.QuestionDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreQuestionRequest;
import com.example.university.model.request.search.QuestionSearch;
import com.example.university.service.QuestionService;
import com.example.university.util.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/page")
    public BaseResponse<Page<QuestionDto>> getList(@RequestBody PageRequest<QuestionSearch> request, @AuthenticationPrincipal User user) {
        if (!List.of("TEACHER", "STUDENT").contains(user.getRoleCode()))
            return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", questionService.getPage(request, user));
    }

    @PostMapping("/store")
    public BaseResponse<QuestionDto> getList(@RequestBody StoreQuestionRequest request, @AuthenticationPrincipal User user) {
        if (!Objects.equals(user.getRoleCode(), "STUDENT")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", questionService.store(request, user));
    }
}
