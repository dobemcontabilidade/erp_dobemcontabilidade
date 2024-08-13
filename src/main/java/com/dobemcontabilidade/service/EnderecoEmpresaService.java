package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EnderecoEmpresa}.
 */
@Service
@Transactional
public class EnderecoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EnderecoEmpresaService.class);

    private final EnderecoEmpresaRepository enderecoEmpresaRepository;

    public EnderecoEmpresaService(EnderecoEmpresaRepository enderecoEmpresaRepository) {
        this.enderecoEmpresaRepository = enderecoEmpresaRepository;
    }

    /**
     * Save a enderecoEmpresa.
     *
     * @param enderecoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public EnderecoEmpresa save(EnderecoEmpresa enderecoEmpresa) {
        log.debug("Request to save EnderecoEmpresa : {}", enderecoEmpresa);
        return enderecoEmpresaRepository.save(enderecoEmpresa);
    }

    /**
     * Update a enderecoEmpresa.
     *
     * @param enderecoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public EnderecoEmpresa update(EnderecoEmpresa enderecoEmpresa) {
        log.debug("Request to update EnderecoEmpresa : {}", enderecoEmpresa);
        return enderecoEmpresaRepository.save(enderecoEmpresa);
    }

    /**
     * Partially update a enderecoEmpresa.
     *
     * @param enderecoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnderecoEmpresa> partialUpdate(EnderecoEmpresa enderecoEmpresa) {
        log.debug("Request to partially update EnderecoEmpresa : {}", enderecoEmpresa);

        return enderecoEmpresaRepository
            .findById(enderecoEmpresa.getId())
            .map(existingEnderecoEmpresa -> {
                if (enderecoEmpresa.getLogradouro() != null) {
                    existingEnderecoEmpresa.setLogradouro(enderecoEmpresa.getLogradouro());
                }
                if (enderecoEmpresa.getNumero() != null) {
                    existingEnderecoEmpresa.setNumero(enderecoEmpresa.getNumero());
                }
                if (enderecoEmpresa.getComplemento() != null) {
                    existingEnderecoEmpresa.setComplemento(enderecoEmpresa.getComplemento());
                }
                if (enderecoEmpresa.getBairro() != null) {
                    existingEnderecoEmpresa.setBairro(enderecoEmpresa.getBairro());
                }
                if (enderecoEmpresa.getCep() != null) {
                    existingEnderecoEmpresa.setCep(enderecoEmpresa.getCep());
                }
                if (enderecoEmpresa.getPrincipal() != null) {
                    existingEnderecoEmpresa.setPrincipal(enderecoEmpresa.getPrincipal());
                }
                if (enderecoEmpresa.getFilial() != null) {
                    existingEnderecoEmpresa.setFilial(enderecoEmpresa.getFilial());
                }
                if (enderecoEmpresa.getEnderecoFiscal() != null) {
                    existingEnderecoEmpresa.setEnderecoFiscal(enderecoEmpresa.getEnderecoFiscal());
                }

                return existingEnderecoEmpresa;
            })
            .map(enderecoEmpresaRepository::save);
    }

    /**
     * Get all the enderecoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EnderecoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return enderecoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one enderecoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnderecoEmpresa> findOne(Long id) {
        log.debug("Request to get EnderecoEmpresa : {}", id);
        return enderecoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the enderecoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnderecoEmpresa : {}", id);
        enderecoEmpresaRepository.deleteById(id);
    }
}
