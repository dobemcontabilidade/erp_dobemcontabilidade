package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EscolaridadePessoa;
import com.dobemcontabilidade.repository.EscolaridadePessoaRepository;
import com.dobemcontabilidade.service.EscolaridadePessoaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EscolaridadePessoa}.
 */
@RestController
@RequestMapping("/api/escolaridade-pessoas")
public class EscolaridadePessoaResource {

    private static final Logger log = LoggerFactory.getLogger(EscolaridadePessoaResource.class);

    private static final String ENTITY_NAME = "escolaridadePessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EscolaridadePessoaService escolaridadePessoaService;

    private final EscolaridadePessoaRepository escolaridadePessoaRepository;

    public EscolaridadePessoaResource(
        EscolaridadePessoaService escolaridadePessoaService,
        EscolaridadePessoaRepository escolaridadePessoaRepository
    ) {
        this.escolaridadePessoaService = escolaridadePessoaService;
        this.escolaridadePessoaRepository = escolaridadePessoaRepository;
    }

    /**
     * {@code POST  /escolaridade-pessoas} : Create a new escolaridadePessoa.
     *
     * @param escolaridadePessoa the escolaridadePessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new escolaridadePessoa, or with status {@code 400 (Bad Request)} if the escolaridadePessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EscolaridadePessoa> createEscolaridadePessoa(@Valid @RequestBody EscolaridadePessoa escolaridadePessoa)
        throws URISyntaxException {
        log.debug("REST request to save EscolaridadePessoa : {}", escolaridadePessoa);
        if (escolaridadePessoa.getId() != null) {
            throw new BadRequestAlertException("A new escolaridadePessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        escolaridadePessoa = escolaridadePessoaService.save(escolaridadePessoa);
        return ResponseEntity.created(new URI("/api/escolaridade-pessoas/" + escolaridadePessoa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, escolaridadePessoa.getId().toString()))
            .body(escolaridadePessoa);
    }

    /**
     * {@code PUT  /escolaridade-pessoas/:id} : Updates an existing escolaridadePessoa.
     *
     * @param id the id of the escolaridadePessoa to save.
     * @param escolaridadePessoa the escolaridadePessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escolaridadePessoa,
     * or with status {@code 400 (Bad Request)} if the escolaridadePessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the escolaridadePessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EscolaridadePessoa> updateEscolaridadePessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EscolaridadePessoa escolaridadePessoa
    ) throws URISyntaxException {
        log.debug("REST request to update EscolaridadePessoa : {}, {}", id, escolaridadePessoa);
        if (escolaridadePessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, escolaridadePessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!escolaridadePessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        escolaridadePessoa = escolaridadePessoaService.update(escolaridadePessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escolaridadePessoa.getId().toString()))
            .body(escolaridadePessoa);
    }

    /**
     * {@code PATCH  /escolaridade-pessoas/:id} : Partial updates given fields of an existing escolaridadePessoa, field will ignore if it is null
     *
     * @param id the id of the escolaridadePessoa to save.
     * @param escolaridadePessoa the escolaridadePessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated escolaridadePessoa,
     * or with status {@code 400 (Bad Request)} if the escolaridadePessoa is not valid,
     * or with status {@code 404 (Not Found)} if the escolaridadePessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the escolaridadePessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EscolaridadePessoa> partialUpdateEscolaridadePessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EscolaridadePessoa escolaridadePessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update EscolaridadePessoa partially : {}, {}", id, escolaridadePessoa);
        if (escolaridadePessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, escolaridadePessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!escolaridadePessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EscolaridadePessoa> result = escolaridadePessoaService.partialUpdate(escolaridadePessoa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, escolaridadePessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /escolaridade-pessoas} : get all the escolaridadePessoas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of escolaridadePessoas in body.
     */
    @GetMapping("")
    public List<EscolaridadePessoa> getAllEscolaridadePessoas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all EscolaridadePessoas");
        return escolaridadePessoaService.findAll();
    }

    /**
     * {@code GET  /escolaridade-pessoas/:id} : get the "id" escolaridadePessoa.
     *
     * @param id the id of the escolaridadePessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the escolaridadePessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EscolaridadePessoa> getEscolaridadePessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get EscolaridadePessoa : {}", id);
        Optional<EscolaridadePessoa> escolaridadePessoa = escolaridadePessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(escolaridadePessoa);
    }

    /**
     * {@code DELETE  /escolaridade-pessoas/:id} : delete the "id" escolaridadePessoa.
     *
     * @param id the id of the escolaridadePessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEscolaridadePessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete EscolaridadePessoa : {}", id);
        escolaridadePessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
