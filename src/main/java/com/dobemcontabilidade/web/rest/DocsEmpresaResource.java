package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DocsEmpresa;
import com.dobemcontabilidade.repository.DocsEmpresaRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.DocsEmpresa}.
 */
@RestController
@RequestMapping("/api/docs-empresas")
@Transactional
public class DocsEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(DocsEmpresaResource.class);

    private static final String ENTITY_NAME = "docsEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocsEmpresaRepository docsEmpresaRepository;

    public DocsEmpresaResource(DocsEmpresaRepository docsEmpresaRepository) {
        this.docsEmpresaRepository = docsEmpresaRepository;
    }

    /**
     * {@code POST  /docs-empresas} : Create a new docsEmpresa.
     *
     * @param docsEmpresa the docsEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docsEmpresa, or with status {@code 400 (Bad Request)} if the docsEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocsEmpresa> createDocsEmpresa(@Valid @RequestBody DocsEmpresa docsEmpresa) throws URISyntaxException {
        log.debug("REST request to save DocsEmpresa : {}", docsEmpresa);
        if (docsEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new docsEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        docsEmpresa = docsEmpresaRepository.save(docsEmpresa);
        return ResponseEntity.created(new URI("/api/docs-empresas/" + docsEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, docsEmpresa.getId().toString()))
            .body(docsEmpresa);
    }

    /**
     * {@code PUT  /docs-empresas/:id} : Updates an existing docsEmpresa.
     *
     * @param id the id of the docsEmpresa to save.
     * @param docsEmpresa the docsEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsEmpresa,
     * or with status {@code 400 (Bad Request)} if the docsEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docsEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocsEmpresa> updateDocsEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocsEmpresa docsEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update DocsEmpresa : {}, {}", id, docsEmpresa);
        if (docsEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docsEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docsEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        docsEmpresa = docsEmpresaRepository.save(docsEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docsEmpresa.getId().toString()))
            .body(docsEmpresa);
    }

    /**
     * {@code PATCH  /docs-empresas/:id} : Partial updates given fields of an existing docsEmpresa, field will ignore if it is null
     *
     * @param id the id of the docsEmpresa to save.
     * @param docsEmpresa the docsEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsEmpresa,
     * or with status {@code 400 (Bad Request)} if the docsEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the docsEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the docsEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocsEmpresa> partialUpdateDocsEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocsEmpresa docsEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocsEmpresa partially : {}, {}", id, docsEmpresa);
        if (docsEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docsEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docsEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocsEmpresa> result = docsEmpresaRepository
            .findById(docsEmpresa.getId())
            .map(existingDocsEmpresa -> {
                if (docsEmpresa.getDocumento() != null) {
                    existingDocsEmpresa.setDocumento(docsEmpresa.getDocumento());
                }
                if (docsEmpresa.getDescricao() != null) {
                    existingDocsEmpresa.setDescricao(docsEmpresa.getDescricao());
                }
                if (docsEmpresa.getUrl() != null) {
                    existingDocsEmpresa.setUrl(docsEmpresa.getUrl());
                }
                if (docsEmpresa.getDataEmissao() != null) {
                    existingDocsEmpresa.setDataEmissao(docsEmpresa.getDataEmissao());
                }
                if (docsEmpresa.getDataEncerramento() != null) {
                    existingDocsEmpresa.setDataEncerramento(docsEmpresa.getDataEncerramento());
                }
                if (docsEmpresa.getOrgaoEmissor() != null) {
                    existingDocsEmpresa.setOrgaoEmissor(docsEmpresa.getOrgaoEmissor());
                }

                return existingDocsEmpresa;
            })
            .map(docsEmpresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docsEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /docs-empresas} : get all the docsEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docsEmpresas in body.
     */
    @GetMapping("")
    public List<DocsEmpresa> getAllDocsEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all DocsEmpresas");
        if (eagerload) {
            return docsEmpresaRepository.findAllWithEagerRelationships();
        } else {
            return docsEmpresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /docs-empresas/:id} : get the "id" docsEmpresa.
     *
     * @param id the id of the docsEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docsEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocsEmpresa> getDocsEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get DocsEmpresa : {}", id);
        Optional<DocsEmpresa> docsEmpresa = docsEmpresaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(docsEmpresa);
    }

    /**
     * {@code DELETE  /docs-empresas/:id} : delete the "id" docsEmpresa.
     *
     * @param id the id of the docsEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocsEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete DocsEmpresa : {}", id);
        docsEmpresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
