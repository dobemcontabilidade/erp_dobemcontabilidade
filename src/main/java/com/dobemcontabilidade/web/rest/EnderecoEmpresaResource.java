package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
import com.dobemcontabilidade.service.EnderecoEmpresaQueryService;
import com.dobemcontabilidade.service.EnderecoEmpresaService;
import com.dobemcontabilidade.service.criteria.EnderecoEmpresaCriteria;
import com.dobemcontabilidade.service.dto.EnderecoEmpresaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EnderecoEmpresa}.
 */
@RestController
@RequestMapping("/api/endereco-empresas")
public class EnderecoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(EnderecoEmpresaResource.class);

    private static final String ENTITY_NAME = "enderecoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoEmpresaService enderecoEmpresaService;

    private final EnderecoEmpresaRepository enderecoEmpresaRepository;

    private final EnderecoEmpresaQueryService enderecoEmpresaQueryService;

    public EnderecoEmpresaResource(
        EnderecoEmpresaService enderecoEmpresaService,
        EnderecoEmpresaRepository enderecoEmpresaRepository,
        EnderecoEmpresaQueryService enderecoEmpresaQueryService
    ) {
        this.enderecoEmpresaService = enderecoEmpresaService;
        this.enderecoEmpresaRepository = enderecoEmpresaRepository;
        this.enderecoEmpresaQueryService = enderecoEmpresaQueryService;
    }

    /**
     * {@code POST  /endereco-empresas} : Create a new enderecoEmpresa.
     *
     * @param enderecoEmpresaDTO the enderecoEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoEmpresaDTO, or with status {@code 400 (Bad Request)} if the enderecoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EnderecoEmpresaDTO> createEnderecoEmpresa(@Valid @RequestBody EnderecoEmpresaDTO enderecoEmpresaDTO)
        throws URISyntaxException {
        log.debug("REST request to save EnderecoEmpresa : {}", enderecoEmpresaDTO);
        if (enderecoEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new enderecoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enderecoEmpresaDTO = enderecoEmpresaService.save(enderecoEmpresaDTO);
        return ResponseEntity.created(new URI("/api/endereco-empresas/" + enderecoEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enderecoEmpresaDTO.getId().toString()))
            .body(enderecoEmpresaDTO);
    }

    /**
     * {@code PUT  /endereco-empresas/:id} : Updates an existing enderecoEmpresa.
     *
     * @param id the id of the enderecoEmpresaDTO to save.
     * @param enderecoEmpresaDTO the enderecoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoEmpresaDTO> updateEnderecoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnderecoEmpresaDTO enderecoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EnderecoEmpresa : {}, {}", id, enderecoEmpresaDTO);
        if (enderecoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enderecoEmpresaDTO = enderecoEmpresaService.update(enderecoEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoEmpresaDTO.getId().toString()))
            .body(enderecoEmpresaDTO);
    }

    /**
     * {@code PATCH  /endereco-empresas/:id} : Partial updates given fields of an existing enderecoEmpresa, field will ignore if it is null
     *
     * @param id the id of the enderecoEmpresaDTO to save.
     * @param enderecoEmpresaDTO the enderecoEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enderecoEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enderecoEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnderecoEmpresaDTO> partialUpdateEnderecoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnderecoEmpresaDTO enderecoEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnderecoEmpresa partially : {}, {}", id, enderecoEmpresaDTO);
        if (enderecoEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnderecoEmpresaDTO> result = enderecoEmpresaService.partialUpdate(enderecoEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /endereco-empresas} : get all the enderecoEmpresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecoEmpresas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EnderecoEmpresaDTO>> getAllEnderecoEmpresas(
        EnderecoEmpresaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EnderecoEmpresas by criteria: {}", criteria);

        Page<EnderecoEmpresaDTO> page = enderecoEmpresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endereco-empresas/count} : count all the enderecoEmpresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEnderecoEmpresas(EnderecoEmpresaCriteria criteria) {
        log.debug("REST request to count EnderecoEmpresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(enderecoEmpresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /endereco-empresas/:id} : get the "id" enderecoEmpresa.
     *
     * @param id the id of the enderecoEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoEmpresaDTO> getEnderecoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get EnderecoEmpresa : {}", id);
        Optional<EnderecoEmpresaDTO> enderecoEmpresaDTO = enderecoEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoEmpresaDTO);
    }

    /**
     * {@code DELETE  /endereco-empresas/:id} : delete the "id" enderecoEmpresa.
     *
     * @param id the id of the enderecoEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnderecoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete EnderecoEmpresa : {}", id);
        enderecoEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
