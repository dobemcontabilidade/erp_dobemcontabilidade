package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.CidadeRepository;
import com.dobemcontabilidade.service.CidadeQueryService;
import com.dobemcontabilidade.service.CidadeService;
import com.dobemcontabilidade.service.criteria.CidadeCriteria;
import com.dobemcontabilidade.service.dto.CidadeDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Cidade}.
 */
@RestController
@RequestMapping("/api/cidades")
public class CidadeResource {

    private static final Logger log = LoggerFactory.getLogger(CidadeResource.class);

    private static final String ENTITY_NAME = "cidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CidadeService cidadeService;

    private final CidadeRepository cidadeRepository;

    private final CidadeQueryService cidadeQueryService;

    public CidadeResource(CidadeService cidadeService, CidadeRepository cidadeRepository, CidadeQueryService cidadeQueryService) {
        this.cidadeService = cidadeService;
        this.cidadeRepository = cidadeRepository;
        this.cidadeQueryService = cidadeQueryService;
    }

    /**
     * {@code POST  /cidades} : Create a new cidade.
     *
     * @param cidadeDTO the cidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cidadeDTO, or with status {@code 400 (Bad Request)} if the cidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CidadeDTO> createCidade(@Valid @RequestBody CidadeDTO cidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Cidade : {}", cidadeDTO);
        if (cidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new cidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cidadeDTO = cidadeService.save(cidadeDTO);
        return ResponseEntity.created(new URI("/api/cidades/" + cidadeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cidadeDTO.getId().toString()))
            .body(cidadeDTO);
    }

    /**
     * {@code PUT  /cidades/:id} : Updates an existing cidade.
     *
     * @param id the id of the cidadeDTO to save.
     * @param cidadeDTO the cidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadeDTO,
     * or with status {@code 400 (Bad Request)} if the cidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTO> updateCidade(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CidadeDTO cidadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Cidade : {}, {}", id, cidadeDTO);
        if (cidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cidadeDTO = cidadeService.update(cidadeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cidadeDTO.getId().toString()))
            .body(cidadeDTO);
    }

    /**
     * {@code PATCH  /cidades/:id} : Partial updates given fields of an existing cidade, field will ignore if it is null
     *
     * @param id the id of the cidadeDTO to save.
     * @param cidadeDTO the cidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cidadeDTO,
     * or with status {@code 400 (Bad Request)} if the cidadeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cidadeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CidadeDTO> partialUpdateCidade(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CidadeDTO cidadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cidade partially : {}, {}", id, cidadeDTO);
        if (cidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cidadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CidadeDTO> result = cidadeService.partialUpdate(cidadeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cidadeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cidades} : get all the cidades.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cidades in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CidadeDTO>> getAllCidades(
        CidadeCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Cidades by criteria: {}", criteria);

        Page<CidadeDTO> page = cidadeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cidades/count} : count all the cidades.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCidades(CidadeCriteria criteria) {
        log.debug("REST request to count Cidades by criteria: {}", criteria);
        return ResponseEntity.ok().body(cidadeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cidades/:id} : get the "id" cidade.
     *
     * @param id the id of the cidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> getCidade(@PathVariable("id") Long id) {
        log.debug("REST request to get Cidade : {}", id);
        Optional<CidadeDTO> cidadeDTO = cidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cidadeDTO);
    }

    /**
     * {@code DELETE  /cidades/:id} : delete the "id" cidade.
     *
     * @param id the id of the cidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCidade(@PathVariable("id") Long id) {
        log.debug("REST request to delete Cidade : {}", id);
        cidadeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
