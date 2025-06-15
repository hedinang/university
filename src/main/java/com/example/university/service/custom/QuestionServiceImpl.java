package com.example.university.service.custom;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.QuestionDto;
import com.example.university.model.entity.Question;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreQuestionRequest;
import com.example.university.model.request.search.QuestionSearch;
import com.example.university.repository.QuestionRepository;
import com.example.university.repository.UserRepository;
import com.example.university.service.QuestionService;
import com.example.university.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    public Page<QuestionDto> getPage(PageRequest<QuestionSearch> request, User user) {
        List<QuestionDto> questionDtoList = questionRepository.getList(request, user);
        long totalItems = questionRepository.count(request, user);
        return new Page<>(questionDtoList, totalItems);
    }

    @Transactional
    public QuestionDto store(StoreQuestionRequest request) {
        Question question;

        List<User> userList = userRepository.findByUserIdIn(List.of(request.getQuestionerId(), request.getRecipientId()));
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, user -> user, (a, b) -> a));

        if (request.getQuestionId() != null) {
            question = questionRepository.findById(request.getQuestionId()).orElseGet(null);

            if (question == null) return null;

            question.setTitle(request.getTitle());
            question.setContent(request.getContent());
            question.setStatus(question.getStatus());
            questionRepository.save(question);
        } else {
            question = new Question();
            question.setQuestionId(StringUtil.generateId());
            question.setQuestionerId(request.getQuestionerId());
            question.setRecipientId(request.getRecipientId());
            question.setTitle(request.getTitle());
            question.setContent(request.getContent());
            question.setStatus(question.getStatus());
            questionRepository.save(question);
        }

        return new QuestionDto(question.getQuestionId(), question.getQuestionerId(), question.getRecipientId(),
                question.getTitle(), question.getContent(), question.getQuestionDate(), question.getLastCommentDate(),
                0, userMap.get(question.getQuestionerId()).getName(),
                userMap.get(question.getRecipientId()).getName());
    }
}