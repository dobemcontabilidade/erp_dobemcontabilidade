package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import com.dobemcontabilidade.repository.PerfilContadorDepartamentoRepository;
import com.dobemcontabilidade.service.PerfilContadorDepartamentoQueryService;
import com.dobemcontabilidade.service.PerfilContadorDepartamentoService;
import com.dobemcontabilidade.service.criteria.PerfilContadorDepartamentoCriteria;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PerfilContadorDepartamento}.
 */
@RestController
@RequestMapping("/api/perfil-contador-departamentos")
public class PerfilContadorDepartamentoResource {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorDepartamentoResource.class);

    private static final String ENTITY_NAME = "perfilContadorDepartamento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilContadorDepartamentoService perfilContadorDepartamentoService;

    private final PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository;

    private final PerfilContadorDepartamentoQueryService perfilContadorDepartamentoQueryService;

    public PerfilContadorDepartamentoResource(
        PerfilContadorDepartamentoService perfilContadorDepartamentoService,
        PerfilContadorDepartamentoRepository perfilContadorDepartamentoRepository,
        PerfilContadorDepartamentoQueryService perfilContadorDepartamentoQueryService
    ) {
        this.perfilContadorDepartamentoService = perfilContadorDepartamentoService;
        this.perfilContadorDepartamentoRepository = perfilContadorDepartamentoRepository;
        this.perfilContadorDepartamentoQueryService = perfilContadorDepartamentoQueryService;
    }

    /**
     * {@code POST  /perfil-contador-departamentos} : Create a new perfilContadorDepartamento.
     *
     * @param perfilContadorDepartamento the perfilContadorDepartamento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilContadorDepartamento, or with status {@code 400 (Bad Request)} if the perfilContadorDepartamento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerfilContadorDepartamento> createPerfilContadorDepartamento(
        @Valid @RequestBody PerfilContadorDepartamento perfilContadorDepartamento
    ) throws URISyntaxException {
        log.debug("REST request to save PerfilContadorDepartamento : {}", perfilContadorDepartamento);
        if (perfilContadorDepartamento.getId() != null) {
            throw new BadRequestAlertException("A new perfilContadorDepartamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        perfilContadorDepartamento = perfilContadorDepartamentoService.save(perfilContadorDepartamento);
        return ResponseEntity.created(new URI("/api/perfil-contador-departamentos/" + perfilContadorDepartamento.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, perfilContadorDepartamento.getId().toString())
            )
            .body(perfilContadorDepartamento);
    }

    /**
     * {@code PUT  /perfil-contador-departamentos/:id} : Updates an existing perfilContadorDepartamento.
     *
     * @param id the id of the perfilContadorDepartamento to save.
     * @param perfilContadorDepartamento the perfilContadorDepartamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilContadorDepartamento,
     * or with status {@code 400 (Bad Request)} if the perfilContadorDepartamento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilContadorDepartamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerfilContadorDepartamento> updatePerfilContadorDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PerfilContadorDepartamento perfilContadorDepartamento
    ) throws URISyntaxException {
        log.debug("REST request to update PerfilContadorDepartamento : {}, {}", id, perfilContadorDepartamento);
        if (perfilContadorDepartamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilContadorDepartamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilContadorDepartamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        perfilContadorDepartamento = perfilContadorDepartamentoService.update(perfilContadorDepartamento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilContadorDepartamento.getId().toString()))
            .body(perfilContadorDepartamento);
    }

    /**
     * {@code PATCH  /perfil-contador-departamentos/:id} : Partial updates given fields of an existing perfilContadorDepartamento, field will ignore if it is null
     *
     * @param id the id of the perfilContadorDepartamento to save.
     * @param perfilContadorDepartamento the perfilContadorDepartamento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilContadorDepartamento,
     * or with status {@code 400 (Bad Request)} if the perfilContadorDepartamento is not valid,
     * or with status {@code 404 (Not Found)} if the perfilContadorDepartamento is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfilContadorDepartamento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerfilContadorDepartamento> partialUpdatePerfilContadorDepartamento(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PerfilContadorDepartamento perfilContadorDepartamento
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerfilContadorDepartamento partially : {}, {}", id, perfilContadorDepartamento);
        if (perfilContadorDepartamento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilContadorDepartamento.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilContadorDepartamentoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerfilContadorDepartamento> result = perfilContadorDepartamentoService.partialUpdate(perfilContadorDepartamento);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilContadorDepartamento.getId().toString())
        );
    }

    /**
     * {@code GET  /perfil-contador-departamentos} : get all the perfilContadorDepartamentos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilContadorDepartamentos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PerfilContadorDepartamento>> getAllPerfilContadorDepartamentos(
        PerfilContadorDepartamentoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerfilContadorDepartamentos by criteria: {}", criteria);

        Page<PerfilContadorDepartamento> page = perfilContadorDepartamentoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfil-contador-departamentos/count} : count all the perfilContadorDepartamentos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPerfilContadorDepartamentos(PerfilContadorDepartamentoCriteria criteria) {
        log.debug("REST request to count PerfilContadorDepartamentos by criteria: {}", criteria);
        return ResponseEntity.ok().body(perfilContadorDepartamentoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /perfil-contador-departamentos/:id} : get the "id" perfilContadorDepartamento.
     *
     * @param id the id of the perfilContadorDepartamento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilContadorDepartamento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilContadorDepartamento> getPerfilContadorDepartamento(@PathVariable("id") Long id) {
        log.debug("REST request to get PerfilContadorDepartamento : {}", id);
        Optional<PerfilContadorDepartamento> perfilContadorDepartamento = perfilContadorDepartamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilContadorDepartamento);
    }

    /**
     * {@code DELETE  /perfil-contador-departamentos/:id} : delete the "id" perfilContadorDepartamento.
     *
     * @param id the id of the perfilContadorDepartamento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfilContadorDepartamento(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerfilContadorDepartamento : {}", id);
        perfilContadorDepartamentoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
