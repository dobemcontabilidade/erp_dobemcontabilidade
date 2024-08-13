package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import com.dobemcontabilidade.repository.DepartamentoFuncionarioRepository;
import com.dobemcontabilidade.service.DepartamentoFuncionarioQueryService;
import com.dobemcontabilidade.service.DepartamentoFuncionarioService;
import com.dobemcontabilidade.service.criteria.DepartamentoFuncionarioCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DepartamentoFuncionario}.
 */
@RestController
@RequestMapping("/api/departamento-funcionarios")
public class DepartamentoFuncionarioResource {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoFuncionarioResource.class);

    private static final String ENTITY_NAME = "departamentoFuncionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentoFuncionarioService departamentoFuncionarioService;

    private final DepartamentoFuncionarioRepository departamentoFuncionarioRepository;

    private final DepartamentoFuncionarioQueryService departamentoFuncionarioQueryService;

    public DepartamentoFuncionarioResource(
        DepartamentoFuncionarioService departamentoFuncionarioService,
        DepartamentoFuncionarioRepository departamentoFuncionarioRepository,
        DepartamentoFuncionarioQueryService departamentoFuncionarioQueryService
    ) {
        this.departamentoFuncionarioService = departamentoFuncionarioService;
        this.departamentoFuncionarioRepository = departamentoFuncionarioRepository;
        this.departamentoFuncionarioQueryService = departamentoFuncionarioQueryService;
    }

    /**
     * {@code POST  /departamento-funcionarios} : Create a new departamentoFuncionario.
     *
     * @param departamentoFuncionario the departamentoFuncionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentoFuncionario, or with status {@code 400 (Bad Request)} if the departamentoFuncionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DepartamentoFuncionario> createDepartamentoFuncionario(
        @Valid @RequestBody DepartamentoFuncionario departamentoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to save DepartamentoFuncionario : {}", departamentoFuncionario);
        if (departamentoFuncionario.getId() != null) {
            throw new BadRequestAlertException("A new departamentoFuncionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        departamentoFuncionario = departamentoFuncionarioService.save(departamentoFuncionario);
        return ResponseEntity.created(new URI("/api/departamento-funcionarios/" + departamentoFuncionario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, departamentoFuncionario.getId().toString()))
            .body(departamentoFuncionario);
    }

    /**
     * {@code PUT  /departamento-funcionarios/:id} : Updates an existing departamentoFuncionario.
     *
     * @param id the id of the departamentoFuncionario to save.
     * @param departamentoFuncionario the departamentoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoFuncionario,
     * or with status {@code 400 (Bad Request)} if the departamentoFuncionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoFuncionario> updateDepartamentoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepartamentoFuncionario departamentoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to update DepartamentoFuncionario : {}, {}", id, departamentoFuncionario);
        if (departamentoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        departamentoFuncionario = departamentoFuncionarioService.update(departamentoFuncionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentoFuncionario.getId().toString()))
            .body(departamentoFuncionario);
    }

    /**
     * {@code PATCH  /departamento-funcionarios/:id} : Partial updates given fields of an existing departamentoFuncionario, field will ignore if it is null
     *
     * @param id the id of the departamentoFuncionario to save.
     * @param departamentoFuncionario the departamentoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoFuncionario,
     * or with status {@code 400 (Bad Request)} if the departamentoFuncionario is not valid,
     * or with status {@code 404 (Not Found)} if the departamentoFuncionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the departamentoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartamentoFuncionario> partialUpdateDepartamentoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepartamentoFuncionario departamentoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update DepartamentoFuncionario partially : {}, {}", id, departamentoFuncionario);
        if (departamentoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepartamentoFuncionario> result = departamentoFuncionarioService.partialUpdate(departamentoFuncionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentoFuncionario.getId().toString())
        );
    }

    /**
     * {@code GET  /departamento-funcionarios} : get all the departamentoFuncionarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentoFuncionarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DepartamentoFuncionario>> getAllDepartamentoFuncionarios(
        DepartamentoFuncionarioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DepartamentoFuncionarios by criteria: {}", criteria);

        Page<DepartamentoFuncionario> page = departamentoFuncionarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departamento-funcionarios/count} : count all the departamentoFuncionarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDepartamentoFuncionarios(DepartamentoFuncionarioCriteria criteria) {
        log.debug("REST request to count DepartamentoFuncionarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(departamentoFuncionarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /departamento-funcionarios/:id} : get the "id" departamentoFuncionario.
     *
     * @param id the id of the departamentoFuncionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentoFuncionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoFuncionario> getDepartamentoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to get DepartamentoFuncionario : {}", id);
        Optional<DepartamentoFuncionario> departamentoFuncionario = departamentoFuncionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departamentoFuncionario);
    }

    /**
     * {@code DELETE  /departamento-funcionarios/:id} : delete the "id" departamentoFuncionario.
     *
     * @param id the id of the departamentoFuncionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamentoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to delete DepartamentoFuncionario : {}", id);
        departamentoFuncionarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
