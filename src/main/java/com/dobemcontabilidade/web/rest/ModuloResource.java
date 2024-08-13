package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Modulo;
import com.dobemcontabilidade.repository.ModuloRepository;
import com.dobemcontabilidade.service.ModuloQueryService;
import com.dobemcontabilidade.service.ModuloService;
import com.dobemcontabilidade.service.criteria.ModuloCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Modulo}.
 */
@RestController
@RequestMapping("/api/modulos")
public class ModuloResource {

    private static final Logger log = LoggerFactory.getLogger(ModuloResource.class);

    private static final String ENTITY_NAME = "modulo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuloService moduloService;

    private final ModuloRepository moduloRepository;

    private final ModuloQueryService moduloQueryService;

    public ModuloResource(ModuloService moduloService, ModuloRepository moduloRepository, ModuloQueryService moduloQueryService) {
        this.moduloService = moduloService;
        this.moduloRepository = moduloRepository;
        this.moduloQueryService = moduloQueryService;
    }

    /**
     * {@code POST  /modulos} : Create a new modulo.
     *
     * @param modulo the modulo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modulo, or with status {@code 400 (Bad Request)} if the modulo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Modulo> createModulo(@Valid @RequestBody Modulo modulo) throws URISyntaxException {
        log.debug("REST request to save Modulo : {}", modulo);
        if (modulo.getId() != null) {
            throw new BadRequestAlertException("A new modulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        modulo = moduloService.save(modulo);
        return ResponseEntity.created(new URI("/api/modulos/" + modulo.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, modulo.getId().toString()))
            .body(modulo);
    }

    /**
     * {@code PUT  /modulos/:id} : Updates an existing modulo.
     *
     * @param id the id of the modulo to save.
     * @param modulo the modulo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulo,
     * or with status {@code 400 (Bad Request)} if the modulo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modulo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Modulo> updateModulo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Modulo modulo
    ) throws URISyntaxException {
        log.debug("REST request to update Modulo : {}, {}", id, modulo);
        if (modulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modulo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        modulo = moduloService.update(modulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulo.getId().toString()))
            .body(modulo);
    }

    /**
     * {@code PATCH  /modulos/:id} : Partial updates given fields of an existing modulo, field will ignore if it is null
     *
     * @param id the id of the modulo to save.
     * @param modulo the modulo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modulo,
     * or with status {@code 400 (Bad Request)} if the modulo is not valid,
     * or with status {@code 404 (Not Found)} if the modulo is not found,
     * or with status {@code 500 (Internal Server Error)} if the modulo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Modulo> partialUpdateModulo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Modulo modulo
    ) throws URISyntaxException {
        log.debug("REST request to partial update Modulo partially : {}, {}", id, modulo);
        if (modulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modulo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduloRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Modulo> result = moduloService.partialUpdate(modulo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modulo.getId().toString())
        );
    }

    /**
     * {@code GET  /modulos} : get all the modulos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modulos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Modulo>> getAllModulos(
        ModuloCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Modulos by criteria: {}", criteria);

        Page<Modulo> page = moduloQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modulos/count} : count all the modulos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countModulos(ModuloCriteria criteria) {
        log.debug("REST request to count Modulos by criteria: {}", criteria);
        return ResponseEntity.ok().body(moduloQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /modulos/:id} : get the "id" modulo.
     *
     * @param id the id of the modulo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modulo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Modulo> getModulo(@PathVariable("id") Long id) {
        log.debug("REST request to get Modulo : {}", id);
        Optional<Modulo> modulo = moduloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modulo);
    }

    /**
     * {@code DELETE  /modulos/:id} : delete the "id" modulo.
     *
     * @param id the id of the modulo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModulo(@PathVariable("id") Long id) {
        log.debug("REST request to delete Modulo : {}", id);
        moduloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
