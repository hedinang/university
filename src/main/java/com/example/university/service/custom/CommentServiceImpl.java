package com.example.university.service.custom;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.dto.Page;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CommentSearch;
import com.example.university.repository.CommentRepository;
import com.example.university.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public Page<CommentDto> getPage(PageRequest<CommentSearch> request) {
        List<CommentDto> commentDtoList = commentRepository.getList(request);
//        long totalItems = topicRepository.count(request);
        return new Page<>(commentDtoList, 100L);
    }
}
