package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.ContratoFuncionario;
import com.dobemcontabilidade.repository.ContratoFuncionarioRepository;
import com.dobemcontabilidade.service.ContratoFuncionarioService;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.ContratoFuncionario}.
 */
@RestController
@RequestMapping("/api/contrato-funcionarios")
public class ContratoFuncionarioResource {

    private static final Logger log = LoggerFactory.getLogger(ContratoFuncionarioResource.class);

    private static final String ENTITY_NAME = "contratoFuncionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratoFuncionarioService contratoFuncionarioService;

    private final ContratoFuncionarioRepository contratoFuncionarioRepository;

    public ContratoFuncionarioResource(
        ContratoFuncionarioService contratoFuncionarioService,
        ContratoFuncionarioRepository contratoFuncionarioRepository
    ) {
        this.contratoFuncionarioService = contratoFuncionarioService;
        this.contratoFuncionarioRepository = contratoFuncionarioRepository;
    }

    /**
     * {@code POST  /contrato-funcionarios} : Create a new contratoFuncionario.
     *
     * @param contratoFuncionario the contratoFuncionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratoFuncionario, or with status {@code 400 (Bad Request)} if the contratoFuncionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContratoFuncionario> createContratoFuncionario(@Valid @RequestBody ContratoFuncionario contratoFuncionario)
        throws URISyntaxException {
        log.debug("REST request to save ContratoFuncionario : {}", contratoFuncionario);
        if (contratoFuncionario.getId() != null) {
            throw new BadRequestAlertException("A new contratoFuncionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contratoFuncionario = contratoFuncionarioService.save(contratoFuncionario);
        return ResponseEntity.created(new URI("/api/contrato-funcionarios/" + contratoFuncionario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contratoFuncionario.getId().toString()))
            .body(contratoFuncionario);
    }

    /**
     * {@code PUT  /contrato-funcionarios/:id} : Updates an existing contratoFuncionario.
     *
     * @param id the id of the contratoFuncionario to save.
     * @param contratoFuncionario the contratoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratoFuncionario,
     * or with status {@code 400 (Bad Request)} if the contratoFuncionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContratoFuncionario> updateContratoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContratoFuncionario contratoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to update ContratoFuncionario : {}, {}", id, contratoFuncionario);
        if (contratoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contratoFuncionario = contratoFuncionarioService.update(contratoFuncionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratoFuncionario.getId().toString()))
            .body(contratoFuncionario);
    }

    /**
     * {@code PATCH  /contrato-funcionarios/:id} : Partial updates given fields of an existing contratoFuncionario, field will ignore if it is null
     *
     * @param id the id of the contratoFuncionario to save.
     * @param contratoFuncionario the contratoFuncionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratoFuncionario,
     * or with status {@code 400 (Bad Request)} if the contratoFuncionario is not valid,
     * or with status {@code 404 (Not Found)} if the contratoFuncionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the contratoFuncionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContratoFuncionario> partialUpdateContratoFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContratoFuncionario contratoFuncionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContratoFuncionario partially : {}, {}", id, contratoFuncionario);
        if (contratoFuncionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratoFuncionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratoFuncionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContratoFuncionario> result = contratoFuncionarioService.partialUpdate(contratoFuncionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratoFuncionario.getId().toString())
        );
    }

    /**
     * {@code GET  /contrato-funcionarios} : get all the contratoFuncionarios.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contratoFuncionarios in body.
     */
    @GetMapping("")
    public List<ContratoFuncionario> getAllContratoFuncionarios(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all ContratoFuncionarios");
        return contratoFuncionarioService.findAll();
    }

    /**
     * {@code GET  /contrato-funcionarios/:id} : get the "id" contratoFuncionario.
     *
     * @param id the id of the contratoFuncionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratoFuncionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratoFuncionario> getContratoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to get ContratoFuncionario : {}", id);
        Optional<ContratoFuncionario> contratoFuncionario = contratoFuncionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratoFuncionario);
    }

    /**
     * {@code DELETE  /contrato-funcionarios/:id} : delete the "id" contratoFuncionario.
     *
     * @param id the id of the contratoFuncionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContratoFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to delete ContratoFuncionario : {}", id);
        contratoFuncionarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
