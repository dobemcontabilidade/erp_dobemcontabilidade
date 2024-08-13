package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.BancoPessoa;
import com.dobemcontabilidade.repository.BancoPessoaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.BancoPessoa}.
 */
@Service
@Transactional
public class BancoPessoaService {

    private static final Logger log = LoggerFactory.getLogger(BancoPessoaService.class);

    private final BancoPessoaRepository bancoPessoaRepository;

    public BancoPessoaService(BancoPessoaRepository bancoPessoaRepository) {
        this.bancoPessoaRepository = bancoPessoaRepository;
    }

    /**
     * Save a bancoPessoa.
     *
     * @param bancoPessoa the entity to save.
     * @return the persisted entity.
     */
    public BancoPessoa save(BancoPessoa bancoPessoa) {
        log.debug("Request to save BancoPessoa : {}", bancoPessoa);
        return bancoPessoaRepository.save(bancoPessoa);
    }

    /**
     * Update a bancoPessoa.
     *
     * @param bancoPessoa the entity to save.
     * @return the persisted entity.
     */
    public BancoPessoa update(BancoPessoa bancoPessoa) {
        log.debug("Request to update BancoPessoa : {}", bancoPessoa);
        return bancoPessoaRepository.save(bancoPessoa);
    }

    /**
     * Partially update a bancoPessoa.
     *
     * @param bancoPessoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BancoPessoa> partialUpdate(BancoPessoa bancoPessoa) {
        log.debug("Request to partially update BancoPessoa : {}", bancoPessoa);

        return bancoPessoaRepository
            .findById(bancoPessoa.getId())
            .map(existingBancoPessoa -> {
                if (bancoPessoa.getAgencia() != null) {
                    existingBancoPessoa.setAgencia(bancoPessoa.getAgencia());
                }
                if (bancoPessoa.getConta() != null) {
                    existingBancoPessoa.setConta(bancoPessoa.getConta());
                }
                if (bancoPessoa.getTipoConta() != null) {
                    existingBancoPessoa.setTipoConta(bancoPessoa.getTipoConta());
                }
                if (bancoPessoa.getPrincipal() != null) {
                    existingBancoPessoa.setPrincipal(bancoPessoa.getPrincipal());
                }

                return existingBancoPessoa;
            })
            .map(bancoPessoaRepository::save);
    }

    /**
     * Get all the bancoPessoas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BancoPessoa> findAll() {
        log.debug("Request to get all BancoPessoas");
        return bancoPessoaRepository.findAll();
    }

    /**
     * Get all the bancoPessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<BancoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return bancoPessoaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one bancoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BancoPessoa> findOne(Long id) {
        log.debug("Request to get BancoPessoa : {}", id);
        return bancoPessoaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the bancoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BancoPessoa : {}", id);
        bancoPessoaRepository.deleteById(id);
    }
}
