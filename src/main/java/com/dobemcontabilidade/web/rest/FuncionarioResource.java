package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.FuncionarioRepository;
import com.dobemcontabilidade.service.FuncionarioQueryService;
import com.dobemcontabilidade.service.FuncionarioService;
import com.dobemcontabilidade.service.criteria.FuncionarioCriteria;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dobemcontabilidade.domain.Funcionario}.
 */
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioResource {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private static final String ENTITY_NAME = "funcionario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionarioService funcionarioService;

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioQueryService funcionarioQueryService;

    public FuncionarioResource(
        FuncionarioService funcionarioService,
        FuncionarioRepository funcionarioRepository,
        FuncionarioQueryService funcionarioQueryService
    ) {
        this.funcionarioService = funcionarioService;
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioQueryService = funcionarioQueryService;
    }

    /**
     * {@code POST  /funcionarios} : Create a new funcionario.
     *
     * @param funcionario the funcionario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionario, or with status {@code 400 (Bad Request)} if the funcionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Funcionario> createFuncionario(@Valid @RequestBody Funcionario funcionario) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionario);
        if (funcionario.getId() != null) {
            throw new BadRequestAlertException("A new funcionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        funcionario = funcionarioService.save(funcionario);
        return ResponseEntity.created(new URI("/api/funcionarios/" + funcionario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, funcionario.getId().toString()))
            .body(funcionario);
    }

    /**
     * {@code PUT  /funcionarios/:id} : Updates an existing funcionario.
     *
     * @param id the id of the funcionario to save.
     * @param funcionario the funcionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionario,
     * or with status {@code 400 (Bad Request)} if the funcionario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> updateFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Funcionario funcionario
    ) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}, {}", id, funcionario);
        if (funcionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        funcionario = funcionarioService.update(funcionario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionario.getId().toString()))
            .body(funcionario);
    }

    /**
     * {@code PATCH  /funcionarios/:id} : Partial updates given fields of an existing funcionario, field will ignore if it is null
     *
     * @param id the id of the funcionario to save.
     * @param funcionario the funcionario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionario,
     * or with status {@code 400 (Bad Request)} if the funcionario is not valid,
     * or with status {@code 404 (Not Found)} if the funcionario is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Funcionario> partialUpdateFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Funcionario funcionario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Funcionario partially : {}, {}", id, funcionario);
        if (funcionario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Funcionario> result = funcionarioService.partialUpdate(funcionario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionario.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionarios} : get all the funcionarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Funcionario>> getAllFuncionarios(
        FuncionarioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Funcionarios by criteria: {}", criteria);

        Page<Funcionario> page = funcionarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funcionarios/count} : count all the funcionarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFuncionarios(FuncionarioCriteria criteria) {
        log.debug("REST request to count Funcionarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(funcionarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funcionarios/:id} : get the "id" funcionario.
     *
     * @param id the id of the funcionario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        Optional<Funcionario> funcionario = funcionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionario);
    }

    /**
     * {@code DELETE  /funcionarios/:id} : delete the "id" funcionario.
     *
     * @param id the id of the funcionario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable("id") Long id) {
        log.debug("REST request to delete Funcionario : {}", id);
        funcionarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
