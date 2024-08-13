package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Denuncia;
import com.dobemcontabilidade.repository.DenunciaRepository;
import com.dobemcontabilidade.service.DenunciaQueryService;
import com.dobemcontabilidade.service.DenunciaService;
import com.dobemcontabilidade.service.criteria.DenunciaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Denuncia}.
 */
@RestController
@RequestMapping("/api/denuncias")
public class DenunciaResource {

    private static final Logger log = LoggerFactory.getLogger(DenunciaResource.class);

    private static final String ENTITY_NAME = "denuncia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DenunciaService denunciaService;

    private final DenunciaRepository denunciaRepository;

    private final DenunciaQueryService denunciaQueryService;

    public DenunciaResource(
        DenunciaService denunciaService,
        DenunciaRepository denunciaRepository,
        DenunciaQueryService denunciaQueryService
    ) {
        this.denunciaService = denunciaService;
        this.denunciaRepository = denunciaRepository;
        this.denunciaQueryService = denunciaQueryService;
    }

    /**
     * {@code POST  /denuncias} : Create a new denuncia.
     *
     * @param denuncia the denuncia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new denuncia, or with status {@code 400 (Bad Request)} if the denuncia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Denuncia> createDenuncia(@Valid @RequestBody Denuncia denuncia) throws URISyntaxException {
        log.debug("REST request to save Denuncia : {}", denuncia);
        if (denuncia.getId() != null) {
            throw new BadRequestAlertException("A new denuncia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        denuncia = denunciaService.save(denuncia);
        return ResponseEntity.created(new URI("/api/denuncias/" + denuncia.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, denuncia.getId().toString()))
            .body(denuncia);
    }

    /**
     * {@code PUT  /denuncias/:id} : Updates an existing denuncia.
     *
     * @param id the id of the denuncia to save.
     * @param denuncia the denuncia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated denuncia,
     * or with status {@code 400 (Bad Request)} if the denuncia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the denuncia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Denuncia> updateDenuncia(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Denuncia denuncia
    ) throws URISyntaxException {
        log.debug("REST request to update Denuncia : {}, {}", id, denuncia);
        if (denuncia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, denuncia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!denunciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        denuncia = denunciaService.update(denuncia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, denuncia.getId().toString()))
            .body(denuncia);
    }

    /**
     * {@code PATCH  /denuncias/:id} : Partial updates given fields of an existing denuncia, field will ignore if it is null
     *
     * @param id the id of the denuncia to save.
     * @param denuncia the denuncia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated denuncia,
     * or with status {@code 400 (Bad Request)} if the denuncia is not valid,
     * or with status {@code 404 (Not Found)} if the denuncia is not found,
     * or with status {@code 500 (Internal Server Error)} if the denuncia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Denuncia> partialUpdateDenuncia(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Denuncia denuncia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Denuncia partially : {}, {}", id, denuncia);
        if (denuncia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, denuncia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!denunciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Denuncia> result = denunciaService.partialUpdate(denuncia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, denuncia.getId().toString())
        );
    }

    /**
     * {@code GET  /denuncias} : get all the denuncias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of denuncias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Denuncia>> getAllDenuncias(
        DenunciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Denuncias by criteria: {}", criteria);

        Page<Denuncia> page = denunciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /denuncias/count} : count all the denuncias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDenuncias(DenunciaCriteria criteria) {
        log.debug("REST request to count Denuncias by criteria: {}", criteria);
        return ResponseEntity.ok().body(denunciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /denuncias/:id} : get the "id" denuncia.
     *
     * @param id the id of the denuncia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the denuncia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Denuncia> getDenuncia(@PathVariable("id") Long id) {
        log.debug("REST request to get Denuncia : {}", id);
        Optional<Denuncia> denuncia = denunciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(denuncia);
    }

    /**
     * {@code DELETE  /denuncias/:id} : delete the "id" denuncia.
     *
     * @param id the id of the denuncia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDenuncia(@PathVariable("id") Long id) {
        log.debug("REST request to delete Denuncia : {}", id);
        denunciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
