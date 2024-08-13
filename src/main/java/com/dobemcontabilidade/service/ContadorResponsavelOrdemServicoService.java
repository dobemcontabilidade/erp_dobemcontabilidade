package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico;
import com.dobemcontabilidade.repository.ContadorResponsavelOrdemServicoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico}.
 */
@Service
@Transactional
public class ContadorResponsavelOrdemServicoService {

    private static final Logger log = LoggerFactory.getLogger(ContadorResponsavelOrdemServicoService.class);

    private final ContadorResponsavelOrdemServicoRepository contadorResponsavelOrdemServicoRepository;

    public ContadorResponsavelOrdemServicoService(ContadorResponsavelOrdemServicoRepository contadorResponsavelOrdemServicoRepository) {
        this.contadorResponsavelOrdemServicoRepository = contadorResponsavelOrdemServicoRepository;
    }

    /**
     * Save a contadorResponsavelOrdemServico.
     *
     * @param contadorResponsavelOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public ContadorResponsavelOrdemServico save(ContadorResponsavelOrdemServico contadorResponsavelOrdemServico) {
        log.debug("Request to save ContadorResponsavelOrdemServico : {}", contadorResponsavelOrdemServico);
        return contadorResponsavelOrdemServicoRepository.save(contadorResponsavelOrdemServico);
    }

    /**
     * Update a contadorResponsavelOrdemServico.
     *
     * @param contadorResponsavelOrdemServico the entity to save.
     * @return the persisted entity.
     */
    public ContadorResponsavelOrdemServico update(ContadorResponsavelOrdemServico contadorResponsavelOrdemServico) {
        log.debug("Request to update ContadorResponsavelOrdemServico : {}", contadorResponsavelOrdemServico);
        return contadorResponsavelOrdemServicoRepository.save(contadorResponsavelOrdemServico);
    }

    /**
     * Partially update a contadorResponsavelOrdemServico.
     *
     * @param contadorResponsavelOrdemServico the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContadorResponsavelOrdemServico> partialUpdate(ContadorResponsavelOrdemServico contadorResponsavelOrdemServico) {
        log.debug("Request to partially update ContadorResponsavelOrdemServico : {}", contadorResponsavelOrdemServico);

        return contadorResponsavelOrdemServicoRepository
            .findById(contadorResponsavelOrdemServico.getId())
            .map(existingContadorResponsavelOrdemServico -> {
                if (contadorResponsavelOrdemServico.getDataAtribuicao() != null) {
                    existingContadorResponsavelOrdemServico.setDataAtribuicao(contadorResponsavelOrdemServico.getDataAtribuicao());
                }
                if (contadorResponsavelOrdemServico.getDataRevogacao() != null) {
                    existingContadorResponsavelOrdemServico.setDataRevogacao(contadorResponsavelOrdemServico.getDataRevogacao());
                }

                return existingContadorResponsavelOrdemServico;
            })
            .map(contadorResponsavelOrdemServicoRepository::save);
    }

    /**
     * Get all the contadorResponsavelOrdemServicos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContadorResponsavelOrdemServico> findAll() {
        log.debug("Request to get all ContadorResponsavelOrdemServicos");
        return contadorResponsavelOrdemServicoRepository.findAll();
    }

    /**
     * Get all the contadorResponsavelOrdemServicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ContadorResponsavelOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return contadorResponsavelOrdemServicoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one contadorResponsavelOrdemServico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContadorResponsavelOrdemServico> findOne(Long id) {
        log.debug("Request to get ContadorResponsavelOrdemServico : {}", id);
        return contadorResponsavelOrdemServicoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the contadorResponsavelOrdemServico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContadorResponsavelOrdemServico : {}", id);
        contadorResponsavelOrdemServicoRepository.deleteById(id);
    }
}
