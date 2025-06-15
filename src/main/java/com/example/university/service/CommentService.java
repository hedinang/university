package com.example.university.service;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.dto.Page;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CommentSearch;

public interface CommentService {
    Page<CommentDto> getPage(PageRequest<CommentSearch> request);
}
