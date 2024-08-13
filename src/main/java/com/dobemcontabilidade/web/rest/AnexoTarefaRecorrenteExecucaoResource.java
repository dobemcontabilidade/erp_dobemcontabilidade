package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao;
import com.dobemcontabilidade.repository.AnexoTarefaRecorrenteExecucaoRepository;
import com.dobemcontabilidade.service.AnexoTarefaRecorrenteExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoTarefaRecorrenteExecucao}.
 */
@RestController
@RequestMapping("/api/anexo-tarefa-recorrente-execucaos")
public class AnexoTarefaRecorrenteExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoTarefaRecorrenteExecucaoResource.class);

    private static final String ENTITY_NAME = "anexoTarefaRecorrenteExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoTarefaRecorrenteExecucaoService anexoTarefaRecorrenteExecucaoService;

    private final AnexoTarefaRecorrenteExecucaoRepository anexoTarefaRecorrenteExecucaoRepository;

    public AnexoTarefaRecorrenteExecucaoResource(
        AnexoTarefaRecorrenteExecucaoService anexoTarefaRecorrenteExecucaoService,
        AnexoTarefaRecorrenteExecucaoRepository anexoTarefaRecorrenteExecucaoRepository
    ) {
        this.anexoTarefaRecorrenteExecucaoService = anexoTarefaRecorrenteExecucaoService;
        this.anexoTarefaRecorrenteExecucaoRepository = anexoTarefaRecorrenteExecucaoRepository;
    }

    /**
     * {@code POST  /anexo-tarefa-recorrente-execucaos} : Create a new anexoTarefaRecorrenteExecucao.
     *
     * @param anexoTarefaRecorrenteExecucao the anexoTarefaRecorrenteExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoTarefaRecorrenteExecucao, or with status {@code 400 (Bad Request)} if the anexoTarefaRecorrenteExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoTarefaRecorrenteExecucao> createAnexoTarefaRecorrenteExecucao(
        @Valid @RequestBody AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoTarefaRecorrenteExecucao : {}", anexoTarefaRecorrenteExecucao);
        if (anexoTarefaRecorrenteExecucao.getId() != null) {
            throw new BadRequestAlertException("A new anexoTarefaRecorrenteExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoService.save(anexoTarefaRecorrenteExecucao);
        return ResponseEntity.created(new URI("/api/anexo-tarefa-recorrente-execucaos/" + anexoTarefaRecorrenteExecucao.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoTarefaRecorrenteExecucao.getId().toString())
            )
            .body(anexoTarefaRecorrenteExecucao);
    }

    /**
     * {@code PUT  /anexo-tarefa-recorrente-execucaos/:id} : Updates an existing anexoTarefaRecorrenteExecucao.
     *
     * @param id the id of the anexoTarefaRecorrenteExecucao to save.
     * @param anexoTarefaRecorrenteExecucao the anexoTarefaRecorrenteExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoTarefaRecorrenteExecucao,
     * or with status {@code 400 (Bad Request)} if the anexoTarefaRecorrenteExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoTarefaRecorrenteExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoTarefaRecorrenteExecucao> updateAnexoTarefaRecorrenteExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoTarefaRecorrenteExecucao : {}, {}", id, anexoTarefaRecorrenteExecucao);
        if (anexoTarefaRecorrenteExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoTarefaRecorrenteExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoTarefaRecorrenteExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoService.update(anexoTarefaRecorrenteExecucao);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoTarefaRecorrenteExecucao.getId().toString())
            )
            .body(anexoTarefaRecorrenteExecucao);
    }

    /**
     * {@code PATCH  /anexo-tarefa-recorrente-execucaos/:id} : Partial updates given fields of an existing anexoTarefaRecorrenteExecucao, field will ignore if it is null
     *
     * @param id the id of the anexoTarefaRecorrenteExecucao to save.
     * @param anexoTarefaRecorrenteExecucao the anexoTarefaRecorrenteExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoTarefaRecorrenteExecucao,
     * or with status {@code 400 (Bad Request)} if the anexoTarefaRecorrenteExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the anexoTarefaRecorrenteExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoTarefaRecorrenteExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoTarefaRecorrenteExecucao> partialUpdateAnexoTarefaRecorrenteExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoTarefaRecorrenteExecucao anexoTarefaRecorrenteExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoTarefaRecorrenteExecucao partially : {}, {}", id, anexoTarefaRecorrenteExecucao);
        if (anexoTarefaRecorrenteExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoTarefaRecorrenteExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoTarefaRecorrenteExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoTarefaRecorrenteExecucao> result = anexoTarefaRecorrenteExecucaoService.partialUpdate(anexoTarefaRecorrenteExecucao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoTarefaRecorrenteExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-tarefa-recorrente-execucaos} : get all the anexoTarefaRecorrenteExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoTarefaRecorrenteExecucaos in body.
     */
    @GetMapping("")
    public List<AnexoTarefaRecorrenteExecucao> getAllAnexoTarefaRecorrenteExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoTarefaRecorrenteExecucaos");
        return anexoTarefaRecorrenteExecucaoService.findAll();
    }

    /**
     * {@code GET  /anexo-tarefa-recorrente-execucaos/:id} : get the "id" anexoTarefaRecorrenteExecucao.
     *
     * @param id the id of the anexoTarefaRecorrenteExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoTarefaRecorrenteExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoTarefaRecorrenteExecucao> getAnexoTarefaRecorrenteExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoTarefaRecorrenteExecucao : {}", id);
        Optional<AnexoTarefaRecorrenteExecucao> anexoTarefaRecorrenteExecucao = anexoTarefaRecorrenteExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoTarefaRecorrenteExecucao);
    }

    /**
     * {@code DELETE  /anexo-tarefa-recorrente-execucaos/:id} : delete the "id" anexoTarefaRecorrenteExecucao.
     *
     * @param id the id of the anexoTarefaRecorrenteExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoTarefaRecorrenteExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoTarefaRecorrenteExecucao : {}", id);
        anexoTarefaRecorrenteExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
