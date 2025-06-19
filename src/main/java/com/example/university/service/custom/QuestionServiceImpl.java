package com.example.university.service.custom;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.QuestionDto;
import com.example.university.model.entity.Question;
import com.example.university.model.entity.Seen;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreQuestionRequest;
import com.example.university.model.request.search.QuestionSearch;
import com.example.university.repository.QuestionRepository;
import com.example.university.repository.SeenRepository;
import com.example.university.repository.UserRepository;
import com.example.university.service.QuestionService;
import com.example.university.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final SeenRepository seenRepository;

    public Page<QuestionDto> getPage(PageRequest<QuestionSearch> request, User user) {
        List<QuestionDto> questionDtoList = questionRepository.getList(request, user);
        long totalItems = questionRepository.count(request, user);
        return new Page<>(questionDtoList, totalItems);
    }

    @Transactional
    public QuestionDto store(StoreQuestionRequest request, User user) {
        Question question;

        List<User> userList = userRepository.findByUserIdIn(List.of(user.getUserId(), request.getRecipientId()));
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, u -> u, (a, b) -> a));
        String lastCommentDate;

        if (request.getQuestionId() != null) {
            question = questionRepository.findById(request.getQuestionId()).orElseGet(null);

            if (question == null) return null;

            lastCommentDate = question.getCreatedAt();
            question.setTitle(request.getTitle());
            question.setContent(request.getContent());
            question.setStatus(question.getStatus());
            question.setLastCommentDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            question = questionRepository.save(question);
        } else {
            lastCommentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            question = new Question();
            question.setQuestionId(StringUtil.generateId());
            question.setQuestionerId(user.getUserId());
            question.setRecipientId(request.getRecipientId());
            question.setTitle(request.getTitle());
            question.setContent(request.getContent());
            question.setStatus(question.getStatus());
            question.setLastCommentDate(lastCommentDate);
            question = questionRepository.save(question);

            List<Seen> seenList = new ArrayList<>();
            seenList.add(new Seen(StringUtil.generateId(), question.getQuestionId(), user.getUserId(), 0));
            seenList.add(new Seen(StringUtil.generateId(), question.getQuestionId(), question.getRecipientId(), 1));
            seenRepository.saveAll(seenList);
        }

        return new QuestionDto(question.getQuestionId(), question.getQuestionerId(), question.getRecipientId(),
                question.getTitle(), question.getContent(), lastCommentDate, lastCommentDate,
                0, userMap.get(question.getQuestionerId()).getName(),
                userMap.get(question.getRecipientId()).getName());
    }
}