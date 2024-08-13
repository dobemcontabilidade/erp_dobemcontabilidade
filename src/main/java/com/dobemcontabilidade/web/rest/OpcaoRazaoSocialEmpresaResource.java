package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import com.dobemcontabilidade.repository.OpcaoRazaoSocialEmpresaRepository;
import com.dobemcontabilidade.service.OpcaoRazaoSocialEmpresaQueryService;
import com.dobemcontabilidade.service.OpcaoRazaoSocialEmpresaService;
import com.dobemcontabilidade.service.criteria.OpcaoRazaoSocialEmpresaCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa}.
 */
@RestController
@RequestMapping("/api/opcao-razao-social-empresas")
public class OpcaoRazaoSocialEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(OpcaoRazaoSocialEmpresaResource.class);

    private static final String ENTITY_NAME = "opcaoRazaoSocialEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpcaoRazaoSocialEmpresaService opcaoRazaoSocialEmpresaService;

    private final OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository;

    private final OpcaoRazaoSocialEmpresaQueryService opcaoRazaoSocialEmpresaQueryService;

    public OpcaoRazaoSocialEmpresaResource(
        OpcaoRazaoSocialEmpresaService opcaoRazaoSocialEmpresaService,
        OpcaoRazaoSocialEmpresaRepository opcaoRazaoSocialEmpresaRepository,
        OpcaoRazaoSocialEmpresaQueryService opcaoRazaoSocialEmpresaQueryService
    ) {
        this.opcaoRazaoSocialEmpresaService = opcaoRazaoSocialEmpresaService;
        this.opcaoRazaoSocialEmpresaRepository = opcaoRazaoSocialEmpresaRepository;
        this.opcaoRazaoSocialEmpresaQueryService = opcaoRazaoSocialEmpresaQueryService;
    }

    /**
     * {@code POST  /opcao-razao-social-empresas} : Create a new opcaoRazaoSocialEmpresa.
     *
     * @param opcaoRazaoSocialEmpresa the opcaoRazaoSocialEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opcaoRazaoSocialEmpresa, or with status {@code 400 (Bad Request)} if the opcaoRazaoSocialEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OpcaoRazaoSocialEmpresa> createOpcaoRazaoSocialEmpresa(
        @Valid @RequestBody OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to save OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresa);
        if (opcaoRazaoSocialEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new opcaoRazaoSocialEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaService.save(opcaoRazaoSocialEmpresa);
        return ResponseEntity.created(new URI("/api/opcao-razao-social-empresas/" + opcaoRazaoSocialEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, opcaoRazaoSocialEmpresa.getId().toString()))
            .body(opcaoRazaoSocialEmpresa);
    }

    /**
     * {@code PUT  /opcao-razao-social-empresas/:id} : Updates an existing opcaoRazaoSocialEmpresa.
     *
     * @param id the id of the opcaoRazaoSocialEmpresa to save.
     * @param opcaoRazaoSocialEmpresa the opcaoRazaoSocialEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRazaoSocialEmpresa,
     * or with status {@code 400 (Bad Request)} if the opcaoRazaoSocialEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRazaoSocialEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OpcaoRazaoSocialEmpresa> updateOpcaoRazaoSocialEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update OpcaoRazaoSocialEmpresa : {}, {}", id, opcaoRazaoSocialEmpresa);
        if (opcaoRazaoSocialEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcaoRazaoSocialEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcaoRazaoSocialEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaService.update(opcaoRazaoSocialEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRazaoSocialEmpresa.getId().toString()))
            .body(opcaoRazaoSocialEmpresa);
    }

    /**
     * {@code PATCH  /opcao-razao-social-empresas/:id} : Partial updates given fields of an existing opcaoRazaoSocialEmpresa, field will ignore if it is null
     *
     * @param id the id of the opcaoRazaoSocialEmpresa to save.
     * @param opcaoRazaoSocialEmpresa the opcaoRazaoSocialEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRazaoSocialEmpresa,
     * or with status {@code 400 (Bad Request)} if the opcaoRazaoSocialEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the opcaoRazaoSocialEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRazaoSocialEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpcaoRazaoSocialEmpresa> partialUpdateOpcaoRazaoSocialEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpcaoRazaoSocialEmpresa partially : {}, {}", id, opcaoRazaoSocialEmpresa);
        if (opcaoRazaoSocialEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcaoRazaoSocialEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcaoRazaoSocialEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpcaoRazaoSocialEmpresa> result = opcaoRazaoSocialEmpresaService.partialUpdate(opcaoRazaoSocialEmpresa);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRazaoSocialEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /opcao-razao-social-empresas} : get all the opcaoRazaoSocialEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opcaoRazaoSocialEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<OpcaoRazaoSocialEmpresa>> getAllOpcaoRazaoSocialEmpresas(
        OpcaoRazaoSocialEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OpcaoRazaoSocialEmpresas by criteria: {}", criteria);

        Page<OpcaoRazaoSocialEmpresa> page = opcaoRazaoSocialEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /opcao-razao-social-empresas/count} : count all the opcaoRazaoSocialEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countOpcaoRazaoSocialEmpresas(OpcaoRazaoSocialEmpresaCriteria criteria) {
        log.debug("REST request to count OpcaoRazaoSocialEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(opcaoRazaoSocialEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /opcao-razao-social-empresas/:id} : get the "id" opcaoRazaoSocialEmpresa.
     *
     * @param id the id of the opcaoRazaoSocialEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opcaoRazaoSocialEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpcaoRazaoSocialEmpresa> getOpcaoRazaoSocialEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get OpcaoRazaoSocialEmpresa : {}", id);
        Optional<OpcaoRazaoSocialEmpresa> opcaoRazaoSocialEmpresa = opcaoRazaoSocialEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opcaoRazaoSocialEmpresa);
    }

    /**
     * {@code DELETE  /opcao-razao-social-empresas/:id} : delete the "id" opcaoRazaoSocialEmpresa.
     *
     * @param id the id of the opcaoRazaoSocialEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOpcaoRazaoSocialEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete OpcaoRazaoSocialEmpresa : {}", id);
        opcaoRazaoSocialEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
