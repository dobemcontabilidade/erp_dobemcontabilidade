package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.TributacaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Tributacao}.
 */
@Service
@Transactional
public class TributacaoService {

    private static final Logger log = LoggerFactory.getLogger(TributacaoService.class);

    private final TributacaoRepository tributacaoRepository;

    public TributacaoService(TributacaoRepository tributacaoRepository) {
        this.tributacaoRepository = tributacaoRepository;
    }

    /**
     * Save a tributacao.
     *
     * @param tributacao the entity to save.
     * @return the persisted entity.
     */
    public Tributacao save(Tributacao tributacao) {
        log.debug("Request to save Tributacao : {}", tributacao);
        return tributacaoRepository.save(tributacao);
    }

    /**
     * Update a tributacao.
     *
     * @param tributacao the entity to save.
     * @return the persisted entity.
     */
    public Tributacao update(Tributacao tributacao) {
        log.debug("Request to update Tributacao : {}", tributacao);
        return tributacaoRepository.save(tributacao);
    }

    /**
     * Partially update a tributacao.
     *
     * @param tributacao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Tributacao> partialUpdate(Tributacao tributacao) {
        log.debug("Request to partially update Tributacao : {}", tributacao);

        return tributacaoRepository
            .findById(tributacao.getId())
            .map(existingTributacao -> {
                if (tributacao.getNome() != null) {
                    existingTributacao.setNome(tributacao.getNome());
                }
                if (tributacao.getDescricao() != null) {
                    existingTributacao.setDescricao(tributacao.getDescricao());
                }
                if (tributacao.getSituacao() != null) {
                    existingTributacao.setSituacao(tributacao.getSituacao());
                }

                return existingTributacao;
            })
            .map(tributacaoRepository::save);
    }

    /**
     * Get one tributacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tributacao> findOne(Long id) {
        log.debug("Request to get Tributacao : {}", id);
        return tributacaoRepository.findById(id);
    }

    /**
     * Delete the tributacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tributacao : {}", id);
        tributacaoRepository.deleteById(id);
    }
}
