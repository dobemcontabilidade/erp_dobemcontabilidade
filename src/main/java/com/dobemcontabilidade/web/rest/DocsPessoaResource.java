package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DocsPessoa;
import com.dobemcontabilidade.repository.DocsPessoaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DocsPessoa}.
 */
@RestController
@RequestMapping("/api/docs-pessoas")
@Transactional
public class DocsPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(DocsPessoaResource.class);

    private static final String ENTITY_NAME = "docsPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocsPessoaRepository docsPessoaRepository;

    public DocsPessoaResource(DocsPessoaRepository docsPessoaRepository) {
        this.docsPessoaRepository = docsPessoaRepository;
    }

    /**
     * {@code POST  /docs-pessoas} : Create a new docsPessoa.
     *
     * @param docsPessoa the docsPessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docsPessoa, or with status {@code 400 (Bad Request)} if the docsPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocsPessoa> createDocsPessoa(@Valid @RequestBody DocsPessoa docsPessoa) throws URISyntaxException {
        log.debug("REST request to save DocsPessoa : {}", docsPessoa);
        if (docsPessoa.getId() != null) {
            throw new BadRequestAlertException("A new docsPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        docsPessoa = docsPessoaRepository.save(docsPessoa);
        return ResponseEntity.created(new URI("/api/docs-pessoas/" + docsPessoa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, docsPessoa.getId().toString()))
            .body(docsPessoa);
    }

    /**
     * {@code PUT  /docs-pessoas/:id} : Updates an existing docsPessoa.
     *
     * @param id the id of the docsPessoa to save.
     * @param docsPessoa the docsPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsPessoa,
     * or with status {@code 400 (Bad Request)} if the docsPessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docsPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocsPessoa> updateDocsPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DocsPessoa docsPessoa
    ) throws URISyntaxException {
        log.debug("REST request to update DocsPessoa : {}, {}", id, docsPessoa);
        if (docsPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docsPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docsPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        docsPessoa = docsPessoaRepository.save(docsPessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docsPessoa.getId().toString()))
            .body(docsPessoa);
    }

    /**
     * {@code PATCH  /docs-pessoas/:id} : Partial updates given fields of an existing docsPessoa, field will ignore if it is null
     *
     * @param id the id of the docsPessoa to save.
     * @param docsPessoa the docsPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsPessoa,
     * or with status {@code 400 (Bad Request)} if the docsPessoa is not valid,
     * or with status {@code 404 (Not Found)} if the docsPessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the docsPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocsPessoa> partialUpdateDocsPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DocsPessoa docsPessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update DocsPessoa partially : {}, {}", id, docsPessoa);
        if (docsPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docsPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docsPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocsPessoa> result = docsPessoaRepository
            .findById(docsPessoa.getId())
            .map(existingDocsPessoa -> {
                if (docsPessoa.getUrlArquivo() != null) {
                    existingDocsPessoa.setUrlArquivo(docsPessoa.getUrlArquivo());
                }
                if (docsPessoa.getTipo() != null) {
                    existingDocsPessoa.setTipo(docsPessoa.getTipo());
                }
                if (docsPessoa.getDescricao() != null) {
                    existingDocsPessoa.setDescricao(docsPessoa.getDescricao());
                }

                return existingDocsPessoa;
            })
            .map(docsPessoaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docsPessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /docs-pessoas} : get all the docsPessoas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docsPessoas in body.
     */
    @GetMapping("")
    public List<DocsPessoa> getAllDocsPessoas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all DocsPessoas");
        if (eagerload) {
            return docsPessoaRepository.findAllWithEagerRelationships();
        } else {
            return docsPessoaRepository.findAll();
        }
    }

    /**
     * {@code GET  /docs-pessoas/:id} : get the "id" docsPessoa.
     *
     * @param id the id of the docsPessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docsPessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocsPessoa> getDocsPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get DocsPessoa : {}", id);
        Optional<DocsPessoa> docsPessoa = docsPessoaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(docsPessoa);
    }

    /**
     * {@code DELETE  /docs-pessoas/:id} : delete the "id" docsPessoa.
     *
     * @param id the id of the docsPessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocsPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete DocsPessoa : {}", id);
        docsPessoaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
