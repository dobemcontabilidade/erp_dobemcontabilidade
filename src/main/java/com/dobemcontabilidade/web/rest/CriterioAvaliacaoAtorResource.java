package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.CriterioAvaliacaoAtor;
import com.dobemcontabilidade.repository.CriterioAvaliacaoAtorRepository;
import com.dobemcontabilidade.service.CriterioAvaliacaoAtorService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.CriterioAvaliacaoAtor}.
 */
@RestController
@RequestMapping("/api/criterio-avaliacao-ators")
public class CriterioAvaliacaoAtorResource {

    private static final Logger log = LoggerFactory.getLogger(CriterioAvaliacaoAtorResource.class);

    private static final String ENTITY_NAME = "criterioAvaliacaoAtor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CriterioAvaliacaoAtorService criterioAvaliacaoAtorService;

    private final CriterioAvaliacaoAtorRepository criterioAvaliacaoAtorRepository;

    public CriterioAvaliacaoAtorResource(
        CriterioAvaliacaoAtorService criterioAvaliacaoAtorService,
        CriterioAvaliacaoAtorRepository criterioAvaliacaoAtorRepository
    ) {
        this.criterioAvaliacaoAtorService = criterioAvaliacaoAtorService;
        this.criterioAvaliacaoAtorRepository = criterioAvaliacaoAtorRepository;
    }

    /**
     * {@code POST  /criterio-avaliacao-ators} : Create a new criterioAvaliacaoAtor.
     *
     * @param criterioAvaliacaoAtor the criterioAvaliacaoAtor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new criterioAvaliacaoAtor, or with status {@code 400 (Bad Request)} if the criterioAvaliacaoAtor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CriterioAvaliacaoAtor> createCriterioAvaliacaoAtor(
        @Valid @RequestBody CriterioAvaliacaoAtor criterioAvaliacaoAtor
    ) throws URISyntaxException {
        log.debug("REST request to save CriterioAvaliacaoAtor : {}", criterioAvaliacaoAtor);
        if (criterioAvaliacaoAtor.getId() != null) {
            throw new BadRequestAlertException("A new criterioAvaliacaoAtor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        criterioAvaliacaoAtor = criterioAvaliacaoAtorService.save(criterioAvaliacaoAtor);
        return ResponseEntity.created(new URI("/api/criterio-avaliacao-ators/" + criterioAvaliacaoAtor.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, criterioAvaliacaoAtor.getId().toString()))
            .body(criterioAvaliacaoAtor);
    }

    /**
     * {@code PUT  /criterio-avaliacao-ators/:id} : Updates an existing criterioAvaliacaoAtor.
     *
     * @param id the id of the criterioAvaliacaoAtor to save.
     * @param criterioAvaliacaoAtor the criterioAvaliacaoAtor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criterioAvaliacaoAtor,
     * or with status {@code 400 (Bad Request)} if the criterioAvaliacaoAtor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the criterioAvaliacaoAtor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CriterioAvaliacaoAtor> updateCriterioAvaliacaoAtor(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CriterioAvaliacaoAtor criterioAvaliacaoAtor
    ) throws URISyntaxException {
        log.debug("REST request to update CriterioAvaliacaoAtor : {}, {}", id, criterioAvaliacaoAtor);
        if (criterioAvaliacaoAtor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criterioAvaliacaoAtor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criterioAvaliacaoAtorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        criterioAvaliacaoAtor = criterioAvaliacaoAtorService.update(criterioAvaliacaoAtor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criterioAvaliacaoAtor.getId().toString()))
            .body(criterioAvaliacaoAtor);
    }

    /**
     * {@code PATCH  /criterio-avaliacao-ators/:id} : Partial updates given fields of an existing criterioAvaliacaoAtor, field will ignore if it is null
     *
     * @param id the id of the criterioAvaliacaoAtor to save.
     * @param criterioAvaliacaoAtor the criterioAvaliacaoAtor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated criterioAvaliacaoAtor,
     * or with status {@code 400 (Bad Request)} if the criterioAvaliacaoAtor is not valid,
     * or with status {@code 404 (Not Found)} if the criterioAvaliacaoAtor is not found,
     * or with status {@code 500 (Internal Server Error)} if the criterioAvaliacaoAtor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CriterioAvaliacaoAtor> partialUpdateCriterioAvaliacaoAtor(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CriterioAvaliacaoAtor criterioAvaliacaoAtor
    ) throws URISyntaxException {
        log.debug("REST request to partial update CriterioAvaliacaoAtor partially : {}, {}", id, criterioAvaliacaoAtor);
        if (criterioAvaliacaoAtor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, criterioAvaliacaoAtor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!criterioAvaliacaoAtorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CriterioAvaliacaoAtor> result = criterioAvaliacaoAtorService.partialUpdate(criterioAvaliacaoAtor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, criterioAvaliacaoAtor.getId().toString())
        );
    }

    /**
     * {@code GET  /criterio-avaliacao-ators} : get all the criterioAvaliacaoAtors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of criterioAvaliacaoAtors in body.
     */
    @GetMapping("")
    public List<CriterioAvaliacaoAtor> getAllCriterioAvaliacaoAtors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all CriterioAvaliacaoAtors");
        return criterioAvaliacaoAtorService.findAll();
    }

    /**
     * {@code GET  /criterio-avaliacao-ators/:id} : get the "id" criterioAvaliacaoAtor.
     *
     * @param id the id of the criterioAvaliacaoAtor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the criterioAvaliacaoAtor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CriterioAvaliacaoAtor> getCriterioAvaliacaoAtor(@PathVariable("id") Long id) {
        log.debug("REST request to get CriterioAvaliacaoAtor : {}", id);
        Optional<CriterioAvaliacaoAtor> criterioAvaliacaoAtor = criterioAvaliacaoAtorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(criterioAvaliacaoAtor);
    }

    /**
     * {@code DELETE  /criterio-avaliacao-ators/:id} : delete the "id" criterioAvaliacaoAtor.
     *
     * @param id the id of the criterioAvaliacaoAtor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriterioAvaliacaoAtor(@PathVariable("id") Long id) {
        log.debug("REST request to delete CriterioAvaliacaoAtor : {}", id);
        criterioAvaliacaoAtorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
