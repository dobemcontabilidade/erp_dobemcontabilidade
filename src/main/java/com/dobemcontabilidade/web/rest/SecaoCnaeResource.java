package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.SecaoCnaeRepository;
import com.dobemcontabilidade.service.SecaoCnaeQueryService;
import com.dobemcontabilidade.service.SecaoCnaeService;
import com.dobemcontabilidade.service.criteria.SecaoCnaeCriteria;
import com.dobemcontabilidade.service.dto.SecaoCnaeDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.SecaoCnae}.
 */
@RestController
@RequestMapping("/api/secao-cnaes")
public class SecaoCnaeResource {

    private static final Logger log = LoggerFactory.getLogger(SecaoCnaeResource.class);

    private static final String ENTITY_NAME = "secaoCnae";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SecaoCnaeService secaoCnaeService;

    private final SecaoCnaeRepository secaoCnaeRepository;

    private final SecaoCnaeQueryService secaoCnaeQueryService;

    public SecaoCnaeResource(
        SecaoCnaeService secaoCnaeService,
        SecaoCnaeRepository secaoCnaeRepository,
        SecaoCnaeQueryService secaoCnaeQueryService
    ) {
        this.secaoCnaeService = secaoCnaeService;
        this.secaoCnaeRepository = secaoCnaeRepository;
        this.secaoCnaeQueryService = secaoCnaeQueryService;
    }

    /**
     * {@code POST  /secao-cnaes} : Create a new secaoCnae.
     *
     * @param secaoCnaeDTO the secaoCnaeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new secaoCnaeDTO, or with status {@code 400 (Bad Request)} if the secaoCnae has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SecaoCnaeDTO> createSecaoCnae(@Valid @RequestBody SecaoCnaeDTO secaoCnaeDTO) throws URISyntaxException {
        log.debug("REST request to save SecaoCnae : {}", secaoCnaeDTO);
        if (secaoCnaeDTO.getId() != null) {
            throw new BadRequestAlertException("A new secaoCnae cannot already have an ID", ENTITY_NAME, "idexists");
        }
        secaoCnaeDTO = secaoCnaeService.save(secaoCnaeDTO);
        return ResponseEntity.created(new URI("/api/secao-cnaes/" + secaoCnaeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, secaoCnaeDTO.getId().toString()))
            .body(secaoCnaeDTO);
    }

    /**
     * {@code PUT  /secao-cnaes/:id} : Updates an existing secaoCnae.
     *
     * @param id the id of the secaoCnaeDTO to save.
     * @param secaoCnaeDTO the secaoCnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secaoCnaeDTO,
     * or with status {@code 400 (Bad Request)} if the secaoCnaeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the secaoCnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SecaoCnaeDTO> updateSecaoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SecaoCnaeDTO secaoCnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SecaoCnae : {}, {}", id, secaoCnaeDTO);
        if (secaoCnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secaoCnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secaoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        secaoCnaeDTO = secaoCnaeService.update(secaoCnaeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secaoCnaeDTO.getId().toString()))
            .body(secaoCnaeDTO);
    }

    /**
     * {@code PATCH  /secao-cnaes/:id} : Partial updates given fields of an existing secaoCnae, field will ignore if it is null
     *
     * @param id the id of the secaoCnaeDTO to save.
     * @param secaoCnaeDTO the secaoCnaeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated secaoCnaeDTO,
     * or with status {@code 400 (Bad Request)} if the secaoCnaeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the secaoCnaeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the secaoCnaeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SecaoCnaeDTO> partialUpdateSecaoCnae(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SecaoCnaeDTO secaoCnaeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SecaoCnae partially : {}, {}", id, secaoCnaeDTO);
        if (secaoCnaeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, secaoCnaeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!secaoCnaeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SecaoCnaeDTO> result = secaoCnaeService.partialUpdate(secaoCnaeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, secaoCnaeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /secao-cnaes} : get all the secaoCnaes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of secaoCnaes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SecaoCnaeDTO>> getAllSecaoCnaes(
        SecaoCnaeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get SecaoCnaes by criteria: {}", criteria);

        Page<SecaoCnaeDTO> page = secaoCnaeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /secao-cnaes/count} : count all the secaoCnaes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSecaoCnaes(SecaoCnaeCriteria criteria) {
        log.debug("REST request to count SecaoCnaes by criteria: {}", criteria);
        return ResponseEntity.ok().body(secaoCnaeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /secao-cnaes/:id} : get the "id" secaoCnae.
     *
     * @param id the id of the secaoCnaeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the secaoCnaeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SecaoCnaeDTO> getSecaoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to get SecaoCnae : {}", id);
        Optional<SecaoCnaeDTO> secaoCnaeDTO = secaoCnaeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(secaoCnaeDTO);
    }

    /**
     * {@code DELETE  /secao-cnaes/:id} : delete the "id" secaoCnae.
     *
     * @param id the id of the secaoCnaeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSecaoCnae(@PathVariable("id") Long id) {
        log.debug("REST request to delete SecaoCnae : {}", id);
        secaoCnaeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
