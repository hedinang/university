package com.example.university.controller;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.TopicDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreCouncilRequest;
import com.example.university.model.request.StoreTopicRequest;
import com.example.university.model.request.search.TopicSearch;
import com.example.university.service.TopicService;
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
@RequestMapping("/api/topic")
public class TopicController {

    private final TopicService topicService;

    @PostMapping("/page")
    public BaseResponse<Page<TopicDto>> getList(@RequestBody PageRequest<TopicSearch> request, @AuthenticationPrincipal User user) {
        if (!List.of("TEACHER", "STUDENT").contains(user.getRoleCode()))
            return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", topicService.getPage(request));
    }

    @PostMapping("/store")
    public BaseResponse<TopicDto> getList(@RequestBody StoreTopicRequest request, @AuthenticationPrincipal User user) {
//        if (!Objects.equals(user.getRoleCode(), "ADMIN")) return new BaseResponse<>(403, "Dont have permission", null);

        return new BaseResponse<>(200, "Get data successfully", topicService.store(request, user));
    }
}
