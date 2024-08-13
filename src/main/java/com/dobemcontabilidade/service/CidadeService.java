package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.repository.CidadeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Cidade}.
 */
@Service
@Transactional
public class CidadeService {

    private static final Logger log = LoggerFactory.getLogger(CidadeService.class);

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    /**
     * Save a cidade.
     *
     * @param cidade the entity to save.
     * @return the persisted entity.
     */
    public Cidade save(Cidade cidade) {
        log.debug("Request to save Cidade : {}", cidade);
        return cidadeRepository.save(cidade);
    }

    /**
     * Update a cidade.
     *
     * @param cidade the entity to save.
     * @return the persisted entity.
     */
    public Cidade update(Cidade cidade) {
        log.debug("Request to update Cidade : {}", cidade);
        return cidadeRepository.save(cidade);
    }

    /**
     * Partially update a cidade.
     *
     * @param cidade the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Cidade> partialUpdate(Cidade cidade) {
        log.debug("Request to partially update Cidade : {}", cidade);

        return cidadeRepository
            .findById(cidade.getId())
            .map(existingCidade -> {
                if (cidade.getNome() != null) {
                    existingCidade.setNome(cidade.getNome());
                }
                if (cidade.getContratacao() != null) {
                    existingCidade.setContratacao(cidade.getContratacao());
                }
                if (cidade.getAbertura() != null) {
                    existingCidade.setAbertura(cidade.getAbertura());
                }

                return existingCidade;
            })
            .map(cidadeRepository::save);
    }

    /**
     * Get all the cidades with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Cidade> findAllWithEagerRelationships(Pageable pageable) {
        return cidadeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one cidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Cidade> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cidade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}
