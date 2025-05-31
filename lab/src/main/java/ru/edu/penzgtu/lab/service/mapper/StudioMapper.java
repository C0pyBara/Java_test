package ru.edu.penzgtu.lab.service.mapper;

import org.springframework.stereotype.Service;
import ru.edu.penzgtu.lab.dto.StudioDto;
import ru.edu.penzgtu.lab.entity.Studio;

@Service
public class StudioMapper {

    public StudioDto toDto(Studio studio) {
        if (studio == null) {
            return null;
        }
        return StudioDto.builder()
                .id(studio.getId())
                .name(studio.getName())
                .foundedYear(studio.getFoundedYear())
                .country(studio.getCountry())
                .website(studio.getWebsite())
                .build();
    }

    public Studio toEntity(StudioDto dto) {
        if (dto == null) {
            return null;
        }
        return new Studio();
    }
}