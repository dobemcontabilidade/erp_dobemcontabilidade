package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo;
import com.dobemcontabilidade.repository.ServicoContabilEtapaFluxoModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo}.
 */
@Service
@Transactional
public class ServicoContabilEtapaFluxoModeloService {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilEtapaFluxoModeloService.class);

    private final ServicoContabilEtapaFluxoModeloRepository servicoContabilEtapaFluxoModeloRepository;

    public ServicoContabilEtapaFluxoModeloService(ServicoContabilEtapaFluxoModeloRepository servicoContabilEtapaFluxoModeloRepository) {
        this.servicoContabilEtapaFluxoModeloRepository = servicoContabilEtapaFluxoModeloRepository;
    }

    /**
     * Save a servicoContabilEtapaFluxoModelo.
     *
     * @param servicoContabilEtapaFluxoModelo the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilEtapaFluxoModelo save(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        log.debug("Request to save ServicoContabilEtapaFluxoModelo : {}", servicoContabilEtapaFluxoModelo);
        return servicoContabilEtapaFluxoModeloRepository.save(servicoContabilEtapaFluxoModelo);
    }

    /**
     * Update a servicoContabilEtapaFluxoModelo.
     *
     * @param servicoContabilEtapaFluxoModelo the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilEtapaFluxoModelo update(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        log.debug("Request to update ServicoContabilEtapaFluxoModelo : {}", servicoContabilEtapaFluxoModelo);
        return servicoContabilEtapaFluxoModeloRepository.save(servicoContabilEtapaFluxoModelo);
    }

    /**
     * Partially update a servicoContabilEtapaFluxoModelo.
     *
     * @param servicoContabilEtapaFluxoModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServicoContabilEtapaFluxoModelo> partialUpdate(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        log.debug("Request to partially update ServicoContabilEtapaFluxoModelo : {}", servicoContabilEtapaFluxoModelo);

        return servicoContabilEtapaFluxoModeloRepository
            .findById(servicoContabilEtapaFluxoModelo.getId())
            .map(existingServicoContabilEtapaFluxoModelo -> {
                if (servicoContabilEtapaFluxoModelo.getOrdem() != null) {
                    existingServicoContabilEtapaFluxoModelo.setOrdem(servicoContabilEtapaFluxoModelo.getOrdem());
                }
                if (servicoContabilEtapaFluxoModelo.getPrazo() != null) {
                    existingServicoContabilEtapaFluxoModelo.setPrazo(servicoContabilEtapaFluxoModelo.getPrazo());
                }

                return existingServicoContabilEtapaFluxoModelo;
            })
            .map(servicoContabilEtapaFluxoModeloRepository::save);
    }

    /**
     * Get all the servicoContabilEtapaFluxoModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoContabilEtapaFluxoModelo> findAll() {
        log.debug("Request to get all ServicoContabilEtapaFluxoModelos");
        return servicoContabilEtapaFluxoModeloRepository.findAll();
    }

    /**
     * Get all the servicoContabilEtapaFluxoModelos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ServicoContabilEtapaFluxoModelo> findAllWithEagerRelationships(Pageable pageable) {
        return servicoContabilEtapaFluxoModeloRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one servicoContabilEtapaFluxoModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicoContabilEtapaFluxoModelo> findOne(Long id) {
        log.debug("Request to get ServicoContabilEtapaFluxoModelo : {}", id);
        return servicoContabilEtapaFluxoModeloRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the servicoContabilEtapaFluxoModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServicoContabilEtapaFluxoModelo : {}", id);
        servicoContabilEtapaFluxoModeloRepository.deleteById(id);
    }
}
