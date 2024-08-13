package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilRedeSocial;
import com.dobemcontabilidade.repository.PerfilRedeSocialRepository;
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

    public PerfilRedeSocialService(PerfilRedeSocialRepository perfilRedeSocialRepository) {
        this.perfilRedeSocialRepository = perfilRedeSocialRepository;
    }

    /**
     * Save a perfilRedeSocial.
     *
     * @param perfilRedeSocial the entity to save.
     * @return the persisted entity.
     */
    public PerfilRedeSocial save(PerfilRedeSocial perfilRedeSocial) {
        log.debug("Request to save PerfilRedeSocial : {}", perfilRedeSocial);
        return perfilRedeSocialRepository.save(perfilRedeSocial);
    }

    /**
     * Update a perfilRedeSocial.
     *
     * @param perfilRedeSocial the entity to save.
     * @return the persisted entity.
     */
    public PerfilRedeSocial update(PerfilRedeSocial perfilRedeSocial) {
        log.debug("Request to update PerfilRedeSocial : {}", perfilRedeSocial);
        return perfilRedeSocialRepository.save(perfilRedeSocial);
    }

    /**
     * Partially update a perfilRedeSocial.
     *
     * @param perfilRedeSocial the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilRedeSocial> partialUpdate(PerfilRedeSocial perfilRedeSocial) {
        log.debug("Request to partially update PerfilRedeSocial : {}", perfilRedeSocial);

        return perfilRedeSocialRepository
            .findById(perfilRedeSocial.getId())
            .map(existingPerfilRedeSocial -> {
                if (perfilRedeSocial.getRede() != null) {
                    existingPerfilRedeSocial.setRede(perfilRedeSocial.getRede());
                }
                if (perfilRedeSocial.getUrlPerfil() != null) {
                    existingPerfilRedeSocial.setUrlPerfil(perfilRedeSocial.getUrlPerfil());
                }
                if (perfilRedeSocial.getTipoRede() != null) {
                    existingPerfilRedeSocial.setTipoRede(perfilRedeSocial.getTipoRede());
                }

                return existingPerfilRedeSocial;
            })
            .map(perfilRedeSocialRepository::save);
    }

    /**
     * Get one perfilRedeSocial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilRedeSocial> findOne(Long id) {
        log.debug("Request to get PerfilRedeSocial : {}", id);
        return perfilRedeSocialRepository.findById(id);
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
