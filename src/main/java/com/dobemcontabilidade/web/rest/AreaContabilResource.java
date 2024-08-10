package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AreaContabilRepository;
import com.dobemcontabilidade.service.AreaContabilService;
import com.dobemcontabilidade.service.dto.AreaContabilDTO;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.AreaContabil}.
 */
@RestController
@RequestMapping("/api/area-contabils")
public class AreaContabilResource {

    private static final Logger log = LoggerFactory.getLogger(AreaContabilResource.class);

    private static final String ENTITY_NAME = "areaContabil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AreaContabilService areaContabilService;

    private final AreaContabilRepository areaContabilRepository;

    public AreaContabilResource(AreaContabilService areaContabilService, AreaContabilRepository areaContabilRepository) {
        this.areaContabilService = areaContabilService;
        this.areaContabilRepository = areaContabilRepository;
    }

    /**
     * {@code POST  /area-contabils} : Create a new areaContabil.
     *
     * @param areaContabilDTO the areaContabilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new areaContabilDTO, or with status {@code 400 (Bad Request)} if the areaContabil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AreaContabilDTO> createAreaContabil(@RequestBody AreaContabilDTO areaContabilDTO) throws URISyntaxException {
        log.debug("REST request to save AreaContabil : {}", areaContabilDTO);
        if (areaContabilDTO.getId() != null) {
            throw new BadRequestAlertException("A new areaContabil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        areaContabilDTO = areaContabilService.save(areaContabilDTO);
        return ResponseEntity.created(new URI("/api/area-contabils/" + areaContabilDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, areaContabilDTO.getId().toString()))
            .body(areaContabilDTO);
    }

    /**
     * {@code PUT  /area-contabils/:id} : Updates an existing areaContabil.
     *
     * @param id the id of the areaContabilDTO to save.
     * @param areaContabilDTO the areaContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilDTO,
     * or with status {@code 400 (Bad Request)} if the areaContabilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AreaContabilDTO> updateAreaContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AreaContabilDTO areaContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AreaContabil : {}, {}", id, areaContabilDTO);
        if (areaContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        areaContabilDTO = areaContabilService.update(areaContabilDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilDTO.getId().toString()))
            .body(areaContabilDTO);
    }

    /**
     * {@code PATCH  /area-contabils/:id} : Partial updates given fields of an existing areaContabil, field will ignore if it is null
     *
     * @param id the id of the areaContabilDTO to save.
     * @param areaContabilDTO the areaContabilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated areaContabilDTO,
     * or with status {@code 400 (Bad Request)} if the areaContabilDTO is not valid,
     * or with status {@code 404 (Not Found)} if the areaContabilDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the areaContabilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AreaContabilDTO> partialUpdateAreaContabil(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AreaContabilDTO areaContabilDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AreaContabil partially : {}, {}", id, areaContabilDTO);
        if (areaContabilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, areaContabilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!areaContabilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AreaContabilDTO> result = areaContabilService.partialUpdate(areaContabilDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, areaContabilDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /area-contabils} : get all the areaContabils.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of areaContabils in body.
     */
    @GetMapping("")
    public List<AreaContabilDTO> getAllAreaContabils() {
        log.debug("REST request to get all AreaContabils");
        return areaContabilService.findAll();
    }

    /**
     * {@code GET  /area-contabils/:id} : get the "id" areaContabil.
     *
     * @param id the id of the areaContabilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the areaContabilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AreaContabilDTO> getAreaContabil(@PathVariable("id") Long id) {
        log.debug("REST request to get AreaContabil : {}", id);
        Optional<AreaContabilDTO> areaContabilDTO = areaContabilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(areaContabilDTO);
    }

    /**
     * {@code DELETE  /area-contabils/:id} : delete the "id" areaContabil.
     *
     * @param id the id of the areaContabilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAreaContabil(@PathVariable("id") Long id) {
        log.debug("REST request to delete AreaContabil : {}", id);
        areaContabilService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
