package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PrazoAssinatura;
import com.dobemcontabilidade.repository.PrazoAssinaturaRepository;
import com.dobemcontabilidade.service.PrazoAssinaturaQueryService;
import com.dobemcontabilidade.service.PrazoAssinaturaService;
import com.dobemcontabilidade.service.criteria.PrazoAssinaturaCriteria;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PrazoAssinatura}.
 */
@RestController
@RequestMapping("/api/prazo-assinaturas")
public class PrazoAssinaturaResource {

    private static final Logger log = LoggerFactory.getLogger(PrazoAssinaturaResource.class);

    private static final String ENTITY_NAME = "prazoAssinatura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrazoAssinaturaService prazoAssinaturaService;

    private final PrazoAssinaturaRepository prazoAssinaturaRepository;

    private final PrazoAssinaturaQueryService prazoAssinaturaQueryService;

    public PrazoAssinaturaResource(
        PrazoAssinaturaService prazoAssinaturaService,
        PrazoAssinaturaRepository prazoAssinaturaRepository,
        PrazoAssinaturaQueryService prazoAssinaturaQueryService
    ) {
        this.prazoAssinaturaService = prazoAssinaturaService;
        this.prazoAssinaturaRepository = prazoAssinaturaRepository;
        this.prazoAssinaturaQueryService = prazoAssinaturaQueryService;
    }

    /**
     * {@code POST  /prazo-assinaturas} : Create a new prazoAssinatura.
     *
     * @param prazoAssinatura the prazoAssinatura to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prazoAssinatura, or with status {@code 400 (Bad Request)} if the prazoAssinatura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PrazoAssinatura> createPrazoAssinatura(@RequestBody PrazoAssinatura prazoAssinatura) throws URISyntaxException {
        log.debug("REST request to save PrazoAssinatura : {}", prazoAssinatura);
        if (prazoAssinatura.getId() != null) {
            throw new BadRequestAlertException("A new prazoAssinatura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prazoAssinatura = prazoAssinaturaService.save(prazoAssinatura);
        return ResponseEntity.created(new URI("/api/prazo-assinaturas/" + prazoAssinatura.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, prazoAssinatura.getId().toString()))
            .body(prazoAssinatura);
    }

    /**
     * {@code PUT  /prazo-assinaturas/:id} : Updates an existing prazoAssinatura.
     *
     * @param id the id of the prazoAssinatura to save.
     * @param prazoAssinatura the prazoAssinatura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prazoAssinatura,
     * or with status {@code 400 (Bad Request)} if the prazoAssinatura is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prazoAssinatura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PrazoAssinatura> updatePrazoAssinatura(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrazoAssinatura prazoAssinatura
    ) throws URISyntaxException {
        log.debug("REST request to update PrazoAssinatura : {}, {}", id, prazoAssinatura);
        if (prazoAssinatura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prazoAssinatura.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prazoAssinaturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        prazoAssinatura = prazoAssinaturaService.update(prazoAssinatura);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prazoAssinatura.getId().toString()))
            .body(prazoAssinatura);
    }

    /**
     * {@code PATCH  /prazo-assinaturas/:id} : Partial updates given fields of an existing prazoAssinatura, field will ignore if it is null
     *
     * @param id the id of the prazoAssinatura to save.
     * @param prazoAssinatura the prazoAssinatura to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prazoAssinatura,
     * or with status {@code 400 (Bad Request)} if the prazoAssinatura is not valid,
     * or with status {@code 404 (Not Found)} if the prazoAssinatura is not found,
     * or with status {@code 500 (Internal Server Error)} if the prazoAssinatura couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PrazoAssinatura> partialUpdatePrazoAssinatura(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PrazoAssinatura prazoAssinatura
    ) throws URISyntaxException {
        log.debug("REST request to partial update PrazoAssinatura partially : {}, {}", id, prazoAssinatura);
        if (prazoAssinatura.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, prazoAssinatura.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!prazoAssinaturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PrazoAssinatura> result = prazoAssinaturaService.partialUpdate(prazoAssinatura);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prazoAssinatura.getId().toString())
        );
    }

    /**
     * {@code GET  /prazo-assinaturas} : get all the prazoAssinaturas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prazoAssinaturas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PrazoAssinatura>> getAllPrazoAssinaturas(
        PrazoAssinaturaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PrazoAssinaturas by criteria: {}", criteria);

        Page<PrazoAssinatura> page = prazoAssinaturaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prazo-assinaturas/count} : count all the prazoAssinaturas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPrazoAssinaturas(PrazoAssinaturaCriteria criteria) {
        log.debug("REST request to count PrazoAssinaturas by criteria: {}", criteria);
        return ResponseEntity.ok().body(prazoAssinaturaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prazo-assinaturas/:id} : get the "id" prazoAssinatura.
     *
     * @param id the id of the prazoAssinatura to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prazoAssinatura, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PrazoAssinatura> getPrazoAssinatura(@PathVariable("id") Long id) {
        log.debug("REST request to get PrazoAssinatura : {}", id);
        Optional<PrazoAssinatura> prazoAssinatura = prazoAssinaturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prazoAssinatura);
    }

    /**
     * {@code DELETE  /prazo-assinaturas/:id} : delete the "id" prazoAssinatura.
     *
     * @param id the id of the prazoAssinatura to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrazoAssinatura(@PathVariable("id") Long id) {
        log.debug("REST request to delete PrazoAssinatura : {}", id);
        prazoAssinaturaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
