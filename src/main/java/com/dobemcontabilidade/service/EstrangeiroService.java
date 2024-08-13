package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Estrangeiro;
import com.dobemcontabilidade.repository.EstrangeiroRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Estrangeiro}.
 */
@Service
@Transactional
public class EstrangeiroService {

    private static final Logger log = LoggerFactory.getLogger(EstrangeiroService.class);

    private final EstrangeiroRepository estrangeiroRepository;

    public EstrangeiroService(EstrangeiroRepository estrangeiroRepository) {
        this.estrangeiroRepository = estrangeiroRepository;
    }

    /**
     * Save a estrangeiro.
     *
     * @param estrangeiro the entity to save.
     * @return the persisted entity.
     */
    public Estrangeiro save(Estrangeiro estrangeiro) {
        log.debug("Request to save Estrangeiro : {}", estrangeiro);
        return estrangeiroRepository.save(estrangeiro);
    }

    /**
     * Update a estrangeiro.
     *
     * @param estrangeiro the entity to save.
     * @return the persisted entity.
     */
    public Estrangeiro update(Estrangeiro estrangeiro) {
        log.debug("Request to update Estrangeiro : {}", estrangeiro);
        return estrangeiroRepository.save(estrangeiro);
    }

    /**
     * Partially update a estrangeiro.
     *
     * @param estrangeiro the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Estrangeiro> partialUpdate(Estrangeiro estrangeiro) {
        log.debug("Request to partially update Estrangeiro : {}", estrangeiro);

        return estrangeiroRepository
            .findById(estrangeiro.getId())
            .map(existingEstrangeiro -> {
                if (estrangeiro.getDataChegada() != null) {
                    existingEstrangeiro.setDataChegada(estrangeiro.getDataChegada());
                }
                if (estrangeiro.getDataNaturalizacao() != null) {
                    existingEstrangeiro.setDataNaturalizacao(estrangeiro.getDataNaturalizacao());
                }
                if (estrangeiro.getCasadoComBrasileiro() != null) {
                    existingEstrangeiro.setCasadoComBrasileiro(estrangeiro.getCasadoComBrasileiro());
                }
                if (estrangeiro.getFilhosComBrasileiro() != null) {
                    existingEstrangeiro.setFilhosComBrasileiro(estrangeiro.getFilhosComBrasileiro());
                }
                if (estrangeiro.getChecked() != null) {
                    existingEstrangeiro.setChecked(estrangeiro.getChecked());
                }

                return existingEstrangeiro;
            })
            .map(estrangeiroRepository::save);
    }

    /**
     * Get all the estrangeiros.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Estrangeiro> findAll() {
        log.debug("Request to get all Estrangeiros");
        return estrangeiroRepository.findAll();
    }

    /**
     * Get one estrangeiro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Estrangeiro> findOne(Long id) {
        log.debug("Request to get Estrangeiro : {}", id);
        return estrangeiroRepository.findById(id);
    }

    /**
     * Delete the estrangeiro by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Estrangeiro : {}", id);
        estrangeiroRepository.deleteById(id);
    }
}
