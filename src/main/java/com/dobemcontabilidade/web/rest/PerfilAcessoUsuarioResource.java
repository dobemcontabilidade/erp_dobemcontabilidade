package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.domain.PerfilAcessoUsuario;
import com.dobemcontabilidade.repository.PerfilAcessoUsuarioRepository;
import com.dobemcontabilidade.service.PerfilAcessoUsuarioQueryService;
import com.dobemcontabilidade.service.PerfilAcessoUsuarioService;
import com.dobemcontabilidade.service.criteria.PerfilAcessoUsuarioCriteria;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.PerfilAcessoUsuario}.
 */
@RestController
@RequestMapping("/api/perfil-acesso-usuarios")
public class PerfilAcessoUsuarioResource {

    private static final Logger log = LoggerFactory.getLogger(PerfilAcessoUsuarioResource.class);

    private static final String ENTITY_NAME = "perfilAcessoUsuario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerfilAcessoUsuarioService perfilAcessoUsuarioService;

    private final PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository;

    private final PerfilAcessoUsuarioQueryService perfilAcessoUsuarioQueryService;

    public PerfilAcessoUsuarioResource(
        PerfilAcessoUsuarioService perfilAcessoUsuarioService,
        PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository,
        PerfilAcessoUsuarioQueryService perfilAcessoUsuarioQueryService
    ) {
        this.perfilAcessoUsuarioService = perfilAcessoUsuarioService;
        this.perfilAcessoUsuarioRepository = perfilAcessoUsuarioRepository;
        this.perfilAcessoUsuarioQueryService = perfilAcessoUsuarioQueryService;
    }

    /**
     * {@code POST  /perfil-acesso-usuarios} : Create a new perfilAcessoUsuario.
     *
     * @param perfilAcessoUsuario the perfilAcessoUsuario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new perfilAcessoUsuario, or with status {@code 400 (Bad Request)} if the perfilAcessoUsuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PerfilAcessoUsuario> createPerfilAcessoUsuario(@RequestBody PerfilAcessoUsuario perfilAcessoUsuario)
        throws URISyntaxException {
        log.debug("REST request to save PerfilAcessoUsuario : {}", perfilAcessoUsuario);
        if (perfilAcessoUsuario.getId() != null) {
            throw new BadRequestAlertException("A new perfilAcessoUsuario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        perfilAcessoUsuario = perfilAcessoUsuarioService.save(perfilAcessoUsuario);
        return ResponseEntity.created(new URI("/api/perfil-acesso-usuarios/" + perfilAcessoUsuario.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, perfilAcessoUsuario.getId().toString()))
            .body(perfilAcessoUsuario);
    }

    /**
     * {@code PUT  /perfil-acesso-usuarios/:id} : Updates an existing perfilAcessoUsuario.
     *
     * @param id the id of the perfilAcessoUsuario to save.
     * @param perfilAcessoUsuario the perfilAcessoUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilAcessoUsuario,
     * or with status {@code 400 (Bad Request)} if the perfilAcessoUsuario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the perfilAcessoUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PerfilAcessoUsuario> updatePerfilAcessoUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerfilAcessoUsuario perfilAcessoUsuario
    ) throws URISyntaxException {
        log.debug("REST request to update PerfilAcessoUsuario : {}, {}", id, perfilAcessoUsuario);
        if (perfilAcessoUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilAcessoUsuario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilAcessoUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        perfilAcessoUsuario = perfilAcessoUsuarioService.update(perfilAcessoUsuario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilAcessoUsuario.getId().toString()))
            .body(perfilAcessoUsuario);
    }

    /**
     * {@code PATCH  /perfil-acesso-usuarios/:id} : Partial updates given fields of an existing perfilAcessoUsuario, field will ignore if it is null
     *
     * @param id the id of the perfilAcessoUsuario to save.
     * @param perfilAcessoUsuario the perfilAcessoUsuario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated perfilAcessoUsuario,
     * or with status {@code 400 (Bad Request)} if the perfilAcessoUsuario is not valid,
     * or with status {@code 404 (Not Found)} if the perfilAcessoUsuario is not found,
     * or with status {@code 500 (Internal Server Error)} if the perfilAcessoUsuario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PerfilAcessoUsuario> partialUpdatePerfilAcessoUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PerfilAcessoUsuario perfilAcessoUsuario
    ) throws URISyntaxException {
        log.debug("REST request to partial update PerfilAcessoUsuario partially : {}, {}", id, perfilAcessoUsuario);
        if (perfilAcessoUsuario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, perfilAcessoUsuario.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!perfilAcessoUsuarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PerfilAcessoUsuario> result = perfilAcessoUsuarioService.partialUpdate(perfilAcessoUsuario);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, perfilAcessoUsuario.getId().toString())
        );
    }

    /**
     * {@code GET  /perfil-acesso-usuarios} : get all the perfilAcessoUsuarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of perfilAcessoUsuarios in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PerfilAcessoUsuario>> getAllPerfilAcessoUsuarios(
        PerfilAcessoUsuarioCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get PerfilAcessoUsuarios by criteria: {}", criteria);

        Page<PerfilAcessoUsuario> page = perfilAcessoUsuarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /perfil-acesso-usuarios/count} : count all the perfilAcessoUsuarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countPerfilAcessoUsuarios(PerfilAcessoUsuarioCriteria criteria) {
        log.debug("REST request to count PerfilAcessoUsuarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(perfilAcessoUsuarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /perfil-acesso-usuarios/:id} : get the "id" perfilAcessoUsuario.
     *
     * @param id the id of the perfilAcessoUsuario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the perfilAcessoUsuario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PerfilAcessoUsuario> getPerfilAcessoUsuario(@PathVariable("id") Long id) {
        log.debug("REST request to get PerfilAcessoUsuario : {}", id);
        Optional<PerfilAcessoUsuario> perfilAcessoUsuario = perfilAcessoUsuarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(perfilAcessoUsuario);
    }

    /**
     * {@code DELETE  /perfil-acesso-usuarios/:id} : delete the "id" perfilAcessoUsuario.
     *
     * @param id the id of the perfilAcessoUsuario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfilAcessoUsuario(@PathVariable("id") Long id) {
        log.debug("REST request to delete PerfilAcessoUsuario : {}", id);
        perfilAcessoUsuarioService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
