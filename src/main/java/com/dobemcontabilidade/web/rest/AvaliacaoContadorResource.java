package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AvaliacaoContadorRepository;
import com.dobemcontabilidade.service.AvaliacaoContadorService;
import com.dobemcontabilidade.service.dto.AvaliacaoContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AvaliacaoContador}.
 */
@RestController
@RequestMapping("/api/avaliacao-contadors")
public class AvaliacaoContadorResource {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoContadorResource.class);

    private static final String ENTITY_NAME = "avaliacaoContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvaliacaoContadorService avaliacaoContadorService;

    private final AvaliacaoContadorRepository avaliacaoContadorRepository;

    public AvaliacaoContadorResource(
        AvaliacaoContadorService avaliacaoContadorService,
        AvaliacaoContadorRepository avaliacaoContadorRepository
    ) {
        this.avaliacaoContadorService = avaliacaoContadorService;
        this.avaliacaoContadorRepository = avaliacaoContadorRepository;
    }

    /**
     * {@code POST  /avaliacao-contadors} : Create a new avaliacaoContador.
     *
     * @param avaliacaoContadorDTO the avaliacaoContadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avaliacaoContadorDTO, or with status {@code 400 (Bad Request)} if the avaliacaoContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AvaliacaoContadorDTO> createAvaliacaoContador(@Valid @RequestBody AvaliacaoContadorDTO avaliacaoContadorDTO)
        throws URISyntaxException {
        log.debug("REST request to save AvaliacaoContador : {}", avaliacaoContadorDTO);
        if (avaliacaoContadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new avaliacaoContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        avaliacaoContadorDTO = avaliacaoContadorService.save(avaliacaoContadorDTO);
        return ResponseEntity.created(new URI("/api/avaliacao-contadors/" + avaliacaoContadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, avaliacaoContadorDTO.getId().toString()))
            .body(avaliacaoContadorDTO);
    }

    /**
     * {@code PUT  /avaliacao-contadors/:id} : Updates an existing avaliacaoContador.
     *
     * @param id the id of the avaliacaoContadorDTO to save.
     * @param avaliacaoContadorDTO the avaliacaoContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoContadorDTO,
     * or with status {@code 400 (Bad Request)} if the avaliacaoContadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoContadorDTO> updateAvaliacaoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AvaliacaoContadorDTO avaliacaoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AvaliacaoContador : {}, {}", id, avaliacaoContadorDTO);
        if (avaliacaoContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        avaliacaoContadorDTO = avaliacaoContadorService.update(avaliacaoContadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoContadorDTO.getId().toString()))
            .body(avaliacaoContadorDTO);
    }

    /**
     * {@code PATCH  /avaliacao-contadors/:id} : Partial updates given fields of an existing avaliacaoContador, field will ignore if it is null
     *
     * @param id the id of the avaliacaoContadorDTO to save.
     * @param avaliacaoContadorDTO the avaliacaoContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoContadorDTO,
     * or with status {@code 400 (Bad Request)} if the avaliacaoContadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the avaliacaoContadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvaliacaoContadorDTO> partialUpdateAvaliacaoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AvaliacaoContadorDTO avaliacaoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AvaliacaoContador partially : {}, {}", id, avaliacaoContadorDTO);
        if (avaliacaoContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvaliacaoContadorDTO> result = avaliacaoContadorService.partialUpdate(avaliacaoContadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoContadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /avaliacao-contadors} : get all the avaliacaoContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avaliacaoContadors in body.
     */
    @GetMapping("")
    public List<AvaliacaoContadorDTO> getAllAvaliacaoContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AvaliacaoContadors");
        return avaliacaoContadorService.findAll();
    }

    /**
     * {@code GET  /avaliacao-contadors/:id} : get the "id" avaliacaoContador.
     *
     * @param id the id of the avaliacaoContadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avaliacaoContadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoContadorDTO> getAvaliacaoContador(@PathVariable("id") Long id) {
        log.debug("REST request to get AvaliacaoContador : {}", id);
        Optional<AvaliacaoContadorDTO> avaliacaoContadorDTO = avaliacaoContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avaliacaoContadorDTO);
    }

    /**
     * {@code DELETE  /avaliacao-contadors/:id} : delete the "id" avaliacaoContador.
     *
     * @param id the id of the avaliacaoContadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacaoContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete AvaliacaoContador : {}", id);
        avaliacaoContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
