package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AnexoPessoaRepository;
import com.dobemcontabilidade.service.AnexoPessoaQueryService;
import com.dobemcontabilidade.service.AnexoPessoaService;
import com.dobemcontabilidade.service.criteria.AnexoPessoaCriteria;
import com.dobemcontabilidade.service.dto.AnexoPessoaDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.AnexoPessoa}.
 */
@RestController
@RequestMapping("/api/anexo-pessoas")
public class AnexoPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(AnexoPessoaResource.class);

    private static final String ENTITY_NAME = "anexoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnexoPessoaService anexoPessoaService;

    private final AnexoPessoaRepository anexoPessoaRepository;

    private final AnexoPessoaQueryService anexoPessoaQueryService;

    public AnexoPessoaResource(
        AnexoPessoaService anexoPessoaService,
        AnexoPessoaRepository anexoPessoaRepository,
        AnexoPessoaQueryService anexoPessoaQueryService
    ) {
        this.anexoPessoaService = anexoPessoaService;
        this.anexoPessoaRepository = anexoPessoaRepository;
        this.anexoPessoaQueryService = anexoPessoaQueryService;
    }

    /**
     * {@code POST  /anexo-pessoas} : Create a new anexoPessoa.
     *
     * @param anexoPessoaDTO the anexoPessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anexoPessoaDTO, or with status {@code 400 (Bad Request)} if the anexoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AnexoPessoaDTO> createAnexoPessoa(@Valid @RequestBody AnexoPessoaDTO anexoPessoaDTO) throws URISyntaxException {
        log.debug("REST request to save AnexoPessoa : {}", anexoPessoaDTO);
        if (anexoPessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new anexoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        anexoPessoaDTO = anexoPessoaService.save(anexoPessoaDTO);
        return ResponseEntity.created(new URI("/api/anexo-pessoas/" + anexoPessoaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, anexoPessoaDTO.getId().toString()))
            .body(anexoPessoaDTO);
    }

    /**
     * {@code PUT  /anexo-pessoas/:id} : Updates an existing anexoPessoa.
     *
     * @param id the id of the anexoPessoaDTO to save.
     * @param anexoPessoaDTO the anexoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the anexoPessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anexoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AnexoPessoaDTO> updateAnexoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnexoPessoaDTO anexoPessoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnexoPessoa : {}, {}", id, anexoPessoaDTO);
        if (anexoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoPessoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        anexoPessoaDTO = anexoPessoaService.update(anexoPessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoPessoaDTO.getId().toString()))
            .body(anexoPessoaDTO);
    }

    /**
     * {@code PATCH  /anexo-pessoas/:id} : Partial updates given fields of an existing anexoPessoa, field will ignore if it is null
     *
     * @param id the id of the anexoPessoaDTO to save.
     * @param anexoPessoaDTO the anexoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anexoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the anexoPessoaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the anexoPessoaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the anexoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AnexoPessoaDTO> partialUpdateAnexoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnexoPessoaDTO anexoPessoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnexoPessoa partially : {}, {}", id, anexoPessoaDTO);
        if (anexoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, anexoPessoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!anexoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnexoPessoaDTO> result = anexoPessoaService.partialUpdate(anexoPessoaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anexoPessoaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /anexo-pessoas} : get all the anexoPessoas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anexoPessoas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AnexoPessoaDTO>> getAllAnexoPessoas(AnexoPessoaCriteria criteria) {
        log.debug("REST request to get AnexoPessoas by criteria: {}", criteria);

        List<AnexoPessoaDTO> entityList = anexoPessoaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /anexo-pessoas/count} : count all the anexoPessoas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAnexoPessoas(AnexoPessoaCriteria criteria) {
        log.debug("REST request to count AnexoPessoas by criteria: {}", criteria);
        return ResponseEntity.ok().body(anexoPessoaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /anexo-pessoas/:id} : get the "id" anexoPessoa.
     *
     * @param id the id of the anexoPessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anexoPessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AnexoPessoaDTO> getAnexoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get AnexoPessoa : {}", id);
        Optional<AnexoPessoaDTO> anexoPessoaDTO = anexoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anexoPessoaDTO);
    }

    /**
     * {@code DELETE  /anexo-pessoas/:id} : delete the "id" anexoPessoa.
     *
     * @param id the id of the anexoPessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnexoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AnexoPessoa : {}", id);
        anexoPessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
