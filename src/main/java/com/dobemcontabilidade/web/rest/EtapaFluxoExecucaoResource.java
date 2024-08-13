package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EtapaFluxoExecucao;
import com.dobemcontabilidade.repository.EtapaFluxoExecucaoRepository;
import com.dobemcontabilidade.service.EtapaFluxoExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EtapaFluxoExecucao}.
 */
@RestController
@RequestMapping("/api/etapa-fluxo-execucaos")
public class EtapaFluxoExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(EtapaFluxoExecucaoResource.class);

    private static final String ENTITY_NAME = "etapaFluxoExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtapaFluxoExecucaoService etapaFluxoExecucaoService;

    private final EtapaFluxoExecucaoRepository etapaFluxoExecucaoRepository;

    public EtapaFluxoExecucaoResource(
        EtapaFluxoExecucaoService etapaFluxoExecucaoService,
        EtapaFluxoExecucaoRepository etapaFluxoExecucaoRepository
    ) {
        this.etapaFluxoExecucaoService = etapaFluxoExecucaoService;
        this.etapaFluxoExecucaoRepository = etapaFluxoExecucaoRepository;
    }

    /**
     * {@code POST  /etapa-fluxo-execucaos} : Create a new etapaFluxoExecucao.
     *
     * @param etapaFluxoExecucao the etapaFluxoExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etapaFluxoExecucao, or with status {@code 400 (Bad Request)} if the etapaFluxoExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EtapaFluxoExecucao> createEtapaFluxoExecucao(@Valid @RequestBody EtapaFluxoExecucao etapaFluxoExecucao)
        throws URISyntaxException {
        log.debug("REST request to save EtapaFluxoExecucao : {}", etapaFluxoExecucao);
        if (etapaFluxoExecucao.getId() != null) {
            throw new BadRequestAlertException("A new etapaFluxoExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        etapaFluxoExecucao = etapaFluxoExecucaoService.save(etapaFluxoExecucao);
        return ResponseEntity.created(new URI("/api/etapa-fluxo-execucaos/" + etapaFluxoExecucao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, etapaFluxoExecucao.getId().toString()))
            .body(etapaFluxoExecucao);
    }

    /**
     * {@code PUT  /etapa-fluxo-execucaos/:id} : Updates an existing etapaFluxoExecucao.
     *
     * @param id the id of the etapaFluxoExecucao to save.
     * @param etapaFluxoExecucao the etapaFluxoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapaFluxoExecucao,
     * or with status {@code 400 (Bad Request)} if the etapaFluxoExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etapaFluxoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EtapaFluxoExecucao> updateEtapaFluxoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EtapaFluxoExecucao etapaFluxoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update EtapaFluxoExecucao : {}, {}", id, etapaFluxoExecucao);
        if (etapaFluxoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapaFluxoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapaFluxoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        etapaFluxoExecucao = etapaFluxoExecucaoService.update(etapaFluxoExecucao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapaFluxoExecucao.getId().toString()))
            .body(etapaFluxoExecucao);
    }

    /**
     * {@code PATCH  /etapa-fluxo-execucaos/:id} : Partial updates given fields of an existing etapaFluxoExecucao, field will ignore if it is null
     *
     * @param id the id of the etapaFluxoExecucao to save.
     * @param etapaFluxoExecucao the etapaFluxoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etapaFluxoExecucao,
     * or with status {@code 400 (Bad Request)} if the etapaFluxoExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the etapaFluxoExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the etapaFluxoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtapaFluxoExecucao> partialUpdateEtapaFluxoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EtapaFluxoExecucao etapaFluxoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update EtapaFluxoExecucao partially : {}, {}", id, etapaFluxoExecucao);
        if (etapaFluxoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etapaFluxoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etapaFluxoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtapaFluxoExecucao> result = etapaFluxoExecucaoService.partialUpdate(etapaFluxoExecucao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etapaFluxoExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /etapa-fluxo-execucaos} : get all the etapaFluxoExecucaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etapaFluxoExecucaos in body.
     */
    @GetMapping("")
    public List<EtapaFluxoExecucao> getAllEtapaFluxoExecucaos() {
        log.debug("REST request to get all EtapaFluxoExecucaos");
        return etapaFluxoExecucaoService.findAll();
    }

    /**
     * {@code GET  /etapa-fluxo-execucaos/:id} : get the "id" etapaFluxoExecucao.
     *
     * @param id the id of the etapaFluxoExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etapaFluxoExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EtapaFluxoExecucao> getEtapaFluxoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get EtapaFluxoExecucao : {}", id);
        Optional<EtapaFluxoExecucao> etapaFluxoExecucao = etapaFluxoExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etapaFluxoExecucao);
    }

    /**
     * {@code DELETE  /etapa-fluxo-execucaos/:id} : delete the "id" etapaFluxoExecucao.
     *
     * @param id the id of the etapaFluxoExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEtapaFluxoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete EtapaFluxoExecucao : {}", id);
        etapaFluxoExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
