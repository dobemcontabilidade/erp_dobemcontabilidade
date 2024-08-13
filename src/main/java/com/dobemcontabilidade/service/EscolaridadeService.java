package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Escolaridade;
import com.dobemcontabilidade.repository.EscolaridadeRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Escolaridade}.
 */
@Service
@Transactional
public class EscolaridadeService {

    private static final Logger log = LoggerFactory.getLogger(EscolaridadeService.class);

    private final EscolaridadeRepository escolaridadeRepository;

    public EscolaridadeService(EscolaridadeRepository escolaridadeRepository) {
        this.escolaridadeRepository = escolaridadeRepository;
    }

    /**
     * Save a escolaridade.
     *
     * @param escolaridade the entity to save.
     * @return the persisted entity.
     */
    public Escolaridade save(Escolaridade escolaridade) {
        log.debug("Request to save Escolaridade : {}", escolaridade);
        return escolaridadeRepository.save(escolaridade);
    }

    /**
     * Update a escolaridade.
     *
     * @param escolaridade the entity to save.
     * @return the persisted entity.
     */
    public Escolaridade update(Escolaridade escolaridade) {
        log.debug("Request to update Escolaridade : {}", escolaridade);
        return escolaridadeRepository.save(escolaridade);
    }

    /**
     * Partially update a escolaridade.
     *
     * @param escolaridade the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Escolaridade> partialUpdate(Escolaridade escolaridade) {
        log.debug("Request to partially update Escolaridade : {}", escolaridade);

        return escolaridadeRepository
            .findById(escolaridade.getId())
            .map(existingEscolaridade -> {
                if (escolaridade.getNome() != null) {
                    existingEscolaridade.setNome(escolaridade.getNome());
                }
                if (escolaridade.getDescricao() != null) {
                    existingEscolaridade.setDescricao(escolaridade.getDescricao());
                }

                return existingEscolaridade;
            })
            .map(escolaridadeRepository::save);
    }

    /**
     * Get all the escolaridades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Escolaridade> findAll() {
        log.debug("Request to get all Escolaridades");
        return escolaridadeRepository.findAll();
    }

    /**
     * Get one escolaridade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Escolaridade> findOne(Long id) {
        log.debug("Request to get Escolaridade : {}", id);
        return escolaridadeRepository.findById(id);
    }

    /**
     * Delete the escolaridade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Escolaridade : {}", id);
        escolaridadeRepository.deleteById(id);
    }
}
