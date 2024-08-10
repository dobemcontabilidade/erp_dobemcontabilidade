package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.BancoContadorRepository;
import com.dobemcontabilidade.service.BancoContadorService;
import com.dobemcontabilidade.service.dto.BancoContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.BancoContador}.
 */
@RestController
@RequestMapping("/api/banco-contadors")
public class BancoContadorResource {

    private static final Logger log = LoggerFactory.getLogger(BancoContadorResource.class);

    private static final String ENTITY_NAME = "bancoContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BancoContadorService bancoContadorService;

    private final BancoContadorRepository bancoContadorRepository;

    public BancoContadorResource(BancoContadorService bancoContadorService, BancoContadorRepository bancoContadorRepository) {
        this.bancoContadorService = bancoContadorService;
        this.bancoContadorRepository = bancoContadorRepository;
    }

    /**
     * {@code POST  /banco-contadors} : Create a new bancoContador.
     *
     * @param bancoContadorDTO the bancoContadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bancoContadorDTO, or with status {@code 400 (Bad Request)} if the bancoContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BancoContadorDTO> createBancoContador(@Valid @RequestBody BancoContadorDTO bancoContadorDTO)
        throws URISyntaxException {
        log.debug("REST request to save BancoContador : {}", bancoContadorDTO);
        if (bancoContadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new bancoContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bancoContadorDTO = bancoContadorService.save(bancoContadorDTO);
        return ResponseEntity.created(new URI("/api/banco-contadors/" + bancoContadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bancoContadorDTO.getId().toString()))
            .body(bancoContadorDTO);
    }

    /**
     * {@code PUT  /banco-contadors/:id} : Updates an existing bancoContador.
     *
     * @param id the id of the bancoContadorDTO to save.
     * @param bancoContadorDTO the bancoContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bancoContadorDTO,
     * or with status {@code 400 (Bad Request)} if the bancoContadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bancoContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BancoContadorDTO> updateBancoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BancoContadorDTO bancoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BancoContador : {}, {}", id, bancoContadorDTO);
        if (bancoContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bancoContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bancoContadorDTO = bancoContadorService.update(bancoContadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bancoContadorDTO.getId().toString()))
            .body(bancoContadorDTO);
    }

    /**
     * {@code PATCH  /banco-contadors/:id} : Partial updates given fields of an existing bancoContador, field will ignore if it is null
     *
     * @param id the id of the bancoContadorDTO to save.
     * @param bancoContadorDTO the bancoContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bancoContadorDTO,
     * or with status {@code 400 (Bad Request)} if the bancoContadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bancoContadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bancoContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BancoContadorDTO> partialUpdateBancoContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BancoContadorDTO bancoContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BancoContador partially : {}, {}", id, bancoContadorDTO);
        if (bancoContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bancoContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BancoContadorDTO> result = bancoContadorService.partialUpdate(bancoContadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bancoContadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /banco-contadors} : get all the bancoContadors.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bancoContadors in body.
     */
    @GetMapping("")
    public List<BancoContadorDTO> getAllBancoContadors(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all BancoContadors");
        return bancoContadorService.findAll();
    }

    /**
     * {@code GET  /banco-contadors/:id} : get the "id" bancoContador.
     *
     * @param id the id of the bancoContadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bancoContadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BancoContadorDTO> getBancoContador(@PathVariable("id") Long id) {
        log.debug("REST request to get BancoContador : {}", id);
        Optional<BancoContadorDTO> bancoContadorDTO = bancoContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bancoContadorDTO);
    }

    /**
     * {@code DELETE  /banco-contadors/:id} : delete the "id" bancoContador.
     *
     * @param id the id of the bancoContadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBancoContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete BancoContador : {}", id);
        bancoContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
