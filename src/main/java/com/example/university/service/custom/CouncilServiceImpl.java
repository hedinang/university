package com.example.university.service.custom;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.Page;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CouncilSearch;
import com.example.university.repository.CouncilRepository;
import com.example.university.service.CouncilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouncilServiceImpl implements CouncilService {
    private final CouncilRepository councilRepository;

    public Page<CouncilDto> getPage(PageRequest<CouncilSearch> request) {
        List<CouncilDto> councils = councilRepository.getList(request);
        long totalItems = councilRepository.count(request);
        return new Page<>(councils, totalItems);
    }
}
