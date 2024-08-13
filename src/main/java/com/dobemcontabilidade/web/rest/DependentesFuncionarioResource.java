package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.DependentesFuncionario;
import com.dobemcontabilidade.repository.DependentesFuncionarioRepository;
import com.dobemcontabilidade.service.DependentesFuncionarioService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.DependentesFuncionario}.
 */
@RestController
@RequestMapping("/api/dependentes-funcionarios")
public class DependentesFuncionarioResource {

    private static final Logger log = LoggerFactory.getLogger(DependentesFuncionarioResource.class);

    private static final String ENTITY_NAME = "dependentesFuncionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DependentesFuncionarioService dependentesFuncionarioService;

    private final DependentesFuncionarioRepository dependentesFuncionarioRepository;

    public DependentesFuncionarioResource(
        DependentesFuncionarioService dependentesFuncionarioService,
        DependentesFuncionarioRepository dependentesFuncionarioRepository
    ) {
        this.dependentesFuncionarioService = dependentesFuncionarioService;
        this.dependentesFuncionarioRepository = dependentesFuncionarioRepository;
    }

    /**
     * {@code POST  /dependentes-funcionarios} : Create a new dependentesFuncionario.
     *
     * @param dependentesFuncionario the dependentesFuncionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dependentesFuncionario, or with status {@code 400 (Bad Request)} if the dependentesFuncionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DependentesFuncionario> createDependentesFuncionario(
        @Valid @RequestBody DependentesFuncionario dependentesFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to save DependentesFuncionario : {}", dependentesFuncionario);
        if (dependentesFuncionario.getId() != null) {
            throw new BadRequestAlertException("A new dependentesFuncionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dependentesFuncionario = dependentesFuncionarioService.save(dependentesFuncionario);
        return ResponseEntity.created(new URI("/api/dependentes-funcionarios/" + dependentesFuncionario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dependentesFuncionario.getId().toString()))
            .body(dependentesFuncionario);
    }

    /**
     * {@code PUT  /dependentes-funcionarios/:id} : Updates an existing dependentesFuncionario.
     *
     * @param id the id of the dependentesFuncionario to save.
     * @param dependentesFuncionario the dependentesFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependentesFuncionario,
     * or with status {@code 400 (Bad Request)} if the dependentesFuncionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dependentesFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DependentesFuncionario> updateDependentesFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DependentesFuncionario dependentesFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to update DependentesFuncionario : {}, {}", id, dependentesFuncionario);
        if (dependentesFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dependentesFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dependentesFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dependentesFuncionario = dependentesFuncionarioService.update(dependentesFuncionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dependentesFuncionario.getId().toString()))
            .body(dependentesFuncionario);
    }

    /**
     * {@code PATCH  /dependentes-funcionarios/:id} : Partial updates given fields of an existing dependentesFuncionario, field will ignore if it is null
     *
     * @param id the id of the dependentesFuncionario to save.
     * @param dependentesFuncionario the dependentesFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dependentesFuncionario,
     * or with status {@code 400 (Bad Request)} if the dependentesFuncionario is not valid,
     * or with status {@code 404 (Not Found)} if the dependentesFuncionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the dependentesFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DependentesFuncionario> partialUpdateDependentesFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DependentesFuncionario dependentesFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update DependentesFuncionario partially : {}, {}", id, dependentesFuncionario);
        if (dependentesFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dependentesFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dependentesFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DependentesFuncionario> result = dependentesFuncionarioService.partialUpdate(dependentesFuncionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dependentesFuncionario.getId().toString())
        );
    }

    /**
     * {@code GET  /dependentes-funcionarios} : get all the dependentesFuncionarios.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dependentesFuncionarios in body.
     */
    @GetMapping("")
    public List<DependentesFuncionario> getAllDependentesFuncionarios(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all DependentesFuncionarios");
        return dependentesFuncionarioService.findAll();
    }

    /**
     * {@code GET  /dependentes-funcionarios/:id} : get the "id" dependentesFuncionario.
     *
     * @param id the id of the dependentesFuncionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dependentesFuncionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DependentesFuncionario> getDependentesFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to get DependentesFuncionario : {}", id);
        Optional<DependentesFuncionario> dependentesFuncionario = dependentesFuncionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dependentesFuncionario);
    }

    /**
     * {@code DELETE  /dependentes-funcionarios/:id} : delete the "id" dependentesFuncionario.
     *
     * @param id the id of the dependentesFuncionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDependentesFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to delete DependentesFuncionario : {}", id);
        dependentesFuncionarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
