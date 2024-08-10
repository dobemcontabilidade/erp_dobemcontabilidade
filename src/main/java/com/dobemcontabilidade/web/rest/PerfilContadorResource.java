package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.PerfilContadorRepository;
import com.dobemcontabilidade.service.PerfilContadorQueryService;
import com.dobemcontabilidade.service.PerfilContadorService;
import com.dobemcontabilidade.service.criteria.PerfilContadorCriteria;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PerfilContador}.
 */
@RestController
@RequestMapping("/api/perfil-contadors")
public class PerfilContadorResource {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorResource.class);

    private static final String ENTITY_NAME = "perfilContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilContadorService perfilContadorService;

    private final PerfilContadorRepository perfilContadorRepository;

    private final PerfilContadorQueryService perfilContadorQueryService;

    public PerfilContadorResource(
        PerfilContadorService perfilContadorService,
        PerfilContadorRepository perfilContadorRepository,
        PerfilContadorQueryService perfilContadorQueryService
    ) {
        this.perfilContadorService = perfilContadorService;
        this.perfilContadorRepository = perfilContadorRepository;
        this.perfilContadorQueryService = perfilContadorQueryService;
    }

    /**
     * {@code POST  /perfil-contadors} : Create a new perfilContador.
     *
     * @param perfilContadorDTO the perfilContadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilContadorDTO, or with status {@code 400 (Bad Request)} if the perfilContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerfilContadorDTO> createPerfilContador(@Valid @RequestBody PerfilContadorDTO perfilContadorDTO)
        throws URISyntaxException {
        log.debug("REST request to save PerfilContador : {}", perfilContadorDTO);
        if (perfilContadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new perfilContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        perfilContadorDTO = perfilContadorService.save(perfilContadorDTO);
        return ResponseEntity.created(new URI("/api/perfil-contadors/" + perfilContadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, perfilContadorDTO.getId().toString()))
            .body(perfilContadorDTO);
    }

    /**
     * {@code PUT  /perfil-contadors/:id} : Updates an existing perfilContador.
     *
     * @param id the id of the perfilContadorDTO to save.
     * @param perfilContadorDTO the perfilContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilContadorDTO,
     * or with status {@code 400 (Bad Request)} if the perfilContadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerfilContadorDTO> updatePerfilContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PerfilContadorDTO perfilContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PerfilContador : {}, {}", id, perfilContadorDTO);
        if (perfilContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        perfilContadorDTO = perfilContadorService.update(perfilContadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilContadorDTO.getId().toString()))
            .body(perfilContadorDTO);
    }

    /**
     * {@code PATCH  /perfil-contadors/:id} : Partial updates given fields of an existing perfilContador, field will ignore if it is null
     *
     * @param id the id of the perfilContadorDTO to save.
     * @param perfilContadorDTO the perfilContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilContadorDTO,
     * or with status {@code 400 (Bad Request)} if the perfilContadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the perfilContadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfilContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerfilContadorDTO> partialUpdatePerfilContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PerfilContadorDTO perfilContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerfilContador partially : {}, {}", id, perfilContadorDTO);
        if (perfilContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerfilContadorDTO> result = perfilContadorService.partialUpdate(perfilContadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilContadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /perfil-contadors} : get all the perfilContadors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilContadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PerfilContadorDTO>> getAllPerfilContadors(
        PerfilContadorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerfilContadors by criteria: {}", criteria);

        Page<PerfilContadorDTO> page = perfilContadorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfil-contadors/count} : count all the perfilContadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPerfilContadors(PerfilContadorCriteria criteria) {
        log.debug("REST request to count PerfilContadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(perfilContadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /perfil-contadors/:id} : get the "id" perfilContador.
     *
     * @param id the id of the perfilContadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilContadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilContadorDTO> getPerfilContador(@PathVariable("id") Long id) {
        log.debug("REST request to get PerfilContador : {}", id);
        Optional<PerfilContadorDTO> perfilContadorDTO = perfilContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilContadorDTO);
    }

    /**
     * {@code DELETE  /perfil-contadors/:id} : delete the "id" perfilContador.
     *
     * @param id the id of the perfilContadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfilContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerfilContador : {}", id);
        perfilContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
