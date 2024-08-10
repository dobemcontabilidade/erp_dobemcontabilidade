package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.BancoRepository;
import com.dobemcontabilidade.service.BancoService;
import com.dobemcontabilidade.service.dto.BancoDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Banco}.
 */
@RestController
@RequestMapping("/api/bancos")
public class BancoResource {

    private static final Logger log = LoggerFactory.getLogger(BancoResource.class);

    private static final String ENTITY_NAME = "banco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BancoService bancoService;

    private final BancoRepository bancoRepository;

    public BancoResource(BancoService bancoService, BancoRepository bancoRepository) {
        this.bancoService = bancoService;
        this.bancoRepository = bancoRepository;
    }

    /**
     * {@code POST  /bancos} : Create a new banco.
     *
     * @param bancoDTO the bancoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bancoDTO, or with status {@code 400 (Bad Request)} if the banco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BancoDTO> createBanco(@Valid @RequestBody BancoDTO bancoDTO) throws URISyntaxException {
        log.debug("REST request to save Banco : {}", bancoDTO);
        if (bancoDTO.getId() != null) {
            throw new BadRequestAlertException("A new banco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bancoDTO = bancoService.save(bancoDTO);
        return ResponseEntity.created(new URI("/api/bancos/" + bancoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bancoDTO.getId().toString()))
            .body(bancoDTO);
    }

    /**
     * {@code PUT  /bancos/:id} : Updates an existing banco.
     *
     * @param id the id of the bancoDTO to save.
     * @param bancoDTO the bancoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bancoDTO,
     * or with status {@code 400 (Bad Request)} if the bancoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bancoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BancoDTO> updateBanco(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BancoDTO bancoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Banco : {}, {}", id, bancoDTO);
        if (bancoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bancoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bancoDTO = bancoService.update(bancoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bancoDTO.getId().toString()))
            .body(bancoDTO);
    }

    /**
     * {@code PATCH  /bancos/:id} : Partial updates given fields of an existing banco, field will ignore if it is null
     *
     * @param id the id of the bancoDTO to save.
     * @param bancoDTO the bancoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bancoDTO,
     * or with status {@code 400 (Bad Request)} if the bancoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bancoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bancoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BancoDTO> partialUpdateBanco(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BancoDTO bancoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Banco partially : {}, {}", id, bancoDTO);
        if (bancoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bancoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BancoDTO> result = bancoService.partialUpdate(bancoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bancoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bancos} : get all the bancos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bancos in body.
     */
    @GetMapping("")
    public List<BancoDTO> getAllBancos() {
        log.debug("REST request to get all Bancos");
        return bancoService.findAll();
    }

    /**
     * {@code GET  /bancos/:id} : get the "id" banco.
     *
     * @param id the id of the bancoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bancoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BancoDTO> getBanco(@PathVariable("id") Long id) {
        log.debug("REST request to get Banco : {}", id);
        Optional<BancoDTO> bancoDTO = bancoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bancoDTO);
    }

    /**
     * {@code DELETE  /bancos/:id} : delete the "id" banco.
     *
     * @param id the id of the bancoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanco(@PathVariable("id") Long id) {
        log.debug("REST request to delete Banco : {}", id);
        bancoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
