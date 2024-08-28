package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.EmpresaRepository;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Empresa}.
 */
@RestController
@RequestMapping("/api/empresas")
@Transactional
public class EmpresaResource {

    private static final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "empresa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresaRepository empresaRepository;

    public EmpresaResource(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /**
     * {@code POST  /empresas} : Create a new empresa.
     *
     * @param empresa the empresa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresa, or with status {@code 400 (Bad Request)} if the empresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Empresa> createEmpresa(@Valid @RequestBody Empresa empresa) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresa);
        if (empresa.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        empresa = empresaRepository.save(empresa);
        return ResponseEntity.created(new URI("/api/empresas/" + empresa.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, empresa.getId().toString()))
            .body(empresa);
    }

    /**
     * {@code PUT  /empresas/:id} : Updates an existing empresa.
     *
     * @param id the id of the empresa to save.
     * @param empresa the empresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresa,
     * or with status {@code 400 (Bad Request)} if the empresa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Empresa empresa
    ) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}, {}", id, empresa);
        if (empresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        empresa = empresaRepository.save(empresa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresa.getId().toString()))
            .body(empresa);
    }

    /**
     * {@code PATCH  /empresas/:id} : Partial updates given fields of an existing empresa, field will ignore if it is null
     *
     * @param id the id of the empresa to save.
     * @param empresa the empresa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresa,
     * or with status {@code 400 (Bad Request)} if the empresa is not valid,
     * or with status {@code 404 (Not Found)} if the empresa is not found,
     * or with status {@code 500 (Internal Server Error)} if the empresa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Empresa> partialUpdateEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Empresa empresa
    ) throws URISyntaxException {
        log.debug("REST request to partial update Empresa partially : {}, {}", id, empresa);
        if (empresa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresa.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Empresa> result = empresaRepository
            .findById(empresa.getId())
            .map(existingEmpresa -> {
                if (empresa.getRazaoSocial() != null) {
                    existingEmpresa.setRazaoSocial(empresa.getRazaoSocial());
                }
                if (empresa.getDescricaoDoNegocio() != null) {
                    existingEmpresa.setDescricaoDoNegocio(empresa.getDescricaoDoNegocio());
                }
                if (empresa.getDataAbertura() != null) {
                    existingEmpresa.setDataAbertura(empresa.getDataAbertura());
                }
                if (empresa.getUrlContratoSocial() != null) {
                    existingEmpresa.setUrlContratoSocial(empresa.getUrlContratoSocial());
                }
                if (empresa.getCapitalSocial() != null) {
                    existingEmpresa.setCapitalSocial(empresa.getCapitalSocial());
                }
                if (empresa.getCnae() != null) {
                    existingEmpresa.setCnae(empresa.getCnae());
                }

                return existingEmpresa;
            })
            .map(empresaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresa.getId().toString())
        );
    }

    /**
     * {@code GET  /empresas} : get all the empresas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresas in body.
     */
    @GetMapping("")
    public List<Empresa> getAllEmpresas(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Empresas");
        if (eagerload) {
            return empresaRepository.findAllWithEagerRelationships();
        } else {
            return empresaRepository.findAll();
        }
    }

    /**
     * {@code GET  /empresas/:id} : get the "id" empresa.
     *
     * @param id the id of the empresa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<Empresa> empresa = empresaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(empresa);
    }

    /**
     * {@code DELETE  /empresas/:id} : delete the "id" empresa.
     *
     * @param id the id of the empresa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable("id") Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
