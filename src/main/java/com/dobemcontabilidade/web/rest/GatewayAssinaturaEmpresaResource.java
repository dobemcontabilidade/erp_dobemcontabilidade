package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa;
import com.dobemcontabilidade.repository.GatewayAssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.GatewayAssinaturaEmpresaService;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/gateway-assinatura-empresas")
public class GatewayAssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(GatewayAssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "gatewayAssinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GatewayAssinaturaEmpresaService gatewayAssinaturaEmpresaService;

    private final GatewayAssinaturaEmpresaRepository gatewayAssinaturaEmpresaRepository;

    public GatewayAssinaturaEmpresaResource(
        GatewayAssinaturaEmpresaService gatewayAssinaturaEmpresaService,
        GatewayAssinaturaEmpresaRepository gatewayAssinaturaEmpresaRepository
    ) {
        this.gatewayAssinaturaEmpresaService = gatewayAssinaturaEmpresaService;
        this.gatewayAssinaturaEmpresaRepository = gatewayAssinaturaEmpresaRepository;
    }

    /**
     * {@code POST  /gateway-assinatura-empresas} : Create a new gatewayAssinaturaEmpresa.
     *
     * @param gatewayAssinaturaEmpresa the gatewayAssinaturaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gatewayAssinaturaEmpresa, or with status {@code 400 (Bad Request)} if the gatewayAssinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GatewayAssinaturaEmpresa> createGatewayAssinaturaEmpresa(
        @Valid @RequestBody GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save GatewayAssinaturaEmpresa : {}", gatewayAssinaturaEmpresa);
        if (gatewayAssinaturaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new gatewayAssinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        gatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaService.save(gatewayAssinaturaEmpresa);
        return ResponseEntity.created(new URI("/api/gateway-assinatura-empresas/" + gatewayAssinaturaEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, gatewayAssinaturaEmpresa.getId().toString()))
            .body(gatewayAssinaturaEmpresa);
    }

    /**
     * {@code PUT  /gateway-assinatura-empresas/:id} : Updates an existing gatewayAssinaturaEmpresa.
     *
     * @param id the id of the gatewayAssinaturaEmpresa to save.
     * @param gatewayAssinaturaEmpresa the gatewayAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gatewayAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the gatewayAssinaturaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gatewayAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GatewayAssinaturaEmpresa> updateGatewayAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update GatewayAssinaturaEmpresa : {}, {}", id, gatewayAssinaturaEmpresa);
        if (gatewayAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gatewayAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gatewayAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        gatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaService.update(gatewayAssinaturaEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gatewayAssinaturaEmpresa.getId().toString()))
            .body(gatewayAssinaturaEmpresa);
    }

    /**
     * {@code PATCH  /gateway-assinatura-empresas/:id} : Partial updates given fields of an existing gatewayAssinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the gatewayAssinaturaEmpresa to save.
     * @param gatewayAssinaturaEmpresa the gatewayAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gatewayAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the gatewayAssinaturaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the gatewayAssinaturaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the gatewayAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GatewayAssinaturaEmpresa> partialUpdateGatewayAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update GatewayAssinaturaEmpresa partially : {}, {}", id, gatewayAssinaturaEmpresa);
        if (gatewayAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gatewayAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gatewayAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GatewayAssinaturaEmpresa> result = gatewayAssinaturaEmpresaService.partialUpdate(gatewayAssinaturaEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gatewayAssinaturaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /gateway-assinatura-empresas} : get all the gatewayAssinaturaEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gatewayAssinaturaEmpresas in body.
     */
    @GetMapping("")
    public List<GatewayAssinaturaEmpresa> getAllGatewayAssinaturaEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all GatewayAssinaturaEmpresas");
        return gatewayAssinaturaEmpresaService.findAll();
    }

    /**
     * {@code GET  /gateway-assinatura-empresas/:id} : get the "id" gatewayAssinaturaEmpresa.
     *
     * @param id the id of the gatewayAssinaturaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gatewayAssinaturaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GatewayAssinaturaEmpresa> getGatewayAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get GatewayAssinaturaEmpresa : {}", id);
        Optional<GatewayAssinaturaEmpresa> gatewayAssinaturaEmpresa = gatewayAssinaturaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gatewayAssinaturaEmpresa);
    }

    /**
     * {@code DELETE  /gateway-assinatura-empresas/:id} : delete the "id" gatewayAssinaturaEmpresa.
     *
     * @param id the id of the gatewayAssinaturaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGatewayAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete GatewayAssinaturaEmpresa : {}", id);
        gatewayAssinaturaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
