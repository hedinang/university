package com.example.university.service.custom;

import com.example.university.model.dto.CommentDto;
import com.example.university.model.dto.Page;
import com.example.university.model.entity.Comment;
import com.example.university.model.entity.Question;
import com.example.university.model.entity.Seen;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreCommentRequest;
import com.example.university.model.request.search.CommentSearch;
import com.example.university.repository.CommentRepository;
import com.example.university.repository.QuestionRepository;
import com.example.university.repository.SeenRepository;
import com.example.university.service.CommentService;
import com.example.university.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final SeenRepository seenRepository;

    public Page<CommentDto> getPage(PageRequest<CommentSearch> request) {
        List<CommentDto> commentDtoList = commentRepository.getList(request);
//        long totalItems = topicRepository.count(request);
        return new Page<>(commentDtoList, 100L);
    }

    @Transactional
    public CommentDto store(StoreCommentRequest request, User user) {
        Comment comment = new Comment();

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (request.getCommentId() != null) {
            comment = commentRepository.findById(request.getCommentId()).orElseGet(null);

            if (comment == null) return null;

            comment.setStatus(request.getStatus());
            comment = commentRepository.save(comment);
        } else {
            comment.setCommentId(StringUtil.generateId());
            comment.setQuestionId(request.getQuestionId());
            comment.setContent(request.getContent());
            comment.setUserId(user.getUserId());
            comment.setStatus("ACTIVE");
            comment = commentRepository.save(comment);

            Question question = questionRepository.findById(comment.getQuestionId()).orElseGet(null);

            if (question != null) {
                question.setLastCommentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                questionRepository.save(question);
            }

            Seen seen = seenRepository.findByQuestionIdAndUserIdNot(comment.getQuestionId(), user.getUserId());

            if (seen != null) {
                seen.setUnread(seen.getUnread() + 1);
                seenRepository.save(seen);
            }
        }

        return new CommentDto(comment.getCommentId(), comment.getQuestionId(), comment.getUserId(), comment.getContent(),
                date, user.getName());
    }
}
