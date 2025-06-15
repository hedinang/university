package com.example.university.controller;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.dto.Page;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CommentSearch;
import com.example.university.service.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/page")
    public BaseResponse<Page<CommentDto>> getList(@RequestBody PageRequest<CommentSearch> request, @AuthenticationPrincipal User user) {
        if (!List.of("TEACHER", "STUDENT").contains(user.getRoleCode()))
            return new BaseResponse<>(403, "Dont have permission", null);


        return new BaseResponse<>(200, "Get data successfully", commentService.getPage(request));
    }
//
//    @PostMapping("/store")
//    public BaseResponse<CouncilDto> getList(@RequestBody StoreCouncilRequest request, @AuthenticationPrincipal User user) {
//        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);
//
//        return new BaseResponse<>(200, "Get data successfully", councilService.store(request));
//    }
}
