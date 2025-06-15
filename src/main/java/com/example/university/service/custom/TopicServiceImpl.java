package com.example.university.service.custom;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.Page;
import com.example.university.model.dto.TopicDto;
import com.example.university.model.entity.CouncilMember;
import com.example.university.model.entity.Topic;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreTopicRequest;
import com.example.university.model.request.search.TopicSearch;
import com.example.university.repository.TopicRepository;
import com.example.university.repository.UserRepository;
import com.example.university.service.TopicService;
import com.example.university.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public Page<TopicDto> getPage(PageRequest<TopicSearch> request) {
        List<TopicDto> topicDtoList = topicRepository.getList(request);
//        long totalItems = topicRepository.count(request);
        return new Page<>(topicDtoList, 100L);
    }

    @Transactional
    public TopicDto store(StoreTopicRequest request) {
        Topic topic;

        if (request.getTopicId() != null) {
            topic = topicRepository.findById(request.getTopicId()).orElseGet(null);

            if (topic == null) return null;

            topic.setTitle(request.getTitle());
            topic.setProposerId(request.getProposerId());
            topic.setApproverId(request.getApproverId());
            topic.setTopicType(request.getTopicType());
            topic.setStartTime(request.getStartTime());
            topic.setEndTime(request.getEndTime());
            topic.setProgress(request.getProgress());
            topic.setScore(request.getScore());
            topic.setStatus(request.getStatus());

            topicRepository.save(topic);
        } else {
            topic = new Topic();
            topic.setTopicId(StringUtil.generateId());
            topic.setTitle(request.getTitle());
            topic.setProposerId(request.getProposerId());
            topic.setApproverId(request.getApproverId());
            topic.setTopicType(request.getTopicType());
            topic.setStartTime(request.getStartTime());
            topic.setEndTime(request.getEndTime());
            topic.setProgress(request.getProgress());
            topic.setScore(request.getScore());
            topic.setStatus(request.getStatus());
            topicRepository.save(topic);
        }

        Map<String, User> memberMap = userRepository.findByUserIdIn(List.of(request.getProposerId(), request.getApproverId())).
                stream().collect(Collectors.toMap(User::getUserId, user -> user));
        return new TopicDto(StringUtil.generateId(), request.getProposerId(), request.getApproverId(), request.getTitle(),
                request.getTopicType(), request.getStatus(), request.getStartTime(), request.getEndTime(), request.getProgress(),
                request.getScore(), memberMap.get(request.getProposerId()).getName(), memberMap.get(request.getApproverId()).getName());
    }
}
