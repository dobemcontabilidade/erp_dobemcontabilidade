package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import com.dobemcontabilidade.repository.DescontoPlanoContaAzulRepository;
import com.dobemcontabilidade.service.dto.DescontoPlanoContaAzulDTO;
import com.dobemcontabilidade.service.mapper.DescontoPlanoContaAzulMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DescontoPlanoContaAzul}.
 */
@Service
@Transactional
public class DescontoPlanoContaAzulService {

    private static final Logger log = LoggerFactory.getLogger(DescontoPlanoContaAzulService.class);

    private final DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository;

    private final DescontoPlanoContaAzulMapper descontoPlanoContaAzulMapper;

    public DescontoPlanoContaAzulService(
        DescontoPlanoContaAzulRepository descontoPlanoContaAzulRepository,
        DescontoPlanoContaAzulMapper descontoPlanoContaAzulMapper
    ) {
        this.descontoPlanoContaAzulRepository = descontoPlanoContaAzulRepository;
        this.descontoPlanoContaAzulMapper = descontoPlanoContaAzulMapper;
    }

    /**
     * Save a descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzulDTO the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContaAzulDTO save(DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO) {
        log.debug("Request to save DescontoPlanoContaAzul : {}", descontoPlanoContaAzulDTO);
        DescontoPlanoContaAzul descontoPlanoContaAzul = descontoPlanoContaAzulMapper.toEntity(descontoPlanoContaAzulDTO);
        descontoPlanoContaAzul = descontoPlanoContaAzulRepository.save(descontoPlanoContaAzul);
        return descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);
    }

    /**
     * Update a descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzulDTO the entity to save.
     * @return the persisted entity.
     */
    public DescontoPlanoContaAzulDTO update(DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO) {
        log.debug("Request to update DescontoPlanoContaAzul : {}", descontoPlanoContaAzulDTO);
        DescontoPlanoContaAzul descontoPlanoContaAzul = descontoPlanoContaAzulMapper.toEntity(descontoPlanoContaAzulDTO);
        descontoPlanoContaAzul = descontoPlanoContaAzulRepository.save(descontoPlanoContaAzul);
        return descontoPlanoContaAzulMapper.toDto(descontoPlanoContaAzul);
    }

    /**
     * Partially update a descontoPlanoContaAzul.
     *
     * @param descontoPlanoContaAzulDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DescontoPlanoContaAzulDTO> partialUpdate(DescontoPlanoContaAzulDTO descontoPlanoContaAzulDTO) {
        log.debug("Request to partially update DescontoPlanoContaAzul : {}", descontoPlanoContaAzulDTO);

        return descontoPlanoContaAzulRepository
            .findById(descontoPlanoContaAzulDTO.getId())
            .map(existingDescontoPlanoContaAzul -> {
                descontoPlanoContaAzulMapper.partialUpdate(existingDescontoPlanoContaAzul, descontoPlanoContaAzulDTO);

                return existingDescontoPlanoContaAzul;
            })
            .map(descontoPlanoContaAzulRepository::save)
            .map(descontoPlanoContaAzulMapper::toDto);
    }

    /**
     * Get all the descontoPlanoContaAzuls with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<DescontoPlanoContaAzulDTO> findAllWithEagerRelationships(Pageable pageable) {
        return descontoPlanoContaAzulRepository.findAllWithEagerRelationships(pageable).map(descontoPlanoContaAzulMapper::toDto);
    }

    /**
     * Get one descontoPlanoContaAzul by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DescontoPlanoContaAzulDTO> findOne(Long id) {
        log.debug("Request to get DescontoPlanoContaAzul : {}", id);
        return descontoPlanoContaAzulRepository.findOneWithEagerRelationships(id).map(descontoPlanoContaAzulMapper::toDto);
    }

    /**
     * Delete the descontoPlanoContaAzul by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DescontoPlanoContaAzul : {}", id);
        descontoPlanoContaAzulRepository.deleteById(id);
    }
}
