package com.example.university.service;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.dto.Page;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreCommentRequest;
import com.example.university.model.request.search.CommentSearch;

public interface CommentService {
    Page<CommentDto> getPage(PageRequest<CommentSearch> request);

    CommentDto store(StoreCommentRequest request, User user);
}
