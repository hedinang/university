package com.example.university.service.custom;

import com.example.university.model.entity.Seen;
import com.example.university.model.entity.User;
import com.example.university.model.request.ResetUnreadRequest;
import com.example.university.repository.SeenRepository;
import com.example.university.service.SeenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeenServiceImpl implements SeenService {
    private final SeenRepository seenRepository;

    public void resetUnread(ResetUnreadRequest resetUnreadRequest, User user) {
        Seen seen = seenRepository.findByQuestionIdAndUserId(resetUnreadRequest.getQuestionId(), user.getUserId());
        seen.setUnread(0);
        seenRepository.save(seen);
    }
}
