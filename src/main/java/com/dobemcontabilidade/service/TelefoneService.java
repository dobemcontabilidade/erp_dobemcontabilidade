package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Telefone;
import com.dobemcontabilidade.repository.TelefoneRepository;
import com.dobemcontabilidade.service.dto.TelefoneDTO;
import com.dobemcontabilidade.service.mapper.TelefoneMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Telefone}.
 */
@Service
@Transactional
public class TelefoneService {

    private static final Logger log = LoggerFactory.getLogger(TelefoneService.class);

    private final TelefoneRepository telefoneRepository;

    private final TelefoneMapper telefoneMapper;

    public TelefoneService(TelefoneRepository telefoneRepository, TelefoneMapper telefoneMapper) {
        this.telefoneRepository = telefoneRepository;
        this.telefoneMapper = telefoneMapper;
    }

    /**
     * Save a telefone.
     *
     * @param telefoneDTO the entity to save.
     * @return the persisted entity.
     */
    public TelefoneDTO save(TelefoneDTO telefoneDTO) {
        log.debug("Request to save Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        return telefoneMapper.toDto(telefone);
    }

    /**
     * Update a telefone.
     *
     * @param telefoneDTO the entity to save.
     * @return the persisted entity.
     */
    public TelefoneDTO update(TelefoneDTO telefoneDTO) {
        log.debug("Request to update Telefone : {}", telefoneDTO);
        Telefone telefone = telefoneMapper.toEntity(telefoneDTO);
        telefone = telefoneRepository.save(telefone);
        return telefoneMapper.toDto(telefone);
    }

    /**
     * Partially update a telefone.
     *
     * @param telefoneDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TelefoneDTO> partialUpdate(TelefoneDTO telefoneDTO) {
        log.debug("Request to partially update Telefone : {}", telefoneDTO);

        return telefoneRepository
            .findById(telefoneDTO.getId())
            .map(existingTelefone -> {
                telefoneMapper.partialUpdate(existingTelefone, telefoneDTO);

                return existingTelefone;
            })
            .map(telefoneRepository::save)
            .map(telefoneMapper::toDto);
    }

    /**
     * Get all the telefones with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TelefoneDTO> findAllWithEagerRelationships(Pageable pageable) {
        return telefoneRepository.findAllWithEagerRelationships(pageable).map(telefoneMapper::toDto);
    }

    /**
     * Get one telefone by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TelefoneDTO> findOne(Long id) {
        log.debug("Request to get Telefone : {}", id);
        return telefoneRepository.findOneWithEagerRelationships(id).map(telefoneMapper::toDto);
    }

    /**
     * Delete the telefone by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Telefone : {}", id);
        telefoneRepository.deleteById(id);
    }
}
