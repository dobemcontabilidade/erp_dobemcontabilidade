package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CriterioAvaliacao;
import com.dobemcontabilidade.repository.CriterioAvaliacaoRepository;
import com.dobemcontabilidade.service.CriterioAvaliacaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CriterioAvaliacao}.
 */
@RestController
@RequestMapping("/api/criterio-avaliacaos")
public class CriterioAvaliacaoResource {

    private static final Logger log = LoggerFactory.getLogger(CriterioAvaliacaoResource.class);

    private static final String ENTITY_NAME = "criterioAvaliacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CriterioAvaliacaoService criterioAvaliacaoService;

    private final CriterioAvaliacaoRepository criterioAvaliacaoRepository;

    public CriterioAvaliacaoResource(
        CriterioAvaliacaoService criterioAvaliacaoService,
        CriterioAvaliacaoRepository criterioAvaliacaoRepository
    ) {
        this.criterioAvaliacaoService = criterioAvaliacaoService;
        this.criterioAvaliacaoRepository = criterioAvaliacaoRepository;
    }

    /**
     * {@code POST  /criterio-avaliacaos} : Create a new criterioAvaliacao.
     *
     * @param criterioAvaliacao the criterioAvaliacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new criterioAvaliacao, or with status {@code 400 (Bad Request)} if the criterioAvaliacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CriterioAvaliacao> createCriterioAvaliacao(@Valid @RequestBody CriterioAvaliacao criterioAvaliacao)
        throws URISyntaxException {
        log.debug("REST request to save CriterioAvaliacao : {}", criterioAvaliacao);
        if (criterioAvaliacao.getId() != null) {
            throw new BadRequestAlertException("A new criterioAvaliacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        criterioAvaliacao = criterioAvaliacaoService.save(criterioAvaliacao);
        return ResponseEntity.created(new URI("/api/criterio-avaliacaos/" + criterioAvaliacao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, criterioAvaliacao.getId().toString()))
            .body(criterioAvaliacao);
    }

    /**
     * {@code PUT  /criterio-avaliacaos/:id} : Updates an existing criterioAvaliacao.
     *
     * @param id the id of the criterioAvaliacao to save.
     * @param criterioAvaliacao the criterioAvaliacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criterioAvaliacao,
     * or with status {@code 400 (Bad Request)} if the criterioAvaliacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the criterioAvaliacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CriterioAvaliacao> updateCriterioAvaliacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CriterioAvaliacao criterioAvaliacao
    ) throws URISyntaxException {
        log.debug("REST request to update CriterioAvaliacao : {}, {}", id, criterioAvaliacao);
        if (criterioAvaliacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criterioAvaliacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criterioAvaliacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        criterioAvaliacao = criterioAvaliacaoService.update(criterioAvaliacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criterioAvaliacao.getId().toString()))
            .body(criterioAvaliacao);
    }

    /**
     * {@code PATCH  /criterio-avaliacaos/:id} : Partial updates given fields of an existing criterioAvaliacao, field will ignore if it is null
     *
     * @param id the id of the criterioAvaliacao to save.
     * @param criterioAvaliacao the criterioAvaliacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criterioAvaliacao,
     * or with status {@code 400 (Bad Request)} if the criterioAvaliacao is not valid,
     * or with status {@code 404 (Not Found)} if the criterioAvaliacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the criterioAvaliacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CriterioAvaliacao> partialUpdateCriterioAvaliacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CriterioAvaliacao criterioAvaliacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update CriterioAvaliacao partially : {}, {}", id, criterioAvaliacao);
        if (criterioAvaliacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criterioAvaliacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criterioAvaliacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CriterioAvaliacao> result = criterioAvaliacaoService.partialUpdate(criterioAvaliacao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criterioAvaliacao.getId().toString())
        );
    }

    /**
     * {@code GET  /criterio-avaliacaos} : get all the criterioAvaliacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of criterioAvaliacaos in body.
     */
    @GetMapping("")
    public List<CriterioAvaliacao> getAllCriterioAvaliacaos() {
        log.debug("REST request to get all CriterioAvaliacaos");
        return criterioAvaliacaoService.findAll();
    }

    /**
     * {@code GET  /criterio-avaliacaos/:id} : get the "id" criterioAvaliacao.
     *
     * @param id the id of the criterioAvaliacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the criterioAvaliacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CriterioAvaliacao> getCriterioAvaliacao(@PathVariable("id") Long id) {
        log.debug("REST request to get CriterioAvaliacao : {}", id);
        Optional<CriterioAvaliacao> criterioAvaliacao = criterioAvaliacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criterioAvaliacao);
    }

    /**
     * {@code DELETE  /criterio-avaliacaos/:id} : delete the "id" criterioAvaliacao.
     *
     * @param id the id of the criterioAvaliacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriterioAvaliacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete CriterioAvaliacao : {}", id);
        criterioAvaliacaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
