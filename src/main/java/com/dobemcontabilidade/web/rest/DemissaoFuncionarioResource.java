package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DemissaoFuncionario;
import com.dobemcontabilidade.repository.DemissaoFuncionarioRepository;
import com.dobemcontabilidade.service.DemissaoFuncionarioService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DemissaoFuncionario}.
 */
@RestController
@RequestMapping("/api/demissao-funcionarios")
public class DemissaoFuncionarioResource {

    private static final Logger log = LoggerFactory.getLogger(DemissaoFuncionarioResource.class);

    private static final String ENTITY_NAME = "demissaoFuncionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemissaoFuncionarioService demissaoFuncionarioService;

    private final DemissaoFuncionarioRepository demissaoFuncionarioRepository;

    public DemissaoFuncionarioResource(
        DemissaoFuncionarioService demissaoFuncionarioService,
        DemissaoFuncionarioRepository demissaoFuncionarioRepository
    ) {
        this.demissaoFuncionarioService = demissaoFuncionarioService;
        this.demissaoFuncionarioRepository = demissaoFuncionarioRepository;
    }

    /**
     * {@code POST  /demissao-funcionarios} : Create a new demissaoFuncionario.
     *
     * @param demissaoFuncionario the demissaoFuncionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demissaoFuncionario, or with status {@code 400 (Bad Request)} if the demissaoFuncionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemissaoFuncionario> createDemissaoFuncionario(@Valid @RequestBody DemissaoFuncionario demissaoFuncionario)
        throws URISyntaxException {
        log.debug("REST request to save DemissaoFuncionario : {}", demissaoFuncionario);
        if (demissaoFuncionario.getId() != null) {
            throw new BadRequestAlertException("A new demissaoFuncionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demissaoFuncionario = demissaoFuncionarioService.save(demissaoFuncionario);
        return ResponseEntity.created(new URI("/api/demissao-funcionarios/" + demissaoFuncionario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demissaoFuncionario.getId().toString()))
            .body(demissaoFuncionario);
    }

    /**
     * {@code PUT  /demissao-funcionarios/:id} : Updates an existing demissaoFuncionario.
     *
     * @param id the id of the demissaoFuncionario to save.
     * @param demissaoFuncionario the demissaoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demissaoFuncionario,
     * or with status {@code 400 (Bad Request)} if the demissaoFuncionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demissaoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemissaoFuncionario> updateDemissaoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemissaoFuncionario demissaoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to update DemissaoFuncionario : {}, {}", id, demissaoFuncionario);
        if (demissaoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demissaoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demissaoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demissaoFuncionario = demissaoFuncionarioService.update(demissaoFuncionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demissaoFuncionario.getId().toString()))
            .body(demissaoFuncionario);
    }

    /**
     * {@code PATCH  /demissao-funcionarios/:id} : Partial updates given fields of an existing demissaoFuncionario, field will ignore if it is null
     *
     * @param id the id of the demissaoFuncionario to save.
     * @param demissaoFuncionario the demissaoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demissaoFuncionario,
     * or with status {@code 400 (Bad Request)} if the demissaoFuncionario is not valid,
     * or with status {@code 404 (Not Found)} if the demissaoFuncionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the demissaoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemissaoFuncionario> partialUpdateDemissaoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemissaoFuncionario demissaoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemissaoFuncionario partially : {}, {}", id, demissaoFuncionario);
        if (demissaoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demissaoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demissaoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemissaoFuncionario> result = demissaoFuncionarioService.partialUpdate(demissaoFuncionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demissaoFuncionario.getId().toString())
        );
    }

    /**
     * {@code GET  /demissao-funcionarios} : get all the demissaoFuncionarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demissaoFuncionarios in body.
     */
    @GetMapping("")
    public List<DemissaoFuncionario> getAllDemissaoFuncionarios() {
        log.debug("REST request to get all DemissaoFuncionarios");
        return demissaoFuncionarioService.findAll();
    }

    /**
     * {@code GET  /demissao-funcionarios/:id} : get the "id" demissaoFuncionario.
     *
     * @param id the id of the demissaoFuncionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demissaoFuncionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemissaoFuncionario> getDemissaoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to get DemissaoFuncionario : {}", id);
        Optional<DemissaoFuncionario> demissaoFuncionario = demissaoFuncionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demissaoFuncionario);
    }

    /**
     * {@code DELETE  /demissao-funcionarios/:id} : delete the "id" demissaoFuncionario.
     *
     * @param id the id of the demissaoFuncionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemissaoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to delete DemissaoFuncionario : {}", id);
        demissaoFuncionarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
