package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.ClasseCnaeRepository;
import com.dobemcontabilidade.service.ClasseCnaeQueryService;
import com.dobemcontabilidade.service.ClasseCnaeService;
import com.dobemcontabilidade.service.criteria.ClasseCnaeCriteria;
import com.dobemcontabilidade.service.dto.ClasseCnaeDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ClasseCnae}.
 */
@RestController
@RequestMapping("/api/classe-cnaes")
public class ClasseCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(ClasseCnaeResource.class);

    private static final String ENTITY_NAME = "classeCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClasseCnaeService classeCnaeService;

    private final ClasseCnaeRepository classeCnaeRepository;

    private final ClasseCnaeQueryService classeCnaeQueryService;

    public ClasseCnaeResource(
        ClasseCnaeService classeCnaeService,
        ClasseCnaeRepository classeCnaeRepository,
        ClasseCnaeQueryService classeCnaeQueryService
    ) {
        this.classeCnaeService = classeCnaeService;
        this.classeCnaeRepository = classeCnaeRepository;
        this.classeCnaeQueryService = classeCnaeQueryService;
    }

    /**
     * {@code POST  /classe-cnaes} : Create a new classeCnae.
     *
     * @param classeCnaeDTO the classeCnaeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classeCnaeDTO, or with status {@code 400 (Bad Request)} if the classeCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClasseCnaeDTO> createClasseCnae(@Valid @RequestBody ClasseCnaeDTO classeCnaeDTO) throws URISyntaxException {
        log.debug("REST request to save ClasseCnae : {}", classeCnaeDTO);
        if (classeCnaeDTO.getId() != null) {
            throw new BadRequestAlertException("A new classeCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        classeCnaeDTO = classeCnaeService.save(classeCnaeDTO);
        return ResponseEntity.created(new URI("/api/classe-cnaes/" + classeCnaeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, classeCnaeDTO.getId().toString()))
            .body(classeCnaeDTO);
    }

    /**
     * {@code PUT  /classe-cnaes/:id} : Updates an existing classeCnae.
     *
     * @param id the id of the classeCnaeDTO to save.
     * @param classeCnaeDTO the classeCnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeCnaeDTO,
     * or with status {@code 400 (Bad Request)} if the classeCnaeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classeCnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClasseCnaeDTO> updateClasseCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClasseCnaeDTO classeCnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClasseCnae : {}, {}", id, classeCnaeDTO);
        if (classeCnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classeCnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classeCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        classeCnaeDTO = classeCnaeService.update(classeCnaeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classeCnaeDTO.getId().toString()))
            .body(classeCnaeDTO);
    }

    /**
     * {@code PATCH  /classe-cnaes/:id} : Partial updates given fields of an existing classeCnae, field will ignore if it is null
     *
     * @param id the id of the classeCnaeDTO to save.
     * @param classeCnaeDTO the classeCnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classeCnaeDTO,
     * or with status {@code 400 (Bad Request)} if the classeCnaeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the classeCnaeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the classeCnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClasseCnaeDTO> partialUpdateClasseCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClasseCnaeDTO classeCnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClasseCnae partially : {}, {}", id, classeCnaeDTO);
        if (classeCnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, classeCnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!classeCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClasseCnaeDTO> result = classeCnaeService.partialUpdate(classeCnaeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classeCnaeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /classe-cnaes} : get all the classeCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classeCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClasseCnaeDTO>> getAllClasseCnaes(
        ClasseCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClasseCnaes by criteria: {}", criteria);

        Page<ClasseCnaeDTO> page = classeCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /classe-cnaes/count} : count all the classeCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClasseCnaes(ClasseCnaeCriteria criteria) {
        log.debug("REST request to count ClasseCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(classeCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /classe-cnaes/:id} : get the "id" classeCnae.
     *
     * @param id the id of the classeCnaeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classeCnaeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClasseCnaeDTO> getClasseCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get ClasseCnae : {}", id);
        Optional<ClasseCnaeDTO> classeCnaeDTO = classeCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classeCnaeDTO);
    }

    /**
     * {@code DELETE  /classe-cnaes/:id} : delete the "id" classeCnae.
     *
     * @param id the id of the classeCnaeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasseCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete ClasseCnae : {}", id);
        classeCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
