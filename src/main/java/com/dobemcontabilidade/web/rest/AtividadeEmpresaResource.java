package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AtividadeEmpresaRepository;
import com.dobemcontabilidade.service.AtividadeEmpresaService;
import com.dobemcontabilidade.service.dto.AtividadeEmpresaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AtividadeEmpresa}.
 */
@RestController
@RequestMapping("/api/atividade-empresas")
public class AtividadeEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(AtividadeEmpresaResource.class);

    private static final String ENTITY_NAME = "atividadeEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AtividadeEmpresaService atividadeEmpresaService;

    private final AtividadeEmpresaRepository atividadeEmpresaRepository;

    public AtividadeEmpresaResource(
        AtividadeEmpresaService atividadeEmpresaService,
        AtividadeEmpresaRepository atividadeEmpresaRepository
    ) {
        this.atividadeEmpresaService = atividadeEmpresaService;
        this.atividadeEmpresaRepository = atividadeEmpresaRepository;
    }

    /**
     * {@code POST  /atividade-empresas} : Create a new atividadeEmpresa.
     *
     * @param atividadeEmpresaDTO the atividadeEmpresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new atividadeEmpresaDTO, or with status {@code 400 (Bad Request)} if the atividadeEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AtividadeEmpresaDTO> createAtividadeEmpresa(@Valid @RequestBody AtividadeEmpresaDTO atividadeEmpresaDTO)
        throws URISyntaxException {
        log.debug("REST request to save AtividadeEmpresa : {}", atividadeEmpresaDTO);
        if (atividadeEmpresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new atividadeEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        atividadeEmpresaDTO = atividadeEmpresaService.save(atividadeEmpresaDTO);
        return ResponseEntity.created(new URI("/api/atividade-empresas/" + atividadeEmpresaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, atividadeEmpresaDTO.getId().toString()))
            .body(atividadeEmpresaDTO);
    }

    /**
     * {@code PUT  /atividade-empresas/:id} : Updates an existing atividadeEmpresa.
     *
     * @param id the id of the atividadeEmpresaDTO to save.
     * @param atividadeEmpresaDTO the atividadeEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atividadeEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the atividadeEmpresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the atividadeEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AtividadeEmpresaDTO> updateAtividadeEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AtividadeEmpresaDTO atividadeEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AtividadeEmpresa : {}, {}", id, atividadeEmpresaDTO);
        if (atividadeEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atividadeEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atividadeEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        atividadeEmpresaDTO = atividadeEmpresaService.update(atividadeEmpresaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atividadeEmpresaDTO.getId().toString()))
            .body(atividadeEmpresaDTO);
    }

    /**
     * {@code PATCH  /atividade-empresas/:id} : Partial updates given fields of an existing atividadeEmpresa, field will ignore if it is null
     *
     * @param id the id of the atividadeEmpresaDTO to save.
     * @param atividadeEmpresaDTO the atividadeEmpresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated atividadeEmpresaDTO,
     * or with status {@code 400 (Bad Request)} if the atividadeEmpresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the atividadeEmpresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the atividadeEmpresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AtividadeEmpresaDTO> partialUpdateAtividadeEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AtividadeEmpresaDTO atividadeEmpresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AtividadeEmpresa partially : {}, {}", id, atividadeEmpresaDTO);
        if (atividadeEmpresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, atividadeEmpresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!atividadeEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AtividadeEmpresaDTO> result = atividadeEmpresaService.partialUpdate(atividadeEmpresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, atividadeEmpresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /atividade-empresas} : get all the atividadeEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of atividadeEmpresas in body.
     */
    @GetMapping("")
    public List<AtividadeEmpresaDTO> getAllAtividadeEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AtividadeEmpresas");
        return atividadeEmpresaService.findAll();
    }

    /**
     * {@code GET  /atividade-empresas/:id} : get the "id" atividadeEmpresa.
     *
     * @param id the id of the atividadeEmpresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the atividadeEmpresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AtividadeEmpresaDTO> getAtividadeEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get AtividadeEmpresa : {}", id);
        Optional<AtividadeEmpresaDTO> atividadeEmpresaDTO = atividadeEmpresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(atividadeEmpresaDTO);
    }

    /**
     * {@code DELETE  /atividade-empresas/:id} : delete the "id" atividadeEmpresa.
     *
     * @param id the id of the atividadeEmpresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAtividadeEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete AtividadeEmpresa : {}", id);
        atividadeEmpresaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
