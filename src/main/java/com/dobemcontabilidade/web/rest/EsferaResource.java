package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.repository.EsferaRepository;
import com.dobemcontabilidade.service.EsferaQueryService;
import com.dobemcontabilidade.service.EsferaService;
import com.dobemcontabilidade.service.criteria.EsferaCriteria;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Esfera}.
 */
@RestController
@RequestMapping("/api/esferas")
public class EsferaResource {

    private static final Logger log = LoggerFactory.getLogger(EsferaResource.class);

    private static final String ENTITY_NAME = "esfera";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EsferaService esferaService;

    private final EsferaRepository esferaRepository;

    private final EsferaQueryService esferaQueryService;

    public EsferaResource(EsferaService esferaService, EsferaRepository esferaRepository, EsferaQueryService esferaQueryService) {
        this.esferaService = esferaService;
        this.esferaRepository = esferaRepository;
        this.esferaQueryService = esferaQueryService;
    }

    /**
     * {@code POST  /esferas} : Create a new esfera.
     *
     * @param esfera the esfera to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new esfera, or with status {@code 400 (Bad Request)} if the esfera has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Esfera> createEsfera(@Valid @RequestBody Esfera esfera) throws URISyntaxException {
        log.debug("REST request to save Esfera : {}", esfera);
        if (esfera.getId() != null) {
            throw new BadRequestAlertException("A new esfera cannot already have an ID", ENTITY_NAME, "idexists");
        }
        esfera = esferaService.save(esfera);
        return ResponseEntity.created(new URI("/api/esferas/" + esfera.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, esfera.getId().toString()))
            .body(esfera);
    }

    /**
     * {@code PUT  /esferas/:id} : Updates an existing esfera.
     *
     * @param id the id of the esfera to save.
     * @param esfera the esfera to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esfera,
     * or with status {@code 400 (Bad Request)} if the esfera is not valid,
     * or with status {@code 500 (Internal Server Error)} if the esfera couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Esfera> updateEsfera(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Esfera esfera
    ) throws URISyntaxException {
        log.debug("REST request to update Esfera : {}, {}", id, esfera);
        if (esfera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, esfera.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!esferaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        esfera = esferaService.update(esfera);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esfera.getId().toString()))
            .body(esfera);
    }

    /**
     * {@code PATCH  /esferas/:id} : Partial updates given fields of an existing esfera, field will ignore if it is null
     *
     * @param id the id of the esfera to save.
     * @param esfera the esfera to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esfera,
     * or with status {@code 400 (Bad Request)} if the esfera is not valid,
     * or with status {@code 404 (Not Found)} if the esfera is not found,
     * or with status {@code 500 (Internal Server Error)} if the esfera couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Esfera> partialUpdateEsfera(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Esfera esfera
    ) throws URISyntaxException {
        log.debug("REST request to partial update Esfera partially : {}, {}", id, esfera);
        if (esfera.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, esfera.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!esferaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Esfera> result = esferaService.partialUpdate(esfera);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esfera.getId().toString())
        );
    }

    /**
     * {@code GET  /esferas} : get all the esferas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of esferas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Esfera>> getAllEsferas(
        EsferaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Esferas by criteria: {}", criteria);

        Page<Esfera> page = esferaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /esferas/count} : count all the esferas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEsferas(EsferaCriteria criteria) {
        log.debug("REST request to count Esferas by criteria: {}", criteria);
        return ResponseEntity.ok().body(esferaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /esferas/:id} : get the "id" esfera.
     *
     * @param id the id of the esfera to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the esfera, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Esfera> getEsfera(@PathVariable("id") Long id) {
        log.debug("REST request to get Esfera : {}", id);
        Optional<Esfera> esfera = esferaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esfera);
    }

    /**
     * {@code DELETE  /esferas/:id} : delete the "id" esfera.
     *
     * @param id the id of the esfera to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEsfera(@PathVariable("id") Long id) {
        log.debug("REST request to delete Esfera : {}", id);
        esferaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
