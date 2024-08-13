package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.BancoPessoa;
import com.dobemcontabilidade.repository.BancoPessoaRepository;
import com.dobemcontabilidade.service.BancoPessoaService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.BancoPessoa}.
 */
@RestController
@RequestMapping("/api/banco-pessoas")
public class BancoPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(BancoPessoaResource.class);

    private static final String ENTITY_NAME = "bancoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BancoPessoaService bancoPessoaService;

    private final BancoPessoaRepository bancoPessoaRepository;

    public BancoPessoaResource(BancoPessoaService bancoPessoaService, BancoPessoaRepository bancoPessoaRepository) {
        this.bancoPessoaService = bancoPessoaService;
        this.bancoPessoaRepository = bancoPessoaRepository;
    }

    /**
     * {@code POST  /banco-pessoas} : Create a new bancoPessoa.
     *
     * @param bancoPessoa the bancoPessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bancoPessoa, or with status {@code 400 (Bad Request)} if the bancoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BancoPessoa> createBancoPessoa(@Valid @RequestBody BancoPessoa bancoPessoa) throws URISyntaxException {
        log.debug("REST request to save BancoPessoa : {}", bancoPessoa);
        if (bancoPessoa.getId() != null) {
            throw new BadRequestAlertException("A new bancoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bancoPessoa = bancoPessoaService.save(bancoPessoa);
        return ResponseEntity.created(new URI("/api/banco-pessoas/" + bancoPessoa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bancoPessoa.getId().toString()))
            .body(bancoPessoa);
    }

    /**
     * {@code PUT  /banco-pessoas/:id} : Updates an existing bancoPessoa.
     *
     * @param id the id of the bancoPessoa to save.
     * @param bancoPessoa the bancoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bancoPessoa,
     * or with status {@code 400 (Bad Request)} if the bancoPessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bancoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BancoPessoa> updateBancoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BancoPessoa bancoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to update BancoPessoa : {}, {}", id, bancoPessoa);
        if (bancoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bancoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bancoPessoa = bancoPessoaService.update(bancoPessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bancoPessoa.getId().toString()))
            .body(bancoPessoa);
    }

    /**
     * {@code PATCH  /banco-pessoas/:id} : Partial updates given fields of an existing bancoPessoa, field will ignore if it is null
     *
     * @param id the id of the bancoPessoa to save.
     * @param bancoPessoa the bancoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bancoPessoa,
     * or with status {@code 400 (Bad Request)} if the bancoPessoa is not valid,
     * or with status {@code 404 (Not Found)} if the bancoPessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the bancoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BancoPessoa> partialUpdateBancoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BancoPessoa bancoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update BancoPessoa partially : {}, {}", id, bancoPessoa);
        if (bancoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bancoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BancoPessoa> result = bancoPessoaService.partialUpdate(bancoPessoa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bancoPessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /banco-pessoas} : get all the bancoPessoas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bancoPessoas in body.
     */
    @GetMapping("")
    public List<BancoPessoa> getAllBancoPessoas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all BancoPessoas");
        return bancoPessoaService.findAll();
    }

    /**
     * {@code GET  /banco-pessoas/:id} : get the "id" bancoPessoa.
     *
     * @param id the id of the bancoPessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bancoPessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BancoPessoa> getBancoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get BancoPessoa : {}", id);
        Optional<BancoPessoa> bancoPessoa = bancoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bancoPessoa);
    }

    /**
     * {@code DELETE  /banco-pessoas/:id} : delete the "id" bancoPessoa.
     *
     * @param id the id of the bancoPessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBancoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete BancoPessoa : {}", id);
        bancoPessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
