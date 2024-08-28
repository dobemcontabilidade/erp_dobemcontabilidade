package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.repository.TributacaoRepository;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Tributacao}.
 */
@RestController
@RequestMapping("/api/tributacaos")
@Transactional
public class TributacaoResource {

    private static final Logger log = LoggerFactory.getLogger(TributacaoResource.class);

    private static final String ENTITY_NAME = "tributacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TributacaoRepository tributacaoRepository;

    public TributacaoResource(TributacaoRepository tributacaoRepository) {
        this.tributacaoRepository = tributacaoRepository;
    }

    /**
     * {@code POST  /tributacaos} : Create a new tributacao.
     *
     * @param tributacao the tributacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tributacao, or with status {@code 400 (Bad Request)} if the tributacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Tributacao> createTributacao(@RequestBody Tributacao tributacao) throws URISyntaxException {
        log.debug("REST request to save Tributacao : {}", tributacao);
        if (tributacao.getId() != null) {
            throw new BadRequestAlertException("A new tributacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tributacao = tributacaoRepository.save(tributacao);
        return ResponseEntity.created(new URI("/api/tributacaos/" + tributacao.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tributacao.getId().toString()))
            .body(tributacao);
    }

    /**
     * {@code PUT  /tributacaos/:id} : Updates an existing tributacao.
     *
     * @param id the id of the tributacao to save.
     * @param tributacao the tributacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tributacao,
     * or with status {@code 400 (Bad Request)} if the tributacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tributacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tributacao> updateTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tributacao tributacao
    ) throws URISyntaxException {
        log.debug("REST request to update Tributacao : {}, {}", id, tributacao);
        if (tributacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tributacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tributacao = tributacaoRepository.save(tributacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tributacao.getId().toString()))
            .body(tributacao);
    }

    /**
     * {@code PATCH  /tributacaos/:id} : Partial updates given fields of an existing tributacao, field will ignore if it is null
     *
     * @param id the id of the tributacao to save.
     * @param tributacao the tributacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tributacao,
     * or with status {@code 400 (Bad Request)} if the tributacao is not valid,
     * or with status {@code 404 (Not Found)} if the tributacao is not found,
     * or with status {@code 500 (Internal Server Error)} if the tributacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Tributacao> partialUpdateTributacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Tributacao tributacao
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tributacao partially : {}, {}", id, tributacao);
        if (tributacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tributacao.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tributacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Tributacao> result = tributacaoRepository
            .findById(tributacao.getId())
            .map(existingTributacao -> {
                if (tributacao.getNome() != null) {
                    existingTributacao.setNome(tributacao.getNome());
                }
                if (tributacao.getDescricao() != null) {
                    existingTributacao.setDescricao(tributacao.getDescricao());
                }
                if (tributacao.getSituacao() != null) {
                    existingTributacao.setSituacao(tributacao.getSituacao());
                }

                return existingTributacao;
            })
            .map(tributacaoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tributacao.getId().toString())
        );
    }

    /**
     * {@code GET  /tributacaos} : get all the tributacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tributacaos in body.
     */
    @GetMapping("")
    public List<Tributacao> getAllTributacaos() {
        log.debug("REST request to get all Tributacaos");
        return tributacaoRepository.findAll();
    }

    /**
     * {@code GET  /tributacaos/:id} : get the "id" tributacao.
     *
     * @param id the id of the tributacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tributacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tributacao> getTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to get Tributacao : {}", id);
        Optional<Tributacao> tributacao = tributacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tributacao);
    }

    /**
     * {@code DELETE  /tributacaos/:id} : delete the "id" tributacao.
     *
     * @param id the id of the tributacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTributacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tributacao : {}", id);
        tributacaoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
