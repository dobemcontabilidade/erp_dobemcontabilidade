package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.repository.EnquadramentoRepository;
import com.dobemcontabilidade.service.dto.EnquadramentoDTO;
import com.dobemcontabilidade.service.mapper.EnquadramentoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Enquadramento}.
 */
@Service
@Transactional
public class EnquadramentoService {

    private static final Logger log = LoggerFactory.getLogger(EnquadramentoService.class);

    private final EnquadramentoRepository enquadramentoRepository;

    private final EnquadramentoMapper enquadramentoMapper;

    public EnquadramentoService(EnquadramentoRepository enquadramentoRepository, EnquadramentoMapper enquadramentoMapper) {
        this.enquadramentoRepository = enquadramentoRepository;
        this.enquadramentoMapper = enquadramentoMapper;
    }

    /**
     * Save a enquadramento.
     *
     * @param enquadramentoDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquadramentoDTO save(EnquadramentoDTO enquadramentoDTO) {
        log.debug("Request to save Enquadramento : {}", enquadramentoDTO);
        Enquadramento enquadramento = enquadramentoMapper.toEntity(enquadramentoDTO);
        enquadramento = enquadramentoRepository.save(enquadramento);
        return enquadramentoMapper.toDto(enquadramento);
    }

    /**
     * Update a enquadramento.
     *
     * @param enquadramentoDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquadramentoDTO update(EnquadramentoDTO enquadramentoDTO) {
        log.debug("Request to update Enquadramento : {}", enquadramentoDTO);
        Enquadramento enquadramento = enquadramentoMapper.toEntity(enquadramentoDTO);
        enquadramento = enquadramentoRepository.save(enquadramento);
        return enquadramentoMapper.toDto(enquadramento);
    }

    /**
     * Partially update a enquadramento.
     *
     * @param enquadramentoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnquadramentoDTO> partialUpdate(EnquadramentoDTO enquadramentoDTO) {
        log.debug("Request to partially update Enquadramento : {}", enquadramentoDTO);

        return enquadramentoRepository
            .findById(enquadramentoDTO.getId())
            .map(existingEnquadramento -> {
                enquadramentoMapper.partialUpdate(existingEnquadramento, enquadramentoDTO);

                return existingEnquadramento;
            })
            .map(enquadramentoRepository::save)
            .map(enquadramentoMapper::toDto);
    }

    /**
     * Get one enquadramento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnquadramentoDTO> findOne(Long id) {
        log.debug("Request to get Enquadramento : {}", id);
        return enquadramentoRepository.findById(id).map(enquadramentoMapper::toDto);
    }

    /**
     * Delete the enquadramento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Enquadramento : {}", id);
        enquadramentoRepository.deleteById(id);
    }
}
