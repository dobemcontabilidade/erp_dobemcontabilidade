package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ServicoContabilOrdemServico;
import com.dobemcontabilidade.repository.ServicoContabilOrdemServicoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ServicoContabilOrdemServico}.
 */
@Service
@Transactional
public class ServicoContabilOrdemServicoService {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilOrdemServicoService.class);

    private final ServicoContabilOrdemServicoRepository servicoContabilOrdemServicoRepository;

    public ServicoContabilOrdemServicoService(ServicoContabilOrdemServicoRepository servicoContabilOrdemServicoRepository) {
        this.servicoContabilOrdemServicoRepository = servicoContabilOrdemServicoRepository;
    }

    /**
     * Save a servicoContabilOrdemServico.
     *
     * @param servicoContabilOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilOrdemServico save(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        log.debug("Request to save ServicoContabilOrdemServico : {}", servicoContabilOrdemServico);
        return servicoContabilOrdemServicoRepository.save(servicoContabilOrdemServico);
    }

    /**
     * Update a servicoContabilOrdemServico.
     *
     * @param servicoContabilOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabilOrdemServico update(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        log.debug("Request to update ServicoContabilOrdemServico : {}", servicoContabilOrdemServico);
        return servicoContabilOrdemServicoRepository.save(servicoContabilOrdemServico);
    }

    /**
     * Partially update a servicoContabilOrdemServico.
     *
     * @param servicoContabilOrdemServico the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServicoContabilOrdemServico> partialUpdate(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        log.debug("Request to partially update ServicoContabilOrdemServico : {}", servicoContabilOrdemServico);

        return servicoContabilOrdemServicoRepository
            .findById(servicoContabilOrdemServico.getId())
            .map(existingServicoContabilOrdemServico -> {
                if (servicoContabilOrdemServico.getDataAdmin() != null) {
                    existingServicoContabilOrdemServico.setDataAdmin(servicoContabilOrdemServico.getDataAdmin());
                }
                if (servicoContabilOrdemServico.getDataLegal() != null) {
                    existingServicoContabilOrdemServico.setDataLegal(servicoContabilOrdemServico.getDataLegal());
                }

                return existingServicoContabilOrdemServico;
            })
            .map(servicoContabilOrdemServicoRepository::save);
    }

    /**
     * Get all the servicoContabilOrdemServicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoContabilOrdemServico> findAll() {
        log.debug("Request to get all ServicoContabilOrdemServicos");
        return servicoContabilOrdemServicoRepository.findAll();
    }

    /**
     * Get all the servicoContabilOrdemServicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ServicoContabilOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return servicoContabilOrdemServicoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one servicoContabilOrdemServico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicoContabilOrdemServico> findOne(Long id) {
        log.debug("Request to get ServicoContabilOrdemServico : {}", id);
        return servicoContabilOrdemServicoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the servicoContabilOrdemServico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServicoContabilOrdemServico : {}", id);
        servicoContabilOrdemServicoRepository.deleteById(id);
    }
}
