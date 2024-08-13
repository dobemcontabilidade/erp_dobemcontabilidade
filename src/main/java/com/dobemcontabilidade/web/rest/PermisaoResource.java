package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Permisao;
import com.dobemcontabilidade.repository.PermisaoRepository;
import com.dobemcontabilidade.service.PermisaoService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Permisao}.
 */
@RestController
@RequestMapping("/api/permisaos")
public class PermisaoResource {

    private static final Logger log = LoggerFactory.getLogger(PermisaoResource.class);

    private static final String ENTITY_NAME = "permisao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermisaoService permisaoService;

    private final PermisaoRepository permisaoRepository;

    public PermisaoResource(PermisaoService permisaoService, PermisaoRepository permisaoRepository) {
        this.permisaoService = permisaoService;
        this.permisaoRepository = permisaoRepository;
    }

    /**
     * {@code POST  /permisaos} : Create a new permisao.
     *
     * @param permisao the permisao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permisao, or with status {@code 400 (Bad Request)} if the permisao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Permisao> createPermisao(@RequestBody Permisao permisao) throws URISyntaxException {
        log.debug("REST request to save Permisao : {}", permisao);
        if (permisao.getId() != null) {
            throw new BadRequestAlertException("A new permisao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        permisao = permisaoService.save(permisao);
        return ResponseEntity.created(new URI("/api/permisaos/" + permisao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, permisao.getId().toString()))
            .body(permisao);
    }

    /**
     * {@code PUT  /permisaos/:id} : Updates an existing permisao.
     *
     * @param id the id of the permisao to save.
     * @param permisao the permisao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permisao,
     * or with status {@code 400 (Bad Request)} if the permisao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permisao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Permisao> updatePermisao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Permisao permisao
    ) throws URISyntaxException {
        log.debug("REST request to update Permisao : {}, {}", id, permisao);
        if (permisao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permisao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permisaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        permisao = permisaoService.update(permisao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permisao.getId().toString()))
            .body(permisao);
    }

    /**
     * {@code PATCH  /permisaos/:id} : Partial updates given fields of an existing permisao, field will ignore if it is null
     *
     * @param id the id of the permisao to save.
     * @param permisao the permisao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permisao,
     * or with status {@code 400 (Bad Request)} if the permisao is not valid,
     * or with status {@code 404 (Not Found)} if the permisao is not found,
     * or with status {@code 500 (Internal Server Error)} if the permisao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Permisao> partialUpdatePermisao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Permisao permisao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Permisao partially : {}, {}", id, permisao);
        if (permisao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, permisao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!permisaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Permisao> result = permisaoService.partialUpdate(permisao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permisao.getId().toString())
        );
    }

    /**
     * {@code GET  /permisaos} : get all the permisaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permisaos in body.
     */
    @GetMapping("")
    public List<Permisao> getAllPermisaos() {
        log.debug("REST request to get all Permisaos");
        return permisaoService.findAll();
    }

    /**
     * {@code GET  /permisaos/:id} : get the "id" permisao.
     *
     * @param id the id of the permisao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permisao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Permisao> getPermisao(@PathVariable("id") Long id) {
        log.debug("REST request to get Permisao : {}", id);
        Optional<Permisao> permisao = permisaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permisao);
    }

    /**
     * {@code DELETE  /permisaos/:id} : delete the "id" permisao.
     *
     * @param id the id of the permisao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermisao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Permisao : {}", id);
        permisaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
