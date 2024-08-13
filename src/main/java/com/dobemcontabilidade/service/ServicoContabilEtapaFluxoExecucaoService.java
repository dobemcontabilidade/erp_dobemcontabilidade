package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao;
import com.dobemcontabilidade.repository.ServicoContabilEtapaFluxoExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao}.
 */
@Service
@Transactional
public class ServicoContabilEtapaFluxoExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilEtapaFluxoExecucaoService.class);

    private final ServicoContabilEtapaFluxoExecucaoRepository servicoContabilEtapaFluxoExecucaoRepository;

    public ServicoContabilEtapaFluxoExecucaoService(
        ServicoContabilEtapaFluxoExecucaoRepository servicoContabilEtapaFluxoExecucaoRepository
    ) {
        this.servicoContabilEtapaFluxoExecucaoRepository = servicoContabilEtapaFluxoExecucaoRepository;
    }

    /**
     * Save a servicoContabilEtapaFluxoExecucao.
     *
     * @param servicoContabilEtapaFluxoExecucao the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilEtapaFluxoExecucao save(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        log.debug("Request to save ServicoContabilEtapaFluxoExecucao : {}", servicoContabilEtapaFluxoExecucao);
        return servicoContabilEtapaFluxoExecucaoRepository.save(servicoContabilEtapaFluxoExecucao);
    }

    /**
     * Update a servicoContabilEtapaFluxoExecucao.
     *
     * @param servicoContabilEtapaFluxoExecucao the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilEtapaFluxoExecucao update(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        log.debug("Request to update ServicoContabilEtapaFluxoExecucao : {}", servicoContabilEtapaFluxoExecucao);
        return servicoContabilEtapaFluxoExecucaoRepository.save(servicoContabilEtapaFluxoExecucao);
    }

    /**
     * Partially update a servicoContabilEtapaFluxoExecucao.
     *
     * @param servicoContabilEtapaFluxoExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServicoContabilEtapaFluxoExecucao> partialUpdate(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        log.debug("Request to partially update ServicoContabilEtapaFluxoExecucao : {}", servicoContabilEtapaFluxoExecucao);

        return servicoContabilEtapaFluxoExecucaoRepository
            .findById(servicoContabilEtapaFluxoExecucao.getId())
            .map(existingServicoContabilEtapaFluxoExecucao -> {
                if (servicoContabilEtapaFluxoExecucao.getOrdem() != null) {
                    existingServicoContabilEtapaFluxoExecucao.setOrdem(servicoContabilEtapaFluxoExecucao.getOrdem());
                }
                if (servicoContabilEtapaFluxoExecucao.getFeito() != null) {
                    existingServicoContabilEtapaFluxoExecucao.setFeito(servicoContabilEtapaFluxoExecucao.getFeito());
                }
                if (servicoContabilEtapaFluxoExecucao.getPrazo() != null) {
                    existingServicoContabilEtapaFluxoExecucao.setPrazo(servicoContabilEtapaFluxoExecucao.getPrazo());
                }

                return existingServicoContabilEtapaFluxoExecucao;
            })
            .map(servicoContabilEtapaFluxoExecucaoRepository::save);
    }

    /**
     * Get all the servicoContabilEtapaFluxoExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoContabilEtapaFluxoExecucao> findAll() {
        log.debug("Request to get all ServicoContabilEtapaFluxoExecucaos");
        return servicoContabilEtapaFluxoExecucaoRepository.findAll();
    }

    /**
     * Get all the servicoContabilEtapaFluxoExecucaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ServicoContabilEtapaFluxoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return servicoContabilEtapaFluxoExecucaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one servicoContabilEtapaFluxoExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicoContabilEtapaFluxoExecucao> findOne(Long id) {
        log.debug("Request to get ServicoContabilEtapaFluxoExecucao : {}", id);
        return servicoContabilEtapaFluxoExecucaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the servicoContabilEtapaFluxoExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServicoContabilEtapaFluxoExecucao : {}", id);
        servicoContabilEtapaFluxoExecucaoRepository.deleteById(id);
    }
}
