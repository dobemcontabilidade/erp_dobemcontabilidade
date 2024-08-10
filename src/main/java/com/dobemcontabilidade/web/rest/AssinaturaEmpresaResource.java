package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AssinaturaEmpresaRepository;
import com.dobemcontabilidade.service.AssinaturaEmpresaQueryService;
import com.dobemcontabilidade.service.AssinaturaEmpresaService;
import com.dobemcontabilidade.service.criteria.AssinaturaEmpresaCriteria;
import com.dobemcontabilidade.service.dto.AssinaturaEmpresaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AssinaturaEmpresa}.
 */
@RestController
@RequestMapping("/api/assinatura-empresas")
public class AssinaturaEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AssinaturaEmpresaResource.class);

    private static final String ENTITY_NAME = "assinaturaEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssinaturaEmpresaService assinaturaEmpresaService;

    private final AssinaturaEmpresaRepository assinaturaEmpresaRepository;

    private final AssinaturaEmpresaQueryService assinaturaEmpresaQueryService;

    public AssinaturaEmpresaResource(
        AssinaturaEmpresaService assinaturaEmpresaService,
        AssinaturaEmpresaRepository assinaturaEmpresaRepository,
        AssinaturaEmpresaQueryService assinaturaEmpresaQueryService
    ) {
        this.assinaturaEmpresaService = assinaturaEmpresaService;
        this.assinaturaEmpresaRepository = assinaturaEmpresaRepository;
        this.assinaturaEmpresaQueryService = assinaturaEmpresaQueryService;
    }

    /**
     * {@code POST  /assinatura-empresas} : Create a new assinaturaEmpresa.
     *
     * @param assinaturaEmpresaDTO the assinaturaEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assinaturaEmpresaDTO, or with status {@code 400 (Bad Request)} if the assinaturaEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AssinaturaEmpresaDTO> createAssinaturaEmpresa(@Valid @RequestBody AssinaturaEmpresaDTO assinaturaEmpresaDTO)
        throws URISyntaxException {
        log.debug("REST request to save AssinaturaEmpresa : {}", assinaturaEmpresaDTO);
        if (assinaturaEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new assinaturaEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        assinaturaEmpresaDTO = assinaturaEmpresaService.save(assinaturaEmpresaDTO);
        return ResponseEntity.created(new URI("/api/assinatura-empresas/" + assinaturaEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresaDTO.getId().toString()))
            .body(assinaturaEmpresaDTO);
    }

    /**
     * {@code PUT  /assinatura-empresas/:id} : Updates an existing assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresaDTO to save.
     * @param assinaturaEmpresaDTO the assinaturaEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assinaturaEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the assinaturaEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assinaturaEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaEmpresaDTO> updateAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssinaturaEmpresaDTO assinaturaEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AssinaturaEmpresa : {}, {}", id, assinaturaEmpresaDTO);
        if (assinaturaEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assinaturaEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        assinaturaEmpresaDTO = assinaturaEmpresaService.update(assinaturaEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresaDTO.getId().toString()))
            .body(assinaturaEmpresaDTO);
    }

    /**
     * {@code PATCH  /assinatura-empresas/:id} : Partial updates given fields of an existing assinaturaEmpresa, field will ignore if it is null
     *
     * @param id the id of the assinaturaEmpresaDTO to save.
     * @param assinaturaEmpresaDTO the assinaturaEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assinaturaEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the assinaturaEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assinaturaEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the assinaturaEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssinaturaEmpresaDTO> partialUpdateAssinaturaEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssinaturaEmpresaDTO assinaturaEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AssinaturaEmpresa partially : {}, {}", id, assinaturaEmpresaDTO);
        if (assinaturaEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, assinaturaEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!assinaturaEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AssinaturaEmpresaDTO> result = assinaturaEmpresaService.partialUpdate(assinaturaEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assinaturaEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /assinatura-empresas} : get all the assinaturaEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assinaturaEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AssinaturaEmpresaDTO>> getAllAssinaturaEmpresas(
        AssinaturaEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AssinaturaEmpresas by criteria: {}", criteria);

        Page<AssinaturaEmpresaDTO> page = assinaturaEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /assinatura-empresas/count} : count all the assinaturaEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAssinaturaEmpresas(AssinaturaEmpresaCriteria criteria) {
        log.debug("REST request to count AssinaturaEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(assinaturaEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /assinatura-empresas/:id} : get the "id" assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assinaturaEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaEmpresaDTO> getAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AssinaturaEmpresa : {}", id);
        Optional<AssinaturaEmpresaDTO> assinaturaEmpresaDTO = assinaturaEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assinaturaEmpresaDTO);
    }

    /**
     * {@code DELETE  /assinatura-empresas/:id} : delete the "id" assinaturaEmpresa.
     *
     * @param id the id of the assinaturaEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssinaturaEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AssinaturaEmpresa : {}", id);
        assinaturaEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
