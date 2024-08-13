package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa;
import com.dobemcontabilidade.repository.GatewayAssinaturaEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa}.
 */
@Service
@Transactional
public class GatewayAssinaturaEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(GatewayAssinaturaEmpresaService.class);

    private final GatewayAssinaturaEmpresaRepository gatewayAssinaturaEmpresaRepository;

    public GatewayAssinaturaEmpresaService(GatewayAssinaturaEmpresaRepository gatewayAssinaturaEmpresaRepository) {
        this.gatewayAssinaturaEmpresaRepository = gatewayAssinaturaEmpresaRepository;
    }

    /**
     * Save a gatewayAssinaturaEmpresa.
     *
     * @param gatewayAssinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public GatewayAssinaturaEmpresa save(GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa) {
        log.debug("Request to save GatewayAssinaturaEmpresa : {}", gatewayAssinaturaEmpresa);
        return gatewayAssinaturaEmpresaRepository.save(gatewayAssinaturaEmpresa);
    }

    /**
     * Update a gatewayAssinaturaEmpresa.
     *
     * @param gatewayAssinaturaEmpresa the entity to save.
     * @return the persisted entity.
     */
    public GatewayAssinaturaEmpresa update(GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa) {
        log.debug("Request to update GatewayAssinaturaEmpresa : {}", gatewayAssinaturaEmpresa);
        return gatewayAssinaturaEmpresaRepository.save(gatewayAssinaturaEmpresa);
    }

    /**
     * Partially update a gatewayAssinaturaEmpresa.
     *
     * @param gatewayAssinaturaEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GatewayAssinaturaEmpresa> partialUpdate(GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa) {
        log.debug("Request to partially update GatewayAssinaturaEmpresa : {}", gatewayAssinaturaEmpresa);

        return gatewayAssinaturaEmpresaRepository
            .findById(gatewayAssinaturaEmpresa.getId())
            .map(existingGatewayAssinaturaEmpresa -> {
                if (gatewayAssinaturaEmpresa.getAtivo() != null) {
                    existingGatewayAssinaturaEmpresa.setAtivo(gatewayAssinaturaEmpresa.getAtivo());
                }
                if (gatewayAssinaturaEmpresa.getCodigoAssinatura() != null) {
                    existingGatewayAssinaturaEmpresa.setCodigoAssinatura(gatewayAssinaturaEmpresa.getCodigoAssinatura());
                }

                return existingGatewayAssinaturaEmpresa;
            })
            .map(gatewayAssinaturaEmpresaRepository::save);
    }

    /**
     * Get all the gatewayAssinaturaEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GatewayAssinaturaEmpresa> findAll() {
        log.debug("Request to get all GatewayAssinaturaEmpresas");
        return gatewayAssinaturaEmpresaRepository.findAll();
    }

    /**
     * Get all the gatewayAssinaturaEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GatewayAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return gatewayAssinaturaEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one gatewayAssinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GatewayAssinaturaEmpresa> findOne(Long id) {
        log.debug("Request to get GatewayAssinaturaEmpresa : {}", id);
        return gatewayAssinaturaEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the gatewayAssinaturaEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GatewayAssinaturaEmpresa : {}", id);
        gatewayAssinaturaEmpresaRepository.deleteById(id);
    }
}
