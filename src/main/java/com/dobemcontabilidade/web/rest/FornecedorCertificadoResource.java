package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.FornecedorCertificado;
import com.dobemcontabilidade.repository.FornecedorCertificadoRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.FornecedorCertificado}.
 */
@RestController
@RequestMapping("/api/fornecedor-certificados")
@Transactional
public class FornecedorCertificadoResource {

    private static final Logger log = LoggerFactory.getLogger(FornecedorCertificadoResource.class);

    private static final String ENTITY_NAME = "fornecedorCertificado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FornecedorCertificadoRepository fornecedorCertificadoRepository;

    public FornecedorCertificadoResource(FornecedorCertificadoRepository fornecedorCertificadoRepository) {
        this.fornecedorCertificadoRepository = fornecedorCertificadoRepository;
    }

    /**
     * {@code POST  /fornecedor-certificados} : Create a new fornecedorCertificado.
     *
     * @param fornecedorCertificado the fornecedorCertificado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedorCertificado, or with status {@code 400 (Bad Request)} if the fornecedorCertificado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FornecedorCertificado> createFornecedorCertificado(
        @Valid @RequestBody FornecedorCertificado fornecedorCertificado
    ) throws URISyntaxException {
        log.debug("REST request to save FornecedorCertificado : {}", fornecedorCertificado);
        if (fornecedorCertificado.getId() != null) {
            throw new BadRequestAlertException("A new fornecedorCertificado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fornecedorCertificado = fornecedorCertificadoRepository.save(fornecedorCertificado);
        return ResponseEntity.created(new URI("/api/fornecedor-certificados/" + fornecedorCertificado.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fornecedorCertificado.getId().toString()))
            .body(fornecedorCertificado);
    }

    /**
     * {@code PUT  /fornecedor-certificados/:id} : Updates an existing fornecedorCertificado.
     *
     * @param id the id of the fornecedorCertificado to save.
     * @param fornecedorCertificado the fornecedorCertificado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorCertificado,
     * or with status {@code 400 (Bad Request)} if the fornecedorCertificado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorCertificado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorCertificado> updateFornecedorCertificado(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FornecedorCertificado fornecedorCertificado
    ) throws URISyntaxException {
        log.debug("REST request to update FornecedorCertificado : {}, {}", id, fornecedorCertificado);
        if (fornecedorCertificado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorCertificado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorCertificadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fornecedorCertificado = fornecedorCertificadoRepository.save(fornecedorCertificado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorCertificado.getId().toString()))
            .body(fornecedorCertificado);
    }

    /**
     * {@code PATCH  /fornecedor-certificados/:id} : Partial updates given fields of an existing fornecedorCertificado, field will ignore if it is null
     *
     * @param id the id of the fornecedorCertificado to save.
     * @param fornecedorCertificado the fornecedorCertificado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorCertificado,
     * or with status {@code 400 (Bad Request)} if the fornecedorCertificado is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedorCertificado is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorCertificado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FornecedorCertificado> partialUpdateFornecedorCertificado(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FornecedorCertificado fornecedorCertificado
    ) throws URISyntaxException {
        log.debug("REST request to partial update FornecedorCertificado partially : {}, {}", id, fornecedorCertificado);
        if (fornecedorCertificado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorCertificado.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorCertificadoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FornecedorCertificado> result = fornecedorCertificadoRepository
            .findById(fornecedorCertificado.getId())
            .map(existingFornecedorCertificado -> {
                if (fornecedorCertificado.getRazaoSocial() != null) {
                    existingFornecedorCertificado.setRazaoSocial(fornecedorCertificado.getRazaoSocial());
                }
                if (fornecedorCertificado.getSigla() != null) {
                    existingFornecedorCertificado.setSigla(fornecedorCertificado.getSigla());
                }
                if (fornecedorCertificado.getDescricao() != null) {
                    existingFornecedorCertificado.setDescricao(fornecedorCertificado.getDescricao());
                }

                return existingFornecedorCertificado;
            })
            .map(fornecedorCertificadoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorCertificado.getId().toString())
        );
    }

    /**
     * {@code GET  /fornecedor-certificados} : get all the fornecedorCertificados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedorCertificados in body.
     */
    @GetMapping("")
    public List<FornecedorCertificado> getAllFornecedorCertificados() {
        log.debug("REST request to get all FornecedorCertificados");
        return fornecedorCertificadoRepository.findAll();
    }

    /**
     * {@code GET  /fornecedor-certificados/:id} : get the "id" fornecedorCertificado.
     *
     * @param id the id of the fornecedorCertificado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedorCertificado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorCertificado> getFornecedorCertificado(@PathVariable("id") Long id) {
        log.debug("REST request to get FornecedorCertificado : {}", id);
        Optional<FornecedorCertificado> fornecedorCertificado = fornecedorCertificadoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fornecedorCertificado);
    }

    /**
     * {@code DELETE  /fornecedor-certificados/:id} : delete the "id" fornecedorCertificado.
     *
     * @param id the id of the fornecedorCertificado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedorCertificado(@PathVariable("id") Long id) {
        log.debug("REST request to delete FornecedorCertificado : {}", id);
        fornecedorCertificadoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
