package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TipoDenunciaRepository;
import com.dobemcontabilidade.service.TipoDenunciaQueryService;
import com.dobemcontabilidade.service.TipoDenunciaService;
import com.dobemcontabilidade.service.criteria.TipoDenunciaCriteria;
import com.dobemcontabilidade.service.dto.TipoDenunciaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.TipoDenuncia}.
 */
@RestController
@RequestMapping("/api/tipo-denuncias")
public class TipoDenunciaResource {

    private static final Logger log = LoggerFactory.getLogger(TipoDenunciaResource.class);

    private static final String ENTITY_NAME = "tipoDenuncia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDenunciaService tipoDenunciaService;

    private final TipoDenunciaRepository tipoDenunciaRepository;

    private final TipoDenunciaQueryService tipoDenunciaQueryService;

    public TipoDenunciaResource(
        TipoDenunciaService tipoDenunciaService,
        TipoDenunciaRepository tipoDenunciaRepository,
        TipoDenunciaQueryService tipoDenunciaQueryService
    ) {
        this.tipoDenunciaService = tipoDenunciaService;
        this.tipoDenunciaRepository = tipoDenunciaRepository;
        this.tipoDenunciaQueryService = tipoDenunciaQueryService;
    }

    /**
     * {@code POST  /tipo-denuncias} : Create a new tipoDenuncia.
     *
     * @param tipoDenunciaDTO the tipoDenunciaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDenunciaDTO, or with status {@code 400 (Bad Request)} if the tipoDenuncia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TipoDenunciaDTO> createTipoDenuncia(@RequestBody TipoDenunciaDTO tipoDenunciaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoDenuncia : {}", tipoDenunciaDTO);
        if (tipoDenunciaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoDenuncia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tipoDenunciaDTO = tipoDenunciaService.save(tipoDenunciaDTO);
        return ResponseEntity.created(new URI("/api/tipo-denuncias/" + tipoDenunciaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tipoDenunciaDTO.getId().toString()))
            .body(tipoDenunciaDTO);
    }

    /**
     * {@code PUT  /tipo-denuncias/:id} : Updates an existing tipoDenuncia.
     *
     * @param id the id of the tipoDenunciaDTO to save.
     * @param tipoDenunciaDTO the tipoDenunciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDenunciaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDenunciaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDenunciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TipoDenunciaDTO> updateTipoDenuncia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoDenunciaDTO tipoDenunciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoDenuncia : {}, {}", id, tipoDenunciaDTO);
        if (tipoDenunciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDenunciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDenunciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tipoDenunciaDTO = tipoDenunciaService.update(tipoDenunciaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDenunciaDTO.getId().toString()))
            .body(tipoDenunciaDTO);
    }

    /**
     * {@code PATCH  /tipo-denuncias/:id} : Partial updates given fields of an existing tipoDenuncia, field will ignore if it is null
     *
     * @param id the id of the tipoDenunciaDTO to save.
     * @param tipoDenunciaDTO the tipoDenunciaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDenunciaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoDenunciaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoDenunciaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoDenunciaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoDenunciaDTO> partialUpdateTipoDenuncia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoDenunciaDTO tipoDenunciaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoDenuncia partially : {}, {}", id, tipoDenunciaDTO);
        if (tipoDenunciaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoDenunciaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoDenunciaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoDenunciaDTO> result = tipoDenunciaService.partialUpdate(tipoDenunciaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDenunciaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-denuncias} : get all the tipoDenuncias.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDenuncias in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TipoDenunciaDTO>> getAllTipoDenuncias(
        TipoDenunciaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TipoDenuncias by criteria: {}", criteria);

        Page<TipoDenunciaDTO> page = tipoDenunciaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-denuncias/count} : count all the tipoDenuncias.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTipoDenuncias(TipoDenunciaCriteria criteria) {
        log.debug("REST request to count TipoDenuncias by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoDenunciaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-denuncias/:id} : get the "id" tipoDenuncia.
     *
     * @param id the id of the tipoDenunciaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDenunciaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TipoDenunciaDTO> getTipoDenuncia(@PathVariable("id") Long id) {
        log.debug("REST request to get TipoDenuncia : {}", id);
        Optional<TipoDenunciaDTO> tipoDenunciaDTO = tipoDenunciaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDenunciaDTO);
    }

    /**
     * {@code DELETE  /tipo-denuncias/:id} : delete the "id" tipoDenuncia.
     *
     * @param id the id of the tipoDenunciaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoDenuncia(@PathVariable("id") Long id) {
        log.debug("REST request to delete TipoDenuncia : {}", id);
        tipoDenunciaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
