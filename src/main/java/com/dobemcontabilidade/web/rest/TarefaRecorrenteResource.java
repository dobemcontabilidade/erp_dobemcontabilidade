package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TarefaRecorrente;
import com.dobemcontabilidade.repository.TarefaRecorrenteRepository;
import com.dobemcontabilidade.service.TarefaRecorrenteService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TarefaRecorrente}.
 */
@RestController
@RequestMapping("/api/tarefa-recorrentes")
public class TarefaRecorrenteResource {

    private static final Logger log = LoggerFactory.getLogger(TarefaRecorrenteResource.class);

    private static final String ENTITY_NAME = "tarefaRecorrente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TarefaRecorrenteService tarefaRecorrenteService;

    private final TarefaRecorrenteRepository tarefaRecorrenteRepository;

    public TarefaRecorrenteResource(
        TarefaRecorrenteService tarefaRecorrenteService,
        TarefaRecorrenteRepository tarefaRecorrenteRepository
    ) {
        this.tarefaRecorrenteService = tarefaRecorrenteService;
        this.tarefaRecorrenteRepository = tarefaRecorrenteRepository;
    }

    /**
     * {@code POST  /tarefa-recorrentes} : Create a new tarefaRecorrente.
     *
     * @param tarefaRecorrente the tarefaRecorrente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tarefaRecorrente, or with status {@code 400 (Bad Request)} if the tarefaRecorrente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TarefaRecorrente> createTarefaRecorrente(@Valid @RequestBody TarefaRecorrente tarefaRecorrente)
        throws URISyntaxException {
        log.debug("REST request to save TarefaRecorrente : {}", tarefaRecorrente);
        if (tarefaRecorrente.getId() != null) {
            throw new BadRequestAlertException("A new tarefaRecorrente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tarefaRecorrente = tarefaRecorrenteService.save(tarefaRecorrente);
        return ResponseEntity.created(new URI("/api/tarefa-recorrentes/" + tarefaRecorrente.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tarefaRecorrente.getId().toString()))
            .body(tarefaRecorrente);
    }

    /**
     * {@code PUT  /tarefa-recorrentes/:id} : Updates an existing tarefaRecorrente.
     *
     * @param id the id of the tarefaRecorrente to save.
     * @param tarefaRecorrente the tarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the tarefaRecorrente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TarefaRecorrente> updateTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TarefaRecorrente tarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to update TarefaRecorrente : {}, {}", id, tarefaRecorrente);
        if (tarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tarefaRecorrente = tarefaRecorrenteService.update(tarefaRecorrente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaRecorrente.getId().toString()))
            .body(tarefaRecorrente);
    }

    /**
     * {@code PATCH  /tarefa-recorrentes/:id} : Partial updates given fields of an existing tarefaRecorrente, field will ignore if it is null
     *
     * @param id the id of the tarefaRecorrente to save.
     * @param tarefaRecorrente the tarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the tarefaRecorrente is not valid,
     * or with status {@code 404 (Not Found)} if the tarefaRecorrente is not found,
     * or with status {@code 500 (Internal Server Error)} if the tarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TarefaRecorrente> partialUpdateTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TarefaRecorrente tarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to partial update TarefaRecorrente partially : {}, {}", id, tarefaRecorrente);
        if (tarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TarefaRecorrente> result = tarefaRecorrenteService.partialUpdate(tarefaRecorrente);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tarefaRecorrente.getId().toString())
        );
    }

    /**
     * {@code GET  /tarefa-recorrentes} : get all the tarefaRecorrentes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tarefaRecorrentes in body.
     */
    @GetMapping("")
    public List<TarefaRecorrente> getAllTarefaRecorrentes() {
        log.debug("REST request to get all TarefaRecorrentes");
        return tarefaRecorrenteService.findAll();
    }

    /**
     * {@code GET  /tarefa-recorrentes/:id} : get the "id" tarefaRecorrente.
     *
     * @param id the id of the tarefaRecorrente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tarefaRecorrente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TarefaRecorrente> getTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to get TarefaRecorrente : {}", id);
        Optional<TarefaRecorrente> tarefaRecorrente = tarefaRecorrenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tarefaRecorrente);
    }

    /**
     * {@code DELETE  /tarefa-recorrentes/:id} : delete the "id" tarefaRecorrente.
     *
     * @param id the id of the tarefaRecorrente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to delete TarefaRecorrente : {}", id);
        tarefaRecorrenteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
