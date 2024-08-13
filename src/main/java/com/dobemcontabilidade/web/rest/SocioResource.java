package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.repository.SocioRepository;
import com.dobemcontabilidade.service.SocioQueryService;
import com.dobemcontabilidade.service.SocioService;
import com.dobemcontabilidade.service.criteria.SocioCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Socio}.
 */
@RestController
@RequestMapping("/api/socios")
public class SocioResource {

    private static final Logger log = LoggerFactory.getLogger(SocioResource.class);

    private static final String ENTITY_NAME = "socio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocioService socioService;

    private final SocioRepository socioRepository;

    private final SocioQueryService socioQueryService;

    public SocioResource(SocioService socioService, SocioRepository socioRepository, SocioQueryService socioQueryService) {
        this.socioService = socioService;
        this.socioRepository = socioRepository;
        this.socioQueryService = socioQueryService;
    }

    /**
     * {@code POST  /socios} : Create a new socio.
     *
     * @param socio the socio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socio, or with status {@code 400 (Bad Request)} if the socio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Socio> createSocio(@Valid @RequestBody Socio socio) throws URISyntaxException {
        log.debug("REST request to save Socio : {}", socio);
        if (socio.getId() != null) {
            throw new BadRequestAlertException("A new socio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        socio = socioService.save(socio);
        return ResponseEntity.created(new URI("/api/socios/" + socio.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, socio.getId().toString()))
            .body(socio);
    }

    /**
     * {@code PUT  /socios/:id} : Updates an existing socio.
     *
     * @param id the id of the socio to save.
     * @param socio the socio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socio,
     * or with status {@code 400 (Bad Request)} if the socio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Socio> updateSocio(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Socio socio)
        throws URISyntaxException {
        log.debug("REST request to update Socio : {}, {}", id, socio);
        if (socio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        socio = socioService.update(socio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socio.getId().toString()))
            .body(socio);
    }

    /**
     * {@code PATCH  /socios/:id} : Partial updates given fields of an existing socio, field will ignore if it is null
     *
     * @param id the id of the socio to save.
     * @param socio the socio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socio,
     * or with status {@code 400 (Bad Request)} if the socio is not valid,
     * or with status {@code 404 (Not Found)} if the socio is not found,
     * or with status {@code 500 (Internal Server Error)} if the socio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Socio> partialUpdateSocio(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Socio socio
    ) throws URISyntaxException {
        log.debug("REST request to partial update Socio partially : {}, {}", id, socio);
        if (socio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socio.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Socio> result = socioService.partialUpdate(socio);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, socio.getId().toString())
        );
    }

    /**
     * {@code GET  /socios} : get all the socios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Socio>> getAllSocios(
        SocioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Socios by criteria: {}", criteria);

        Page<Socio> page = socioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /socios/count} : count all the socios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSocios(SocioCriteria criteria) {
        log.debug("REST request to count Socios by criteria: {}", criteria);
        return ResponseEntity.ok().body(socioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /socios/:id} : get the "id" socio.
     *
     * @param id the id of the socio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Socio> getSocio(@PathVariable("id") Long id) {
        log.debug("REST request to get Socio : {}", id);
        Optional<Socio> socio = socioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socio);
    }

    /**
     * {@code DELETE  /socios/:id} : delete the "id" socio.
     *
     * @param id the id of the socio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSocio(@PathVariable("id") Long id) {
        log.debug("REST request to delete Socio : {}", id);
        socioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
