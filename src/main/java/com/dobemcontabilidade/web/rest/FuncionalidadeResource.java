package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Funcionalidade;
import com.dobemcontabilidade.repository.FuncionalidadeRepository;
import com.dobemcontabilidade.service.FuncionalidadeQueryService;
import com.dobemcontabilidade.service.FuncionalidadeService;
import com.dobemcontabilidade.service.criteria.FuncionalidadeCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Funcionalidade}.
 */
@RestController
@RequestMapping("/api/funcionalidades")
public class FuncionalidadeResource {

    private static final Logger log = LoggerFactory.getLogger(FuncionalidadeResource.class);

    private static final String ENTITY_NAME = "funcionalidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionalidadeService funcionalidadeService;

    private final FuncionalidadeRepository funcionalidadeRepository;

    private final FuncionalidadeQueryService funcionalidadeQueryService;

    public FuncionalidadeResource(
        FuncionalidadeService funcionalidadeService,
        FuncionalidadeRepository funcionalidadeRepository,
        FuncionalidadeQueryService funcionalidadeQueryService
    ) {
        this.funcionalidadeService = funcionalidadeService;
        this.funcionalidadeRepository = funcionalidadeRepository;
        this.funcionalidadeQueryService = funcionalidadeQueryService;
    }

    /**
     * {@code POST  /funcionalidades} : Create a new funcionalidade.
     *
     * @param funcionalidade the funcionalidade to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionalidade, or with status {@code 400 (Bad Request)} if the funcionalidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Funcionalidade> createFuncionalidade(@Valid @RequestBody Funcionalidade funcionalidade)
        throws URISyntaxException {
        log.debug("REST request to save Funcionalidade : {}", funcionalidade);
        if (funcionalidade.getId() != null) {
            throw new BadRequestAlertException("A new funcionalidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        funcionalidade = funcionalidadeService.save(funcionalidade);
        return ResponseEntity.created(new URI("/api/funcionalidades/" + funcionalidade.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, funcionalidade.getId().toString()))
            .body(funcionalidade);
    }

    /**
     * {@code PUT  /funcionalidades/:id} : Updates an existing funcionalidade.
     *
     * @param id the id of the funcionalidade to save.
     * @param funcionalidade the funcionalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidade,
     * or with status {@code 400 (Bad Request)} if the funcionalidade is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Funcionalidade> updateFuncionalidade(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Funcionalidade funcionalidade
    ) throws URISyntaxException {
        log.debug("REST request to update Funcionalidade : {}, {}", id, funcionalidade);
        if (funcionalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        funcionalidade = funcionalidadeService.update(funcionalidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidade.getId().toString()))
            .body(funcionalidade);
    }

    /**
     * {@code PATCH  /funcionalidades/:id} : Partial updates given fields of an existing funcionalidade, field will ignore if it is null
     *
     * @param id the id of the funcionalidade to save.
     * @param funcionalidade the funcionalidade to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionalidade,
     * or with status {@code 400 (Bad Request)} if the funcionalidade is not valid,
     * or with status {@code 404 (Not Found)} if the funcionalidade is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionalidade couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Funcionalidade> partialUpdateFuncionalidade(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Funcionalidade funcionalidade
    ) throws URISyntaxException {
        log.debug("REST request to partial update Funcionalidade partially : {}, {}", id, funcionalidade);
        if (funcionalidade.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionalidade.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionalidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Funcionalidade> result = funcionalidadeService.partialUpdate(funcionalidade);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionalidade.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionalidades} : get all the funcionalidades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionalidades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Funcionalidade>> getAllFuncionalidades(
        FuncionalidadeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Funcionalidades by criteria: {}", criteria);

        Page<Funcionalidade> page = funcionalidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funcionalidades/count} : count all the funcionalidades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFuncionalidades(FuncionalidadeCriteria criteria) {
        log.debug("REST request to count Funcionalidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(funcionalidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funcionalidades/:id} : get the "id" funcionalidade.
     *
     * @param id the id of the funcionalidade to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionalidade, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Funcionalidade> getFuncionalidade(@PathVariable("id") Long id) {
        log.debug("REST request to get Funcionalidade : {}", id);
        Optional<Funcionalidade> funcionalidade = funcionalidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionalidade);
    }

    /**
     * {@code DELETE  /funcionalidades/:id} : delete the "id" funcionalidade.
     *
     * @param id the id of the funcionalidade to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionalidade(@PathVariable("id") Long id) {
        log.debug("REST request to delete Funcionalidade : {}", id);
        funcionalidadeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
