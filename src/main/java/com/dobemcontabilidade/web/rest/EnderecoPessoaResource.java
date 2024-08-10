package com.dobemcontabilidade.web.rest;

import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
import com.dobemcontabilidade.service.EnderecoPessoaQueryService;
import com.dobemcontabilidade.service.EnderecoPessoaService;
import com.dobemcontabilidade.service.criteria.EnderecoPessoaCriteria;
import com.dobemcontabilidade.service.dto.EnderecoPessoaDTO;
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
 * REST controller for managing {@link com.dobemcontabilidade.domain.EnderecoPessoa}.
 */
@RestController
@RequestMapping("/api/endereco-pessoas")
public class EnderecoPessoaResource {

    private static final Logger log = LoggerFactory.getLogger(EnderecoPessoaResource.class);

    private static final String ENTITY_NAME = "enderecoPessoa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnderecoPessoaService enderecoPessoaService;

    private final EnderecoPessoaRepository enderecoPessoaRepository;

    private final EnderecoPessoaQueryService enderecoPessoaQueryService;

    public EnderecoPessoaResource(
        EnderecoPessoaService enderecoPessoaService,
        EnderecoPessoaRepository enderecoPessoaRepository,
        EnderecoPessoaQueryService enderecoPessoaQueryService
    ) {
        this.enderecoPessoaService = enderecoPessoaService;
        this.enderecoPessoaRepository = enderecoPessoaRepository;
        this.enderecoPessoaQueryService = enderecoPessoaQueryService;
    }

    /**
     * {@code POST  /endereco-pessoas} : Create a new enderecoPessoa.
     *
     * @param enderecoPessoaDTO the enderecoPessoaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enderecoPessoaDTO, or with status {@code 400 (Bad Request)} if the enderecoPessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EnderecoPessoaDTO> createEnderecoPessoa(@Valid @RequestBody EnderecoPessoaDTO enderecoPessoaDTO)
        throws URISyntaxException {
        log.debug("REST request to save EnderecoPessoa : {}", enderecoPessoaDTO);
        if (enderecoPessoaDTO.getId() != null) {
            throw new BadRequestAlertException("A new enderecoPessoa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        enderecoPessoaDTO = enderecoPessoaService.save(enderecoPessoaDTO);
        return ResponseEntity.created(new URI("/api/endereco-pessoas/" + enderecoPessoaDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, enderecoPessoaDTO.getId().toString()))
            .body(enderecoPessoaDTO);
    }

    /**
     * {@code PUT  /endereco-pessoas/:id} : Updates an existing enderecoPessoa.
     *
     * @param id the id of the enderecoPessoaDTO to save.
     * @param enderecoPessoaDTO the enderecoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoPessoaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enderecoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoPessoaDTO> updateEnderecoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnderecoPessoaDTO enderecoPessoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EnderecoPessoa : {}, {}", id, enderecoPessoaDTO);
        if (enderecoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoPessoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        enderecoPessoaDTO = enderecoPessoaService.update(enderecoPessoaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoPessoaDTO.getId().toString()))
            .body(enderecoPessoaDTO);
    }

    /**
     * {@code PATCH  /endereco-pessoas/:id} : Partial updates given fields of an existing enderecoPessoa, field will ignore if it is null
     *
     * @param id the id of the enderecoPessoaDTO to save.
     * @param enderecoPessoaDTO the enderecoPessoaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enderecoPessoaDTO,
     * or with status {@code 400 (Bad Request)} if the enderecoPessoaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the enderecoPessoaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the enderecoPessoaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EnderecoPessoaDTO> partialUpdateEnderecoPessoa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnderecoPessoaDTO enderecoPessoaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EnderecoPessoa partially : {}, {}", id, enderecoPessoaDTO);
        if (enderecoPessoaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enderecoPessoaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enderecoPessoaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnderecoPessoaDTO> result = enderecoPessoaService.partialUpdate(enderecoPessoaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enderecoPessoaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /endereco-pessoas} : get all the enderecoPessoas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enderecoPessoas in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EnderecoPessoaDTO>> getAllEnderecoPessoas(
        EnderecoPessoaCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get EnderecoPessoas by criteria: {}", criteria);

        Page<EnderecoPessoaDTO> page = enderecoPessoaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endereco-pessoas/count} : count all the enderecoPessoas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countEnderecoPessoas(EnderecoPessoaCriteria criteria) {
        log.debug("REST request to count EnderecoPessoas by criteria: {}", criteria);
        return ResponseEntity.ok().body(enderecoPessoaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /endereco-pessoas/:id} : get the "id" enderecoPessoa.
     *
     * @param id the id of the enderecoPessoaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enderecoPessoaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoPessoaDTO> getEnderecoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to get EnderecoPessoa : {}", id);
        Optional<EnderecoPessoaDTO> enderecoPessoaDTO = enderecoPessoaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enderecoPessoaDTO);
    }

    /**
     * {@code DELETE  /endereco-pessoas/:id} : delete the "id" enderecoPessoa.
     *
     * @param id the id of the enderecoPessoaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnderecoPessoa(@PathVariable("id") Long id) {
        log.debug("REST request to delete EnderecoPessoa : {}", id);
        enderecoPessoaService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
