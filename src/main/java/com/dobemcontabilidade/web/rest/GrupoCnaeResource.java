package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.GrupoCnaeRepository;
import com.dobemcontabilidade.service.GrupoCnaeQueryService;
import com.dobemcontabilidade.service.GrupoCnaeService;
import com.dobemcontabilidade.service.criteria.GrupoCnaeCriteria;
import com.dobemcontabilidade.service.dto.GrupoCnaeDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.GrupoCnae}.
 */
@RestController
@RequestMapping("/api/grupo-cnaes")
public class GrupoCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(GrupoCnaeResource.class);

    private static final String ENTITY_NAME = "grupoCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoCnaeService grupoCnaeService;

    private final GrupoCnaeRepository grupoCnaeRepository;

    private final GrupoCnaeQueryService grupoCnaeQueryService;

    public GrupoCnaeResource(
        GrupoCnaeService grupoCnaeService,
        GrupoCnaeRepository grupoCnaeRepository,
        GrupoCnaeQueryService grupoCnaeQueryService
    ) {
        this.grupoCnaeService = grupoCnaeService;
        this.grupoCnaeRepository = grupoCnaeRepository;
        this.grupoCnaeQueryService = grupoCnaeQueryService;
    }

    /**
     * {@code POST  /grupo-cnaes} : Create a new grupoCnae.
     *
     * @param grupoCnaeDTO the grupoCnaeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoCnaeDTO, or with status {@code 400 (Bad Request)} if the grupoCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GrupoCnaeDTO> createGrupoCnae(@Valid @RequestBody GrupoCnaeDTO grupoCnaeDTO) throws URISyntaxException {
        log.debug("REST request to save GrupoCnae : {}", grupoCnaeDTO);
        if (grupoCnaeDTO.getId() != null) {
            throw new BadRequestAlertException("A new grupoCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        grupoCnaeDTO = grupoCnaeService.save(grupoCnaeDTO);
        return ResponseEntity.created(new URI("/api/grupo-cnaes/" + grupoCnaeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, grupoCnaeDTO.getId().toString()))
            .body(grupoCnaeDTO);
    }

    /**
     * {@code PUT  /grupo-cnaes/:id} : Updates an existing grupoCnae.
     *
     * @param id the id of the grupoCnaeDTO to save.
     * @param grupoCnaeDTO the grupoCnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoCnaeDTO,
     * or with status {@code 400 (Bad Request)} if the grupoCnaeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoCnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GrupoCnaeDTO> updateGrupoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GrupoCnaeDTO grupoCnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GrupoCnae : {}, {}", id, grupoCnaeDTO);
        if (grupoCnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoCnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        grupoCnaeDTO = grupoCnaeService.update(grupoCnaeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoCnaeDTO.getId().toString()))
            .body(grupoCnaeDTO);
    }

    /**
     * {@code PATCH  /grupo-cnaes/:id} : Partial updates given fields of an existing grupoCnae, field will ignore if it is null
     *
     * @param id the id of the grupoCnaeDTO to save.
     * @param grupoCnaeDTO the grupoCnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoCnaeDTO,
     * or with status {@code 400 (Bad Request)} if the grupoCnaeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the grupoCnaeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the grupoCnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GrupoCnaeDTO> partialUpdateGrupoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GrupoCnaeDTO grupoCnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GrupoCnae partially : {}, {}", id, grupoCnaeDTO);
        if (grupoCnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, grupoCnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!grupoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GrupoCnaeDTO> result = grupoCnaeService.partialUpdate(grupoCnaeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoCnaeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /grupo-cnaes} : get all the grupoCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GrupoCnaeDTO>> getAllGrupoCnaes(
        GrupoCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get GrupoCnaes by criteria: {}", criteria);

        Page<GrupoCnaeDTO> page = grupoCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /grupo-cnaes/count} : count all the grupoCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countGrupoCnaes(GrupoCnaeCriteria criteria) {
        log.debug("REST request to count GrupoCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(grupoCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /grupo-cnaes/:id} : get the "id" grupoCnae.
     *
     * @param id the id of the grupoCnaeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoCnaeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GrupoCnaeDTO> getGrupoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get GrupoCnae : {}", id);
        Optional<GrupoCnaeDTO> grupoCnaeDTO = grupoCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoCnaeDTO);
    }

    /**
     * {@code DELETE  /grupo-cnaes/:id} : delete the "id" grupoCnae.
     *
     * @param id the id of the grupoCnaeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete GrupoCnae : {}", id);
        grupoCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
