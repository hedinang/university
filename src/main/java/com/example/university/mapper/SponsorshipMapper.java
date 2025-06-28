package com.example.university.mapper;

import com.example.university.model.dto.SponsorshipDbDto;
import com.example.university.model.dto.SponsorshipDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SponsorshipMapper {
    SponsorshipDto toSponsorshipDto(SponsorshipDbDto sponsorshipDbDto);
}