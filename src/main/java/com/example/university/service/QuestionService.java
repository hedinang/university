package com.example.university.service;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.QuestionDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreQuestionRequest;
import com.example.university.model.request.search.QuestionSearch;

public interface QuestionService {
    Page<QuestionDto> getPage(PageRequest<QuestionSearch> request, User user);

    QuestionDto store(StoreQuestionRequest request, User user);
}
