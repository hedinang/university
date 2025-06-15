package com.example.university.repository.custom;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CommentSearch;

import java.util.List;

public interface CustomCommentRepository {
    List<CommentDto> getList(PageRequest<CommentSearch> request);
}
