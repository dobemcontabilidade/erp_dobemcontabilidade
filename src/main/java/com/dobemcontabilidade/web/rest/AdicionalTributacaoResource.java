package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AdicionalTributacaoRepository;
import com.dobemcontabilidade.service.AdicionalTributacaoQueryService;
import com.dobemcontabilidade.service.AdicionalTributacaoService;
import com.dobemcontabilidade.service.criteria.AdicionalTributacaoCriteria;
import com.dobemcontabilidade.service.dto.AdicionalTributacaoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AdicionalTributacao}.
 */
@RestController
@RequestMapping("/api/adicional-tributacaos")
public class AdicionalTributacaoResource {

    private static final Logger log = LoggerFactory.getLogger(AdicionalTributacaoResource.class);

    private static final String ENTITY_NAME = "adicionalTributacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdicionalTributacaoService adicionalTributacaoService;

    private final AdicionalTributacaoRepository adicionalTributacaoRepository;

    private final AdicionalTributacaoQueryService adicionalTributacaoQueryService;

    public AdicionalTributacaoResource(
        AdicionalTributacaoService adicionalTributacaoService,
        AdicionalTributacaoRepository adicionalTributacaoRepository,
        AdicionalTributacaoQueryService adicionalTributacaoQueryService
    ) {
        this.adicionalTributacaoService = adicionalTributacaoService;
        this.adicionalTributacaoRepository = adicionalTributacaoRepository;
        this.adicionalTributacaoQueryService = adicionalTributacaoQueryService;
    }

    /**
     * {@code POST  /adicional-tributacaos} : Create a new adicionalTributacao.
     *
     * @param adicionalTributacaoDTO the adicionalTributacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adicionalTributacaoDTO, or with status {@code 400 (Bad Request)} if the adicionalTributacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdicionalTributacaoDTO> createAdicionalTributacao(
        @Valid @RequestBody AdicionalTributacaoDTO adicionalTributacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AdicionalTributacao : {}", adicionalTributacaoDTO);
        if (adicionalTributacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new adicionalTributacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adicionalTributacaoDTO = adicionalTributacaoService.save(adicionalTributacaoDTO);
        return ResponseEntity.created(new URI("/api/adicional-tributacaos/" + adicionalTributacaoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, adicionalTributacaoDTO.getId().toString()))
            .body(adicionalTributacaoDTO);
    }

    /**
     * {@code PUT  /adicional-tributacaos/:id} : Updates an existing adicionalTributacao.
     *
     * @param id the id of the adicionalTributacaoDTO to save.
     * @param adicionalTributacaoDTO the adicionalTributacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalTributacaoDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalTributacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adicionalTributacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdicionalTributacaoDTO> updateAdicionalTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdicionalTributacaoDTO adicionalTributacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AdicionalTributacao : {}, {}", id, adicionalTributacaoDTO);
        if (adicionalTributacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalTributacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalTributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adicionalTributacaoDTO = adicionalTributacaoService.update(adicionalTributacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalTributacaoDTO.getId().toString()))
            .body(adicionalTributacaoDTO);
    }

    /**
     * {@code PATCH  /adicional-tributacaos/:id} : Partial updates given fields of an existing adicionalTributacao, field will ignore if it is null
     *
     * @param id the id of the adicionalTributacaoDTO to save.
     * @param adicionalTributacaoDTO the adicionalTributacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalTributacaoDTO,
     * or with status {@code 400 (Bad Request)} if the adicionalTributacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the adicionalTributacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the adicionalTributacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdicionalTributacaoDTO> partialUpdateAdicionalTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdicionalTributacaoDTO adicionalTributacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdicionalTributacao partially : {}, {}", id, adicionalTributacaoDTO);
        if (adicionalTributacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalTributacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalTributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdicionalTributacaoDTO> result = adicionalTributacaoService.partialUpdate(adicionalTributacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalTributacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /adicional-tributacaos} : get all the adicionalTributacaos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adicionalTributacaos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AdicionalTributacaoDTO>> getAllAdicionalTributacaos(
        AdicionalTributacaoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get AdicionalTributacaos by criteria: {}", criteria);

        Page<AdicionalTributacaoDTO> page = adicionalTributacaoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adicional-tributacaos/count} : count all the adicionalTributacaos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAdicionalTributacaos(AdicionalTributacaoCriteria criteria) {
        log.debug("REST request to count AdicionalTributacaos by criteria: {}", criteria);
        return ResponseEntity.ok().body(adicionalTributacaoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /adicional-tributacaos/:id} : get the "id" adicionalTributacao.
     *
     * @param id the id of the adicionalTributacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adicionalTributacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdicionalTributacaoDTO> getAdicionalTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to get AdicionalTributacao : {}", id);
        Optional<AdicionalTributacaoDTO> adicionalTributacaoDTO = adicionalTributacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adicionalTributacaoDTO);
    }

    /**
     * {@code DELETE  /adicional-tributacaos/:id} : delete the "id" adicionalTributacao.
     *
     * @param id the id of the adicionalTributacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdicionalTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete AdicionalTributacao : {}", id);
        adicionalTributacaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
