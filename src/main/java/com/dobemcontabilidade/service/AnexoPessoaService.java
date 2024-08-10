package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.repository.AnexoPessoaRepository;
import com.dobemcontabilidade.service.dto.AnexoPessoaDTO;
import com.dobemcontabilidade.service.mapper.AnexoPessoaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoPessoa}.
 */
@Service
@Transactional
public class AnexoPessoaService {

    private static final Logger log = LoggerFactory.getLogger(AnexoPessoaService.class);

    private final AnexoPessoaRepository anexoPessoaRepository;

    private final AnexoPessoaMapper anexoPessoaMapper;

    public AnexoPessoaService(AnexoPessoaRepository anexoPessoaRepository, AnexoPessoaMapper anexoPessoaMapper) {
        this.anexoPessoaRepository = anexoPessoaRepository;
        this.anexoPessoaMapper = anexoPessoaMapper;
    }

    /**
     * Save a anexoPessoa.
     *
     * @param anexoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnexoPessoaDTO save(AnexoPessoaDTO anexoPessoaDTO) {
        log.debug("Request to save AnexoPessoa : {}", anexoPessoaDTO);
        AnexoPessoa anexoPessoa = anexoPessoaMapper.toEntity(anexoPessoaDTO);
        anexoPessoa = anexoPessoaRepository.save(anexoPessoa);
        return anexoPessoaMapper.toDto(anexoPessoa);
    }

    /**
     * Update a anexoPessoa.
     *
     * @param anexoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public AnexoPessoaDTO update(AnexoPessoaDTO anexoPessoaDTO) {
        log.debug("Request to update AnexoPessoa : {}", anexoPessoaDTO);
        AnexoPessoa anexoPessoa = anexoPessoaMapper.toEntity(anexoPessoaDTO);
        anexoPessoa = anexoPessoaRepository.save(anexoPessoa);
        return anexoPessoaMapper.toDto(anexoPessoa);
    }

    /**
     * Partially update a anexoPessoa.
     *
     * @param anexoPessoaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoPessoaDTO> partialUpdate(AnexoPessoaDTO anexoPessoaDTO) {
        log.debug("Request to partially update AnexoPessoa : {}", anexoPessoaDTO);

        return anexoPessoaRepository
            .findById(anexoPessoaDTO.getId())
            .map(existingAnexoPessoa -> {
                anexoPessoaMapper.partialUpdate(existingAnexoPessoa, anexoPessoaDTO);

                return existingAnexoPessoa;
            })
            .map(anexoPessoaRepository::save)
            .map(anexoPessoaMapper::toDto);
    }

    /**
     * Get all the anexoPessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AnexoPessoaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return anexoPessoaRepository.findAllWithEagerRelationships(pageable).map(anexoPessoaMapper::toDto);
    }

    /**
     * Get one anexoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoPessoaDTO> findOne(Long id) {
        log.debug("Request to get AnexoPessoa : {}", id);
        return anexoPessoaRepository.findOneWithEagerRelationships(id).map(anexoPessoaMapper::toDto);
    }

    /**
     * Delete the anexoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoPessoa : {}", id);
        anexoPessoaRepository.deleteById(id);
    }
}
