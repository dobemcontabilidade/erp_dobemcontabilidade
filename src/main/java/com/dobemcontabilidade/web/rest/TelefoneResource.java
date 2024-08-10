package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.TelefoneRepository;
import com.dobemcontabilidade.service.TelefoneQueryService;
import com.dobemcontabilidade.service.TelefoneService;
import com.dobemcontabilidade.service.criteria.TelefoneCriteria;
import com.dobemcontabilidade.service.dto.TelefoneDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Telefone}.
 */
@RestController
@RequestMapping("/api/telefones")
public class TelefoneResource {

    private static final Logger log = LoggerFactory.getLogger(TelefoneResource.class);

    private static final String ENTITY_NAME = "telefone";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TelefoneService telefoneService;

    private final TelefoneRepository telefoneRepository;

    private final TelefoneQueryService telefoneQueryService;

    public TelefoneResource(
        TelefoneService telefoneService,
        TelefoneRepository telefoneRepository,
        TelefoneQueryService telefoneQueryService
    ) {
        this.telefoneService = telefoneService;
        this.telefoneRepository = telefoneRepository;
        this.telefoneQueryService = telefoneQueryService;
    }

    /**
     * {@code POST  /telefones} : Create a new telefone.
     *
     * @param telefoneDTO the telefoneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new telefoneDTO, or with status {@code 400 (Bad Request)} if the telefone has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TelefoneDTO> createTelefone(@Valid @RequestBody TelefoneDTO telefoneDTO) throws URISyntaxException {
        log.debug("REST request to save Telefone : {}", telefoneDTO);
        if (telefoneDTO.getId() != null) {
            throw new BadRequestAlertException("A new telefone cannot already have an ID", ENTITY_NAME, "idexists");
        }
        telefoneDTO = telefoneService.save(telefoneDTO);
        return ResponseEntity.created(new URI("/api/telefones/" + telefoneDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, telefoneDTO.getId().toString()))
            .body(telefoneDTO);
    }

    /**
     * {@code PUT  /telefones/:id} : Updates an existing telefone.
     *
     * @param id the id of the telefoneDTO to save.
     * @param telefoneDTO the telefoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoneDTO,
     * or with status {@code 400 (Bad Request)} if the telefoneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the telefoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TelefoneDTO> updateTelefone(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TelefoneDTO telefoneDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Telefone : {}, {}", id, telefoneDTO);
        if (telefoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        telefoneDTO = telefoneService.update(telefoneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telefoneDTO.getId().toString()))
            .body(telefoneDTO);
    }

    /**
     * {@code PATCH  /telefones/:id} : Partial updates given fields of an existing telefone, field will ignore if it is null
     *
     * @param id the id of the telefoneDTO to save.
     * @param telefoneDTO the telefoneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated telefoneDTO,
     * or with status {@code 400 (Bad Request)} if the telefoneDTO is not valid,
     * or with status {@code 404 (Not Found)} if the telefoneDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the telefoneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TelefoneDTO> partialUpdateTelefone(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TelefoneDTO telefoneDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Telefone partially : {}, {}", id, telefoneDTO);
        if (telefoneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, telefoneDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!telefoneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TelefoneDTO> result = telefoneService.partialUpdate(telefoneDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, telefoneDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /telefones} : get all the telefones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of telefones in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TelefoneDTO>> getAllTelefones(
        TelefoneCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Telefones by criteria: {}", criteria);

        Page<TelefoneDTO> page = telefoneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /telefones/count} : count all the telefones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTelefones(TelefoneCriteria criteria) {
        log.debug("REST request to count Telefones by criteria: {}", criteria);
        return ResponseEntity.ok().body(telefoneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /telefones/:id} : get the "id" telefone.
     *
     * @param id the id of the telefoneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the telefoneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TelefoneDTO> getTelefone(@PathVariable("id") Long id) {
        log.debug("REST request to get Telefone : {}", id);
        Optional<TelefoneDTO> telefoneDTO = telefoneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(telefoneDTO);
    }

    /**
     * {@code DELETE  /telefones/:id} : delete the "id" telefone.
     *
     * @param id the id of the telefoneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTelefone(@PathVariable("id") Long id) {
        log.debug("REST request to delete Telefone : {}", id);
        telefoneService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
