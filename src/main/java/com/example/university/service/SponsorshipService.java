package com.example.university.service;

import com.example.university.model.dto.Page;
import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.StoreSponsorshipRequest;
import com.example.university.model.request.search.SponsorshipSearch;

public interface SponsorshipService {
    Page<SponsorshipDto> getPage(PageRequest<SponsorshipSearch> request, User user);

    SponsorshipDto store(StoreSponsorshipRequest request, User user);
}
