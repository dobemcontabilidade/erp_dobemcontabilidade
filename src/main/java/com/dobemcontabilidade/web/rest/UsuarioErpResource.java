package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.UsuarioErpRepository;
import com.dobemcontabilidade.service.UsuarioErpService;
import com.dobemcontabilidade.service.dto.UsuarioErpDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.UsuarioErp}.
 */
@RestController
@RequestMapping("/api/usuario-erps")
public class UsuarioErpResource {

    private static final Logger log = LoggerFactory.getLogger(UsuarioErpResource.class);

    private static final String ENTITY_NAME = "usuarioErp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UsuarioErpService usuarioErpService;

    private final UsuarioErpRepository usuarioErpRepository;

    public UsuarioErpResource(UsuarioErpService usuarioErpService, UsuarioErpRepository usuarioErpRepository) {
        this.usuarioErpService = usuarioErpService;
        this.usuarioErpRepository = usuarioErpRepository;
    }

    /**
     * {@code POST  /usuario-erps} : Create a new usuarioErp.
     *
     * @param usuarioErpDTO the usuarioErpDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioErpDTO, or with status {@code 400 (Bad Request)} if the usuarioErp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<UsuarioErpDTO> createUsuarioErp(@Valid @RequestBody UsuarioErpDTO usuarioErpDTO) throws URISyntaxException {
        log.debug("REST request to save UsuarioErp : {}", usuarioErpDTO);
        if (usuarioErpDTO.getId() != null) {
            throw new BadRequestAlertException("A new usuarioErp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        usuarioErpDTO = usuarioErpService.save(usuarioErpDTO);
        return ResponseEntity.created(new URI("/api/usuario-erps/" + usuarioErpDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, usuarioErpDTO.getId().toString()))
            .body(usuarioErpDTO);
    }

    /**
     * {@code PUT  /usuario-erps/:id} : Updates an existing usuarioErp.
     *
     * @param id the id of the usuarioErpDTO to save.
     * @param usuarioErpDTO the usuarioErpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioErpDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioErpDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the usuarioErpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioErpDTO> updateUsuarioErp(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UsuarioErpDTO usuarioErpDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UsuarioErp : {}, {}", id, usuarioErpDTO);
        if (usuarioErpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioErpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioErpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        usuarioErpDTO = usuarioErpService.update(usuarioErpDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioErpDTO.getId().toString()))
            .body(usuarioErpDTO);
    }

    /**
     * {@code PATCH  /usuario-erps/:id} : Partial updates given fields of an existing usuarioErp, field will ignore if it is null
     *
     * @param id the id of the usuarioErpDTO to save.
     * @param usuarioErpDTO the usuarioErpDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated usuarioErpDTO,
     * or with status {@code 400 (Bad Request)} if the usuarioErpDTO is not valid,
     * or with status {@code 404 (Not Found)} if the usuarioErpDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the usuarioErpDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UsuarioErpDTO> partialUpdateUsuarioErp(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UsuarioErpDTO usuarioErpDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UsuarioErp partially : {}, {}", id, usuarioErpDTO);
        if (usuarioErpDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, usuarioErpDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!usuarioErpRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UsuarioErpDTO> result = usuarioErpService.partialUpdate(usuarioErpDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, usuarioErpDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /usuario-erps} : get all the usuarioErps.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of usuarioErps in body.
     */
    @GetMapping("")
    public List<UsuarioErpDTO> getAllUsuarioErps(
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get all UsuarioErps");
        return usuarioErpService.findAll();
    }

    /**
     * {@code GET  /usuario-erps/:id} : get the "id" usuarioErp.
     *
     * @param id the id of the usuarioErpDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the usuarioErpDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioErpDTO> getUsuarioErp(@PathVariable("id") Long id) {
        log.debug("REST request to get UsuarioErp : {}", id);
        Optional<UsuarioErpDTO> usuarioErpDTO = usuarioErpService.findOne(id);
        return ResponseUtil.wrapOrNotFound(usuarioErpDTO);
    }

    /**
     * {@code DELETE  /usuario-erps/:id} : delete the "id" usuarioErp.
     *
     * @param id the id of the usuarioErpDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuarioErp(@PathVariable("id") Long id) {
        log.debug("REST request to delete UsuarioErp : {}", id);
        usuarioErpService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
