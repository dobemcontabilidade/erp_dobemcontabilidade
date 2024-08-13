package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente;
import com.dobemcontabilidade.repository.ContadorResponsavelTarefaRecorrenteRepository;
import com.dobemcontabilidade.service.ContadorResponsavelTarefaRecorrenteService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente}.
 */
@RestController
@RequestMapping("/api/contador-responsavel-tarefa-recorrentes")
public class ContadorResponsavelTarefaRecorrenteResource {

    private static final Logger log = LoggerFactory.getLogger(ContadorResponsavelTarefaRecorrenteResource.class);

    private static final String ENTITY_NAME = "contadorResponsavelTarefaRecorrente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContadorResponsavelTarefaRecorrenteService contadorResponsavelTarefaRecorrenteService;

    private final ContadorResponsavelTarefaRecorrenteRepository contadorResponsavelTarefaRecorrenteRepository;

    public ContadorResponsavelTarefaRecorrenteResource(
        ContadorResponsavelTarefaRecorrenteService contadorResponsavelTarefaRecorrenteService,
        ContadorResponsavelTarefaRecorrenteRepository contadorResponsavelTarefaRecorrenteRepository
    ) {
        this.contadorResponsavelTarefaRecorrenteService = contadorResponsavelTarefaRecorrenteService;
        this.contadorResponsavelTarefaRecorrenteRepository = contadorResponsavelTarefaRecorrenteRepository;
    }

    /**
     * {@code POST  /contador-responsavel-tarefa-recorrentes} : Create a new contadorResponsavelTarefaRecorrente.
     *
     * @param contadorResponsavelTarefaRecorrente the contadorResponsavelTarefaRecorrente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contadorResponsavelTarefaRecorrente, or with status {@code 400 (Bad Request)} if the contadorResponsavelTarefaRecorrente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContadorResponsavelTarefaRecorrente> createContadorResponsavelTarefaRecorrente(
        @Valid @RequestBody ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to save ContadorResponsavelTarefaRecorrente : {}", contadorResponsavelTarefaRecorrente);
        if (contadorResponsavelTarefaRecorrente.getId() != null) {
            throw new BadRequestAlertException(
                "A new contadorResponsavelTarefaRecorrente cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        contadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteService.save(contadorResponsavelTarefaRecorrente);
        return ResponseEntity.created(
            new URI("/api/contador-responsavel-tarefa-recorrentes/" + contadorResponsavelTarefaRecorrente.getId())
        )
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    contadorResponsavelTarefaRecorrente.getId().toString()
                )
            )
            .body(contadorResponsavelTarefaRecorrente);
    }

    /**
     * {@code PUT  /contador-responsavel-tarefa-recorrentes/:id} : Updates an existing contadorResponsavelTarefaRecorrente.
     *
     * @param id the id of the contadorResponsavelTarefaRecorrente to save.
     * @param contadorResponsavelTarefaRecorrente the contadorResponsavelTarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contadorResponsavelTarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the contadorResponsavelTarefaRecorrente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contadorResponsavelTarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContadorResponsavelTarefaRecorrente> updateContadorResponsavelTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) throws URISyntaxException {
        log.debug("REST request to update ContadorResponsavelTarefaRecorrente : {}, {}", id, contadorResponsavelTarefaRecorrente);
        if (contadorResponsavelTarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contadorResponsavelTarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contadorResponsavelTarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contadorResponsavelTarefaRecorrente = contadorResponsavelTarefaRecorrenteService.update(contadorResponsavelTarefaRecorrente);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    contadorResponsavelTarefaRecorrente.getId().toString()
                )
            )
            .body(contadorResponsavelTarefaRecorrente);
    }

    /**
     * {@code PATCH  /contador-responsavel-tarefa-recorrentes/:id} : Partial updates given fields of an existing contadorResponsavelTarefaRecorrente, field will ignore if it is null
     *
     * @param id the id of the contadorResponsavelTarefaRecorrente to save.
     * @param contadorResponsavelTarefaRecorrente the contadorResponsavelTarefaRecorrente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contadorResponsavelTarefaRecorrente,
     * or with status {@code 400 (Bad Request)} if the contadorResponsavelTarefaRecorrente is not valid,
     * or with status {@code 404 (Not Found)} if the contadorResponsavelTarefaRecorrente is not found,
     * or with status {@code 500 (Internal Server Error)} if the contadorResponsavelTarefaRecorrente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContadorResponsavelTarefaRecorrente> partialUpdateContadorResponsavelTarefaRecorrente(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update ContadorResponsavelTarefaRecorrente partially : {}, {}",
            id,
            contadorResponsavelTarefaRecorrente
        );
        if (contadorResponsavelTarefaRecorrente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contadorResponsavelTarefaRecorrente.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contadorResponsavelTarefaRecorrenteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContadorResponsavelTarefaRecorrente> result = contadorResponsavelTarefaRecorrenteService.partialUpdate(
            contadorResponsavelTarefaRecorrente
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contadorResponsavelTarefaRecorrente.getId().toString())
        );
    }

    /**
     * {@code GET  /contador-responsavel-tarefa-recorrentes} : get all the contadorResponsavelTarefaRecorrentes.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contadorResponsavelTarefaRecorrentes in body.
     */
    @GetMapping("")
    public List<ContadorResponsavelTarefaRecorrente> getAllContadorResponsavelTarefaRecorrentes(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ContadorResponsavelTarefaRecorrentes");
        return contadorResponsavelTarefaRecorrenteService.findAll();
    }

    /**
     * {@code GET  /contador-responsavel-tarefa-recorrentes/:id} : get the "id" contadorResponsavelTarefaRecorrente.
     *
     * @param id the id of the contadorResponsavelTarefaRecorrente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contadorResponsavelTarefaRecorrente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContadorResponsavelTarefaRecorrente> getContadorResponsavelTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to get ContadorResponsavelTarefaRecorrente : {}", id);
        Optional<ContadorResponsavelTarefaRecorrente> contadorResponsavelTarefaRecorrente =
            contadorResponsavelTarefaRecorrenteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contadorResponsavelTarefaRecorrente);
    }

    /**
     * {@code DELETE  /contador-responsavel-tarefa-recorrentes/:id} : delete the "id" contadorResponsavelTarefaRecorrente.
     *
     * @param id the id of the contadorResponsavelTarefaRecorrente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContadorResponsavelTarefaRecorrente(@PathVariable("id") Long id) {
        log.debug("REST request to delete ContadorResponsavelTarefaRecorrente : {}", id);
        contadorResponsavelTarefaRecorrenteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
