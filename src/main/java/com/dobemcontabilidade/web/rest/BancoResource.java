package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.repository.BancoRepository;
import com.dobemcontabilidade.service.BancoService;
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
     * @param banco the banco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banco, or with status {@code 400 (Bad Request)} if the banco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Banco> createBanco(@Valid @RequestBody Banco banco) throws URISyntaxException {
        log.debug("REST request to save Banco : {}", banco);
        if (banco.getId() != null) {
            throw new BadRequestAlertException("A new banco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        banco = bancoService.save(banco);
        return ResponseEntity.created(new URI("/api/bancos/" + banco.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, banco.getId().toString()))
            .body(banco);
    }

    /**
     * {@code PUT  /bancos/:id} : Updates an existing banco.
     *
     * @param id the id of the banco to save.
     * @param banco the banco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banco,
     * or with status {@code 400 (Bad Request)} if the banco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Banco> updateBanco(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Banco banco)
        throws URISyntaxException {
        log.debug("REST request to update Banco : {}, {}", id, banco);
        if (banco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banco.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        banco = bancoService.update(banco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banco.getId().toString()))
            .body(banco);
    }

    /**
     * {@code PATCH  /bancos/:id} : Partial updates given fields of an existing banco, field will ignore if it is null
     *
     * @param id the id of the banco to save.
     * @param banco the banco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banco,
     * or with status {@code 400 (Bad Request)} if the banco is not valid,
     * or with status {@code 404 (Not Found)} if the banco is not found,
     * or with status {@code 500 (Internal Server Error)} if the banco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Banco> partialUpdateBanco(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Banco banco
    ) throws URISyntaxException {
        log.debug("REST request to partial update Banco partially : {}, {}", id, banco);
        if (banco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banco.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bancoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Banco> result = bancoService.partialUpdate(banco);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banco.getId().toString())
        );
    }

    /**
     * {@code GET  /bancos} : get all the bancos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bancos in body.
     */
    @GetMapping("")
    public List<Banco> getAllBancos() {
        log.debug("REST request to get all Bancos");
        return bancoService.findAll();
    }

    /**
     * {@code GET  /bancos/:id} : get the "id" banco.
     *
     * @param id the id of the banco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Banco> getBanco(@PathVariable("id") Long id) {
        log.debug("REST request to get Banco : {}", id);
        Optional<Banco> banco = bancoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banco);
    }

    /**
     * {@code DELETE  /bancos/:id} : delete the "id" banco.
     *
     * @param id the id of the banco to delete.
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
