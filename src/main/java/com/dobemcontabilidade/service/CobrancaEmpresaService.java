package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CobrancaEmpresa;
import com.dobemcontabilidade.repository.CobrancaEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.CobrancaEmpresa}.
 */
@Service
@Transactional
public class CobrancaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(CobrancaEmpresaService.class);

    private final CobrancaEmpresaRepository cobrancaEmpresaRepository;

    public CobrancaEmpresaService(CobrancaEmpresaRepository cobrancaEmpresaRepository) {
        this.cobrancaEmpresaRepository = cobrancaEmpresaRepository;
    }

    /**
     * Save a cobrancaEmpresa.
     *
     * @param cobrancaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public CobrancaEmpresa save(CobrancaEmpresa cobrancaEmpresa) {
        log.debug("Request to save CobrancaEmpresa : {}", cobrancaEmpresa);
        return cobrancaEmpresaRepository.save(cobrancaEmpresa);
    }

    /**
     * Update a cobrancaEmpresa.
     *
     * @param cobrancaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public CobrancaEmpresa update(CobrancaEmpresa cobrancaEmpresa) {
        log.debug("Request to update CobrancaEmpresa : {}", cobrancaEmpresa);
        return cobrancaEmpresaRepository.save(cobrancaEmpresa);
    }

    /**
     * Partially update a cobrancaEmpresa.
     *
     * @param cobrancaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CobrancaEmpresa> partialUpdate(CobrancaEmpresa cobrancaEmpresa) {
        log.debug("Request to partially update CobrancaEmpresa : {}", cobrancaEmpresa);

        return cobrancaEmpresaRepository
            .findById(cobrancaEmpresa.getId())
            .map(existingCobrancaEmpresa -> {
                if (cobrancaEmpresa.getDataCobranca() != null) {
                    existingCobrancaEmpresa.setDataCobranca(cobrancaEmpresa.getDataCobranca());
                }
                if (cobrancaEmpresa.getValorPago() != null) {
                    existingCobrancaEmpresa.setValorPago(cobrancaEmpresa.getValorPago());
                }
                if (cobrancaEmpresa.getUrlCobranca() != null) {
                    existingCobrancaEmpresa.setUrlCobranca(cobrancaEmpresa.getUrlCobranca());
                }
                if (cobrancaEmpresa.getUrlArquivo() != null) {
                    existingCobrancaEmpresa.setUrlArquivo(cobrancaEmpresa.getUrlArquivo());
                }
                if (cobrancaEmpresa.getValorCobrado() != null) {
                    existingCobrancaEmpresa.setValorCobrado(cobrancaEmpresa.getValorCobrado());
                }
                if (cobrancaEmpresa.getSituacaoCobranca() != null) {
                    existingCobrancaEmpresa.setSituacaoCobranca(cobrancaEmpresa.getSituacaoCobranca());
                }

                return existingCobrancaEmpresa;
            })
            .map(cobrancaEmpresaRepository::save);
    }

    /**
     * Get all the cobrancaEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CobrancaEmpresa> findAll() {
        log.debug("Request to get all CobrancaEmpresas");
        return cobrancaEmpresaRepository.findAll();
    }

    /**
     * Get all the cobrancaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CobrancaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return cobrancaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one cobrancaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CobrancaEmpresa> findOne(Long id) {
        log.debug("Request to get CobrancaEmpresa : {}", id);
        return cobrancaEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cobrancaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CobrancaEmpresa : {}", id);
        cobrancaEmpresaRepository.deleteById(id);
    }
}
