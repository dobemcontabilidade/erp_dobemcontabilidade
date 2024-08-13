package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.repository.TermoDeAdesaoRepository;
import com.dobemcontabilidade.service.TermoDeAdesaoQueryService;
import com.dobemcontabilidade.service.TermoDeAdesaoService;
import com.dobemcontabilidade.service.criteria.TermoDeAdesaoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TermoDeAdesao}.
 */
@RestController
@RequestMapping("/api/termo-de-adesaos")
public class TermoDeAdesaoResource {

    private static final Logger log = LoggerFactory.getLogger(TermoDeAdesaoResource.class);

    private static final String ENTITY_NAME = "termoDeAdesao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermoDeAdesaoService termoDeAdesaoService;

    private final TermoDeAdesaoRepository termoDeAdesaoRepository;

    private final TermoDeAdesaoQueryService termoDeAdesaoQueryService;

    public TermoDeAdesaoResource(
        TermoDeAdesaoService termoDeAdesaoService,
        TermoDeAdesaoRepository termoDeAdesaoRepository,
        TermoDeAdesaoQueryService termoDeAdesaoQueryService
    ) {
        this.termoDeAdesaoService = termoDeAdesaoService;
        this.termoDeAdesaoRepository = termoDeAdesaoRepository;
        this.termoDeAdesaoQueryService = termoDeAdesaoQueryService;
    }

    /**
     * {@code POST  /termo-de-adesaos} : Create a new termoDeAdesao.
     *
     * @param termoDeAdesao the termoDeAdesao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termoDeAdesao, or with status {@code 400 (Bad Request)} if the termoDeAdesao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermoDeAdesao> createTermoDeAdesao(@RequestBody TermoDeAdesao termoDeAdesao) throws URISyntaxException {
        log.debug("REST request to save TermoDeAdesao : {}", termoDeAdesao);
        if (termoDeAdesao.getId() != null) {
            throw new BadRequestAlertException("A new termoDeAdesao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termoDeAdesao = termoDeAdesaoService.save(termoDeAdesao);
        return ResponseEntity.created(new URI("/api/termo-de-adesaos/" + termoDeAdesao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termoDeAdesao.getId().toString()))
            .body(termoDeAdesao);
    }

    /**
     * {@code PUT  /termo-de-adesaos/:id} : Updates an existing termoDeAdesao.
     *
     * @param id the id of the termoDeAdesao to save.
     * @param termoDeAdesao the termoDeAdesao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoDeAdesao,
     * or with status {@code 400 (Bad Request)} if the termoDeAdesao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termoDeAdesao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermoDeAdesao> updateTermoDeAdesao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TermoDeAdesao termoDeAdesao
    ) throws URISyntaxException {
        log.debug("REST request to update TermoDeAdesao : {}, {}", id, termoDeAdesao);
        if (termoDeAdesao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoDeAdesao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoDeAdesaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termoDeAdesao = termoDeAdesaoService.update(termoDeAdesao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoDeAdesao.getId().toString()))
            .body(termoDeAdesao);
    }

    /**
     * {@code PATCH  /termo-de-adesaos/:id} : Partial updates given fields of an existing termoDeAdesao, field will ignore if it is null
     *
     * @param id the id of the termoDeAdesao to save.
     * @param termoDeAdesao the termoDeAdesao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termoDeAdesao,
     * or with status {@code 400 (Bad Request)} if the termoDeAdesao is not valid,
     * or with status {@code 404 (Not Found)} if the termoDeAdesao is not found,
     * or with status {@code 500 (Internal Server Error)} if the termoDeAdesao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermoDeAdesao> partialUpdateTermoDeAdesao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TermoDeAdesao termoDeAdesao
    ) throws URISyntaxException {
        log.debug("REST request to partial update TermoDeAdesao partially : {}, {}", id, termoDeAdesao);
        if (termoDeAdesao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termoDeAdesao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termoDeAdesaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermoDeAdesao> result = termoDeAdesaoService.partialUpdate(termoDeAdesao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termoDeAdesao.getId().toString())
        );
    }

    /**
     * {@code GET  /termo-de-adesaos} : get all the termoDeAdesaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termoDeAdesaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TermoDeAdesao>> getAllTermoDeAdesaos(
        TermoDeAdesaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TermoDeAdesaos by criteria: {}", criteria);

        Page<TermoDeAdesao> page = termoDeAdesaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /termo-de-adesaos/count} : count all the termoDeAdesaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTermoDeAdesaos(TermoDeAdesaoCriteria criteria) {
        log.debug("REST request to count TermoDeAdesaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(termoDeAdesaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /termo-de-adesaos/:id} : get the "id" termoDeAdesao.
     *
     * @param id the id of the termoDeAdesao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termoDeAdesao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermoDeAdesao> getTermoDeAdesao(@PathVariable("id") Long id) {
        log.debug("REST request to get TermoDeAdesao : {}", id);
        Optional<TermoDeAdesao> termoDeAdesao = termoDeAdesaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termoDeAdesao);
    }

    /**
     * {@code DELETE  /termo-de-adesaos/:id} : delete the "id" termoDeAdesao.
     *
     * @param id the id of the termoDeAdesao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermoDeAdesao(@PathVariable("id") Long id) {
        log.debug("REST request to delete TermoDeAdesao : {}", id);
        termoDeAdesaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
