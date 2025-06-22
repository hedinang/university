package com.example.university.repository.custom;

import com.example.university.model.dto.SponsorshipDto;
import com.example.university.model.entity.User;
import com.example.university.model.request.PageRequest;
import com.example.university.model.request.search.SponsorshipSearch;

import java.util.List;

public interface CustomSponsorshipRepository {
    List<SponsorshipDto> getList(PageRequest<SponsorshipSearch> request, User user);

    Long count(PageRequest<SponsorshipSearch> request, User user);
}
