package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.PlanoContabilRepository;
import com.dobemcontabilidade.service.PlanoContabilQueryService;
import com.dobemcontabilidade.service.PlanoContabilService;
import com.dobemcontabilidade.service.criteria.PlanoContabilCriteria;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PlanoContabil}.
 */
@RestController
@RequestMapping("/api/plano-contabils")
public class PlanoContabilResource {

    private static final Logger log = LoggerFactory.getLogger(PlanoContabilResource.class);

    private static final String ENTITY_NAME = "planoContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoContabilService planoContabilService;

    private final PlanoContabilRepository planoContabilRepository;

    private final PlanoContabilQueryService planoContabilQueryService;

    public PlanoContabilResource(
        PlanoContabilService planoContabilService,
        PlanoContabilRepository planoContabilRepository,
        PlanoContabilQueryService planoContabilQueryService
    ) {
        this.planoContabilService = planoContabilService;
        this.planoContabilRepository = planoContabilRepository;
        this.planoContabilQueryService = planoContabilQueryService;
    }

    /**
     * {@code POST  /plano-contabils} : Create a new planoContabil.
     *
     * @param planoContabilDTO the planoContabilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoContabilDTO, or with status {@code 400 (Bad Request)} if the planoContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlanoContabilDTO> createPlanoContabil(@RequestBody PlanoContabilDTO planoContabilDTO) throws URISyntaxException {
        log.debug("REST request to save PlanoContabil : {}", planoContabilDTO);
        if (planoContabilDTO.getId() != null) {
            throw new BadRequestAlertException("A new planoContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        planoContabilDTO = planoContabilService.save(planoContabilDTO);
        return ResponseEntity.created(new URI("/api/plano-contabils/" + planoContabilDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, planoContabilDTO.getId().toString()))
            .body(planoContabilDTO);
    }

    /**
     * {@code PUT  /plano-contabils/:id} : Updates an existing planoContabil.
     *
     * @param id the id of the planoContabilDTO to save.
     * @param planoContabilDTO the planoContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoContabilDTO,
     * or with status {@code 400 (Bad Request)} if the planoContabilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanoContabilDTO> updatePlanoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanoContabilDTO planoContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlanoContabil : {}, {}", id, planoContabilDTO);
        if (planoContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        planoContabilDTO = planoContabilService.update(planoContabilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoContabilDTO.getId().toString()))
            .body(planoContabilDTO);
    }

    /**
     * {@code PATCH  /plano-contabils/:id} : Partial updates given fields of an existing planoContabil, field will ignore if it is null
     *
     * @param id the id of the planoContabilDTO to save.
     * @param planoContabilDTO the planoContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoContabilDTO,
     * or with status {@code 400 (Bad Request)} if the planoContabilDTO is not valid,
     * or with status {@code 404 (Not Found)} if the planoContabilDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoContabilDTO> partialUpdatePlanoContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PlanoContabilDTO planoContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlanoContabil partially : {}, {}", id, planoContabilDTO);
        if (planoContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoContabilDTO> result = planoContabilService.partialUpdate(planoContabilDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoContabilDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-contabils} : get all the planoContabils.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoContabils in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlanoContabilDTO>> getAllPlanoContabils(
        PlanoContabilCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PlanoContabils by criteria: {}", criteria);

        Page<PlanoContabilDTO> page = planoContabilQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plano-contabils/count} : count all the planoContabils.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPlanoContabils(PlanoContabilCriteria criteria) {
        log.debug("REST request to count PlanoContabils by criteria: {}", criteria);
        return ResponseEntity.ok().body(planoContabilQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /plano-contabils/:id} : get the "id" planoContabil.
     *
     * @param id the id of the planoContabilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoContabilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanoContabilDTO> getPlanoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get PlanoContabil : {}", id);
        Optional<PlanoContabilDTO> planoContabilDTO = planoContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planoContabilDTO);
    }

    /**
     * {@code DELETE  /plano-contabils/:id} : delete the "id" planoContabil.
     *
     * @param id the id of the planoContabilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanoContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete PlanoContabil : {}", id);
        planoContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
