package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CriterioAvaliacao;
import com.dobemcontabilidade.repository.CriterioAvaliacaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.CriterioAvaliacao}.
 */
@Service
@Transactional
public class CriterioAvaliacaoService {

    private static final Logger log = LoggerFactory.getLogger(CriterioAvaliacaoService.class);

    private final CriterioAvaliacaoRepository criterioAvaliacaoRepository;

    public CriterioAvaliacaoService(CriterioAvaliacaoRepository criterioAvaliacaoRepository) {
        this.criterioAvaliacaoRepository = criterioAvaliacaoRepository;
    }

    /**
     * Save a criterioAvaliacao.
     *
     * @param criterioAvaliacao the entity to save.
     * @return the persisted entity.
     */
    public CriterioAvaliacao save(CriterioAvaliacao criterioAvaliacao) {
        log.debug("Request to save CriterioAvaliacao : {}", criterioAvaliacao);
        return criterioAvaliacaoRepository.save(criterioAvaliacao);
    }

    /**
     * Update a criterioAvaliacao.
     *
     * @param criterioAvaliacao the entity to save.
     * @return the persisted entity.
     */
    public CriterioAvaliacao update(CriterioAvaliacao criterioAvaliacao) {
        log.debug("Request to update CriterioAvaliacao : {}", criterioAvaliacao);
        return criterioAvaliacaoRepository.save(criterioAvaliacao);
    }

    /**
     * Partially update a criterioAvaliacao.
     *
     * @param criterioAvaliacao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CriterioAvaliacao> partialUpdate(CriterioAvaliacao criterioAvaliacao) {
        log.debug("Request to partially update CriterioAvaliacao : {}", criterioAvaliacao);

        return criterioAvaliacaoRepository
            .findById(criterioAvaliacao.getId())
            .map(existingCriterioAvaliacao -> {
                if (criterioAvaliacao.getNome() != null) {
                    existingCriterioAvaliacao.setNome(criterioAvaliacao.getNome());
                }
                if (criterioAvaliacao.getDescricao() != null) {
                    existingCriterioAvaliacao.setDescricao(criterioAvaliacao.getDescricao());
                }

                return existingCriterioAvaliacao;
            })
            .map(criterioAvaliacaoRepository::save);
    }

    /**
     * Get all the criterioAvaliacaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CriterioAvaliacao> findAll() {
        log.debug("Request to get all CriterioAvaliacaos");
        return criterioAvaliacaoRepository.findAll();
    }

    /**
     * Get one criterioAvaliacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CriterioAvaliacao> findOne(Long id) {
        log.debug("Request to get CriterioAvaliacao : {}", id);
        return criterioAvaliacaoRepository.findById(id);
    }

    /**
     * Delete the criterioAvaliacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CriterioAvaliacao : {}", id);
        criterioAvaliacaoRepository.deleteById(id);
    }
}
