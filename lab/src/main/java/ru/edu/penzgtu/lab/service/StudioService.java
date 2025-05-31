package ru.edu.penzgtu.lab.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.penzgtu.lab.dto.StudioDto;
import ru.edu.penzgtu.lab.entity.Studio;
import ru.edu.penzgtu.lab.repository.StudioRepository;
import ru.edu.penzgtu.lab.service.mapper.StudioMapper;
import ru.edu.penzgtu.lab.exception.PenzGtuException;
import ru.edu.penzgtu.lab.exception.ErrorType;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudioService {

    private static final Logger logger = LoggerFactory.getLogger(StudioService.class);
    private final StudioRepository studioRepository;
    private final StudioMapper studioMapper;

    @Transactional(readOnly = true)
    public List<StudioDto> getAllStudios() {
        logger.info("Получение всех студий");
        return studioRepository.findAll().stream()
                .map(studioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StudioDto getStudioById(UUID id) {
        logger.info("Получение студии с ID: {}", id);
        return studioMapper.toDto(studioRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Студия не найдена")));
    }

    @Transactional
    public StudioDto createStudio(StudioDto studioDto) {
        logger.info("Создание студии: {}", studioDto.getName());
        Studio studio = studioMapper.toEntity(studioDto);
        return studioMapper.toDto(studioRepository.save(studio));
    }

    @Transactional
    public StudioDto updateStudio(UUID id, StudioDto studioDto) {
        logger.info("Обновление студии с ID: {}", id);
        Studio existingStudio = studioRepository.findById(id)
                .orElseThrow(() -> new PenzGtuException(ErrorType.NOT_FOUND, "Студия не найдена"));
        existingStudio.setName(studioDto.getName());
        existingStudio.setFoundedYear(studioDto.getFoundedYear());
        existingStudio.setCountry(studioDto.getCountry());
        existingStudio.setWebsite(studioDto.getWebsite());
        return studioMapper.toDto(studioRepository.save(existingStudio));
    }

    @Transactional
    public void deleteStudio(UUID id) {
        logger.info("Удаление студии с ID: {}", id);
        if (!studioRepository.existsById(id)) {
            throw new PenzGtuException(ErrorType.NOT_FOUND, "Студия не найдена");
        }
        studioRepository.deleteById(id);
    }
}