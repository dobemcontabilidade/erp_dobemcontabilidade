package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.repository.AnexoPessoaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoPessoa}.
 */
@Service
@Transactional
public class AnexoPessoaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoPessoaService.class);

    private final AnexoPessoaRepository anexoPessoaRepository;

    public AnexoPessoaService(AnexoPessoaRepository anexoPessoaRepository) {
        this.anexoPessoaRepository = anexoPessoaRepository;
    }

    /**
     * Save a anexoPessoa.
     *
     * @param anexoPessoa the entity to save.
     * @return the persisted entity.
     */
    public AnexoPessoa save(AnexoPessoa anexoPessoa) {
        log.debug("Request to save AnexoPessoa : {}", anexoPessoa);
        return anexoPessoaRepository.save(anexoPessoa);
    }

    /**
     * Update a anexoPessoa.
     *
     * @param anexoPessoa the entity to save.
     * @return the persisted entity.
     */
    public AnexoPessoa update(AnexoPessoa anexoPessoa) {
        log.debug("Request to update AnexoPessoa : {}", anexoPessoa);
        return anexoPessoaRepository.save(anexoPessoa);
    }

    /**
     * Partially update a anexoPessoa.
     *
     * @param anexoPessoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoPessoa> partialUpdate(AnexoPessoa anexoPessoa) {
        log.debug("Request to partially update AnexoPessoa : {}", anexoPessoa);

        return anexoPessoaRepository
            .findById(anexoPessoa.getId())
            .map(existingAnexoPessoa -> {
                if (anexoPessoa.getUrlArquivo() != null) {
                    existingAnexoPessoa.setUrlArquivo(anexoPessoa.getUrlArquivo());
                }
                if (anexoPessoa.getDescricao() != null) {
                    existingAnexoPessoa.setDescricao(anexoPessoa.getDescricao());
                }

                return existingAnexoPessoa;
            })
            .map(anexoPessoaRepository::save);
    }

    /**
     * Get all the anexoPessoas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoPessoa> findAll() {
        log.debug("Request to get all AnexoPessoas");
        return anexoPessoaRepository.findAll();
    }

    /**
     * Get all the anexoPessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return anexoPessoaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one anexoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoPessoa> findOne(Long id) {
        log.debug("Request to get AnexoPessoa : {}", id);
        return anexoPessoaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the anexoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoPessoa : {}", id);
        anexoPessoaRepository.deleteById(id);
    }
}
