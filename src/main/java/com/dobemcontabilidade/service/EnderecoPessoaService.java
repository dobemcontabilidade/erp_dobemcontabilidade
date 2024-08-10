package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
import com.dobemcontabilidade.service.dto.EnderecoPessoaDTO;
import com.dobemcontabilidade.service.mapper.EnderecoPessoaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EnderecoPessoa}.
 */
@Service
@Transactional
public class EnderecoPessoaService {

    private static final Logger log = LoggerFactory.getLogger(EnderecoPessoaService.class);

    private final EnderecoPessoaRepository enderecoPessoaRepository;

    private final EnderecoPessoaMapper enderecoPessoaMapper;

    public EnderecoPessoaService(EnderecoPessoaRepository enderecoPessoaRepository, EnderecoPessoaMapper enderecoPessoaMapper) {
        this.enderecoPessoaRepository = enderecoPessoaRepository;
        this.enderecoPessoaMapper = enderecoPessoaMapper;
    }

    /**
     * Save a enderecoPessoa.
     *
     * @param enderecoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoPessoaDTO save(EnderecoPessoaDTO enderecoPessoaDTO) {
        log.debug("Request to save EnderecoPessoa : {}", enderecoPessoaDTO);
        EnderecoPessoa enderecoPessoa = enderecoPessoaMapper.toEntity(enderecoPessoaDTO);
        enderecoPessoa = enderecoPessoaRepository.save(enderecoPessoa);
        return enderecoPessoaMapper.toDto(enderecoPessoa);
    }

    /**
     * Update a enderecoPessoa.
     *
     * @param enderecoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoPessoaDTO update(EnderecoPessoaDTO enderecoPessoaDTO) {
        log.debug("Request to update EnderecoPessoa : {}", enderecoPessoaDTO);
        EnderecoPessoa enderecoPessoa = enderecoPessoaMapper.toEntity(enderecoPessoaDTO);
        enderecoPessoa = enderecoPessoaRepository.save(enderecoPessoa);
        return enderecoPessoaMapper.toDto(enderecoPessoa);
    }

    /**
     * Partially update a enderecoPessoa.
     *
     * @param enderecoPessoaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnderecoPessoaDTO> partialUpdate(EnderecoPessoaDTO enderecoPessoaDTO) {
        log.debug("Request to partially update EnderecoPessoa : {}", enderecoPessoaDTO);

        return enderecoPessoaRepository
            .findById(enderecoPessoaDTO.getId())
            .map(existingEnderecoPessoa -> {
                enderecoPessoaMapper.partialUpdate(existingEnderecoPessoa, enderecoPessoaDTO);

                return existingEnderecoPessoa;
            })
            .map(enderecoPessoaRepository::save)
            .map(enderecoPessoaMapper::toDto);
    }

    /**
     * Get all the enderecoPessoas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EnderecoPessoaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return enderecoPessoaRepository.findAllWithEagerRelationships(pageable).map(enderecoPessoaMapper::toDto);
    }

    /**
     * Get one enderecoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnderecoPessoaDTO> findOne(Long id) {
        log.debug("Request to get EnderecoPessoa : {}", id);
        return enderecoPessoaRepository.findOneWithEagerRelationships(id).map(enderecoPessoaMapper::toDto);
    }

    /**
     * Delete the enderecoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnderecoPessoa : {}", id);
        enderecoPessoaRepository.deleteById(id);
    }
}
