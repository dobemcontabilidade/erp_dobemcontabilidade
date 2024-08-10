package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.OpcaoRazaoSocialEmpresaRepository;
import com.dobemcontabilidade.service.OpcaoRazaoSocialEmpresaQueryService;
import com.dobemcontabilidade.service.OpcaoRazaoSocialEmpresaService;
import com.dobemcontabilidade.service.criteria.OpcaoRazaoSocialEmpresaCriteria;
import com.dobemcontabilidade.service.dto.OpcaoRazaoSocialEmpresaDTO;
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
     * @param opcaoRazaoSocialEmpresaDTO the opcaoRazaoSocialEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opcaoRazaoSocialEmpresaDTO, or with status {@code 400 (Bad Request)} if the opcaoRazaoSocialEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<OpcaoRazaoSocialEmpresaDTO> createOpcaoRazaoSocialEmpresa(
        @Valid @RequestBody OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save OpcaoRazaoSocialEmpresa : {}", opcaoRazaoSocialEmpresaDTO);
        if (opcaoRazaoSocialEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new opcaoRazaoSocialEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        opcaoRazaoSocialEmpresaDTO = opcaoRazaoSocialEmpresaService.save(opcaoRazaoSocialEmpresaDTO);
        return ResponseEntity.created(new URI("/api/opcao-razao-social-empresas/" + opcaoRazaoSocialEmpresaDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, opcaoRazaoSocialEmpresaDTO.getId().toString())
            )
            .body(opcaoRazaoSocialEmpresaDTO);
    }

    /**
     * {@code PUT  /opcao-razao-social-empresas/:id} : Updates an existing opcaoRazaoSocialEmpresa.
     *
     * @param id the id of the opcaoRazaoSocialEmpresaDTO to save.
     * @param opcaoRazaoSocialEmpresaDTO the opcaoRazaoSocialEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRazaoSocialEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the opcaoRazaoSocialEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRazaoSocialEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<OpcaoRazaoSocialEmpresaDTO> updateOpcaoRazaoSocialEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OpcaoRazaoSocialEmpresa : {}, {}", id, opcaoRazaoSocialEmpresaDTO);
        if (opcaoRazaoSocialEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcaoRazaoSocialEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcaoRazaoSocialEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        opcaoRazaoSocialEmpresaDTO = opcaoRazaoSocialEmpresaService.update(opcaoRazaoSocialEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRazaoSocialEmpresaDTO.getId().toString()))
            .body(opcaoRazaoSocialEmpresaDTO);
    }

    /**
     * {@code PATCH  /opcao-razao-social-empresas/:id} : Partial updates given fields of an existing opcaoRazaoSocialEmpresa, field will ignore if it is null
     *
     * @param id the id of the opcaoRazaoSocialEmpresaDTO to save.
     * @param opcaoRazaoSocialEmpresaDTO the opcaoRazaoSocialEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opcaoRazaoSocialEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the opcaoRazaoSocialEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the opcaoRazaoSocialEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the opcaoRazaoSocialEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpcaoRazaoSocialEmpresaDTO> partialUpdateOpcaoRazaoSocialEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OpcaoRazaoSocialEmpresaDTO opcaoRazaoSocialEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OpcaoRazaoSocialEmpresa partially : {}, {}", id, opcaoRazaoSocialEmpresaDTO);
        if (opcaoRazaoSocialEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opcaoRazaoSocialEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opcaoRazaoSocialEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpcaoRazaoSocialEmpresaDTO> result = opcaoRazaoSocialEmpresaService.partialUpdate(opcaoRazaoSocialEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opcaoRazaoSocialEmpresaDTO.getId().toString())
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
    public ResponseEntity<List<OpcaoRazaoSocialEmpresaDTO>> getAllOpcaoRazaoSocialEmpresas(
        OpcaoRazaoSocialEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get OpcaoRazaoSocialEmpresas by criteria: {}", criteria);

        Page<OpcaoRazaoSocialEmpresaDTO> page = opcaoRazaoSocialEmpresaQueryService.findByCriteria(criteria, pageable);
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
     * @param id the id of the opcaoRazaoSocialEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opcaoRazaoSocialEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<OpcaoRazaoSocialEmpresaDTO> getOpcaoRazaoSocialEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get OpcaoRazaoSocialEmpresa : {}", id);
        Optional<OpcaoRazaoSocialEmpresaDTO> opcaoRazaoSocialEmpresaDTO = opcaoRazaoSocialEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opcaoRazaoSocialEmpresaDTO);
    }

    /**
     * {@code DELETE  /opcao-razao-social-empresas/:id} : delete the "id" opcaoRazaoSocialEmpresa.
     *
     * @param id the id of the opcaoRazaoSocialEmpresaDTO to delete.
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
