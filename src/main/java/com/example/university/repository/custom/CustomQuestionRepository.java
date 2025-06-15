package com.example.university.repository.custom;

import com.example.university.model.dto.QuestionDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.QuestionSearch;

import java.util.List;

public interface CustomQuestionRepository {
    List<QuestionDto> getList(PageRequest<QuestionSearch> request, User user);
    Long count(PageRequest<QuestionSearch> request, User user);
}
