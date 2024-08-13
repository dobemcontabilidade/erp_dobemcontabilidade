package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.FluxoExecucao;
import com.dobemcontabilidade.repository.FluxoExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.FluxoExecucao}.
 */
@Service
@Transactional
public class FluxoExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(FluxoExecucaoService.class);

    private final FluxoExecucaoRepository fluxoExecucaoRepository;

    public FluxoExecucaoService(FluxoExecucaoRepository fluxoExecucaoRepository) {
        this.fluxoExecucaoRepository = fluxoExecucaoRepository;
    }

    /**
     * Save a fluxoExecucao.
     *
     * @param fluxoExecucao the entity to save.
     * @return the persisted entity.
     */
    public FluxoExecucao save(FluxoExecucao fluxoExecucao) {
        log.debug("Request to save FluxoExecucao : {}", fluxoExecucao);
        return fluxoExecucaoRepository.save(fluxoExecucao);
    }

    /**
     * Update a fluxoExecucao.
     *
     * @param fluxoExecucao the entity to save.
     * @return the persisted entity.
     */
    public FluxoExecucao update(FluxoExecucao fluxoExecucao) {
        log.debug("Request to update FluxoExecucao : {}", fluxoExecucao);
        return fluxoExecucaoRepository.save(fluxoExecucao);
    }

    /**
     * Partially update a fluxoExecucao.
     *
     * @param fluxoExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FluxoExecucao> partialUpdate(FluxoExecucao fluxoExecucao) {
        log.debug("Request to partially update FluxoExecucao : {}", fluxoExecucao);

        return fluxoExecucaoRepository
            .findById(fluxoExecucao.getId())
            .map(existingFluxoExecucao -> {
                if (fluxoExecucao.getNome() != null) {
                    existingFluxoExecucao.setNome(fluxoExecucao.getNome());
                }
                if (fluxoExecucao.getDescricao() != null) {
                    existingFluxoExecucao.setDescricao(fluxoExecucao.getDescricao());
                }

                return existingFluxoExecucao;
            })
            .map(fluxoExecucaoRepository::save);
    }

    /**
     * Get all the fluxoExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FluxoExecucao> findAll() {
        log.debug("Request to get all FluxoExecucaos");
        return fluxoExecucaoRepository.findAll();
    }

    /**
     * Get one fluxoExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FluxoExecucao> findOne(Long id) {
        log.debug("Request to get FluxoExecucao : {}", id);
        return fluxoExecucaoRepository.findById(id);
    }

    /**
     * Delete the fluxoExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FluxoExecucao : {}", id);
        fluxoExecucaoRepository.deleteById(id);
    }
}
