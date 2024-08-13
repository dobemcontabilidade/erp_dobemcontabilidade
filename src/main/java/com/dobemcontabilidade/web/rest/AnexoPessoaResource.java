package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.repository.AnexoPessoaRepository;
import com.dobemcontabilidade.service.AnexoPessoaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoPessoa}.
 */
@RestController
@RequestMapping("/api/anexo-pessoas")
public class AnexoPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoPessoaResource.class);

    private static final String ENTITY_NAME = "anexoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoPessoaService anexoPessoaService;

    private final AnexoPessoaRepository anexoPessoaRepository;

    public AnexoPessoaResource(AnexoPessoaService anexoPessoaService, AnexoPessoaRepository anexoPessoaRepository) {
        this.anexoPessoaService = anexoPessoaService;
        this.anexoPessoaRepository = anexoPessoaRepository;
    }

    /**
     * {@code POST  /anexo-pessoas} : Create a new anexoPessoa.
     *
     * @param anexoPessoa the anexoPessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoPessoa, or with status {@code 400 (Bad Request)} if the anexoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoPessoa> createAnexoPessoa(@Valid @RequestBody AnexoPessoa anexoPessoa) throws URISyntaxException {
        log.debug("REST request to save AnexoPessoa : {}", anexoPessoa);
        if (anexoPessoa.getId() != null) {
            throw new BadRequestAlertException("A new anexoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoPessoa = anexoPessoaService.save(anexoPessoa);
        return ResponseEntity.created(new URI("/api/anexo-pessoas/" + anexoPessoa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoPessoa.getId().toString()))
            .body(anexoPessoa);
    }

    /**
     * {@code PUT  /anexo-pessoas/:id} : Updates an existing anexoPessoa.
     *
     * @param id the id of the anexoPessoa to save.
     * @param anexoPessoa the anexoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoPessoa,
     * or with status {@code 400 (Bad Request)} if the anexoPessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoPessoa> updateAnexoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoPessoa anexoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoPessoa : {}, {}", id, anexoPessoa);
        if (anexoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoPessoa = anexoPessoaService.update(anexoPessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoPessoa.getId().toString()))
            .body(anexoPessoa);
    }

    /**
     * {@code PATCH  /anexo-pessoas/:id} : Partial updates given fields of an existing anexoPessoa, field will ignore if it is null
     *
     * @param id the id of the anexoPessoa to save.
     * @param anexoPessoa the anexoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoPessoa,
     * or with status {@code 400 (Bad Request)} if the anexoPessoa is not valid,
     * or with status {@code 404 (Not Found)} if the anexoPessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoPessoa> partialUpdateAnexoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoPessoa anexoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoPessoa partially : {}, {}", id, anexoPessoa);
        if (anexoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoPessoa> result = anexoPessoaService.partialUpdate(anexoPessoa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoPessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-pessoas} : get all the anexoPessoas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoPessoas in body.
     */
    @GetMapping("")
    public List<AnexoPessoa> getAllAnexoPessoas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoPessoas");
        return anexoPessoaService.findAll();
    }

    /**
     * {@code GET  /anexo-pessoas/:id} : get the "id" anexoPessoa.
     *
     * @param id the id of the anexoPessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoPessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoPessoa> getAnexoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoPessoa : {}", id);
        Optional<AnexoPessoa> anexoPessoa = anexoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoPessoa);
    }

    /**
     * {@code DELETE  /anexo-pessoas/:id} : delete the "id" anexoPessoa.
     *
     * @param id the id of the anexoPessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoPessoa : {}", id);
        anexoPessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
