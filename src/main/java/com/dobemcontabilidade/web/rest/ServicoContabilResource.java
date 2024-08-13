package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.repository.ServicoContabilRepository;
import com.dobemcontabilidade.service.ServicoContabilService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ServicoContabil}.
 */
@RestController
@RequestMapping("/api/servico-contabils")
public class ServicoContabilResource {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilResource.class);

    private static final String ENTITY_NAME = "servicoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicoContabilService servicoContabilService;

    private final ServicoContabilRepository servicoContabilRepository;

    public ServicoContabilResource(ServicoContabilService servicoContabilService, ServicoContabilRepository servicoContabilRepository) {
        this.servicoContabilService = servicoContabilService;
        this.servicoContabilRepository = servicoContabilRepository;
    }

    /**
     * {@code POST  /servico-contabils} : Create a new servicoContabil.
     *
     * @param servicoContabil the servicoContabil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicoContabil, or with status {@code 400 (Bad Request)} if the servicoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ServicoContabil> createServicoContabil(@Valid @RequestBody ServicoContabil servicoContabil)
        throws URISyntaxException {
        log.debug("REST request to save ServicoContabil : {}", servicoContabil);
        if (servicoContabil.getId() != null) {
            throw new BadRequestAlertException("A new servicoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        servicoContabil = servicoContabilService.save(servicoContabil);
        return ResponseEntity.created(new URI("/api/servico-contabils/" + servicoContabil.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, servicoContabil.getId().toString()))
            .body(servicoContabil);
    }

    /**
     * {@code PUT  /servico-contabils/:id} : Updates an existing servicoContabil.
     *
     * @param id the id of the servicoContabil to save.
     * @param servicoContabil the servicoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabil,
     * or with status {@code 400 (Bad Request)} if the servicoContabil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ServicoContabil> updateServicoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicoContabil servicoContabil
    ) throws URISyntaxException {
        log.debug("REST request to update ServicoContabil : {}, {}", id, servicoContabil);
        if (servicoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        servicoContabil = servicoContabilService.update(servicoContabil);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabil.getId().toString()))
            .body(servicoContabil);
    }

    /**
     * {@code PATCH  /servico-contabils/:id} : Partial updates given fields of an existing servicoContabil, field will ignore if it is null
     *
     * @param id the id of the servicoContabil to save.
     * @param servicoContabil the servicoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicoContabil,
     * or with status {@code 400 (Bad Request)} if the servicoContabil is not valid,
     * or with status {@code 404 (Not Found)} if the servicoContabil is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicoContabil> partialUpdateServicoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicoContabil servicoContabil
    ) throws URISyntaxException {
        log.debug("REST request to partial update ServicoContabil partially : {}, {}", id, servicoContabil);
        if (servicoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicoContabil> result = servicoContabilService.partialUpdate(servicoContabil);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicoContabil.getId().toString())
        );
    }

    /**
     * {@code GET  /servico-contabils} : get all the servicoContabils.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicoContabils in body.
     */
    @GetMapping("")
    public List<ServicoContabil> getAllServicoContabils(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ServicoContabils");
        return servicoContabilService.findAll();
    }

    /**
     * {@code GET  /servico-contabils/:id} : get the "id" servicoContabil.
     *
     * @param id the id of the servicoContabil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicoContabil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ServicoContabil> getServicoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get ServicoContabil : {}", id);
        Optional<ServicoContabil> servicoContabil = servicoContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicoContabil);
    }

    /**
     * {@code DELETE  /servico-contabils/:id} : delete the "id" servicoContabil.
     *
     * @param id the id of the servicoContabil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete ServicoContabil : {}", id);
        servicoContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
