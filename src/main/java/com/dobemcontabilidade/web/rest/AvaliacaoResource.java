package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.AvaliacaoRepository;
import com.dobemcontabilidade.service.AvaliacaoService;
import com.dobemcontabilidade.service.dto.AvaliacaoDTO;
import com.dobemcontabilidade.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.Avaliacao}.
 */
@RestController
@RequestMapping("/api/avaliacaos")
public class AvaliacaoResource {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoResource.class);

    private static final String ENTITY_NAME = "avaliacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AvaliacaoService avaliacaoService;

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoResource(AvaliacaoService avaliacaoService, AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoService = avaliacaoService;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    /**
     * {@code POST  /avaliacaos} : Create a new avaliacao.
     *
     * @param avaliacaoDTO the avaliacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new avaliacaoDTO, or with status {@code 400 (Bad Request)} if the avaliacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AvaliacaoDTO> createAvaliacao(@RequestBody AvaliacaoDTO avaliacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Avaliacao : {}", avaliacaoDTO);
        if (avaliacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new avaliacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        avaliacaoDTO = avaliacaoService.save(avaliacaoDTO);
        return ResponseEntity.created(new URI("/api/avaliacaos/" + avaliacaoDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, avaliacaoDTO.getId().toString()))
            .body(avaliacaoDTO);
    }

    /**
     * {@code PUT  /avaliacaos/:id} : Updates an existing avaliacao.
     *
     * @param id the id of the avaliacaoDTO to save.
     * @param avaliacaoDTO the avaliacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoDTO,
     * or with status {@code 400 (Bad Request)} if the avaliacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> updateAvaliacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvaliacaoDTO avaliacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Avaliacao : {}, {}", id, avaliacaoDTO);
        if (avaliacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        avaliacaoDTO = avaliacaoService.update(avaliacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoDTO.getId().toString()))
            .body(avaliacaoDTO);
    }

    /**
     * {@code PATCH  /avaliacaos/:id} : Partial updates given fields of an existing avaliacao, field will ignore if it is null
     *
     * @param id the id of the avaliacaoDTO to save.
     * @param avaliacaoDTO the avaliacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated avaliacaoDTO,
     * or with status {@code 400 (Bad Request)} if the avaliacaoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the avaliacaoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the avaliacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AvaliacaoDTO> partialUpdateAvaliacao(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AvaliacaoDTO avaliacaoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Avaliacao partially : {}, {}", id, avaliacaoDTO);
        if (avaliacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, avaliacaoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!avaliacaoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AvaliacaoDTO> result = avaliacaoService.partialUpdate(avaliacaoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, avaliacaoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /avaliacaos} : get all the avaliacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of avaliacaos in body.
     */
    @GetMapping("")
    public List<AvaliacaoDTO> getAllAvaliacaos() {
        log.debug("REST request to get all Avaliacaos");
        return avaliacaoService.findAll();
    }

    /**
     * {@code GET  /avaliacaos/:id} : get the "id" avaliacao.
     *
     * @param id the id of the avaliacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the avaliacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoDTO> getAvaliacao(@PathVariable("id") Long id) {
        log.debug("REST request to get Avaliacao : {}", id);
        Optional<AvaliacaoDTO> avaliacaoDTO = avaliacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(avaliacaoDTO);
    }

    /**
     * {@code DELETE  /avaliacaos/:id} : delete the "id" avaliacao.
     *
     * @param id the id of the avaliacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable("id") Long id) {
        log.debug("REST request to delete Avaliacao : {}", id);
        avaliacaoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
