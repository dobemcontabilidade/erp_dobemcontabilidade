package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TributacaoRepository;
import com.dobemcontabilidade.service.TributacaoQueryService;
import com.dobemcontabilidade.service.TributacaoService;
import com.dobemcontabilidade.service.criteria.TributacaoCriteria;
import com.dobemcontabilidade.service.dto.TributacaoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Tributacao}.
 */
@RestController
@RequestMapping("/api/tributacaos")
public class TributacaoResource {

    private static final Logger log = LoggerFactory.getLogger(TributacaoResource.class);

    private static final String ENTITY_NAME = "tributacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TributacaoService tributacaoService;

    private final TributacaoRepository tributacaoRepository;

    private final TributacaoQueryService tributacaoQueryService;

    public TributacaoResource(
        TributacaoService tributacaoService,
        TributacaoRepository tributacaoRepository,
        TributacaoQueryService tributacaoQueryService
    ) {
        this.tributacaoService = tributacaoService;
        this.tributacaoRepository = tributacaoRepository;
        this.tributacaoQueryService = tributacaoQueryService;
    }

    /**
     * {@code POST  /tributacaos} : Create a new tributacao.
     *
     * @param tributacaoDTO the tributacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tributacaoDTO, or with status {@code 400 (Bad Request)} if the tributacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TributacaoDTO> createTributacao(@RequestBody TributacaoDTO tributacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Tributacao : {}", tributacaoDTO);
        if (tributacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tributacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tributacaoDTO = tributacaoService.save(tributacaoDTO);
        return ResponseEntity.created(new URI("/api/tributacaos/" + tributacaoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tributacaoDTO.getId().toString()))
            .body(tributacaoDTO);
    }

    /**
     * {@code PUT  /tributacaos/:id} : Updates an existing tributacao.
     *
     * @param id the id of the tributacaoDTO to save.
     * @param tributacaoDTO the tributacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tributacaoDTO,
     * or with status {@code 400 (Bad Request)} if the tributacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tributacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TributacaoDTO> updateTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TributacaoDTO tributacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tributacao : {}, {}", id, tributacaoDTO);
        if (tributacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tributacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tributacaoDTO = tributacaoService.update(tributacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tributacaoDTO.getId().toString()))
            .body(tributacaoDTO);
    }

    /**
     * {@code PATCH  /tributacaos/:id} : Partial updates given fields of an existing tributacao, field will ignore if it is null
     *
     * @param id the id of the tributacaoDTO to save.
     * @param tributacaoDTO the tributacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tributacaoDTO,
     * or with status {@code 400 (Bad Request)} if the tributacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tributacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tributacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TributacaoDTO> partialUpdateTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TributacaoDTO tributacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tributacao partially : {}, {}", id, tributacaoDTO);
        if (tributacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tributacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TributacaoDTO> result = tributacaoService.partialUpdate(tributacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tributacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tributacaos} : get all the tributacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tributacaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TributacaoDTO>> getAllTributacaos(
        TributacaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Tributacaos by criteria: {}", criteria);

        Page<TributacaoDTO> page = tributacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tributacaos/count} : count all the tributacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTributacaos(TributacaoCriteria criteria) {
        log.debug("REST request to count Tributacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(tributacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tributacaos/:id} : get the "id" tributacao.
     *
     * @param id the id of the tributacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tributacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TributacaoDTO> getTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to get Tributacao : {}", id);
        Optional<TributacaoDTO> tributacaoDTO = tributacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tributacaoDTO);
    }

    /**
     * {@code DELETE  /tributacaos/:id} : delete the "id" tributacao.
     *
     * @param id the id of the tributacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tributacao : {}", id);
        tributacaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
