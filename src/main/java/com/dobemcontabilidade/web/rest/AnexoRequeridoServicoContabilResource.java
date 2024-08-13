package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil;
import com.dobemcontabilidade.repository.AnexoRequeridoServicoContabilRepository;
import com.dobemcontabilidade.service.AnexoRequeridoServicoContabilService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil}.
 */
@RestController
@RequestMapping("/api/anexo-requerido-servico-contabils")
public class AnexoRequeridoServicoContabilResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoServicoContabilResource.class);

    private static final String ENTITY_NAME = "anexoRequeridoServicoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRequeridoServicoContabilService anexoRequeridoServicoContabilService;

    private final AnexoRequeridoServicoContabilRepository anexoRequeridoServicoContabilRepository;

    public AnexoRequeridoServicoContabilResource(
        AnexoRequeridoServicoContabilService anexoRequeridoServicoContabilService,
        AnexoRequeridoServicoContabilRepository anexoRequeridoServicoContabilRepository
    ) {
        this.anexoRequeridoServicoContabilService = anexoRequeridoServicoContabilService;
        this.anexoRequeridoServicoContabilRepository = anexoRequeridoServicoContabilRepository;
    }

    /**
     * {@code POST  /anexo-requerido-servico-contabils} : Create a new anexoRequeridoServicoContabil.
     *
     * @param anexoRequeridoServicoContabil the anexoRequeridoServicoContabil to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoRequeridoServicoContabil, or with status {@code 400 (Bad Request)} if the anexoRequeridoServicoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoRequeridoServicoContabil> createAnexoRequeridoServicoContabil(
        @Valid @RequestBody AnexoRequeridoServicoContabil anexoRequeridoServicoContabil
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoRequeridoServicoContabil : {}", anexoRequeridoServicoContabil);
        if (anexoRequeridoServicoContabil.getId() != null) {
            throw new BadRequestAlertException("A new anexoRequeridoServicoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoRequeridoServicoContabil = anexoRequeridoServicoContabilService.save(anexoRequeridoServicoContabil);
        return ResponseEntity.created(new URI("/api/anexo-requerido-servico-contabils/" + anexoRequeridoServicoContabil.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoRequeridoServicoContabil.getId().toString())
            )
            .body(anexoRequeridoServicoContabil);
    }

    /**
     * {@code PUT  /anexo-requerido-servico-contabils/:id} : Updates an existing anexoRequeridoServicoContabil.
     *
     * @param id the id of the anexoRequeridoServicoContabil to save.
     * @param anexoRequeridoServicoContabil the anexoRequeridoServicoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoServicoContabil,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoServicoContabil is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoServicoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoRequeridoServicoContabil> updateAnexoRequeridoServicoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoRequeridoServicoContabil anexoRequeridoServicoContabil
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoRequeridoServicoContabil : {}, {}", id, anexoRequeridoServicoContabil);
        if (anexoRequeridoServicoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoServicoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoServicoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoRequeridoServicoContabil = anexoRequeridoServicoContabilService.update(anexoRequeridoServicoContabil);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoServicoContabil.getId().toString())
            )
            .body(anexoRequeridoServicoContabil);
    }

    /**
     * {@code PATCH  /anexo-requerido-servico-contabils/:id} : Partial updates given fields of an existing anexoRequeridoServicoContabil, field will ignore if it is null
     *
     * @param id the id of the anexoRequeridoServicoContabil to save.
     * @param anexoRequeridoServicoContabil the anexoRequeridoServicoContabil to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoServicoContabil,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoServicoContabil is not valid,
     * or with status {@code 404 (Not Found)} if the anexoRequeridoServicoContabil is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoServicoContabil couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoRequeridoServicoContabil> partialUpdateAnexoRequeridoServicoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoRequeridoServicoContabil anexoRequeridoServicoContabil
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoRequeridoServicoContabil partially : {}, {}", id, anexoRequeridoServicoContabil);
        if (anexoRequeridoServicoContabil.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoServicoContabil.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoServicoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoRequeridoServicoContabil> result = anexoRequeridoServicoContabilService.partialUpdate(anexoRequeridoServicoContabil);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoServicoContabil.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-requerido-servico-contabils} : get all the anexoRequeridoServicoContabils.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoRequeridoServicoContabils in body.
     */
    @GetMapping("")
    public List<AnexoRequeridoServicoContabil> getAllAnexoRequeridoServicoContabils(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoRequeridoServicoContabils");
        return anexoRequeridoServicoContabilService.findAll();
    }

    /**
     * {@code GET  /anexo-requerido-servico-contabils/:id} : get the "id" anexoRequeridoServicoContabil.
     *
     * @param id the id of the anexoRequeridoServicoContabil to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoRequeridoServicoContabil, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRequeridoServicoContabil> getAnexoRequeridoServicoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoRequeridoServicoContabil : {}", id);
        Optional<AnexoRequeridoServicoContabil> anexoRequeridoServicoContabil = anexoRequeridoServicoContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoRequeridoServicoContabil);
    }

    /**
     * {@code DELETE  /anexo-requerido-servico-contabils/:id} : delete the "id" anexoRequeridoServicoContabil.
     *
     * @param id the id of the anexoRequeridoServicoContabil to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoRequeridoServicoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoRequeridoServicoContabil : {}", id);
        anexoRequeridoServicoContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
