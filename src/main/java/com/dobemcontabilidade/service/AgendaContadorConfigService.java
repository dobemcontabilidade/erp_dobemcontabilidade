package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AgendaContadorConfig;
import com.dobemcontabilidade.repository.AgendaContadorConfigRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AgendaContadorConfig}.
 */
@Service
@Transactional
public class AgendaContadorConfigService {

    private static final Logger log = LoggerFactory.getLogger(AgendaContadorConfigService.class);

    private final AgendaContadorConfigRepository agendaContadorConfigRepository;

    public AgendaContadorConfigService(AgendaContadorConfigRepository agendaContadorConfigRepository) {
        this.agendaContadorConfigRepository = agendaContadorConfigRepository;
    }

    /**
     * Save a agendaContadorConfig.
     *
     * @param agendaContadorConfig the entity to save.
     * @return the persisted entity.
     */
    public AgendaContadorConfig save(AgendaContadorConfig agendaContadorConfig) {
        log.debug("Request to save AgendaContadorConfig : {}", agendaContadorConfig);
        return agendaContadorConfigRepository.save(agendaContadorConfig);
    }

    /**
     * Update a agendaContadorConfig.
     *
     * @param agendaContadorConfig the entity to save.
     * @return the persisted entity.
     */
    public AgendaContadorConfig update(AgendaContadorConfig agendaContadorConfig) {
        log.debug("Request to update AgendaContadorConfig : {}", agendaContadorConfig);
        return agendaContadorConfigRepository.save(agendaContadorConfig);
    }

    /**
     * Partially update a agendaContadorConfig.
     *
     * @param agendaContadorConfig the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgendaContadorConfig> partialUpdate(AgendaContadorConfig agendaContadorConfig) {
        log.debug("Request to partially update AgendaContadorConfig : {}", agendaContadorConfig);

        return agendaContadorConfigRepository
            .findById(agendaContadorConfig.getId())
            .map(existingAgendaContadorConfig -> {
                if (agendaContadorConfig.getAtivo() != null) {
                    existingAgendaContadorConfig.setAtivo(agendaContadorConfig.getAtivo());
                }
                if (agendaContadorConfig.getTipoVisualizacaoAgendaEnum() != null) {
                    existingAgendaContadorConfig.setTipoVisualizacaoAgendaEnum(agendaContadorConfig.getTipoVisualizacaoAgendaEnum());
                }

                return existingAgendaContadorConfig;
            })
            .map(agendaContadorConfigRepository::save);
    }

    /**
     * Get all the agendaContadorConfigs.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AgendaContadorConfig> findAll() {
        log.debug("Request to get all AgendaContadorConfigs");
        return agendaContadorConfigRepository.findAll();
    }

    /**
     * Get one agendaContadorConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgendaContadorConfig> findOne(Long id) {
        log.debug("Request to get AgendaContadorConfig : {}", id);
        return agendaContadorConfigRepository.findById(id);
    }

    /**
     * Delete the agendaContadorConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgendaContadorConfig : {}", id);
        agendaContadorConfigRepository.deleteById(id);
    }
}
