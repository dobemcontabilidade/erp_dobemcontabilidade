package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.RamoRepository;
import com.dobemcontabilidade.service.RamoQueryService;
import com.dobemcontabilidade.service.RamoService;
import com.dobemcontabilidade.service.criteria.RamoCriteria;
import com.dobemcontabilidade.service.dto.RamoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Ramo}.
 */
@RestController
@RequestMapping("/api/ramos")
public class RamoResource {

    private static final Logger log = LoggerFactory.getLogger(RamoResource.class);

    private static final String ENTITY_NAME = "ramo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RamoService ramoService;

    private final RamoRepository ramoRepository;

    private final RamoQueryService ramoQueryService;

    public RamoResource(RamoService ramoService, RamoRepository ramoRepository, RamoQueryService ramoQueryService) {
        this.ramoService = ramoService;
        this.ramoRepository = ramoRepository;
        this.ramoQueryService = ramoQueryService;
    }

    /**
     * {@code POST  /ramos} : Create a new ramo.
     *
     * @param ramoDTO the ramoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ramoDTO, or with status {@code 400 (Bad Request)} if the ramo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RamoDTO> createRamo(@RequestBody RamoDTO ramoDTO) throws URISyntaxException {
        log.debug("REST request to save Ramo : {}", ramoDTO);
        if (ramoDTO.getId() != null) {
            throw new BadRequestAlertException("A new ramo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ramoDTO = ramoService.save(ramoDTO);
        return ResponseEntity.created(new URI("/api/ramos/" + ramoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, ramoDTO.getId().toString()))
            .body(ramoDTO);
    }

    /**
     * {@code PUT  /ramos/:id} : Updates an existing ramo.
     *
     * @param id the id of the ramoDTO to save.
     * @param ramoDTO the ramoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramoDTO,
     * or with status {@code 400 (Bad Request)} if the ramoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ramoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RamoDTO> updateRamo(@PathVariable(value = "id", required = false) final Long id, @RequestBody RamoDTO ramoDTO)
        throws URISyntaxException {
        log.debug("REST request to update Ramo : {}, {}", id, ramoDTO);
        if (ramoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ramoDTO = ramoService.update(ramoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramoDTO.getId().toString()))
            .body(ramoDTO);
    }

    /**
     * {@code PATCH  /ramos/:id} : Partial updates given fields of an existing ramo, field will ignore if it is null
     *
     * @param id the id of the ramoDTO to save.
     * @param ramoDTO the ramoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ramoDTO,
     * or with status {@code 400 (Bad Request)} if the ramoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ramoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ramoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RamoDTO> partialUpdateRamo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RamoDTO ramoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ramo partially : {}, {}", id, ramoDTO);
        if (ramoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ramoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RamoDTO> result = ramoService.partialUpdate(ramoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ramoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ramos} : get all the ramos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ramos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RamoDTO>> getAllRamos(
        RamoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Ramos by criteria: {}", criteria);

        Page<RamoDTO> page = ramoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ramos/count} : count all the ramos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countRamos(RamoCriteria criteria) {
        log.debug("REST request to count Ramos by criteria: {}", criteria);
        return ResponseEntity.ok().body(ramoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ramos/:id} : get the "id" ramo.
     *
     * @param id the id of the ramoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ramoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RamoDTO> getRamo(@PathVariable("id") Long id) {
        log.debug("REST request to get Ramo : {}", id);
        Optional<RamoDTO> ramoDTO = ramoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ramoDTO);
    }

    /**
     * {@code DELETE  /ramos/:id} : delete the "id" ramo.
     *
     * @param id the id of the ramoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRamo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Ramo : {}", id);
        ramoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
