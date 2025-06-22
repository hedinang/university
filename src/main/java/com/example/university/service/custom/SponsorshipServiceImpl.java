package com.example.university.service.custom;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.Council;
import com.example.university.model.entity.Sponsorship;
import com.example.university.model.entity.Topic;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreSponsorshipRequest;
import com.example.university.model.request.search.SponsorshipSearch;
import com.example.university.repository.CouncilRepository;
import com.example.university.repository.SponsorshipRepository;
import com.example.university.repository.TopicRepository;
import com.example.university.service.SponsorshipService;
import com.example.university.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SponsorshipServiceImpl implements SponsorshipService {
    private final SponsorshipRepository sponsorshipRepository;
    private final TopicRepository topicRepository;
    private final CouncilRepository councilRepository;

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
        return new SponsorshipDto(sponsorship.getSponsorshipId(), sponsorship.getTopicId(), sponsorship.getCouncilId(),
                sponsorship.getBudget(), topic.getTitle(), council.getCouncilName());
    }
}
