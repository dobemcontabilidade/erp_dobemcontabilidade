package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FluxoExecucao;
import com.dobemcontabilidade.repository.FluxoExecucaoRepository;
import com.dobemcontabilidade.service.FluxoExecucaoService;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.FluxoExecucao}.
 */
@RestController
@RequestMapping("/api/fluxo-execucaos")
public class FluxoExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(FluxoExecucaoResource.class);

    private static final String ENTITY_NAME = "fluxoExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FluxoExecucaoService fluxoExecucaoService;

    private final FluxoExecucaoRepository fluxoExecucaoRepository;

    public FluxoExecucaoResource(FluxoExecucaoService fluxoExecucaoService, FluxoExecucaoRepository fluxoExecucaoRepository) {
        this.fluxoExecucaoService = fluxoExecucaoService;
        this.fluxoExecucaoRepository = fluxoExecucaoRepository;
    }

    /**
     * {@code POST  /fluxo-execucaos} : Create a new fluxoExecucao.
     *
     * @param fluxoExecucao the fluxoExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fluxoExecucao, or with status {@code 400 (Bad Request)} if the fluxoExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FluxoExecucao> createFluxoExecucao(@RequestBody FluxoExecucao fluxoExecucao) throws URISyntaxException {
        log.debug("REST request to save FluxoExecucao : {}", fluxoExecucao);
        if (fluxoExecucao.getId() != null) {
            throw new BadRequestAlertException("A new fluxoExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fluxoExecucao = fluxoExecucaoService.save(fluxoExecucao);
        return ResponseEntity.created(new URI("/api/fluxo-execucaos/" + fluxoExecucao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fluxoExecucao.getId().toString()))
            .body(fluxoExecucao);
    }

    /**
     * {@code PUT  /fluxo-execucaos/:id} : Updates an existing fluxoExecucao.
     *
     * @param id the id of the fluxoExecucao to save.
     * @param fluxoExecucao the fluxoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoExecucao,
     * or with status {@code 400 (Bad Request)} if the fluxoExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fluxoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FluxoExecucao> updateFluxoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FluxoExecucao fluxoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update FluxoExecucao : {}, {}", id, fluxoExecucao);
        if (fluxoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fluxoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fluxoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fluxoExecucao = fluxoExecucaoService.update(fluxoExecucao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoExecucao.getId().toString()))
            .body(fluxoExecucao);
    }

    /**
     * {@code PATCH  /fluxo-execucaos/:id} : Partial updates given fields of an existing fluxoExecucao, field will ignore if it is null
     *
     * @param id the id of the fluxoExecucao to save.
     * @param fluxoExecucao the fluxoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fluxoExecucao,
     * or with status {@code 400 (Bad Request)} if the fluxoExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the fluxoExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the fluxoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FluxoExecucao> partialUpdateFluxoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FluxoExecucao fluxoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update FluxoExecucao partially : {}, {}", id, fluxoExecucao);
        if (fluxoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fluxoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fluxoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FluxoExecucao> result = fluxoExecucaoService.partialUpdate(fluxoExecucao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fluxoExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /fluxo-execucaos} : get all the fluxoExecucaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fluxoExecucaos in body.
     */
    @GetMapping("")
    public List<FluxoExecucao> getAllFluxoExecucaos() {
        log.debug("REST request to get all FluxoExecucaos");
        return fluxoExecucaoService.findAll();
    }

    /**
     * {@code GET  /fluxo-execucaos/:id} : get the "id" fluxoExecucao.
     *
     * @param id the id of the fluxoExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fluxoExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FluxoExecucao> getFluxoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get FluxoExecucao : {}", id);
        Optional<FluxoExecucao> fluxoExecucao = fluxoExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fluxoExecucao);
    }

    /**
     * {@code DELETE  /fluxo-execucaos/:id} : delete the "id" fluxoExecucao.
     *
     * @param id the id of the fluxoExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFluxoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete FluxoExecucao : {}", id);
        fluxoExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
