package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.CnaeRepository;
import com.dobemcontabilidade.service.CnaeService;
import com.dobemcontabilidade.service.dto.CnaeDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Cnae}.
 */
@RestController
@RequestMapping("/api/cnaes")
public class CnaeResource {

    private static final Logger log = LoggerFactory.getLogger(CnaeResource.class);

    private static final String ENTITY_NAME = "cnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CnaeService cnaeService;

    private final CnaeRepository cnaeRepository;

    public CnaeResource(CnaeService cnaeService, CnaeRepository cnaeRepository) {
        this.cnaeService = cnaeService;
        this.cnaeRepository = cnaeRepository;
    }

    /**
     * {@code POST  /cnaes} : Create a new cnae.
     *
     * @param cnaeDTO the cnaeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cnaeDTO, or with status {@code 400 (Bad Request)} if the cnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CnaeDTO> createCnae(@Valid @RequestBody CnaeDTO cnaeDTO) throws URISyntaxException {
        log.debug("REST request to save Cnae : {}", cnaeDTO);
        if (cnaeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cnaeDTO = cnaeService.save(cnaeDTO);
        return ResponseEntity.created(new URI("/api/cnaes/" + cnaeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cnaeDTO.getId().toString()))
            .body(cnaeDTO);
    }

    /**
     * {@code PUT  /cnaes/:id} : Updates an existing cnae.
     *
     * @param id the id of the cnaeDTO to save.
     * @param cnaeDTO the cnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cnaeDTO,
     * or with status {@code 400 (Bad Request)} if the cnaeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CnaeDTO> updateCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CnaeDTO cnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cnae : {}, {}", id, cnaeDTO);
        if (cnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cnaeDTO = cnaeService.update(cnaeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cnaeDTO.getId().toString()))
            .body(cnaeDTO);
    }

    /**
     * {@code PATCH  /cnaes/:id} : Partial updates given fields of an existing cnae, field will ignore if it is null
     *
     * @param id the id of the cnaeDTO to save.
     * @param cnaeDTO the cnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cnaeDTO,
     * or with status {@code 400 (Bad Request)} if the cnaeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cnaeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CnaeDTO> partialUpdateCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CnaeDTO cnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cnae partially : {}, {}", id, cnaeDTO);
        if (cnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CnaeDTO> result = cnaeService.partialUpdate(cnaeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cnaeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cnaes} : get all the cnaes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cnaes in body.
     */
    @GetMapping("")
    public List<CnaeDTO> getAllCnaes() {
        log.debug("REST request to get all Cnaes");
        return cnaeService.findAll();
    }

    /**
     * {@code GET  /cnaes/:id} : get the "id" cnae.
     *
     * @param id the id of the cnaeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cnaeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CnaeDTO> getCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get Cnae : {}", id);
        Optional<CnaeDTO> cnaeDTO = cnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cnaeDTO);
    }

    /**
     * {@code DELETE  /cnaes/:id} : delete the "id" cnae.
     *
     * @param id the id of the cnaeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cnae : {}", id);
        cnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
