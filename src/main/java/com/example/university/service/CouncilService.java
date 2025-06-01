package com.example.university.service;

import com.example.university.model.dto.CouncilDto;
import com.example.university.model.dto.Page;
import com.example.university.model.entity.Council;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreCouncilRequest;
import com.example.university.model.request.search.CouncilSearch;

public interface CouncilService {
    Page<CouncilDto> getPage(PageRequest<CouncilSearch> request);

    CouncilDto store(StoreCouncilRequest request);
}
