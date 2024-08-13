package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Avaliacao;
import com.dobemcontabilidade.repository.AvaliacaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Avaliacao}.
 */
@Service
@Transactional
public class AvaliacaoService {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoService.class);

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    /**
     * Save a avaliacao.
     *
     * @param avaliacao the entity to save.
     * @return the persisted entity.
     */
    public Avaliacao save(Avaliacao avaliacao) {
        log.debug("Request to save Avaliacao : {}", avaliacao);
        return avaliacaoRepository.save(avaliacao);
    }

    /**
     * Update a avaliacao.
     *
     * @param avaliacao the entity to save.
     * @return the persisted entity.
     */
    public Avaliacao update(Avaliacao avaliacao) {
        log.debug("Request to update Avaliacao : {}", avaliacao);
        return avaliacaoRepository.save(avaliacao);
    }

    /**
     * Partially update a avaliacao.
     *
     * @param avaliacao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Avaliacao> partialUpdate(Avaliacao avaliacao) {
        log.debug("Request to partially update Avaliacao : {}", avaliacao);

        return avaliacaoRepository
            .findById(avaliacao.getId())
            .map(existingAvaliacao -> {
                if (avaliacao.getNome() != null) {
                    existingAvaliacao.setNome(avaliacao.getNome());
                }
                if (avaliacao.getDescricao() != null) {
                    existingAvaliacao.setDescricao(avaliacao.getDescricao());
                }

                return existingAvaliacao;
            })
            .map(avaliacaoRepository::save);
    }

    /**
     * Get all the avaliacaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Avaliacao> findAll() {
        log.debug("Request to get all Avaliacaos");
        return avaliacaoRepository.findAll();
    }

    /**
     * Get one avaliacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Avaliacao> findOne(Long id) {
        log.debug("Request to get Avaliacao : {}", id);
        return avaliacaoRepository.findById(id);
    }

    /**
     * Delete the avaliacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Avaliacao : {}", id);
        avaliacaoRepository.deleteById(id);
    }
}
