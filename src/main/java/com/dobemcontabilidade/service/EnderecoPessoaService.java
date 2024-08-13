package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EnderecoPessoa}.
 */
@Service
@Transactional
public class EnderecoPessoaService {

    private static final Logger log = LoggerFactory.getLogger(EnderecoPessoaService.class);

    private final EnderecoPessoaRepository enderecoPessoaRepository;

    public EnderecoPessoaService(EnderecoPessoaRepository enderecoPessoaRepository) {
        this.enderecoPessoaRepository = enderecoPessoaRepository;
    }

    /**
     * Save a enderecoPessoa.
     *
     * @param enderecoPessoa the entity to save.
     * @return the persisted entity.
     */
    public EnderecoPessoa save(EnderecoPessoa enderecoPessoa) {
        log.debug("Request to save EnderecoPessoa : {}", enderecoPessoa);
        return enderecoPessoaRepository.save(enderecoPessoa);
    }

    /**
     * Update a enderecoPessoa.
     *
     * @param enderecoPessoa the entity to save.
     * @return the persisted entity.
     */
    public EnderecoPessoa update(EnderecoPessoa enderecoPessoa) {
        log.debug("Request to update EnderecoPessoa : {}", enderecoPessoa);
        return enderecoPessoaRepository.save(enderecoPessoa);
    }

    /**
     * Partially update a enderecoPessoa.
     *
     * @param enderecoPessoa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnderecoPessoa> partialUpdate(EnderecoPessoa enderecoPessoa) {
        log.debug("Request to partially update EnderecoPessoa : {}", enderecoPessoa);

        return enderecoPessoaRepository
            .findById(enderecoPessoa.getId())
            .map(existingEnderecoPessoa -> {
                if (enderecoPessoa.getLogradouro() != null) {
                    existingEnderecoPessoa.setLogradouro(enderecoPessoa.getLogradouro());
                }
                if (enderecoPessoa.getNumero() != null) {
                    existingEnderecoPessoa.setNumero(enderecoPessoa.getNumero());
                }
                if (enderecoPessoa.getComplemento() != null) {
                    existingEnderecoPessoa.setComplemento(enderecoPessoa.getComplemento());
                }
                if (enderecoPessoa.getBairro() != null) {
                    existingEnderecoPessoa.setBairro(enderecoPessoa.getBairro());
                }
                if (enderecoPessoa.getCep() != null) {
                    existingEnderecoPessoa.setCep(enderecoPessoa.getCep());
                }
                if (enderecoPessoa.getPrincipal() != null) {
                    existingEnderecoPessoa.setPrincipal(enderecoPessoa.getPrincipal());
                }
                if (enderecoPessoa.getResidenciaPropria() != null) {
                    existingEnderecoPessoa.setResidenciaPropria(enderecoPessoa.getResidenciaPropria());
                }

                return existingEnderecoPessoa;
            })
            .map(enderecoPessoaRepository::save);
    }

    /**
     * Get all the enderecoPessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EnderecoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return enderecoPessoaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one enderecoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnderecoPessoa> findOne(Long id) {
        log.debug("Request to get EnderecoPessoa : {}", id);
        return enderecoPessoaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the enderecoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnderecoPessoa : {}", id);
        enderecoPessoaRepository.deleteById(id);
    }
}
