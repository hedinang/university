package com.example.university.service;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.TopicDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreTopicRequest;
import com.example.university.model.request.search.TopicSearch;

public interface TopicService {
    Page<TopicDto> getPage(PageRequest<TopicSearch> request);

    TopicDto store(StoreTopicRequest request, User user);
}
