package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DepartamentoContador;
import com.dobemcontabilidade.repository.DepartamentoContadorRepository;
import com.dobemcontabilidade.service.DepartamentoContadorQueryService;
import com.dobemcontabilidade.service.DepartamentoContadorService;
import com.dobemcontabilidade.service.criteria.DepartamentoContadorCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DepartamentoContador}.
 */
@RestController
@RequestMapping("/api/departamento-contadors")
public class DepartamentoContadorResource {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoContadorResource.class);

    private static final String ENTITY_NAME = "departamentoContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentoContadorService departamentoContadorService;

    private final DepartamentoContadorRepository departamentoContadorRepository;

    private final DepartamentoContadorQueryService departamentoContadorQueryService;

    public DepartamentoContadorResource(
        DepartamentoContadorService departamentoContadorService,
        DepartamentoContadorRepository departamentoContadorRepository,
        DepartamentoContadorQueryService departamentoContadorQueryService
    ) {
        this.departamentoContadorService = departamentoContadorService;
        this.departamentoContadorRepository = departamentoContadorRepository;
        this.departamentoContadorQueryService = departamentoContadorQueryService;
    }

    /**
     * {@code POST  /departamento-contadors} : Create a new departamentoContador.
     *
     * @param departamentoContador the departamentoContador to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentoContador, or with status {@code 400 (Bad Request)} if the departamentoContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DepartamentoContador> createDepartamentoContador(@Valid @RequestBody DepartamentoContador departamentoContador)
        throws URISyntaxException {
        log.debug("REST request to save DepartamentoContador : {}", departamentoContador);
        if (departamentoContador.getId() != null) {
            throw new BadRequestAlertException("A new departamentoContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        departamentoContador = departamentoContadorService.save(departamentoContador);
        return ResponseEntity.created(new URI("/api/departamento-contadors/" + departamentoContador.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, departamentoContador.getId().toString()))
            .body(departamentoContador);
    }

    /**
     * {@code PUT  /departamento-contadors/:id} : Updates an existing departamentoContador.
     *
     * @param id the id of the departamentoContador to save.
     * @param departamentoContador the departamentoContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoContador,
     * or with status {@code 400 (Bad Request)} if the departamentoContador is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentoContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoContador> updateDepartamentoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepartamentoContador departamentoContador
    ) throws URISyntaxException {
        log.debug("REST request to update DepartamentoContador : {}, {}", id, departamentoContador);
        if (departamentoContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        departamentoContador = departamentoContadorService.update(departamentoContador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentoContador.getId().toString()))
            .body(departamentoContador);
    }

    /**
     * {@code PATCH  /departamento-contadors/:id} : Partial updates given fields of an existing departamentoContador, field will ignore if it is null
     *
     * @param id the id of the departamentoContador to save.
     * @param departamentoContador the departamentoContador to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoContador,
     * or with status {@code 400 (Bad Request)} if the departamentoContador is not valid,
     * or with status {@code 404 (Not Found)} if the departamentoContador is not found,
     * or with status {@code 500 (Internal Server Error)} if the departamentoContador couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartamentoContador> partialUpdateDepartamentoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepartamentoContador departamentoContador
    ) throws URISyntaxException {
        log.debug("REST request to partial update DepartamentoContador partially : {}, {}", id, departamentoContador);
        if (departamentoContador.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoContador.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepartamentoContador> result = departamentoContadorService.partialUpdate(departamentoContador);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentoContador.getId().toString())
        );
    }

    /**
     * {@code GET  /departamento-contadors} : get all the departamentoContadors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentoContadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DepartamentoContador>> getAllDepartamentoContadors(
        DepartamentoContadorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DepartamentoContadors by criteria: {}", criteria);

        Page<DepartamentoContador> page = departamentoContadorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departamento-contadors/count} : count all the departamentoContadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDepartamentoContadors(DepartamentoContadorCriteria criteria) {
        log.debug("REST request to count DepartamentoContadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(departamentoContadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /departamento-contadors/:id} : get the "id" departamentoContador.
     *
     * @param id the id of the departamentoContador to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentoContador, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoContador> getDepartamentoContador(@PathVariable("id") Long id) {
        log.debug("REST request to get DepartamentoContador : {}", id);
        Optional<DepartamentoContador> departamentoContador = departamentoContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departamentoContador);
    }

    /**
     * {@code DELETE  /departamento-contadors/:id} : delete the "id" departamentoContador.
     *
     * @param id the id of the departamentoContador to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamentoContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete DepartamentoContador : {}", id);
        departamentoContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
