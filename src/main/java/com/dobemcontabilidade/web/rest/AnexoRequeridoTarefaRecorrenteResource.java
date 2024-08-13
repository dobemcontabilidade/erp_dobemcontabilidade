package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente;
import com.dobemcontabilidade.repository.AnexoRequeridoTarefaRecorrenteRepository;
import com.dobemcontabilidade.service.AnexoRequeridoTarefaRecorrenteService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrente}.
 */
@RestController
@RequestMapping("/api/anexo-requerido-tarefa-recorrentes")
public class AnexoRequeridoTarefaRecorrenteResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoTarefaRecorrenteResource.class);

    private static final String ENTITY_NAME = "anexoRequeridoTarefaRecorrente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoRequeridoTarefaRecorrenteService anexoRequeridoTarefaRecorrenteService;

    private final AnexoRequeridoTarefaRecorrenteRepository anexoRequeridoTarefaRecorrenteRepository;

    public AnexoRequeridoTarefaRecorrenteResource(
        AnexoRequeridoTarefaRecorrenteService anexoRequeridoTarefaRecorrenteService,
        AnexoRequeridoTarefaRecorrenteRepository anexoRequeridoTarefaRecorrenteRepository
    ) {
        this.anexoRequeridoTarefaRecorrenteService = anexoRequeridoTarefaRecorrenteService;
        this.anexoRequeridoTarefaRecorrenteRepository = anexoRequeridoTarefaRecorrenteRepository;
    }

    /**
     * {@code POST  /anexo-requerido-tarefa-recorrentes} : Create a new anexoRequeridoTarefaRecorrente.
     *
     * @param anexoRequeridoTarefaRecorrente the anexoRequeridoTarefaRecorrente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoRequeridoTarefaRecorrente, or with status {@code 400 (Bad Request)} if the anexoRequeridoTarefaRecorrente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoRequeridoTarefaRecorrente> createAnexoRequeridoTarefaRecorrente(
        @Valid @RequestBody AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoRequeridoTarefaRecorrente : {}", anexoRequeridoTarefaRecorrente);
        if (anexoRequeridoTarefaRecorrente.getId() != null) {
            throw new BadRequestAlertException("A new anexoRequeridoTarefaRecorrente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteService.save(anexoRequeridoTarefaRecorrente);
        return ResponseEntity.created(new URI("/api/anexo-requerido-tarefa-recorrentes/" + anexoRequeridoTarefaRecorrente.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoRequeridoTarefaRecorrente.getId().toString())
            )
            .body(anexoRequeridoTarefaRecorrente);
    }

    /**
     * {@code PUT  /anexo-requerido-tarefa-recorrentes/:id} : Updates an existing anexoRequeridoTarefaRecorrente.
     *
     * @param id the id of the anexoRequeridoTarefaRecorrente to save.
     * @param anexoRequeridoTarefaRecorrente the anexoRequeridoTarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoTarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoTarefaRecorrente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoTarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoRequeridoTarefaRecorrente> updateAnexoRequeridoTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoRequeridoTarefaRecorrente : {}, {}", id, anexoRequeridoTarefaRecorrente);
        if (anexoRequeridoTarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoTarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoTarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteService.update(anexoRequeridoTarefaRecorrente);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoTarefaRecorrente.getId().toString())
            )
            .body(anexoRequeridoTarefaRecorrente);
    }

    /**
     * {@code PATCH  /anexo-requerido-tarefa-recorrentes/:id} : Partial updates given fields of an existing anexoRequeridoTarefaRecorrente, field will ignore if it is null
     *
     * @param id the id of the anexoRequeridoTarefaRecorrente to save.
     * @param anexoRequeridoTarefaRecorrente the anexoRequeridoTarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoRequeridoTarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the anexoRequeridoTarefaRecorrente is not valid,
     * or with status {@code 404 (Not Found)} if the anexoRequeridoTarefaRecorrente is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoRequeridoTarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoRequeridoTarefaRecorrente> partialUpdateAnexoRequeridoTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoRequeridoTarefaRecorrente partially : {}, {}", id, anexoRequeridoTarefaRecorrente);
        if (anexoRequeridoTarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoRequeridoTarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoRequeridoTarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoRequeridoTarefaRecorrente> result = anexoRequeridoTarefaRecorrenteService.partialUpdate(
            anexoRequeridoTarefaRecorrente
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoRequeridoTarefaRecorrente.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-requerido-tarefa-recorrentes} : get all the anexoRequeridoTarefaRecorrentes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoRequeridoTarefaRecorrentes in body.
     */
    @GetMapping("")
    public List<AnexoRequeridoTarefaRecorrente> getAllAnexoRequeridoTarefaRecorrentes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoRequeridoTarefaRecorrentes");
        return anexoRequeridoTarefaRecorrenteService.findAll();
    }

    /**
     * {@code GET  /anexo-requerido-tarefa-recorrentes/:id} : get the "id" anexoRequeridoTarefaRecorrente.
     *
     * @param id the id of the anexoRequeridoTarefaRecorrente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoRequeridoTarefaRecorrente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoRequeridoTarefaRecorrente> getAnexoRequeridoTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoRequeridoTarefaRecorrente : {}", id);
        Optional<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrente = anexoRequeridoTarefaRecorrenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoRequeridoTarefaRecorrente);
    }

    /**
     * {@code DELETE  /anexo-requerido-tarefa-recorrentes/:id} : delete the "id" anexoRequeridoTarefaRecorrente.
     *
     * @param id the id of the anexoRequeridoTarefaRecorrente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoRequeridoTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoRequeridoTarefaRecorrente : {}", id);
        anexoRequeridoTarefaRecorrenteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
