package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoRequeridoPessoa;
import com.dobemcontabilidade.repository.AnexoRequeridoPessoaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoPessoa}.
 */
@Service
@Transactional
public class AnexoRequeridoPessoaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoPessoaService.class);

    private final AnexoRequeridoPessoaRepository anexoRequeridoPessoaRepository;

    public AnexoRequeridoPessoaService(AnexoRequeridoPessoaRepository anexoRequeridoPessoaRepository) {
        this.anexoRequeridoPessoaRepository = anexoRequeridoPessoaRepository;
    }

    /**
     * Save a anexoRequeridoPessoa.
     *
     * @param anexoRequeridoPessoa the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoPessoa save(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        log.debug("Request to save AnexoRequeridoPessoa : {}", anexoRequeridoPessoa);
        return anexoRequeridoPessoaRepository.save(anexoRequeridoPessoa);
    }

    /**
     * Update a anexoRequeridoPessoa.
     *
     * @param anexoRequeridoPessoa the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequeridoPessoa update(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        log.debug("Request to update AnexoRequeridoPessoa : {}", anexoRequeridoPessoa);
        return anexoRequeridoPessoaRepository.save(anexoRequeridoPessoa);
    }

    /**
     * Partially update a anexoRequeridoPessoa.
     *
     * @param anexoRequeridoPessoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoRequeridoPessoa> partialUpdate(AnexoRequeridoPessoa anexoRequeridoPessoa) {
        log.debug("Request to partially update AnexoRequeridoPessoa : {}", anexoRequeridoPessoa);

        return anexoRequeridoPessoaRepository
            .findById(anexoRequeridoPessoa.getId())
            .map(existingAnexoRequeridoPessoa -> {
                if (anexoRequeridoPessoa.getObrigatorio() != null) {
                    existingAnexoRequeridoPessoa.setObrigatorio(anexoRequeridoPessoa.getObrigatorio());
                }
                if (anexoRequeridoPessoa.getTipo() != null) {
                    existingAnexoRequeridoPessoa.setTipo(anexoRequeridoPessoa.getTipo());
                }

                return existingAnexoRequeridoPessoa;
            })
            .map(anexoRequeridoPessoaRepository::save);
    }

    /**
     * Get all the anexoRequeridoPessoas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoRequeridoPessoa> findAll() {
        log.debug("Request to get all AnexoRequeridoPessoas");
        return anexoRequeridoPessoaRepository.findAll();
    }

    /**
     * Get all the anexoRequeridoPessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoRequeridoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return anexoRequeridoPessoaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoRequeridoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoRequeridoPessoa> findOne(Long id) {
        log.debug("Request to get AnexoRequeridoPessoa : {}", id);
        return anexoRequeridoPessoaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoRequeridoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoRequeridoPessoa : {}", id);
        anexoRequeridoPessoaRepository.deleteById(id);
    }
}
