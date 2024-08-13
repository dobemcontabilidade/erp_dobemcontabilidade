package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoRequeridoPessoa;
import com.dobemcontabilidade.repository.AnexoRequeridoPessoaRepository;
import com.dobemcontabilidade.service.AnexoRequeridoPessoaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoPessoa}.
 */
@RestController
@RequestMapping("/api/anexo-requerido-pessoas")
public class AnexoRequeridoPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoPessoaResource.class);

    private static final String ENTITY_NAME = "anexoRequeridoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRequeridoPessoaService anexoRequeridoPessoaService;

    private final AnexoRequeridoPessoaRepository anexoRequeridoPessoaRepository;

    public AnexoRequeridoPessoaResource(
        AnexoRequeridoPessoaService anexoRequeridoPessoaService,
        AnexoRequeridoPessoaRepository anexoRequeridoPessoaRepository
    ) {
        this.anexoRequeridoPessoaService = anexoRequeridoPessoaService;
        this.anexoRequeridoPessoaRepository = anexoRequeridoPessoaRepository;
    }

    /**
     * {@code POST  /anexo-requerido-pessoas} : Create a new anexoRequeridoPessoa.
     *
     * @param anexoRequeridoPessoa the anexoRequeridoPessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoRequeridoPessoa, or with status {@code 400 (Bad Request)} if the anexoRequeridoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoRequeridoPessoa> createAnexoRequeridoPessoa(@Valid @RequestBody AnexoRequeridoPessoa anexoRequeridoPessoa)
        throws URISyntaxException {
        log.debug("REST request to save AnexoRequeridoPessoa : {}", anexoRequeridoPessoa);
        if (anexoRequeridoPessoa.getId() != null) {
            throw new BadRequestAlertException("A new anexoRequeridoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoRequeridoPessoa = anexoRequeridoPessoaService.save(anexoRequeridoPessoa);
        return ResponseEntity.created(new URI("/api/anexo-requerido-pessoas/" + anexoRequeridoPessoa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoRequeridoPessoa.getId().toString()))
            .body(anexoRequeridoPessoa);
    }

    /**
     * {@code PUT  /anexo-requerido-pessoas/:id} : Updates an existing anexoRequeridoPessoa.
     *
     * @param id the id of the anexoRequeridoPessoa to save.
     * @param anexoRequeridoPessoa the anexoRequeridoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoPessoa,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoPessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoRequeridoPessoa> updateAnexoRequeridoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoRequeridoPessoa anexoRequeridoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoRequeridoPessoa : {}, {}", id, anexoRequeridoPessoa);
        if (anexoRequeridoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoRequeridoPessoa = anexoRequeridoPessoaService.update(anexoRequeridoPessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoPessoa.getId().toString()))
            .body(anexoRequeridoPessoa);
    }

    /**
     * {@code PATCH  /anexo-requerido-pessoas/:id} : Partial updates given fields of an existing anexoRequeridoPessoa, field will ignore if it is null
     *
     * @param id the id of the anexoRequeridoPessoa to save.
     * @param anexoRequeridoPessoa the anexoRequeridoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoPessoa,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoPessoa is not valid,
     * or with status {@code 404 (Not Found)} if the anexoRequeridoPessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoRequeridoPessoa> partialUpdateAnexoRequeridoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoRequeridoPessoa anexoRequeridoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoRequeridoPessoa partially : {}, {}", id, anexoRequeridoPessoa);
        if (anexoRequeridoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoRequeridoPessoa> result = anexoRequeridoPessoaService.partialUpdate(anexoRequeridoPessoa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoPessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-requerido-pessoas} : get all the anexoRequeridoPessoas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoRequeridoPessoas in body.
     */
    @GetMapping("")
    public List<AnexoRequeridoPessoa> getAllAnexoRequeridoPessoas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoRequeridoPessoas");
        return anexoRequeridoPessoaService.findAll();
    }

    /**
     * {@code GET  /anexo-requerido-pessoas/:id} : get the "id" anexoRequeridoPessoa.
     *
     * @param id the id of the anexoRequeridoPessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoRequeridoPessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRequeridoPessoa> getAnexoRequeridoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoRequeridoPessoa : {}", id);
        Optional<AnexoRequeridoPessoa> anexoRequeridoPessoa = anexoRequeridoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoRequeridoPessoa);
    }

    /**
     * {@code DELETE  /anexo-requerido-pessoas/:id} : delete the "id" anexoRequeridoPessoa.
     *
     * @param id the id of the anexoRequeridoPessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoRequeridoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoRequeridoPessoa : {}", id);
        anexoRequeridoPessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
