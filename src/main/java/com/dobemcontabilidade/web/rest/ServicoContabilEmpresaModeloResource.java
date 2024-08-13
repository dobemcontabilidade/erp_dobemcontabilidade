package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo;
import com.dobemcontabilidade.repository.ServicoContabilEmpresaModeloRepository;
import com.dobemcontabilidade.service.ServicoContabilEmpresaModeloService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ServicoContabilEmpresaModelo}.
 */
@RestController
@RequestMapping("/api/servico-contabil-empresa-modelos")
public class ServicoContabilEmpresaModeloResource {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilEmpresaModeloResource.class);

    private static final String ENTITY_NAME = "servicoContabilEmpresaModelo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoContabilEmpresaModeloService servicoContabilEmpresaModeloService;

    private final ServicoContabilEmpresaModeloRepository servicoContabilEmpresaModeloRepository;

    public ServicoContabilEmpresaModeloResource(
        ServicoContabilEmpresaModeloService servicoContabilEmpresaModeloService,
        ServicoContabilEmpresaModeloRepository servicoContabilEmpresaModeloRepository
    ) {
        this.servicoContabilEmpresaModeloService = servicoContabilEmpresaModeloService;
        this.servicoContabilEmpresaModeloRepository = servicoContabilEmpresaModeloRepository;
    }

    /**
     * {@code POST  /servico-contabil-empresa-modelos} : Create a new servicoContabilEmpresaModelo.
     *
     * @param servicoContabilEmpresaModelo the servicoContabilEmpresaModelo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoContabilEmpresaModelo, or with status {@code 400 (Bad Request)} if the servicoContabilEmpresaModelo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicoContabilEmpresaModelo> createServicoContabilEmpresaModelo(
        @Valid @RequestBody ServicoContabilEmpresaModelo servicoContabilEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to save ServicoContabilEmpresaModelo : {}", servicoContabilEmpresaModelo);
        if (servicoContabilEmpresaModelo.getId() != null) {
            throw new BadRequestAlertException("A new servicoContabilEmpresaModelo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicoContabilEmpresaModelo = servicoContabilEmpresaModeloService.save(servicoContabilEmpresaModelo);
        return ResponseEntity.created(new URI("/api/servico-contabil-empresa-modelos/" + servicoContabilEmpresaModelo.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, servicoContabilEmpresaModelo.getId().toString())
            )
            .body(servicoContabilEmpresaModelo);
    }

    /**
     * {@code PUT  /servico-contabil-empresa-modelos/:id} : Updates an existing servicoContabilEmpresaModelo.
     *
     * @param id the id of the servicoContabilEmpresaModelo to save.
     * @param servicoContabilEmpresaModelo the servicoContabilEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the servicoContabilEmpresaModelo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicoContabilEmpresaModelo> updateServicoContabilEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoContabilEmpresaModelo servicoContabilEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to update ServicoContabilEmpresaModelo : {}, {}", id, servicoContabilEmpresaModelo);
        if (servicoContabilEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicoContabilEmpresaModelo = servicoContabilEmpresaModeloService.update(servicoContabilEmpresaModelo);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilEmpresaModelo.getId().toString())
            )
            .body(servicoContabilEmpresaModelo);
    }

    /**
     * {@code PATCH  /servico-contabil-empresa-modelos/:id} : Partial updates given fields of an existing servicoContabilEmpresaModelo, field will ignore if it is null
     *
     * @param id the id of the servicoContabilEmpresaModelo to save.
     * @param servicoContabilEmpresaModelo the servicoContabilEmpresaModelo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabilEmpresaModelo,
     * or with status {@code 400 (Bad Request)} if the servicoContabilEmpresaModelo is not valid,
     * or with status {@code 404 (Not Found)} if the servicoContabilEmpresaModelo is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabilEmpresaModelo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoContabilEmpresaModelo> partialUpdateServicoContabilEmpresaModelo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoContabilEmpresaModelo servicoContabilEmpresaModelo
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServicoContabilEmpresaModelo partially : {}, {}", id, servicoContabilEmpresaModelo);
        if (servicoContabilEmpresaModelo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabilEmpresaModelo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilEmpresaModeloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoContabilEmpresaModelo> result = servicoContabilEmpresaModeloService.partialUpdate(servicoContabilEmpresaModelo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabilEmpresaModelo.getId().toString())
        );
    }

    /**
     * {@code GET  /servico-contabil-empresa-modelos} : get all the servicoContabilEmpresaModelos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicoContabilEmpresaModelos in body.
     */
    @GetMapping("")
    public List<ServicoContabilEmpresaModelo> getAllServicoContabilEmpresaModelos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ServicoContabilEmpresaModelos");
        return servicoContabilEmpresaModeloService.findAll();
    }

    /**
     * {@code GET  /servico-contabil-empresa-modelos/:id} : get the "id" servicoContabilEmpresaModelo.
     *
     * @param id the id of the servicoContabilEmpresaModelo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoContabilEmpresaModelo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoContabilEmpresaModelo> getServicoContabilEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to get ServicoContabilEmpresaModelo : {}", id);
        Optional<ServicoContabilEmpresaModelo> servicoContabilEmpresaModelo = servicoContabilEmpresaModeloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicoContabilEmpresaModelo);
    }

    /**
     * {@code DELETE  /servico-contabil-empresa-modelos/:id} : delete the "id" servicoContabilEmpresaModelo.
     *
     * @param id the id of the servicoContabilEmpresaModelo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoContabilEmpresaModelo(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServicoContabilEmpresaModelo : {}", id);
        servicoContabilEmpresaModeloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
