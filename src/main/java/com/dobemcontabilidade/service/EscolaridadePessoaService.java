package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EscolaridadePessoa;
import com.dobemcontabilidade.repository.EscolaridadePessoaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EscolaridadePessoa}.
 */
@Service
@Transactional
public class EscolaridadePessoaService {

    private static final Logger log = LoggerFactory.getLogger(EscolaridadePessoaService.class);

    private final EscolaridadePessoaRepository escolaridadePessoaRepository;

    public EscolaridadePessoaService(EscolaridadePessoaRepository escolaridadePessoaRepository) {
        this.escolaridadePessoaRepository = escolaridadePessoaRepository;
    }

    /**
     * Save a escolaridadePessoa.
     *
     * @param escolaridadePessoa the entity to save.
     * @return the persisted entity.
     */
    public EscolaridadePessoa save(EscolaridadePessoa escolaridadePessoa) {
        log.debug("Request to save EscolaridadePessoa : {}", escolaridadePessoa);
        return escolaridadePessoaRepository.save(escolaridadePessoa);
    }

    /**
     * Update a escolaridadePessoa.
     *
     * @param escolaridadePessoa the entity to save.
     * @return the persisted entity.
     */
    public EscolaridadePessoa update(EscolaridadePessoa escolaridadePessoa) {
        log.debug("Request to update EscolaridadePessoa : {}", escolaridadePessoa);
        return escolaridadePessoaRepository.save(escolaridadePessoa);
    }

    /**
     * Partially update a escolaridadePessoa.
     *
     * @param escolaridadePessoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EscolaridadePessoa> partialUpdate(EscolaridadePessoa escolaridadePessoa) {
        log.debug("Request to partially update EscolaridadePessoa : {}", escolaridadePessoa);

        return escolaridadePessoaRepository
            .findById(escolaridadePessoa.getId())
            .map(existingEscolaridadePessoa -> {
                if (escolaridadePessoa.getNomeInstituicao() != null) {
                    existingEscolaridadePessoa.setNomeInstituicao(escolaridadePessoa.getNomeInstituicao());
                }
                if (escolaridadePessoa.getAnoConclusao() != null) {
                    existingEscolaridadePessoa.setAnoConclusao(escolaridadePessoa.getAnoConclusao());
                }
                if (escolaridadePessoa.getUrlComprovanteEscolaridade() != null) {
                    existingEscolaridadePessoa.setUrlComprovanteEscolaridade(escolaridadePessoa.getUrlComprovanteEscolaridade());
                }

                return existingEscolaridadePessoa;
            })
            .map(escolaridadePessoaRepository::save);
    }

    /**
     * Get all the escolaridadePessoas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EscolaridadePessoa> findAll() {
        log.debug("Request to get all EscolaridadePessoas");
        return escolaridadePessoaRepository.findAll();
    }

    /**
     * Get all the escolaridadePessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EscolaridadePessoa> findAllWithEagerRelationships(Pageable pageable) {
        return escolaridadePessoaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one escolaridadePessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EscolaridadePessoa> findOne(Long id) {
        log.debug("Request to get EscolaridadePessoa : {}", id);
        return escolaridadePessoaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the escolaridadePessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EscolaridadePessoa : {}", id);
        escolaridadePessoaRepository.deleteById(id);
    }
}
