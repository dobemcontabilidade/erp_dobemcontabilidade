package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EnderecoPessoa}.
 */
@RestController
@RequestMapping("/api/endereco-pessoas")
@Transactional
public class EnderecoPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(EnderecoPessoaResource.class);

    private static final String ENTITY_NAME = "enderecoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoPessoaRepository enderecoPessoaRepository;

    public EnderecoPessoaResource(EnderecoPessoaRepository enderecoPessoaRepository) {
        this.enderecoPessoaRepository = enderecoPessoaRepository;
    }

    /**
     * {@code POST  /endereco-pessoas} : Create a new enderecoPessoa.
     *
     * @param enderecoPessoa the enderecoPessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoPessoa, or with status {@code 400 (Bad Request)} if the enderecoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EnderecoPessoa> createEnderecoPessoa(@Valid @RequestBody EnderecoPessoa enderecoPessoa)
        throws URISyntaxException {
        log.debug("REST request to save EnderecoPessoa : {}", enderecoPessoa);
        if (enderecoPessoa.getId() != null) {
            throw new BadRequestAlertException("A new enderecoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enderecoPessoa = enderecoPessoaRepository.save(enderecoPessoa);
        return ResponseEntity.created(new URI("/api/endereco-pessoas/" + enderecoPessoa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enderecoPessoa.getId().toString()))
            .body(enderecoPessoa);
    }

    /**
     * {@code PUT  /endereco-pessoas/:id} : Updates an existing enderecoPessoa.
     *
     * @param id the id of the enderecoPessoa to save.
     * @param enderecoPessoa the enderecoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoPessoa,
     * or with status {@code 400 (Bad Request)} if the enderecoPessoa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoPessoa> updateEnderecoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnderecoPessoa enderecoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to update EnderecoPessoa : {}, {}", id, enderecoPessoa);
        if (enderecoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enderecoPessoa = enderecoPessoaRepository.save(enderecoPessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoPessoa.getId().toString()))
            .body(enderecoPessoa);
    }

    /**
     * {@code PATCH  /endereco-pessoas/:id} : Partial updates given fields of an existing enderecoPessoa, field will ignore if it is null
     *
     * @param id the id of the enderecoPessoa to save.
     * @param enderecoPessoa the enderecoPessoa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoPessoa,
     * or with status {@code 400 (Bad Request)} if the enderecoPessoa is not valid,
     * or with status {@code 404 (Not Found)} if the enderecoPessoa is not found,
     * or with status {@code 500 (Internal Server Error)} if the enderecoPessoa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnderecoPessoa> partialUpdateEnderecoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnderecoPessoa enderecoPessoa
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnderecoPessoa partially : {}, {}", id, enderecoPessoa);
        if (enderecoPessoa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoPessoa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnderecoPessoa> result = enderecoPessoaRepository
            .findById(enderecoPessoa.getId())
            .map(existingEnderecoPessoa -> {
                if (enderecoPessoa.getLogradouro() != null) {
                    existingEnderecoPessoa.setLogradouro(enderecoPessoa.getLogradouro());
                }
                if (enderecoPessoa.getNumero() != null) {
                    existingEnderecoPessoa.setNumero(enderecoPessoa.getNumero());
                }
                if (enderecoPessoa.getComplemento() != null) {
                    existingEnderecoPessoa.setComplemento(enderecoPessoa.getComplemento());
                }
                if (enderecoPessoa.getBairro() != null) {
                    existingEnderecoPessoa.setBairro(enderecoPessoa.getBairro());
                }
                if (enderecoPessoa.getCep() != null) {
                    existingEnderecoPessoa.setCep(enderecoPessoa.getCep());
                }
                if (enderecoPessoa.getPrincipal() != null) {
                    existingEnderecoPessoa.setPrincipal(enderecoPessoa.getPrincipal());
                }

                return existingEnderecoPessoa;
            })
            .map(enderecoPessoaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoPessoa.getId().toString())
        );
    }

    /**
     * {@code GET  /endereco-pessoas} : get all the enderecoPessoas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecoPessoas in body.
     */
    @GetMapping("")
    public List<EnderecoPessoa> getAllEnderecoPessoas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all EnderecoPessoas");
        if (eagerload) {
            return enderecoPessoaRepository.findAllWithEagerRelationships();
        } else {
            return enderecoPessoaRepository.findAll();
        }
    }

    /**
     * {@code GET  /endereco-pessoas/:id} : get the "id" enderecoPessoa.
     *
     * @param id the id of the enderecoPessoa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoPessoa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoPessoa> getEnderecoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get EnderecoPessoa : {}", id);
        Optional<EnderecoPessoa> enderecoPessoa = enderecoPessoaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(enderecoPessoa);
    }

    /**
     * {@code DELETE  /endereco-pessoas/:id} : delete the "id" enderecoPessoa.
     *
     * @param id the id of the enderecoPessoa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnderecoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete EnderecoPessoa : {}", id);
        enderecoPessoaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
