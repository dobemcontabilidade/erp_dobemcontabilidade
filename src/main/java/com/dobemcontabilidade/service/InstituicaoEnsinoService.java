package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.InstituicaoEnsino;
import com.dobemcontabilidade.repository.InstituicaoEnsinoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.InstituicaoEnsino}.
 */
@Service
@Transactional
public class InstituicaoEnsinoService {

    private static final Logger log = LoggerFactory.getLogger(InstituicaoEnsinoService.class);

    private final InstituicaoEnsinoRepository instituicaoEnsinoRepository;

    public InstituicaoEnsinoService(InstituicaoEnsinoRepository instituicaoEnsinoRepository) {
        this.instituicaoEnsinoRepository = instituicaoEnsinoRepository;
    }

    /**
     * Save a instituicaoEnsino.
     *
     * @param instituicaoEnsino the entity to save.
     * @return the persisted entity.
     */
    public InstituicaoEnsino save(InstituicaoEnsino instituicaoEnsino) {
        log.debug("Request to save InstituicaoEnsino : {}", instituicaoEnsino);
        return instituicaoEnsinoRepository.save(instituicaoEnsino);
    }

    /**
     * Update a instituicaoEnsino.
     *
     * @param instituicaoEnsino the entity to save.
     * @return the persisted entity.
     */
    public InstituicaoEnsino update(InstituicaoEnsino instituicaoEnsino) {
        log.debug("Request to update InstituicaoEnsino : {}", instituicaoEnsino);
        return instituicaoEnsinoRepository.save(instituicaoEnsino);
    }

    /**
     * Partially update a instituicaoEnsino.
     *
     * @param instituicaoEnsino the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InstituicaoEnsino> partialUpdate(InstituicaoEnsino instituicaoEnsino) {
        log.debug("Request to partially update InstituicaoEnsino : {}", instituicaoEnsino);

        return instituicaoEnsinoRepository
            .findById(instituicaoEnsino.getId())
            .map(existingInstituicaoEnsino -> {
                if (instituicaoEnsino.getNome() != null) {
                    existingInstituicaoEnsino.setNome(instituicaoEnsino.getNome());
                }
                if (instituicaoEnsino.getCnpj() != null) {
                    existingInstituicaoEnsino.setCnpj(instituicaoEnsino.getCnpj());
                }
                if (instituicaoEnsino.getLogradouro() != null) {
                    existingInstituicaoEnsino.setLogradouro(instituicaoEnsino.getLogradouro());
                }
                if (instituicaoEnsino.getNumero() != null) {
                    existingInstituicaoEnsino.setNumero(instituicaoEnsino.getNumero());
                }
                if (instituicaoEnsino.getComplemento() != null) {
                    existingInstituicaoEnsino.setComplemento(instituicaoEnsino.getComplemento());
                }
                if (instituicaoEnsino.getBairro() != null) {
                    existingInstituicaoEnsino.setBairro(instituicaoEnsino.getBairro());
                }
                if (instituicaoEnsino.getCep() != null) {
                    existingInstituicaoEnsino.setCep(instituicaoEnsino.getCep());
                }
                if (instituicaoEnsino.getPrincipal() != null) {
                    existingInstituicaoEnsino.setPrincipal(instituicaoEnsino.getPrincipal());
                }

                return existingInstituicaoEnsino;
            })
            .map(instituicaoEnsinoRepository::save);
    }

    /**
     * Get all the instituicaoEnsinos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InstituicaoEnsino> findAll() {
        log.debug("Request to get all InstituicaoEnsinos");
        return instituicaoEnsinoRepository.findAll();
    }

    /**
     * Get all the instituicaoEnsinos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<InstituicaoEnsino> findAllWithEagerRelationships(Pageable pageable) {
        return instituicaoEnsinoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one instituicaoEnsino by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InstituicaoEnsino> findOne(Long id) {
        log.debug("Request to get InstituicaoEnsino : {}", id);
        return instituicaoEnsinoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the instituicaoEnsino by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InstituicaoEnsino : {}", id);
        instituicaoEnsinoRepository.deleteById(id);
    }
}
