package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.UsuarioEmpresaRepository;
import com.dobemcontabilidade.service.UsuarioEmpresaQueryService;
import com.dobemcontabilidade.service.UsuarioEmpresaService;
import com.dobemcontabilidade.service.criteria.UsuarioEmpresaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.UsuarioEmpresa}.
 */
@RestController
@RequestMapping("/api/usuario-empresas")
public class UsuarioEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEmpresaResource.class);

    private static final String ENTITY_NAME = "usuarioEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioEmpresaService usuarioEmpresaService;

    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    private final UsuarioEmpresaQueryService usuarioEmpresaQueryService;

    public UsuarioEmpresaResource(
        UsuarioEmpresaService usuarioEmpresaService,
        UsuarioEmpresaRepository usuarioEmpresaRepository,
        UsuarioEmpresaQueryService usuarioEmpresaQueryService
    ) {
        this.usuarioEmpresaService = usuarioEmpresaService;
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
        this.usuarioEmpresaQueryService = usuarioEmpresaQueryService;
    }

    /**
     * {@code POST  /usuario-empresas} : Create a new usuarioEmpresa.
     *
     * @param usuarioEmpresa the usuarioEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioEmpresa, or with status {@code 400 (Bad Request)} if the usuarioEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UsuarioEmpresa> createUsuarioEmpresa(@Valid @RequestBody UsuarioEmpresa usuarioEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save UsuarioEmpresa : {}", usuarioEmpresa);
        if (usuarioEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new usuarioEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        usuarioEmpresa = usuarioEmpresaService.save(usuarioEmpresa);
        return ResponseEntity.created(new URI("/api/usuario-empresas/" + usuarioEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, usuarioEmpresa.getId().toString()))
            .body(usuarioEmpresa);
    }

    /**
     * {@code PUT  /usuario-empresas/:id} : Updates an existing usuarioEmpresa.
     *
     * @param id the id of the usuarioEmpresa to save.
     * @param usuarioEmpresa the usuarioEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioEmpresa,
     * or with status {@code 400 (Bad Request)} if the usuarioEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEmpresa> updateUsuarioEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioEmpresa usuarioEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioEmpresa : {}, {}", id, usuarioEmpresa);
        if (usuarioEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        usuarioEmpresa = usuarioEmpresaService.update(usuarioEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioEmpresa.getId().toString()))
            .body(usuarioEmpresa);
    }

    /**
     * {@code PATCH  /usuario-empresas/:id} : Partial updates given fields of an existing usuarioEmpresa, field will ignore if it is null
     *
     * @param id the id of the usuarioEmpresa to save.
     * @param usuarioEmpresa the usuarioEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioEmpresa,
     * or with status {@code 400 (Bad Request)} if the usuarioEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsuarioEmpresa> partialUpdateUsuarioEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioEmpresa usuarioEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsuarioEmpresa partially : {}, {}", id, usuarioEmpresa);
        if (usuarioEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioEmpresa> result = usuarioEmpresaService.partialUpdate(usuarioEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /usuario-empresas} : get all the usuarioEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UsuarioEmpresa>> getAllUsuarioEmpresas(
        UsuarioEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UsuarioEmpresas by criteria: {}", criteria);

        Page<UsuarioEmpresa> page = usuarioEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuario-empresas/count} : count all the usuarioEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUsuarioEmpresas(UsuarioEmpresaCriteria criteria) {
        log.debug("REST request to count UsuarioEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(usuarioEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /usuario-empresas/:id} : get the "id" usuarioEmpresa.
     *
     * @param id the id of the usuarioEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEmpresa> getUsuarioEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get UsuarioEmpresa : {}", id);
        Optional<UsuarioEmpresa> usuarioEmpresa = usuarioEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioEmpresa);
    }

    /**
     * {@code DELETE  /usuario-empresas/:id} : delete the "id" usuarioEmpresa.
     *
     * @param id the id of the usuarioEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete UsuarioEmpresa : {}", id);
        usuarioEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
