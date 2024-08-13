package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa;
import com.dobemcontabilidade.repository.ServicoContabilAssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.ServicoContabilAssinaturaEmpresaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/servico-contabil-assinatura-empresas")
public class ServicoContabilAssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilAssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "servicoContabilAssinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoContabilAssinaturaEmpresaService servicoContabilAssinaturaEmpresaService;

    private final ServicoContabilAssinaturaEmpresaRepository servicoContabilAssinaturaEmpresaRepository;

    public ServicoContabilAssinaturaEmpresaResource(
        ServicoContabilAssinaturaEmpresaService servicoContabilAssinaturaEmpresaService,
        ServicoContabilAssinaturaEmpresaRepository servicoContabilAssinaturaEmpresaRepository
    ) {
        this.servicoContabilAssinaturaEmpresaService = servicoContabilAssinaturaEmpresaService;
        this.servicoContabilAssinaturaEmpresaRepository = servicoContabilAssinaturaEmpresaRepository;
    }

    /**
     * {@code POST  /servico-contabil-assinatura-empresas} : Create a new servicoContabilAssinaturaEmpresa.
     *
     * @param servicoContabilAssinaturaEmpresa the servicoContabilAssinaturaEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoContabilAssinaturaEmpresa, or with status {@code 400 (Bad Request)} if the servicoContabilAssinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicoContabilAssinaturaEmpresa> createServicoContabilAssinaturaEmpresa(
        @Valid @RequestBody ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save ServicoContabilAssinaturaEmpresa : {}", servicoContabilAssinaturaEmpresa);
        if (servicoContabilAssinaturaEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new servicoContabilAssinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaService.save(servicoContabilAssinaturaEmpresa);
        return ResponseEntity.created(new URI("/api/servico-contabil-assinatura-empresas/" + servicoContabilAssinaturaEmpresa.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    servicoContabilAssinaturaEmpresa.getId().toString()
                )
            )
            .body(servicoContabilAssinaturaEmpresa);
    }

    /**
     * {@code PUT  /servico-contabil-assinatura-empresas/:id} : Updates an existing servicoContabilAssinaturaEmpresa.
     *
     * @param id the id of the servicoContabilAssinaturaEmpresa to save.
     * @param servicoContabilAssinaturaEmpresa the servicoContabilAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the servicoContabilAssinaturaEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicoContabilAssinaturaEmpresa> updateServicoContabilAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update ServicoContabilAssinaturaEmpresa : {}, {}", id, servicoContabilAssinaturaEmpresa);
        if (servicoContabilAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaService.update(servicoContabilAssinaturaEmpresa);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilAssinaturaEmpresa.getId().toString())
            )
            .body(servicoContabilAssinaturaEmpresa);
    }

    /**
     * {@code PATCH  /servico-contabil-assinatura-empresas/:id} : Partial updates given fields of an existing servicoContabilAssinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the servicoContabilAssinaturaEmpresa to save.
     * @param servicoContabilAssinaturaEmpresa the servicoContabilAssinaturaEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilAssinaturaEmpresa,
     * or with status {@code 400 (Bad Request)} if the servicoContabilAssinaturaEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the servicoContabilAssinaturaEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilAssinaturaEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoContabilAssinaturaEmpresa> partialUpdateServicoContabilAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ServicoContabilAssinaturaEmpresa partially : {}, {}",
            id,
            servicoContabilAssinaturaEmpresa
        );
        if (servicoContabilAssinaturaEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilAssinaturaEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilAssinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoContabilAssinaturaEmpresa> result = servicoContabilAssinaturaEmpresaService.partialUpdate(
            servicoContabilAssinaturaEmpresa
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilAssinaturaEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /servico-contabil-assinatura-empresas} : get all the servicoContabilAssinaturaEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicoContabilAssinaturaEmpresas in body.
     */
    @GetMapping("")
    public List<ServicoContabilAssinaturaEmpresa> getAllServicoContabilAssinaturaEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ServicoContabilAssinaturaEmpresas");
        return servicoContabilAssinaturaEmpresaService.findAll();
    }

    /**
     * {@code GET  /servico-contabil-assinatura-empresas/:id} : get the "id" servicoContabilAssinaturaEmpresa.
     *
     * @param id the id of the servicoContabilAssinaturaEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoContabilAssinaturaEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoContabilAssinaturaEmpresa> getServicoContabilAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get ServicoContabilAssinaturaEmpresa : {}", id);
        Optional<ServicoContabilAssinaturaEmpresa> servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicoContabilAssinaturaEmpresa);
    }

    /**
     * {@code DELETE  /servico-contabil-assinatura-empresas/:id} : delete the "id" servicoContabilAssinaturaEmpresa.
     *
     * @param id the id of the servicoContabilAssinaturaEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoContabilAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServicoContabilAssinaturaEmpresa : {}", id);
        servicoContabilAssinaturaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
