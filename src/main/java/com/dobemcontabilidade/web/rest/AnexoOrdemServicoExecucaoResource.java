package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao;
import com.dobemcontabilidade.repository.AnexoOrdemServicoExecucaoRepository;
import com.dobemcontabilidade.service.AnexoOrdemServicoExecucaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoOrdemServicoExecucao}.
 */
@RestController
@RequestMapping("/api/anexo-ordem-servico-execucaos")
public class AnexoOrdemServicoExecucaoResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoOrdemServicoExecucaoResource.class);

    private static final String ENTITY_NAME = "anexoOrdemServicoExecucao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoOrdemServicoExecucaoService anexoOrdemServicoExecucaoService;

    private final AnexoOrdemServicoExecucaoRepository anexoOrdemServicoExecucaoRepository;

    public AnexoOrdemServicoExecucaoResource(
        AnexoOrdemServicoExecucaoService anexoOrdemServicoExecucaoService,
        AnexoOrdemServicoExecucaoRepository anexoOrdemServicoExecucaoRepository
    ) {
        this.anexoOrdemServicoExecucaoService = anexoOrdemServicoExecucaoService;
        this.anexoOrdemServicoExecucaoRepository = anexoOrdemServicoExecucaoRepository;
    }

    /**
     * {@code POST  /anexo-ordem-servico-execucaos} : Create a new anexoOrdemServicoExecucao.
     *
     * @param anexoOrdemServicoExecucao the anexoOrdemServicoExecucao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoOrdemServicoExecucao, or with status {@code 400 (Bad Request)} if the anexoOrdemServicoExecucao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoOrdemServicoExecucao> createAnexoOrdemServicoExecucao(
        @Valid @RequestBody AnexoOrdemServicoExecucao anexoOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to save AnexoOrdemServicoExecucao : {}", anexoOrdemServicoExecucao);
        if (anexoOrdemServicoExecucao.getId() != null) {
            throw new BadRequestAlertException("A new anexoOrdemServicoExecucao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoOrdemServicoExecucao = anexoOrdemServicoExecucaoService.save(anexoOrdemServicoExecucao);
        return ResponseEntity.created(new URI("/api/anexo-ordem-servico-execucaos/" + anexoOrdemServicoExecucao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoOrdemServicoExecucao.getId().toString()))
            .body(anexoOrdemServicoExecucao);
    }

    /**
     * {@code PUT  /anexo-ordem-servico-execucaos/:id} : Updates an existing anexoOrdemServicoExecucao.
     *
     * @param id the id of the anexoOrdemServicoExecucao to save.
     * @param anexoOrdemServicoExecucao the anexoOrdemServicoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoOrdemServicoExecucao,
     * or with status {@code 400 (Bad Request)} if the anexoOrdemServicoExecucao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoOrdemServicoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoOrdemServicoExecucao> updateAnexoOrdemServicoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoOrdemServicoExecucao anexoOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoOrdemServicoExecucao : {}, {}", id, anexoOrdemServicoExecucao);
        if (anexoOrdemServicoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoOrdemServicoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoOrdemServicoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoOrdemServicoExecucao = anexoOrdemServicoExecucaoService.update(anexoOrdemServicoExecucao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoOrdemServicoExecucao.getId().toString()))
            .body(anexoOrdemServicoExecucao);
    }

    /**
     * {@code PATCH  /anexo-ordem-servico-execucaos/:id} : Partial updates given fields of an existing anexoOrdemServicoExecucao, field will ignore if it is null
     *
     * @param id the id of the anexoOrdemServicoExecucao to save.
     * @param anexoOrdemServicoExecucao the anexoOrdemServicoExecucao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoOrdemServicoExecucao,
     * or with status {@code 400 (Bad Request)} if the anexoOrdemServicoExecucao is not valid,
     * or with status {@code 404 (Not Found)} if the anexoOrdemServicoExecucao is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoOrdemServicoExecucao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoOrdemServicoExecucao> partialUpdateAnexoOrdemServicoExecucao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoOrdemServicoExecucao anexoOrdemServicoExecucao
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoOrdemServicoExecucao partially : {}, {}", id, anexoOrdemServicoExecucao);
        if (anexoOrdemServicoExecucao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoOrdemServicoExecucao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoOrdemServicoExecucaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoOrdemServicoExecucao> result = anexoOrdemServicoExecucaoService.partialUpdate(anexoOrdemServicoExecucao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoOrdemServicoExecucao.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-ordem-servico-execucaos} : get all the anexoOrdemServicoExecucaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoOrdemServicoExecucaos in body.
     */
    @GetMapping("")
    public List<AnexoOrdemServicoExecucao> getAllAnexoOrdemServicoExecucaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AnexoOrdemServicoExecucaos");
        return anexoOrdemServicoExecucaoService.findAll();
    }

    /**
     * {@code GET  /anexo-ordem-servico-execucaos/:id} : get the "id" anexoOrdemServicoExecucao.
     *
     * @param id the id of the anexoOrdemServicoExecucao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoOrdemServicoExecucao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoOrdemServicoExecucao> getAnexoOrdemServicoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoOrdemServicoExecucao : {}", id);
        Optional<AnexoOrdemServicoExecucao> anexoOrdemServicoExecucao = anexoOrdemServicoExecucaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoOrdemServicoExecucao);
    }

    /**
     * {@code DELETE  /anexo-ordem-servico-execucaos/:id} : delete the "id" anexoOrdemServicoExecucao.
     *
     * @param id the id of the anexoOrdemServicoExecucao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoOrdemServicoExecucao(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoOrdemServicoExecucao : {}", id);
        anexoOrdemServicoExecucaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
