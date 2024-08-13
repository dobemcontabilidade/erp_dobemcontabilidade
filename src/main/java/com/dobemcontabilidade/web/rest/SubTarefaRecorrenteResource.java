package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.SubTarefaRecorrente;
import com.dobemcontabilidade.repository.SubTarefaRecorrenteRepository;
import com.dobemcontabilidade.service.SubTarefaRecorrenteService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.SubTarefaRecorrente}.
 */
@RestController
@RequestMapping("/api/sub-tarefa-recorrentes")
public class SubTarefaRecorrenteResource {

    private static final Logger log = LoggerFactory.getLogger(SubTarefaRecorrenteResource.class);

    private static final String ENTITY_NAME = "subTarefaRecorrente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubTarefaRecorrenteService subTarefaRecorrenteService;

    private final SubTarefaRecorrenteRepository subTarefaRecorrenteRepository;

    public SubTarefaRecorrenteResource(
        SubTarefaRecorrenteService subTarefaRecorrenteService,
        SubTarefaRecorrenteRepository subTarefaRecorrenteRepository
    ) {
        this.subTarefaRecorrenteService = subTarefaRecorrenteService;
        this.subTarefaRecorrenteRepository = subTarefaRecorrenteRepository;
    }

    /**
     * {@code POST  /sub-tarefa-recorrentes} : Create a new subTarefaRecorrente.
     *
     * @param subTarefaRecorrente the subTarefaRecorrente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subTarefaRecorrente, or with status {@code 400 (Bad Request)} if the subTarefaRecorrente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubTarefaRecorrente> createSubTarefaRecorrente(@Valid @RequestBody SubTarefaRecorrente subTarefaRecorrente)
        throws URISyntaxException {
        log.debug("REST request to save SubTarefaRecorrente : {}", subTarefaRecorrente);
        if (subTarefaRecorrente.getId() != null) {
            throw new BadRequestAlertException("A new subTarefaRecorrente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        subTarefaRecorrente = subTarefaRecorrenteService.save(subTarefaRecorrente);
        return ResponseEntity.created(new URI("/api/sub-tarefa-recorrentes/" + subTarefaRecorrente.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, subTarefaRecorrente.getId().toString()))
            .body(subTarefaRecorrente);
    }

    /**
     * {@code PUT  /sub-tarefa-recorrentes/:id} : Updates an existing subTarefaRecorrente.
     *
     * @param id the id of the subTarefaRecorrente to save.
     * @param subTarefaRecorrente the subTarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subTarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the subTarefaRecorrente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subTarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubTarefaRecorrente> updateSubTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubTarefaRecorrente subTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to update SubTarefaRecorrente : {}, {}", id, subTarefaRecorrente);
        if (subTarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subTarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subTarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        subTarefaRecorrente = subTarefaRecorrenteService.update(subTarefaRecorrente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subTarefaRecorrente.getId().toString()))
            .body(subTarefaRecorrente);
    }

    /**
     * {@code PATCH  /sub-tarefa-recorrentes/:id} : Partial updates given fields of an existing subTarefaRecorrente, field will ignore if it is null
     *
     * @param id the id of the subTarefaRecorrente to save.
     * @param subTarefaRecorrente the subTarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subTarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the subTarefaRecorrente is not valid,
     * or with status {@code 404 (Not Found)} if the subTarefaRecorrente is not found,
     * or with status {@code 500 (Internal Server Error)} if the subTarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubTarefaRecorrente> partialUpdateSubTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubTarefaRecorrente subTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to partial update SubTarefaRecorrente partially : {}, {}", id, subTarefaRecorrente);
        if (subTarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subTarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subTarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubTarefaRecorrente> result = subTarefaRecorrenteService.partialUpdate(subTarefaRecorrente);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subTarefaRecorrente.getId().toString())
        );
    }

    /**
     * {@code GET  /sub-tarefa-recorrentes} : get all the subTarefaRecorrentes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subTarefaRecorrentes in body.
     */
    @GetMapping("")
    public List<SubTarefaRecorrente> getAllSubTarefaRecorrentes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all SubTarefaRecorrentes");
        return subTarefaRecorrenteService.findAll();
    }

    /**
     * {@code GET  /sub-tarefa-recorrentes/:id} : get the "id" subTarefaRecorrente.
     *
     * @param id the id of the subTarefaRecorrente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subTarefaRecorrente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubTarefaRecorrente> getSubTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to get SubTarefaRecorrente : {}", id);
        Optional<SubTarefaRecorrente> subTarefaRecorrente = subTarefaRecorrenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subTarefaRecorrente);
    }

    /**
     * {@code DELETE  /sub-tarefa-recorrentes/:id} : delete the "id" subTarefaRecorrente.
     *
     * @param id the id of the subTarefaRecorrente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to delete SubTarefaRecorrente : {}", id);
        subTarefaRecorrenteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
