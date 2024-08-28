package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EnderecoEmpresa}.
 */
@RestController
@RequestMapping("/api/endereco-empresas")
@Transactional
public class EnderecoEmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(EnderecoEmpresaResource.class);

    private static final String ENTITY_NAME = "enderecoEmpresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoEmpresaRepository enderecoEmpresaRepository;

    public EnderecoEmpresaResource(EnderecoEmpresaRepository enderecoEmpresaRepository) {
        this.enderecoEmpresaRepository = enderecoEmpresaRepository;
    }

    /**
     * {@code POST  /endereco-empresas} : Create a new enderecoEmpresa.
     *
     * @param enderecoEmpresa the enderecoEmpresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoEmpresa, or with status {@code 400 (Bad Request)} if the enderecoEmpresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EnderecoEmpresa> createEnderecoEmpresa(@Valid @RequestBody EnderecoEmpresa enderecoEmpresa)
        throws URISyntaxException {
        log.debug("REST request to save EnderecoEmpresa : {}", enderecoEmpresa);
        if (enderecoEmpresa.getId() != null) {
            throw new BadRequestAlertException("A new enderecoEmpresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enderecoEmpresa = enderecoEmpresaRepository.save(enderecoEmpresa);
        return ResponseEntity.created(new URI("/api/endereco-empresas/" + enderecoEmpresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enderecoEmpresa.getId().toString()))
            .body(enderecoEmpresa);
    }

    /**
     * {@code PUT  /endereco-empresas/:id} : Updates an existing enderecoEmpresa.
     *
     * @param id the id of the enderecoEmpresa to save.
     * @param enderecoEmpresa the enderecoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoEmpresa,
     * or with status {@code 400 (Bad Request)} if the enderecoEmpresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoEmpresa> updateEnderecoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnderecoEmpresa enderecoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to update EnderecoEmpresa : {}, {}", id, enderecoEmpresa);
        if (enderecoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enderecoEmpresa = enderecoEmpresaRepository.save(enderecoEmpresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoEmpresa.getId().toString()))
            .body(enderecoEmpresa);
    }

    /**
     * {@code PATCH  /endereco-empresas/:id} : Partial updates given fields of an existing enderecoEmpresa, field will ignore if it is null
     *
     * @param id the id of the enderecoEmpresa to save.
     * @param enderecoEmpresa the enderecoEmpresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoEmpresa,
     * or with status {@code 400 (Bad Request)} if the enderecoEmpresa is not valid,
     * or with status {@code 404 (Not Found)} if the enderecoEmpresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the enderecoEmpresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnderecoEmpresa> partialUpdateEnderecoEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnderecoEmpresa enderecoEmpresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnderecoEmpresa partially : {}, {}", id, enderecoEmpresa);
        if (enderecoEmpresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoEmpresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoEmpresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnderecoEmpresa> result = enderecoEmpresaRepository
            .findById(enderecoEmpresa.getId())
            .map(existingEnderecoEmpresa -> {
                if (enderecoEmpresa.getLogradouro() != null) {
                    existingEnderecoEmpresa.setLogradouro(enderecoEmpresa.getLogradouro());
                }
                if (enderecoEmpresa.getNumero() != null) {
                    existingEnderecoEmpresa.setNumero(enderecoEmpresa.getNumero());
                }
                if (enderecoEmpresa.getComplemento() != null) {
                    existingEnderecoEmpresa.setComplemento(enderecoEmpresa.getComplemento());
                }
                if (enderecoEmpresa.getBairro() != null) {
                    existingEnderecoEmpresa.setBairro(enderecoEmpresa.getBairro());
                }
                if (enderecoEmpresa.getCep() != null) {
                    existingEnderecoEmpresa.setCep(enderecoEmpresa.getCep());
                }
                if (enderecoEmpresa.getPrincipal() != null) {
                    existingEnderecoEmpresa.setPrincipal(enderecoEmpresa.getPrincipal());
                }
                if (enderecoEmpresa.getFilial() != null) {
                    existingEnderecoEmpresa.setFilial(enderecoEmpresa.getFilial());
                }
                if (enderecoEmpresa.getEnderecoFiscal() != null) {
                    existingEnderecoEmpresa.setEnderecoFiscal(enderecoEmpresa.getEnderecoFiscal());
                }

                return existingEnderecoEmpresa;
            })
            .map(enderecoEmpresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoEmpresa.getId().toString())
        );
    }

    /**
     * {@code GET  /endereco-empresas} : get all the enderecoEmpresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecoEmpresas in body.
     */
    @GetMapping("")
    public List<EnderecoEmpresa> getAllEnderecoEmpresas(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all EnderecoEmpresas");
        if (eagerload) {
            return enderecoEmpresaRepository.findAllWithEagerRelationships();
        } else {
            return enderecoEmpresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /endereco-empresas/:id} : get the "id" enderecoEmpresa.
     *
     * @param id the id of the enderecoEmpresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoEmpresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoEmpresa> getEnderecoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get EnderecoEmpresa : {}", id);
        Optional<EnderecoEmpresa> enderecoEmpresa = enderecoEmpresaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(enderecoEmpresa);
    }

    /**
     * {@code DELETE  /endereco-empresas/:id} : delete the "id" enderecoEmpresa.
     *
     * @param id the id of the enderecoEmpresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnderecoEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete EnderecoEmpresa : {}", id);
        enderecoEmpresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
