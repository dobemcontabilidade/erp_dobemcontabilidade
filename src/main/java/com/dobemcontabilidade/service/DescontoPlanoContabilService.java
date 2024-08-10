package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import com.dobemcontabilidade.repository.DescontoPlanoContabilRepository;
import com.dobemcontabilidade.service.dto.DescontoPlanoContabilDTO;
import com.dobemcontabilidade.service.mapper.DescontoPlanoContabilMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DescontoPlanoContabil}.
 */
@Service
@Transactional
public class DescontoPlanoContabilService {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContabilService.class);

    private final DescontoPlanoContabilRepository descontoPlanoContabilRepository;

    private final DescontoPlanoContabilMapper descontoPlanoContabilMapper;

    public DescontoPlanoContabilService(
        DescontoPlanoContabilRepository descontoPlanoContabilRepository,
        DescontoPlanoContabilMapper descontoPlanoContabilMapper
    ) {
        this.descontoPlanoContabilRepository = descontoPlanoContabilRepository;
        this.descontoPlanoContabilMapper = descontoPlanoContabilMapper;
    }

    /**
     * Save a descontoPlanoContabil.
     *
     * @param descontoPlanoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContabilDTO save(DescontoPlanoContabilDTO descontoPlanoContabilDTO) {
        log.debug("Request to save DescontoPlanoContabil : {}", descontoPlanoContabilDTO);
        DescontoPlanoContabil descontoPlanoContabil = descontoPlanoContabilMapper.toEntity(descontoPlanoContabilDTO);
        descontoPlanoContabil = descontoPlanoContabilRepository.save(descontoPlanoContabil);
        return descontoPlanoContabilMapper.toDto(descontoPlanoContabil);
    }

    /**
     * Update a descontoPlanoContabil.
     *
     * @param descontoPlanoContabilDTO the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContabilDTO update(DescontoPlanoContabilDTO descontoPlanoContabilDTO) {
        log.debug("Request to update DescontoPlanoContabil : {}", descontoPlanoContabilDTO);
        DescontoPlanoContabil descontoPlanoContabil = descontoPlanoContabilMapper.toEntity(descontoPlanoContabilDTO);
        descontoPlanoContabil = descontoPlanoContabilRepository.save(descontoPlanoContabil);
        return descontoPlanoContabilMapper.toDto(descontoPlanoContabil);
    }

    /**
     * Partially update a descontoPlanoContabil.
     *
     * @param descontoPlanoContabilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DescontoPlanoContabilDTO> partialUpdate(DescontoPlanoContabilDTO descontoPlanoContabilDTO) {
        log.debug("Request to partially update DescontoPlanoContabil : {}", descontoPlanoContabilDTO);

        return descontoPlanoContabilRepository
            .findById(descontoPlanoContabilDTO.getId())
            .map(existingDescontoPlanoContabil -> {
                descontoPlanoContabilMapper.partialUpdate(existingDescontoPlanoContabil, descontoPlanoContabilDTO);

                return existingDescontoPlanoContabil;
            })
            .map(descontoPlanoContabilRepository::save)
            .map(descontoPlanoContabilMapper::toDto);
    }

    /**
     * Get all the descontoPlanoContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DescontoPlanoContabilDTO> findAllWithEagerRelationships(Pageable pageable) {
        return descontoPlanoContabilRepository.findAllWithEagerRelationships(pageable).map(descontoPlanoContabilMapper::toDto);
    }

    /**
     * Get one descontoPlanoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DescontoPlanoContabilDTO> findOne(Long id) {
        log.debug("Request to get DescontoPlanoContabil : {}", id);
        return descontoPlanoContabilRepository.findOneWithEagerRelationships(id).map(descontoPlanoContabilMapper::toDto);
    }

    /**
     * Delete the descontoPlanoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DescontoPlanoContabil : {}", id);
        descontoPlanoContabilRepository.deleteById(id);
    }
}
