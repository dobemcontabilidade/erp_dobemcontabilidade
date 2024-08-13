package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EtapaFluxoModelo;
import com.dobemcontabilidade.repository.EtapaFluxoModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EtapaFluxoModelo}.
 */
@Service
@Transactional
public class EtapaFluxoModeloService {

    private static final Logger log = LoggerFactory.getLogger(EtapaFluxoModeloService.class);

    private final EtapaFluxoModeloRepository etapaFluxoModeloRepository;

    public EtapaFluxoModeloService(EtapaFluxoModeloRepository etapaFluxoModeloRepository) {
        this.etapaFluxoModeloRepository = etapaFluxoModeloRepository;
    }

    /**
     * Save a etapaFluxoModelo.
     *
     * @param etapaFluxoModelo the entity to save.
     * @return the persisted entity.
     */
    public EtapaFluxoModelo save(EtapaFluxoModelo etapaFluxoModelo) {
        log.debug("Request to save EtapaFluxoModelo : {}", etapaFluxoModelo);
        return etapaFluxoModeloRepository.save(etapaFluxoModelo);
    }

    /**
     * Update a etapaFluxoModelo.
     *
     * @param etapaFluxoModelo the entity to save.
     * @return the persisted entity.
     */
    public EtapaFluxoModelo update(EtapaFluxoModelo etapaFluxoModelo) {
        log.debug("Request to update EtapaFluxoModelo : {}", etapaFluxoModelo);
        return etapaFluxoModeloRepository.save(etapaFluxoModelo);
    }

    /**
     * Partially update a etapaFluxoModelo.
     *
     * @param etapaFluxoModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EtapaFluxoModelo> partialUpdate(EtapaFluxoModelo etapaFluxoModelo) {
        log.debug("Request to partially update EtapaFluxoModelo : {}", etapaFluxoModelo);

        return etapaFluxoModeloRepository
            .findById(etapaFluxoModelo.getId())
            .map(existingEtapaFluxoModelo -> {
                if (etapaFluxoModelo.getNome() != null) {
                    existingEtapaFluxoModelo.setNome(etapaFluxoModelo.getNome());
                }
                if (etapaFluxoModelo.getDescricao() != null) {
                    existingEtapaFluxoModelo.setDescricao(etapaFluxoModelo.getDescricao());
                }
                if (etapaFluxoModelo.getOrdem() != null) {
                    existingEtapaFluxoModelo.setOrdem(etapaFluxoModelo.getOrdem());
                }

                return existingEtapaFluxoModelo;
            })
            .map(etapaFluxoModeloRepository::save);
    }

    /**
     * Get all the etapaFluxoModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EtapaFluxoModelo> findAll() {
        log.debug("Request to get all EtapaFluxoModelos");
        return etapaFluxoModeloRepository.findAll();
    }

    /**
     * Get all the etapaFluxoModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EtapaFluxoModelo> findAllWithEagerRelationships(Pageable pageable) {
        return etapaFluxoModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one etapaFluxoModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtapaFluxoModelo> findOne(Long id) {
        log.debug("Request to get EtapaFluxoModelo : {}", id);
        return etapaFluxoModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the etapaFluxoModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtapaFluxoModelo : {}", id);
        etapaFluxoModeloRepository.deleteById(id);
    }
}
