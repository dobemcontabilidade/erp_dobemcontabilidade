package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PerfilAcesso;
import com.dobemcontabilidade.repository.PerfilAcessoRepository;
import com.dobemcontabilidade.service.PerfilAcessoQueryService;
import com.dobemcontabilidade.service.PerfilAcessoService;
import com.dobemcontabilidade.service.criteria.PerfilAcessoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PerfilAcesso}.
 */
@RestController
@RequestMapping("/api/perfil-acessos")
public class PerfilAcessoResource {

    private static final Logger log = LoggerFactory.getLogger(PerfilAcessoResource.class);

    private static final String ENTITY_NAME = "perfilAcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilAcessoService perfilAcessoService;

    private final PerfilAcessoRepository perfilAcessoRepository;

    private final PerfilAcessoQueryService perfilAcessoQueryService;

    public PerfilAcessoResource(
        PerfilAcessoService perfilAcessoService,
        PerfilAcessoRepository perfilAcessoRepository,
        PerfilAcessoQueryService perfilAcessoQueryService
    ) {
        this.perfilAcessoService = perfilAcessoService;
        this.perfilAcessoRepository = perfilAcessoRepository;
        this.perfilAcessoQueryService = perfilAcessoQueryService;
    }

    /**
     * {@code POST  /perfil-acessos} : Create a new perfilAcesso.
     *
     * @param perfilAcesso the perfilAcesso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilAcesso, or with status {@code 400 (Bad Request)} if the perfilAcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerfilAcesso> createPerfilAcesso(@RequestBody PerfilAcesso perfilAcesso) throws URISyntaxException {
        log.debug("REST request to save PerfilAcesso : {}", perfilAcesso);
        if (perfilAcesso.getId() != null) {
            throw new BadRequestAlertException("A new perfilAcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        perfilAcesso = perfilAcessoService.save(perfilAcesso);
        return ResponseEntity.created(new URI("/api/perfil-acessos/" + perfilAcesso.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, perfilAcesso.getId().toString()))
            .body(perfilAcesso);
    }

    /**
     * {@code PUT  /perfil-acessos/:id} : Updates an existing perfilAcesso.
     *
     * @param id the id of the perfilAcesso to save.
     * @param perfilAcesso the perfilAcesso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilAcesso,
     * or with status {@code 400 (Bad Request)} if the perfilAcesso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilAcesso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerfilAcesso> updatePerfilAcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerfilAcesso perfilAcesso
    ) throws URISyntaxException {
        log.debug("REST request to update PerfilAcesso : {}, {}", id, perfilAcesso);
        if (perfilAcesso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilAcesso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilAcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        perfilAcesso = perfilAcessoService.update(perfilAcesso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilAcesso.getId().toString()))
            .body(perfilAcesso);
    }

    /**
     * {@code PATCH  /perfil-acessos/:id} : Partial updates given fields of an existing perfilAcesso, field will ignore if it is null
     *
     * @param id the id of the perfilAcesso to save.
     * @param perfilAcesso the perfilAcesso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilAcesso,
     * or with status {@code 400 (Bad Request)} if the perfilAcesso is not valid,
     * or with status {@code 404 (Not Found)} if the perfilAcesso is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfilAcesso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerfilAcesso> partialUpdatePerfilAcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerfilAcesso perfilAcesso
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerfilAcesso partially : {}, {}", id, perfilAcesso);
        if (perfilAcesso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilAcesso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilAcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerfilAcesso> result = perfilAcessoService.partialUpdate(perfilAcesso);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilAcesso.getId().toString())
        );
    }

    /**
     * {@code GET  /perfil-acessos} : get all the perfilAcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilAcessos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PerfilAcesso>> getAllPerfilAcessos(
        PerfilAcessoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerfilAcessos by criteria: {}", criteria);

        Page<PerfilAcesso> page = perfilAcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfil-acessos/count} : count all the perfilAcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPerfilAcessos(PerfilAcessoCriteria criteria) {
        log.debug("REST request to count PerfilAcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(perfilAcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /perfil-acessos/:id} : get the "id" perfilAcesso.
     *
     * @param id the id of the perfilAcesso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilAcesso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilAcesso> getPerfilAcesso(@PathVariable("id") Long id) {
        log.debug("REST request to get PerfilAcesso : {}", id);
        Optional<PerfilAcesso> perfilAcesso = perfilAcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilAcesso);
    }

    /**
     * {@code DELETE  /perfil-acessos/:id} : delete the "id" perfilAcesso.
     *
     * @param id the id of the perfilAcesso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfilAcesso(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerfilAcesso : {}", id);
        perfilAcessoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
