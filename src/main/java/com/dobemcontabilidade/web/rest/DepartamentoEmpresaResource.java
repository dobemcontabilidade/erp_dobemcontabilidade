package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.DepartamentoEmpresaRepository;
import com.dobemcontabilidade.service.DepartamentoEmpresaQueryService;
import com.dobemcontabilidade.service.DepartamentoEmpresaService;
import com.dobemcontabilidade.service.criteria.DepartamentoEmpresaCriteria;
import com.dobemcontabilidade.service.dto.DepartamentoEmpresaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DepartamentoEmpresa}.
 */
@RestController
@RequestMapping("/api/departamento-empresas")
public class DepartamentoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoEmpresaResource.class);

    private static final String ENTITY_NAME = "departamentoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentoEmpresaService departamentoEmpresaService;

    private final DepartamentoEmpresaRepository departamentoEmpresaRepository;

    private final DepartamentoEmpresaQueryService departamentoEmpresaQueryService;

    public DepartamentoEmpresaResource(
        DepartamentoEmpresaService departamentoEmpresaService,
        DepartamentoEmpresaRepository departamentoEmpresaRepository,
        DepartamentoEmpresaQueryService departamentoEmpresaQueryService
    ) {
        this.departamentoEmpresaService = departamentoEmpresaService;
        this.departamentoEmpresaRepository = departamentoEmpresaRepository;
        this.departamentoEmpresaQueryService = departamentoEmpresaQueryService;
    }

    /**
     * {@code POST  /departamento-empresas} : Create a new departamentoEmpresa.
     *
     * @param departamentoEmpresaDTO the departamentoEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentoEmpresaDTO, or with status {@code 400 (Bad Request)} if the departamentoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DepartamentoEmpresaDTO> createDepartamentoEmpresa(
        @Valid @RequestBody DepartamentoEmpresaDTO departamentoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DepartamentoEmpresa : {}", departamentoEmpresaDTO);
        if (departamentoEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new departamentoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        departamentoEmpresaDTO = departamentoEmpresaService.save(departamentoEmpresaDTO);
        return ResponseEntity.created(new URI("/api/departamento-empresas/" + departamentoEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, departamentoEmpresaDTO.getId().toString()))
            .body(departamentoEmpresaDTO);
    }

    /**
     * {@code PUT  /departamento-empresas/:id} : Updates an existing departamentoEmpresa.
     *
     * @param id the id of the departamentoEmpresaDTO to save.
     * @param departamentoEmpresaDTO the departamentoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the departamentoEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoEmpresaDTO> updateDepartamentoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepartamentoEmpresaDTO departamentoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DepartamentoEmpresa : {}, {}", id, departamentoEmpresaDTO);
        if (departamentoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        departamentoEmpresaDTO = departamentoEmpresaService.update(departamentoEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentoEmpresaDTO.getId().toString()))
            .body(departamentoEmpresaDTO);
    }

    /**
     * {@code PATCH  /departamento-empresas/:id} : Partial updates given fields of an existing departamentoEmpresa, field will ignore if it is null
     *
     * @param id the id of the departamentoEmpresaDTO to save.
     * @param departamentoEmpresaDTO the departamentoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the departamentoEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the departamentoEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the departamentoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartamentoEmpresaDTO> partialUpdateDepartamentoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepartamentoEmpresaDTO departamentoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DepartamentoEmpresa partially : {}, {}", id, departamentoEmpresaDTO);
        if (departamentoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departamentoEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departamentoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepartamentoEmpresaDTO> result = departamentoEmpresaService.partialUpdate(departamentoEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentoEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /departamento-empresas} : get all the departamentoEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentoEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DepartamentoEmpresaDTO>> getAllDepartamentoEmpresas(
        DepartamentoEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get DepartamentoEmpresas by criteria: {}", criteria);

        Page<DepartamentoEmpresaDTO> page = departamentoEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departamento-empresas/count} : count all the departamentoEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDepartamentoEmpresas(DepartamentoEmpresaCriteria criteria) {
        log.debug("REST request to count DepartamentoEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(departamentoEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /departamento-empresas/:id} : get the "id" departamentoEmpresa.
     *
     * @param id the id of the departamentoEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentoEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoEmpresaDTO> getDepartamentoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get DepartamentoEmpresa : {}", id);
        Optional<DepartamentoEmpresaDTO> departamentoEmpresaDTO = departamentoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departamentoEmpresaDTO);
    }

    /**
     * {@code DELETE  /departamento-empresas/:id} : delete the "id" departamentoEmpresa.
     *
     * @param id the id of the departamentoEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartamentoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete DepartamentoEmpresa : {}", id);
        departamentoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
