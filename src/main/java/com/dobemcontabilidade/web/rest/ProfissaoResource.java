package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.repository.ProfissaoRepository;
import com.dobemcontabilidade.service.ProfissaoQueryService;
import com.dobemcontabilidade.service.ProfissaoService;
import com.dobemcontabilidade.service.criteria.ProfissaoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Profissao}.
 */
@RestController
@RequestMapping("/api/profissaos")
public class ProfissaoResource {

    private static final Logger log = LoggerFactory.getLogger(ProfissaoResource.class);

    private static final String ENTITY_NAME = "profissao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfissaoService profissaoService;

    private final ProfissaoRepository profissaoRepository;

    private final ProfissaoQueryService profissaoQueryService;

    public ProfissaoResource(
        ProfissaoService profissaoService,
        ProfissaoRepository profissaoRepository,
        ProfissaoQueryService profissaoQueryService
    ) {
        this.profissaoService = profissaoService;
        this.profissaoRepository = profissaoRepository;
        this.profissaoQueryService = profissaoQueryService;
    }

    /**
     * {@code POST  /profissaos} : Create a new profissao.
     *
     * @param profissao the profissao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profissao, or with status {@code 400 (Bad Request)} if the profissao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Profissao> createProfissao(@RequestBody Profissao profissao) throws URISyntaxException {
        log.debug("REST request to save Profissao : {}", profissao);
        if (profissao.getId() != null) {
            throw new BadRequestAlertException("A new profissao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        profissao = profissaoService.save(profissao);
        return ResponseEntity.created(new URI("/api/profissaos/" + profissao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, profissao.getId().toString()))
            .body(profissao);
    }

    /**
     * {@code PUT  /profissaos/:id} : Updates an existing profissao.
     *
     * @param id the id of the profissao to save.
     * @param profissao the profissao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profissao,
     * or with status {@code 400 (Bad Request)} if the profissao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profissao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Profissao> updateProfissao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Profissao profissao
    ) throws URISyntaxException {
        log.debug("REST request to update Profissao : {}, {}", id, profissao);
        if (profissao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profissao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profissaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        profissao = profissaoService.update(profissao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profissao.getId().toString()))
            .body(profissao);
    }

    /**
     * {@code PATCH  /profissaos/:id} : Partial updates given fields of an existing profissao, field will ignore if it is null
     *
     * @param id the id of the profissao to save.
     * @param profissao the profissao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profissao,
     * or with status {@code 400 (Bad Request)} if the profissao is not valid,
     * or with status {@code 404 (Not Found)} if the profissao is not found,
     * or with status {@code 500 (Internal Server Error)} if the profissao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Profissao> partialUpdateProfissao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Profissao profissao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Profissao partially : {}, {}", id, profissao);
        if (profissao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, profissao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!profissaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Profissao> result = profissaoService.partialUpdate(profissao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profissao.getId().toString())
        );
    }

    /**
     * {@code GET  /profissaos} : get all the profissaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profissaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Profissao>> getAllProfissaos(
        ProfissaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Profissaos by criteria: {}", criteria);

        Page<Profissao> page = profissaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /profissaos/count} : count all the profissaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProfissaos(ProfissaoCriteria criteria) {
        log.debug("REST request to count Profissaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(profissaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /profissaos/:id} : get the "id" profissao.
     *
     * @param id the id of the profissao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profissao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Profissao> getProfissao(@PathVariable("id") Long id) {
        log.debug("REST request to get Profissao : {}", id);
        Optional<Profissao> profissao = profissaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(profissao);
    }

    /**
     * {@code DELETE  /profissaos/:id} : delete the "id" profissao.
     *
     * @param id the id of the profissao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfissao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Profissao : {}", id);
        profissaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
