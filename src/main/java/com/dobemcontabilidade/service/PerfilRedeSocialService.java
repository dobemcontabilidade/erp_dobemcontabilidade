package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilRedeSocial;
import com.dobemcontabilidade.repository.PerfilRedeSocialRepository;
import com.dobemcontabilidade.service.dto.PerfilRedeSocialDTO;
import com.dobemcontabilidade.service.mapper.PerfilRedeSocialMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilRedeSocial}.
 */
@Service
@Transactional
public class PerfilRedeSocialService {

    private static final Logger log = LoggerFactory.getLogger(PerfilRedeSocialService.class);

    private final PerfilRedeSocialRepository perfilRedeSocialRepository;

    private final PerfilRedeSocialMapper perfilRedeSocialMapper;

    public PerfilRedeSocialService(PerfilRedeSocialRepository perfilRedeSocialRepository, PerfilRedeSocialMapper perfilRedeSocialMapper) {
        this.perfilRedeSocialRepository = perfilRedeSocialRepository;
        this.perfilRedeSocialMapper = perfilRedeSocialMapper;
    }

    /**
     * Save a perfilRedeSocial.
     *
     * @param perfilRedeSocialDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilRedeSocialDTO save(PerfilRedeSocialDTO perfilRedeSocialDTO) {
        log.debug("Request to save PerfilRedeSocial : {}", perfilRedeSocialDTO);
        PerfilRedeSocial perfilRedeSocial = perfilRedeSocialMapper.toEntity(perfilRedeSocialDTO);
        perfilRedeSocial = perfilRedeSocialRepository.save(perfilRedeSocial);
        return perfilRedeSocialMapper.toDto(perfilRedeSocial);
    }

    /**
     * Update a perfilRedeSocial.
     *
     * @param perfilRedeSocialDTO the entity to save.
     * @return the persisted entity.
     */
    public PerfilRedeSocialDTO update(PerfilRedeSocialDTO perfilRedeSocialDTO) {
        log.debug("Request to update PerfilRedeSocial : {}", perfilRedeSocialDTO);
        PerfilRedeSocial perfilRedeSocial = perfilRedeSocialMapper.toEntity(perfilRedeSocialDTO);
        perfilRedeSocial = perfilRedeSocialRepository.save(perfilRedeSocial);
        return perfilRedeSocialMapper.toDto(perfilRedeSocial);
    }

    /**
     * Partially update a perfilRedeSocial.
     *
     * @param perfilRedeSocialDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilRedeSocialDTO> partialUpdate(PerfilRedeSocialDTO perfilRedeSocialDTO) {
        log.debug("Request to partially update PerfilRedeSocial : {}", perfilRedeSocialDTO);

        return perfilRedeSocialRepository
            .findById(perfilRedeSocialDTO.getId())
            .map(existingPerfilRedeSocial -> {
                perfilRedeSocialMapper.partialUpdate(existingPerfilRedeSocial, perfilRedeSocialDTO);

                return existingPerfilRedeSocial;
            })
            .map(perfilRedeSocialRepository::save)
            .map(perfilRedeSocialMapper::toDto);
    }

    /**
     * Get one perfilRedeSocial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilRedeSocialDTO> findOne(Long id) {
        log.debug("Request to get PerfilRedeSocial : {}", id);
        return perfilRedeSocialRepository.findById(id).map(perfilRedeSocialMapper::toDto);
    }

    /**
     * Delete the perfilRedeSocial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilRedeSocial : {}", id);
        perfilRedeSocialRepository.deleteById(id);
    }
}
