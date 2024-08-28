package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.AdicionalTributacao;
import com.dobemcontabilidade.repository.AdicionalTributacaoRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.AdicionalTributacao}.
 */
@RestController
@RequestMapping("/api/adicional-tributacaos")
@Transactional
public class AdicionalTributacaoResource {

    private static final Logger log = LoggerFactory.getLogger(AdicionalTributacaoResource.class);

    private static final String ENTITY_NAME = "adicionalTributacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdicionalTributacaoRepository adicionalTributacaoRepository;

    public AdicionalTributacaoResource(AdicionalTributacaoRepository adicionalTributacaoRepository) {
        this.adicionalTributacaoRepository = adicionalTributacaoRepository;
    }

    /**
     * {@code POST  /adicional-tributacaos} : Create a new adicionalTributacao.
     *
     * @param adicionalTributacao the adicionalTributacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adicionalTributacao, or with status {@code 400 (Bad Request)} if the adicionalTributacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AdicionalTributacao> createAdicionalTributacao(@Valid @RequestBody AdicionalTributacao adicionalTributacao)
        throws URISyntaxException {
        log.debug("REST request to save AdicionalTributacao : {}", adicionalTributacao);
        if (adicionalTributacao.getId() != null) {
            throw new BadRequestAlertException("A new adicionalTributacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        adicionalTributacao = adicionalTributacaoRepository.save(adicionalTributacao);
        return ResponseEntity.created(new URI("/api/adicional-tributacaos/" + adicionalTributacao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, adicionalTributacao.getId().toString()))
            .body(adicionalTributacao);
    }

    /**
     * {@code PUT  /adicional-tributacaos/:id} : Updates an existing adicionalTributacao.
     *
     * @param id the id of the adicionalTributacao to save.
     * @param adicionalTributacao the adicionalTributacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalTributacao,
     * or with status {@code 400 (Bad Request)} if the adicionalTributacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adicionalTributacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdicionalTributacao> updateAdicionalTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdicionalTributacao adicionalTributacao
    ) throws URISyntaxException {
        log.debug("REST request to update AdicionalTributacao : {}, {}", id, adicionalTributacao);
        if (adicionalTributacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalTributacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalTributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        adicionalTributacao = adicionalTributacaoRepository.save(adicionalTributacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalTributacao.getId().toString()))
            .body(adicionalTributacao);
    }

    /**
     * {@code PATCH  /adicional-tributacaos/:id} : Partial updates given fields of an existing adicionalTributacao, field will ignore if it is null
     *
     * @param id the id of the adicionalTributacao to save.
     * @param adicionalTributacao the adicionalTributacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adicionalTributacao,
     * or with status {@code 400 (Bad Request)} if the adicionalTributacao is not valid,
     * or with status {@code 404 (Not Found)} if the adicionalTributacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the adicionalTributacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdicionalTributacao> partialUpdateAdicionalTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdicionalTributacao adicionalTributacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update AdicionalTributacao partially : {}, {}", id, adicionalTributacao);
        if (adicionalTributacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, adicionalTributacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!adicionalTributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdicionalTributacao> result = adicionalTributacaoRepository
            .findById(adicionalTributacao.getId())
            .map(existingAdicionalTributacao -> {
                if (adicionalTributacao.getValor() != null) {
                    existingAdicionalTributacao.setValor(adicionalTributacao.getValor());
                }

                return existingAdicionalTributacao;
            })
            .map(adicionalTributacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adicionalTributacao.getId().toString())
        );
    }

    /**
     * {@code GET  /adicional-tributacaos} : get all the adicionalTributacaos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adicionalTributacaos in body.
     */
    @GetMapping("")
    public List<AdicionalTributacao> getAllAdicionalTributacaos(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all AdicionalTributacaos");
        if (eagerload) {
            return adicionalTributacaoRepository.findAllWithEagerRelationships();
        } else {
            return adicionalTributacaoRepository.findAll();
        }
    }

    /**
     * {@code GET  /adicional-tributacaos/:id} : get the "id" adicionalTributacao.
     *
     * @param id the id of the adicionalTributacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adicionalTributacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdicionalTributacao> getAdicionalTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to get AdicionalTributacao : {}", id);
        Optional<AdicionalTributacao> adicionalTributacao = adicionalTributacaoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(adicionalTributacao);
    }

    /**
     * {@code DELETE  /adicional-tributacaos/:id} : delete the "id" adicionalTributacao.
     *
     * @param id the id of the adicionalTributacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdicionalTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete AdicionalTributacao : {}", id);
        adicionalTributacaoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
