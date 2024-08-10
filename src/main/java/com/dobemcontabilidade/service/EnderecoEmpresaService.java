package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.repository.EnderecoEmpresaRepository;
import com.dobemcontabilidade.service.dto.EnderecoEmpresaDTO;
import com.dobemcontabilidade.service.mapper.EnderecoEmpresaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EnderecoEmpresa}.
 */
@Service
@Transactional
public class EnderecoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(EnderecoEmpresaService.class);

    private final EnderecoEmpresaRepository enderecoEmpresaRepository;

    private final EnderecoEmpresaMapper enderecoEmpresaMapper;

    public EnderecoEmpresaService(EnderecoEmpresaRepository enderecoEmpresaRepository, EnderecoEmpresaMapper enderecoEmpresaMapper) {
        this.enderecoEmpresaRepository = enderecoEmpresaRepository;
        this.enderecoEmpresaMapper = enderecoEmpresaMapper;
    }

    /**
     * Save a enderecoEmpresa.
     *
     * @param enderecoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoEmpresaDTO save(EnderecoEmpresaDTO enderecoEmpresaDTO) {
        log.debug("Request to save EnderecoEmpresa : {}", enderecoEmpresaDTO);
        EnderecoEmpresa enderecoEmpresa = enderecoEmpresaMapper.toEntity(enderecoEmpresaDTO);
        enderecoEmpresa = enderecoEmpresaRepository.save(enderecoEmpresa);
        return enderecoEmpresaMapper.toDto(enderecoEmpresa);
    }

    /**
     * Update a enderecoEmpresa.
     *
     * @param enderecoEmpresaDTO the entity to save.
     * @return the persisted entity.
     */
    public EnderecoEmpresaDTO update(EnderecoEmpresaDTO enderecoEmpresaDTO) {
        log.debug("Request to update EnderecoEmpresa : {}", enderecoEmpresaDTO);
        EnderecoEmpresa enderecoEmpresa = enderecoEmpresaMapper.toEntity(enderecoEmpresaDTO);
        enderecoEmpresa = enderecoEmpresaRepository.save(enderecoEmpresa);
        return enderecoEmpresaMapper.toDto(enderecoEmpresa);
    }

    /**
     * Partially update a enderecoEmpresa.
     *
     * @param enderecoEmpresaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnderecoEmpresaDTO> partialUpdate(EnderecoEmpresaDTO enderecoEmpresaDTO) {
        log.debug("Request to partially update EnderecoEmpresa : {}", enderecoEmpresaDTO);

        return enderecoEmpresaRepository
            .findById(enderecoEmpresaDTO.getId())
            .map(existingEnderecoEmpresa -> {
                enderecoEmpresaMapper.partialUpdate(existingEnderecoEmpresa, enderecoEmpresaDTO);

                return existingEnderecoEmpresa;
            })
            .map(enderecoEmpresaRepository::save)
            .map(enderecoEmpresaMapper::toDto);
    }

    /**
     * Get all the enderecoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EnderecoEmpresaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return enderecoEmpresaRepository.findAllWithEagerRelationships(pageable).map(enderecoEmpresaMapper::toDto);
    }

    /**
     * Get one enderecoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnderecoEmpresaDTO> findOne(Long id) {
        log.debug("Request to get EnderecoEmpresa : {}", id);
        return enderecoEmpresaRepository.findOneWithEagerRelationships(id).map(enderecoEmpresaMapper::toDto);
    }

    /**
     * Delete the enderecoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnderecoEmpresa : {}", id);
        enderecoEmpresaRepository.deleteById(id);
    }
}
