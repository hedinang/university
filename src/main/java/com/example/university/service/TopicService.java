package com.example.university.service;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.Page;
import com.example.university.model.dto.TopicDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreTopicRequest;
import com.example.university.model.request.search.CouncilSearch;
import com.example.university.model.request.search.TopicSearch;

import java.util.List;

public interface TopicService {
    Page<TopicDto> getPage(PageRequest<TopicSearch> request);
    List<TopicDto> getAll(TopicSearch request);

    TopicDto store(StoreTopicRequest request, User user);
}
