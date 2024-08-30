package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AdicionalTributacao;
import com.dobemcontabilidade.repository.AdicionalTributacaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AdicionalTributacao}.
 */
@Service
@Transactional
public class AdicionalTributacaoService {

    private static final Logger log = LoggerFactory.getLogger(AdicionalTributacaoService.class);

    private final AdicionalTributacaoRepository adicionalTributacaoRepository;

    public AdicionalTributacaoService(AdicionalTributacaoRepository adicionalTributacaoRepository) {
        this.adicionalTributacaoRepository = adicionalTributacaoRepository;
    }

    /**
     * Save a adicionalTributacao.
     *
     * @param adicionalTributacao the entity to save.
     * @return the persisted entity.
     */
    public AdicionalTributacao save(AdicionalTributacao adicionalTributacao) {
        log.debug("Request to save AdicionalTributacao : {}", adicionalTributacao);
        return adicionalTributacaoRepository.save(adicionalTributacao);
    }

    /**
     * Update a adicionalTributacao.
     *
     * @param adicionalTributacao the entity to save.
     * @return the persisted entity.
     */
    public AdicionalTributacao update(AdicionalTributacao adicionalTributacao) {
        log.debug("Request to update AdicionalTributacao : {}", adicionalTributacao);
        return adicionalTributacaoRepository.save(adicionalTributacao);
    }

    /**
     * Partially update a adicionalTributacao.
     *
     * @param adicionalTributacao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AdicionalTributacao> partialUpdate(AdicionalTributacao adicionalTributacao) {
        log.debug("Request to partially update AdicionalTributacao : {}", adicionalTributacao);

        return adicionalTributacaoRepository
            .findById(adicionalTributacao.getId())
            .map(existingAdicionalTributacao -> {
                if (adicionalTributacao.getValor() != null) {
                    existingAdicionalTributacao.setValor(adicionalTributacao.getValor());
                }

                return existingAdicionalTributacao;
            })
            .map(adicionalTributacaoRepository::save);
    }

    /**
     * Get all the adicionalTributacaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AdicionalTributacao> findAllWithEagerRelationships(Pageable pageable) {
        return adicionalTributacaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one adicionalTributacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AdicionalTributacao> findOne(Long id) {
        log.debug("Request to get AdicionalTributacao : {}", id);
        return adicionalTributacaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the adicionalTributacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AdicionalTributacao : {}", id);
        adicionalTributacaoRepository.deleteById(id);
    }
}
