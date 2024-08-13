package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.AgendaTarefaRecorrenteExecucaoRepository;
import com.dobemcontabilidade.service.AgendaTarefaRecorrenteExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AgendaTarefaRecorrenteExecucao}.
 */
@RestController
@RequestMapping("/api/agenda-tarefa-recorrente-execucaos")
public class AgendaTarefaRecorrenteExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(AgendaTarefaRecorrenteExecucaoResource.class);

    private static final String ENTITY_NAME = "agendaTarefaRecorrenteExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaTarefaRecorrenteExecucaoService agendaTarefaRecorrenteExecucaoService;

    private final AgendaTarefaRecorrenteExecucaoRepository agendaTarefaRecorrenteExecucaoRepository;

    public AgendaTarefaRecorrenteExecucaoResource(
        AgendaTarefaRecorrenteExecucaoService agendaTarefaRecorrenteExecucaoService,
        AgendaTarefaRecorrenteExecucaoRepository agendaTarefaRecorrenteExecucaoRepository
    ) {
        this.agendaTarefaRecorrenteExecucaoService = agendaTarefaRecorrenteExecucaoService;
        this.agendaTarefaRecorrenteExecucaoRepository = agendaTarefaRecorrenteExecucaoRepository;
    }

    /**
     * {@code POST  /agenda-tarefa-recorrente-execucaos} : Create a new agendaTarefaRecorrenteExecucao.
     *
     * @param agendaTarefaRecorrenteExecucao the agendaTarefaRecorrenteExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agendaTarefaRecorrenteExecucao, or with status {@code 400 (Bad Request)} if the agendaTarefaRecorrenteExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgendaTarefaRecorrenteExecucao> createAgendaTarefaRecorrenteExecucao(
        @Valid @RequestBody AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save AgendaTarefaRecorrenteExecucao : {}", agendaTarefaRecorrenteExecucao);
        if (agendaTarefaRecorrenteExecucao.getId() != null) {
            throw new BadRequestAlertException("A new agendaTarefaRecorrenteExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoService.save(agendaTarefaRecorrenteExecucao);
        return ResponseEntity.created(new URI("/api/agenda-tarefa-recorrente-execucaos/" + agendaTarefaRecorrenteExecucao.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, agendaTarefaRecorrenteExecucao.getId().toString())
            )
            .body(agendaTarefaRecorrenteExecucao);
    }

    /**
     * {@code PUT  /agenda-tarefa-recorrente-execucaos/:id} : Updates an existing agendaTarefaRecorrenteExecucao.
     *
     * @param id the id of the agendaTarefaRecorrenteExecucao to save.
     * @param agendaTarefaRecorrenteExecucao the agendaTarefaRecorrenteExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaTarefaRecorrenteExecucao,
     * or with status {@code 400 (Bad Request)} if the agendaTarefaRecorrenteExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agendaTarefaRecorrenteExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgendaTarefaRecorrenteExecucao> updateAgendaTarefaRecorrenteExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update AgendaTarefaRecorrenteExecucao : {}, {}", id, agendaTarefaRecorrenteExecucao);
        if (agendaTarefaRecorrenteExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaTarefaRecorrenteExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaTarefaRecorrenteExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoService.update(agendaTarefaRecorrenteExecucao);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaTarefaRecorrenteExecucao.getId().toString())
            )
            .body(agendaTarefaRecorrenteExecucao);
    }

    /**
     * {@code PATCH  /agenda-tarefa-recorrente-execucaos/:id} : Partial updates given fields of an existing agendaTarefaRecorrenteExecucao, field will ignore if it is null
     *
     * @param id the id of the agendaTarefaRecorrenteExecucao to save.
     * @param agendaTarefaRecorrenteExecucao the agendaTarefaRecorrenteExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agendaTarefaRecorrenteExecucao,
     * or with status {@code 400 (Bad Request)} if the agendaTarefaRecorrenteExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the agendaTarefaRecorrenteExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the agendaTarefaRecorrenteExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgendaTarefaRecorrenteExecucao> partialUpdateAgendaTarefaRecorrenteExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AgendaTarefaRecorrenteExecucao agendaTarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update AgendaTarefaRecorrenteExecucao partially : {}, {}", id, agendaTarefaRecorrenteExecucao);
        if (agendaTarefaRecorrenteExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agendaTarefaRecorrenteExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agendaTarefaRecorrenteExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgendaTarefaRecorrenteExecucao> result = agendaTarefaRecorrenteExecucaoService.partialUpdate(
            agendaTarefaRecorrenteExecucao
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agendaTarefaRecorrenteExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /agenda-tarefa-recorrente-execucaos} : get all the agendaTarefaRecorrenteExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agendaTarefaRecorrenteExecucaos in body.
     */
    @GetMapping("")
    public List<AgendaTarefaRecorrenteExecucao> getAllAgendaTarefaRecorrenteExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AgendaTarefaRecorrenteExecucaos");
        return agendaTarefaRecorrenteExecucaoService.findAll();
    }

    /**
     * {@code GET  /agenda-tarefa-recorrente-execucaos/:id} : get the "id" agendaTarefaRecorrenteExecucao.
     *
     * @param id the id of the agendaTarefaRecorrenteExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agendaTarefaRecorrenteExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgendaTarefaRecorrenteExecucao> getAgendaTarefaRecorrenteExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get AgendaTarefaRecorrenteExecucao : {}", id);
        Optional<AgendaTarefaRecorrenteExecucao> agendaTarefaRecorrenteExecucao = agendaTarefaRecorrenteExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agendaTarefaRecorrenteExecucao);
    }

    /**
     * {@code DELETE  /agenda-tarefa-recorrente-execucaos/:id} : delete the "id" agendaTarefaRecorrenteExecucao.
     *
     * @param id the id of the agendaTarefaRecorrenteExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendaTarefaRecorrenteExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete AgendaTarefaRecorrenteExecucao : {}", id);
        agendaTarefaRecorrenteExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
