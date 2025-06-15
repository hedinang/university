package com.example.university.repository.custom;

import com.example.university.model.dto.TopicDto;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.TopicSearch;

import java.util.List;

public interface CustomTopicRepository {
    List<TopicDto> getList(PageRequest<TopicSearch> request);

//    Long count(PageRequest<TopicSearch> request);
}
