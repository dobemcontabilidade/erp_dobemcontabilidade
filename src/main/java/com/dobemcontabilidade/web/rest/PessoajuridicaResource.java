package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.PessoajuridicaRepository;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Pessoajuridica}.
 */
@RestController
@RequestMapping("/api/pessoajuridicas")
@Transactional
public class PessoajuridicaResource {

    private static final Logger log = LoggerFactory.getLogger(PessoajuridicaResource.class);

    private static final String ENTITY_NAME = "pessoajuridica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PessoajuridicaRepository pessoajuridicaRepository;

    public PessoajuridicaResource(PessoajuridicaRepository pessoajuridicaRepository) {
        this.pessoajuridicaRepository = pessoajuridicaRepository;
    }

    /**
     * {@code POST  /pessoajuridicas} : Create a new pessoajuridica.
     *
     * @param pessoajuridica the pessoajuridica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pessoajuridica, or with status {@code 400 (Bad Request)} if the pessoajuridica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Pessoajuridica> createPessoajuridica(@Valid @RequestBody Pessoajuridica pessoajuridica)
        throws URISyntaxException {
        log.debug("REST request to save Pessoajuridica : {}", pessoajuridica);
        if (pessoajuridica.getId() != null) {
            throw new BadRequestAlertException("A new pessoajuridica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pessoajuridica = pessoajuridicaRepository.save(pessoajuridica);
        return ResponseEntity.created(new URI("/api/pessoajuridicas/" + pessoajuridica.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pessoajuridica.getId().toString()))
            .body(pessoajuridica);
    }

    /**
     * {@code PUT  /pessoajuridicas/:id} : Updates an existing pessoajuridica.
     *
     * @param id the id of the pessoajuridica to save.
     * @param pessoajuridica the pessoajuridica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoajuridica,
     * or with status {@code 400 (Bad Request)} if the pessoajuridica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pessoajuridica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Pessoajuridica> updatePessoajuridica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Pessoajuridica pessoajuridica
    ) throws URISyntaxException {
        log.debug("REST request to update Pessoajuridica : {}, {}", id, pessoajuridica);
        if (pessoajuridica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoajuridica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoajuridicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pessoajuridica = pessoajuridicaRepository.save(pessoajuridica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoajuridica.getId().toString()))
            .body(pessoajuridica);
    }

    /**
     * {@code PATCH  /pessoajuridicas/:id} : Partial updates given fields of an existing pessoajuridica, field will ignore if it is null
     *
     * @param id the id of the pessoajuridica to save.
     * @param pessoajuridica the pessoajuridica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pessoajuridica,
     * or with status {@code 400 (Bad Request)} if the pessoajuridica is not valid,
     * or with status {@code 404 (Not Found)} if the pessoajuridica is not found,
     * or with status {@code 500 (Internal Server Error)} if the pessoajuridica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Pessoajuridica> partialUpdatePessoajuridica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Pessoajuridica pessoajuridica
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pessoajuridica partially : {}, {}", id, pessoajuridica);
        if (pessoajuridica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pessoajuridica.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pessoajuridicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Pessoajuridica> result = pessoajuridicaRepository
            .findById(pessoajuridica.getId())
            .map(existingPessoajuridica -> {
                if (pessoajuridica.getRazaoSocial() != null) {
                    existingPessoajuridica.setRazaoSocial(pessoajuridica.getRazaoSocial());
                }
                if (pessoajuridica.getNomeFantasia() != null) {
                    existingPessoajuridica.setNomeFantasia(pessoajuridica.getNomeFantasia());
                }
                if (pessoajuridica.getCnpj() != null) {
                    existingPessoajuridica.setCnpj(pessoajuridica.getCnpj());
                }

                return existingPessoajuridica;
            })
            .map(pessoajuridicaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pessoajuridica.getId().toString())
        );
    }

    /**
     * {@code GET  /pessoajuridicas} : get all the pessoajuridicas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pessoajuridicas in body.
     */
    @GetMapping("")
    public List<Pessoajuridica> getAllPessoajuridicas(@RequestParam(name = "filter", required = false) String filter) {
        if ("empresa-is-null".equals(filter)) {
            log.debug("REST request to get all Pessoajuridicas where empresa is null");
            return StreamSupport.stream(pessoajuridicaRepository.findAll().spliterator(), false)
                .filter(pessoajuridica -> pessoajuridica.getEmpresa() == null)
                .toList();
        }
        log.debug("REST request to get all Pessoajuridicas");
        return pessoajuridicaRepository.findAll();
    }

    /**
     * {@code GET  /pessoajuridicas/:id} : get the "id" pessoajuridica.
     *
     * @param id the id of the pessoajuridica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pessoajuridica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pessoajuridica> getPessoajuridica(@PathVariable("id") Long id) {
        log.debug("REST request to get Pessoajuridica : {}", id);
        Optional<Pessoajuridica> pessoajuridica = pessoajuridicaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pessoajuridica);
    }

    /**
     * {@code DELETE  /pessoajuridicas/:id} : delete the "id" pessoajuridica.
     *
     * @param id the id of the pessoajuridica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoajuridica(@PathVariable("id") Long id) {
        log.debug("REST request to delete Pessoajuridica : {}", id);
        pessoajuridicaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
