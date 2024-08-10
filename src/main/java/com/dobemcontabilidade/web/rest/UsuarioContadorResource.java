package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.UsuarioContadorRepository;
import com.dobemcontabilidade.service.UsuarioContadorQueryService;
import com.dobemcontabilidade.service.UsuarioContadorService;
import com.dobemcontabilidade.service.criteria.UsuarioContadorCriteria;
import com.dobemcontabilidade.service.dto.UsuarioContadorDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.UsuarioContador}.
 */
@RestController
@RequestMapping("/api/usuario-contadors")
public class UsuarioContadorResource {

    private static final Logger log = LoggerFactory.getLogger(UsuarioContadorResource.class);

    private static final String ENTITY_NAME = "usuarioContador";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioContadorService usuarioContadorService;

    private final UsuarioContadorRepository usuarioContadorRepository;

    private final UsuarioContadorQueryService usuarioContadorQueryService;

    public UsuarioContadorResource(
        UsuarioContadorService usuarioContadorService,
        UsuarioContadorRepository usuarioContadorRepository,
        UsuarioContadorQueryService usuarioContadorQueryService
    ) {
        this.usuarioContadorService = usuarioContadorService;
        this.usuarioContadorRepository = usuarioContadorRepository;
        this.usuarioContadorQueryService = usuarioContadorQueryService;
    }

    /**
     * {@code POST  /usuario-contadors} : Create a new usuarioContador.
     *
     * @param usuarioContadorDTO the usuarioContadorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioContadorDTO, or with status {@code 400 (Bad Request)} if the usuarioContador has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UsuarioContadorDTO> createUsuarioContador(@Valid @RequestBody UsuarioContadorDTO usuarioContadorDTO)
        throws URISyntaxException {
        log.debug("REST request to save UsuarioContador : {}", usuarioContadorDTO);
        if (usuarioContadorDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioContador cannot already have an ID", ENTITY_NAME, "idexists");
        }
        usuarioContadorDTO = usuarioContadorService.save(usuarioContadorDTO);
        return ResponseEntity.created(new URI("/api/usuario-contadors/" + usuarioContadorDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, usuarioContadorDTO.getId().toString()))
            .body(usuarioContadorDTO);
    }

    /**
     * {@code PUT  /usuario-contadors/:id} : Updates an existing usuarioContador.
     *
     * @param id the id of the usuarioContadorDTO to save.
     * @param usuarioContadorDTO the usuarioContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioContadorDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioContadorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioContadorDTO> updateUsuarioContador(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioContadorDTO usuarioContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioContador : {}, {}", id, usuarioContadorDTO);
        if (usuarioContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        usuarioContadorDTO = usuarioContadorService.update(usuarioContadorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioContadorDTO.getId().toString()))
            .body(usuarioContadorDTO);
    }

    /**
     * {@code PATCH  /usuario-contadors/:id} : Partial updates given fields of an existing usuarioContador, field will ignore if it is null
     *
     * @param id the id of the usuarioContadorDTO to save.
     * @param usuarioContadorDTO the usuarioContadorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioContadorDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioContadorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioContadorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioContadorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsuarioContadorDTO> partialUpdateUsuarioContador(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioContadorDTO usuarioContadorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsuarioContador partially : {}, {}", id, usuarioContadorDTO);
        if (usuarioContadorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioContadorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioContadorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioContadorDTO> result = usuarioContadorService.partialUpdate(usuarioContadorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioContadorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /usuario-contadors} : get all the usuarioContadors.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioContadors in body.
     */
    @GetMapping("")
    public ResponseEntity<List<UsuarioContadorDTO>> getAllUsuarioContadors(
        UsuarioContadorCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get UsuarioContadors by criteria: {}", criteria);

        Page<UsuarioContadorDTO> page = usuarioContadorQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /usuario-contadors/count} : count all the usuarioContadors.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countUsuarioContadors(UsuarioContadorCriteria criteria) {
        log.debug("REST request to count UsuarioContadors by criteria: {}", criteria);
        return ResponseEntity.ok().body(usuarioContadorQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /usuario-contadors/:id} : get the "id" usuarioContador.
     *
     * @param id the id of the usuarioContadorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioContadorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioContadorDTO> getUsuarioContador(@PathVariable("id") Long id) {
        log.debug("REST request to get UsuarioContador : {}", id);
        Optional<UsuarioContadorDTO> usuarioContadorDTO = usuarioContadorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioContadorDTO);
    }

    /**
     * {@code DELETE  /usuario-contadors/:id} : delete the "id" usuarioContador.
     *
     * @param id the id of the usuarioContadorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioContador(@PathVariable("id") Long id) {
        log.debug("REST request to delete UsuarioContador : {}", id);
        usuarioContadorService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
