package com.example.university.service.custom;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.*;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreSponsorshipRequest;
import com.example.university.model.request.search.SponsorshipSearch;
import com.example.university.repository.*;
import com.example.university.service.SponsorshipService;
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
public class SponsorshipServiceImpl implements SponsorshipService {
    private final SponsorshipRepository sponsorshipRepository;
    private final TopicRepository topicRepository;
    private final CouncilRepository councilRepository;
    private final CouncilMemberRepository councilMemberRepository;
    private final UserRepository userRepository;

    public Page<SponsorshipDto> getPage(PageRequest<SponsorshipSearch> request, User user) {
        List<SponsorshipDto> questionDtoList = sponsorshipRepository.getList(request, user);
        long totalItems = sponsorshipRepository.count(request, user);
        return new Page<>(questionDtoList, totalItems);
    }

    @Transactional
    public SponsorshipDto store(StoreSponsorshipRequest request, User user) {
        Sponsorship sponsorship = new Sponsorship();

        Topic topic = topicRepository.findById(request.getTopicId()).orElseGet(null);
        Council council = councilRepository.findById(request.getCouncilId()).orElseGet(null);

        if (request.getSponsorshipId() != null) {
            sponsorship = sponsorshipRepository.findById(request.getSponsorshipId()).orElseGet(null);

            if (sponsorship == null) return null;

            sponsorship.setBudget(request.getBudget());
            sponsorship.setCouncilId(request.getCouncilId());
            sponsorship.setTopicId(request.getTopicId());
            sponsorship = sponsorshipRepository.save(sponsorship);
        } else {
            sponsorship.setSponsorshipId(StringUtil.generateId());
            sponsorship.setBudget(request.getBudget());
            sponsorship.setCouncilId(request.getCouncilId());
            sponsorship.setTopicId(request.getTopicId());
            sponsorship.setStatus("ACTIVE");
            sponsorship = sponsorshipRepository.save(sponsorship);
        }


        List<CouncilMember> councilMembers = councilMemberRepository.findByCouncilId(sponsorship.getCouncilId());
        List<String> userIds = new java.util.ArrayList<>(councilMembers.stream().map(CouncilMember::getMemberId).toList());
        userIds.add(topic.getProposerId());
        userIds.add(topic.getApproverId());

        List<User> users = userRepository.findByUserIdIn(userIds);
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(User::getUserId, u -> u));

        List<User> memberList = councilMembers.stream().map(councilMember -> userMap.get(councilMember.getMemberId())).toList();

        return new SponsorshipDto(sponsorship.getSponsorshipId(), sponsorship.getTopicId(), sponsorship.getCouncilId(),
                sponsorship.getBudget(), sponsorship.getStatus(), topic.getTitle(), council.getCouncilName(), topic.getProposerId(),
                topic.getApproverId(), memberList, userMap.get(topic.getProposerId()), userMap.get(topic.getApproverId()));
    }
}
