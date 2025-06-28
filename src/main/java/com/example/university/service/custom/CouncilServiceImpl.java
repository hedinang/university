package com.example.university.service.custom;

import com.example.university.mapper.CouncilMemberMapper;
import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.CouncilMemberDto;
import com.example.university.model.dto.Page;
import com.example.university.model.entity.Council;
import com.example.university.model.entity.CouncilMember;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreCouncilRequest;
import com.example.university.model.request.search.CouncilSearch;
import com.example.university.repository.CouncilMemberRepository;
import com.example.university.repository.CouncilRepository;
import com.example.university.repository.UserRepository;
import com.example.university.service.CouncilService;
import com.example.university.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouncilServiceImpl implements CouncilService {
    private final CouncilRepository councilRepository;
    private final CouncilMemberRepository councilMemberRepository;
    private final UserRepository userRepository;

    private final CouncilMemberMapper councilMemberMapper;

    public Page<CouncilDto> getPage(PageRequest<CouncilSearch> request) {
        List<CouncilDto> councils = councilRepository.getList(request);
        long totalItems = councilRepository.count(request);
        return new Page<>(councils, totalItems);
    }

    public List<CouncilDto> getAll(CouncilSearch request) {
        return councilRepository.getAll(request);
    }

    @Transactional
    public CouncilDto store(StoreCouncilRequest request) {
        Council council;

        if (request.getCouncilId() != null) {
            council = councilRepository.findById(request.getCouncilId()).orElseGet(null);

            if (council == null) return null;

            council.setCouncilName(request.getCouncilName());
            council.setYear(request.getYear());
            council.setStatus(request.getStatus());
            council.setUpdatedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            councilRepository.save(council);
            councilMemberRepository.deleteByCouncilId(request.getCouncilId());
        } else {
            council = new Council();
            council.setCouncilId(StringUtil.generateId());
            council.setCouncilName(request.getCouncilName());
            council.setYear(request.getYear());
            council.setStatus(request.getStatus());
            councilRepository.save(council);
        }

        List<CouncilMember> councilMembers = request.getTeacherList().stream().map(teacher -> {
            CouncilMember councilMember = new CouncilMember();
            councilMember.setCouncilMemberId(StringUtil.generateId());
            councilMember.setCouncilId(council.getCouncilId());
            councilMember.setCouncilRole(teacher.getCouncilRole());
            councilMember.setMemberId(teacher.getMemberId());
            return councilMember;
        }).toList();
        List<CouncilMember> councilMemberList = councilMemberRepository.saveAll(councilMembers);
        List<String> memberIds = councilMemberList.stream().map(CouncilMember::getMemberId).toList();
        Map<String, User> memberMap = userRepository.findByUserIdIn(memberIds).stream().collect(Collectors.toMap(User::getUserId, user -> user));

        List<CouncilMemberDto> councilMemberDtoList = councilMemberList.stream().map(councilMember -> {
            CouncilMemberDto councilMemberDto = councilMemberMapper.councilMemberToCouncilMemberDto(councilMember);
            councilMemberDto.setName(memberMap.get(councilMember.getMemberId()).getName());
            return councilMemberDto;
        }).toList();

        return new CouncilDto(council.getCouncilId(), council.getCouncilName(), council.getYear(),
                null, null, council.getStatus(), null, councilMemberDtoList);
    }
}
