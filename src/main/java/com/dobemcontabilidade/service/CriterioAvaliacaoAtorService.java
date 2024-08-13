package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CriterioAvaliacaoAtor;
import com.dobemcontabilidade.repository.CriterioAvaliacaoAtorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.CriterioAvaliacaoAtor}.
 */
@Service
@Transactional
public class CriterioAvaliacaoAtorService {

    private static final Logger log = LoggerFactory.getLogger(CriterioAvaliacaoAtorService.class);

    private final CriterioAvaliacaoAtorRepository criterioAvaliacaoAtorRepository;

    public CriterioAvaliacaoAtorService(CriterioAvaliacaoAtorRepository criterioAvaliacaoAtorRepository) {
        this.criterioAvaliacaoAtorRepository = criterioAvaliacaoAtorRepository;
    }

    /**
     * Save a criterioAvaliacaoAtor.
     *
     * @param criterioAvaliacaoAtor the entity to save.
     * @return the persisted entity.
     */
    public CriterioAvaliacaoAtor save(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        log.debug("Request to save CriterioAvaliacaoAtor : {}", criterioAvaliacaoAtor);
        return criterioAvaliacaoAtorRepository.save(criterioAvaliacaoAtor);
    }

    /**
     * Update a criterioAvaliacaoAtor.
     *
     * @param criterioAvaliacaoAtor the entity to save.
     * @return the persisted entity.
     */
    public CriterioAvaliacaoAtor update(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        log.debug("Request to update CriterioAvaliacaoAtor : {}", criterioAvaliacaoAtor);
        return criterioAvaliacaoAtorRepository.save(criterioAvaliacaoAtor);
    }

    /**
     * Partially update a criterioAvaliacaoAtor.
     *
     * @param criterioAvaliacaoAtor the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CriterioAvaliacaoAtor> partialUpdate(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        log.debug("Request to partially update CriterioAvaliacaoAtor : {}", criterioAvaliacaoAtor);

        return criterioAvaliacaoAtorRepository
            .findById(criterioAvaliacaoAtor.getId())
            .map(existingCriterioAvaliacaoAtor -> {
                if (criterioAvaliacaoAtor.getDescricao() != null) {
                    existingCriterioAvaliacaoAtor.setDescricao(criterioAvaliacaoAtor.getDescricao());
                }
                if (criterioAvaliacaoAtor.getAtivo() != null) {
                    existingCriterioAvaliacaoAtor.setAtivo(criterioAvaliacaoAtor.getAtivo());
                }

                return existingCriterioAvaliacaoAtor;
            })
            .map(criterioAvaliacaoAtorRepository::save);
    }

    /**
     * Get all the criterioAvaliacaoAtors.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CriterioAvaliacaoAtor> findAll() {
        log.debug("Request to get all CriterioAvaliacaoAtors");
        return criterioAvaliacaoAtorRepository.findAll();
    }

    /**
     * Get all the criterioAvaliacaoAtors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CriterioAvaliacaoAtor> findAllWithEagerRelationships(Pageable pageable) {
        return criterioAvaliacaoAtorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one criterioAvaliacaoAtor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CriterioAvaliacaoAtor> findOne(Long id) {
        log.debug("Request to get CriterioAvaliacaoAtor : {}", id);
        return criterioAvaliacaoAtorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the criterioAvaliacaoAtor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CriterioAvaliacaoAtor : {}", id);
        criterioAvaliacaoAtorRepository.deleteById(id);
    }
}
