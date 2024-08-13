package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.UsuarioGestao;
import com.dobemcontabilidade.repository.UsuarioGestaoRepository;
import com.dobemcontabilidade.service.UsuarioGestaoQueryService;
import com.dobemcontabilidade.service.UsuarioGestaoService;
import com.dobemcontabilidade.service.criteria.UsuarioGestaoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.UsuarioGestao}.
 */
@RestController
@RequestMapping("/api/usuario-gestaos")
public class UsuarioGestaoResource {

    private static final Logger log = LoggerFactory.getLogger(UsuarioGestaoResource.class);

    private static final String ENTITY_NAME = "usuarioGestao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioGestaoService usuarioGestaoService;

    private final UsuarioGestaoRepository usuarioGestaoRepository;

    private final UsuarioGestaoQueryService usuarioGestaoQueryService;

    public UsuarioGestaoResource(
        UsuarioGestaoService usuarioGestaoService,
        UsuarioGestaoRepository usuarioGestaoRepository,
        UsuarioGestaoQueryService usuarioGestaoQueryService
    ) {
        this.usuarioGestaoService = usuarioGestaoService;
        this.usuarioGestaoRepository = usuarioGestaoRepository;
        this.usuarioGestaoQueryService = usuarioGestaoQueryService;
    }

    /**
     * {@code POST  /usuario-gestaos} : Create a new usuarioGestao.
     *
     * @param usuarioGestao the usuarioGestao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioGestao, or with status {@code 400 (Bad Request)} if the usuarioGestao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UsuarioGestao> createUsuarioGestao(@Valid @RequestBody UsuarioGestao usuarioGestao) throws URISyntaxException {
        log.debug("REST request to save UsuarioGestao : {}", usuarioGestao);
        if (usuarioGestao.getId() != null) {
            throw new BadRequestAlertException("A new usuarioGestao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        usuarioGestao = usuarioGestaoService.save(usuarioGestao);
        return ResponseEntity.created(new URI("/api/usuario-gestaos/" + usuarioGestao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, usuarioGestao.getId().toString()))
            .body(usuarioGestao);
    }

    /**
     * {@code PUT  /usuario-gestaos/:id} : Updates an existing usuarioGestao.
     *
     * @param id the id of the usuarioGestao to save.
     * @param usuarioGestao the usuarioGestao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioGestao,
     * or with status {@code 400 (Bad Request)} if the usuarioGestao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioGestao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGestao> updateUsuarioGestao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioGestao usuarioGestao
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioGestao : {}, {}", id, usuarioGestao);
        if (usuarioGestao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioGestao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioGestaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        usuarioGestao = usuarioGestaoService.update(usuarioGestao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioGestao.getId().toString()))
            .body(usuarioGestao);
    }

    /**
     * {@code PATCH  /usuario-gestaos/:id} : Partial updates given fields of an existing usuarioGestao, field will ignore if it is null
     *
     * @param id the id of the usuarioGestao to save.
     * @param usuarioGestao the usuarioGestao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioGestao,
     * or with status {@code 400 (Bad Request)} if the usuarioGestao is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioGestao is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioGestao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsuarioGestao> partialUpdateUsuarioGestao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioGestao usuarioGestao
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsuarioGestao partially : {}, {}", id, usuarioGestao);
        if (usuarioGestao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioGestao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioGestaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioGestao> result = usuarioGestaoService.partialUpdate(usuarioGestao);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioGestao.getId().toString())
        );
    }

    /**
     * {@code GET  /usuario-gestaos} : get all the usuarioGestaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioGestaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UsuarioGestao>> getAllUsuarioGestaos(
        UsuarioGestaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UsuarioGestaos by criteria: {}", criteria);

        Page<UsuarioGestao> page = usuarioGestaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuario-gestaos/count} : count all the usuarioGestaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUsuarioGestaos(UsuarioGestaoCriteria criteria) {
        log.debug("REST request to count UsuarioGestaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(usuarioGestaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /usuario-gestaos/:id} : get the "id" usuarioGestao.
     *
     * @param id the id of the usuarioGestao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioGestao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGestao> getUsuarioGestao(@PathVariable("id") Long id) {
        log.debug("REST request to get UsuarioGestao : {}", id);
        Optional<UsuarioGestao> usuarioGestao = usuarioGestaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioGestao);
    }

    /**
     * {@code DELETE  /usuario-gestaos/:id} : delete the "id" usuarioGestao.
     *
     * @param id the id of the usuarioGestao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioGestao(@PathVariable("id") Long id) {
        log.debug("REST request to delete UsuarioGestao : {}", id);
        usuarioGestaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
