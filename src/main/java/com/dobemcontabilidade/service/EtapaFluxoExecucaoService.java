package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EtapaFluxoExecucao;
import com.dobemcontabilidade.repository.EtapaFluxoExecucaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EtapaFluxoExecucao}.
 */
@Service
@Transactional
public class EtapaFluxoExecucaoService {

    private static final Logger log = LoggerFactory.getLogger(EtapaFluxoExecucaoService.class);

    private final EtapaFluxoExecucaoRepository etapaFluxoExecucaoRepository;

    public EtapaFluxoExecucaoService(EtapaFluxoExecucaoRepository etapaFluxoExecucaoRepository) {
        this.etapaFluxoExecucaoRepository = etapaFluxoExecucaoRepository;
    }

    /**
     * Save a etapaFluxoExecucao.
     *
     * @param etapaFluxoExecucao the entity to save.
     * @return the persisted entity.
     */
    public EtapaFluxoExecucao save(EtapaFluxoExecucao etapaFluxoExecucao) {
        log.debug("Request to save EtapaFluxoExecucao : {}", etapaFluxoExecucao);
        return etapaFluxoExecucaoRepository.save(etapaFluxoExecucao);
    }

    /**
     * Update a etapaFluxoExecucao.
     *
     * @param etapaFluxoExecucao the entity to save.
     * @return the persisted entity.
     */
    public EtapaFluxoExecucao update(EtapaFluxoExecucao etapaFluxoExecucao) {
        log.debug("Request to update EtapaFluxoExecucao : {}", etapaFluxoExecucao);
        return etapaFluxoExecucaoRepository.save(etapaFluxoExecucao);
    }

    /**
     * Partially update a etapaFluxoExecucao.
     *
     * @param etapaFluxoExecucao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EtapaFluxoExecucao> partialUpdate(EtapaFluxoExecucao etapaFluxoExecucao) {
        log.debug("Request to partially update EtapaFluxoExecucao : {}", etapaFluxoExecucao);

        return etapaFluxoExecucaoRepository
            .findById(etapaFluxoExecucao.getId())
            .map(existingEtapaFluxoExecucao -> {
                if (etapaFluxoExecucao.getNome() != null) {
                    existingEtapaFluxoExecucao.setNome(etapaFluxoExecucao.getNome());
                }
                if (etapaFluxoExecucao.getDescricao() != null) {
                    existingEtapaFluxoExecucao.setDescricao(etapaFluxoExecucao.getDescricao());
                }
                if (etapaFluxoExecucao.getFeito() != null) {
                    existingEtapaFluxoExecucao.setFeito(etapaFluxoExecucao.getFeito());
                }
                if (etapaFluxoExecucao.getOrdem() != null) {
                    existingEtapaFluxoExecucao.setOrdem(etapaFluxoExecucao.getOrdem());
                }
                if (etapaFluxoExecucao.getAgendada() != null) {
                    existingEtapaFluxoExecucao.setAgendada(etapaFluxoExecucao.getAgendada());
                }

                return existingEtapaFluxoExecucao;
            })
            .map(etapaFluxoExecucaoRepository::save);
    }

    /**
     * Get all the etapaFluxoExecucaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EtapaFluxoExecucao> findAll() {
        log.debug("Request to get all EtapaFluxoExecucaos");
        return etapaFluxoExecucaoRepository.findAll();
    }

    /**
     * Get one etapaFluxoExecucao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EtapaFluxoExecucao> findOne(Long id) {
        log.debug("Request to get EtapaFluxoExecucao : {}", id);
        return etapaFluxoExecucaoRepository.findById(id);
    }

    /**
     * Delete the etapaFluxoExecucao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EtapaFluxoExecucao : {}", id);
        etapaFluxoExecucaoRepository.deleteById(id);
    }
}
