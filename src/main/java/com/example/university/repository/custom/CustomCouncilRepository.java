package com.example.university.repository.custom;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.entity.Council;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.CouncilSearch;

import java.util.List;

public interface CustomCouncilRepository {
    List<CouncilDto> getList(PageRequest<CouncilSearch> request);

    Long countCouncil(PageRequest<CouncilSearch> request);
}
