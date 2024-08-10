package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import com.dobemcontabilidade.repository.CalculoPlanoAssinaturaRepository;
import com.dobemcontabilidade.service.dto.CalculoPlanoAssinaturaDTO;
import com.dobemcontabilidade.service.mapper.CalculoPlanoAssinaturaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.CalculoPlanoAssinatura}.
 */
@Service
@Transactional
public class CalculoPlanoAssinaturaService {

    private static final Logger log = LoggerFactory.getLogger(CalculoPlanoAssinaturaService.class);

    private final CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository;

    private final CalculoPlanoAssinaturaMapper calculoPlanoAssinaturaMapper;

    public CalculoPlanoAssinaturaService(
        CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository,
        CalculoPlanoAssinaturaMapper calculoPlanoAssinaturaMapper
    ) {
        this.calculoPlanoAssinaturaRepository = calculoPlanoAssinaturaRepository;
        this.calculoPlanoAssinaturaMapper = calculoPlanoAssinaturaMapper;
    }

    /**
     * Save a calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinaturaDTO the entity to save.
     * @return the persisted entity.
     */
    public CalculoPlanoAssinaturaDTO save(CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO) {
        log.debug("Request to save CalculoPlanoAssinatura : {}", calculoPlanoAssinaturaDTO);
        CalculoPlanoAssinatura calculoPlanoAssinatura = calculoPlanoAssinaturaMapper.toEntity(calculoPlanoAssinaturaDTO);
        calculoPlanoAssinatura = calculoPlanoAssinaturaRepository.save(calculoPlanoAssinatura);
        return calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);
    }

    /**
     * Update a calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinaturaDTO the entity to save.
     * @return the persisted entity.
     */
    public CalculoPlanoAssinaturaDTO update(CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO) {
        log.debug("Request to update CalculoPlanoAssinatura : {}", calculoPlanoAssinaturaDTO);
        CalculoPlanoAssinatura calculoPlanoAssinatura = calculoPlanoAssinaturaMapper.toEntity(calculoPlanoAssinaturaDTO);
        calculoPlanoAssinatura = calculoPlanoAssinaturaRepository.save(calculoPlanoAssinatura);
        return calculoPlanoAssinaturaMapper.toDto(calculoPlanoAssinatura);
    }

    /**
     * Partially update a calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinaturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CalculoPlanoAssinaturaDTO> partialUpdate(CalculoPlanoAssinaturaDTO calculoPlanoAssinaturaDTO) {
        log.debug("Request to partially update CalculoPlanoAssinatura : {}", calculoPlanoAssinaturaDTO);

        return calculoPlanoAssinaturaRepository
            .findById(calculoPlanoAssinaturaDTO.getId())
            .map(existingCalculoPlanoAssinatura -> {
                calculoPlanoAssinaturaMapper.partialUpdate(existingCalculoPlanoAssinatura, calculoPlanoAssinaturaDTO);

                return existingCalculoPlanoAssinatura;
            })
            .map(calculoPlanoAssinaturaRepository::save)
            .map(calculoPlanoAssinaturaMapper::toDto);
    }

    /**
     * Get all the calculoPlanoAssinaturas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CalculoPlanoAssinaturaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return calculoPlanoAssinaturaRepository.findAllWithEagerRelationships(pageable).map(calculoPlanoAssinaturaMapper::toDto);
    }

    /**
     * Get one calculoPlanoAssinatura by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CalculoPlanoAssinaturaDTO> findOne(Long id) {
        log.debug("Request to get CalculoPlanoAssinatura : {}", id);
        return calculoPlanoAssinaturaRepository.findOneWithEagerRelationships(id).map(calculoPlanoAssinaturaMapper::toDto);
    }

    /**
     * Delete the calculoPlanoAssinatura by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CalculoPlanoAssinatura : {}", id);
        calculoPlanoAssinaturaRepository.deleteById(id);
    }
}
