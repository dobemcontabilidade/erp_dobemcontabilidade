package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PrazoAssinatura;
import com.dobemcontabilidade.repository.PrazoAssinaturaRepository;
import com.dobemcontabilidade.service.dto.PrazoAssinaturaDTO;
import com.dobemcontabilidade.service.mapper.PrazoAssinaturaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PrazoAssinatura}.
 */
@Service
@Transactional
public class PrazoAssinaturaService {

    private static final Logger log = LoggerFactory.getLogger(PrazoAssinaturaService.class);

    private final PrazoAssinaturaRepository prazoAssinaturaRepository;

    private final PrazoAssinaturaMapper prazoAssinaturaMapper;

    public PrazoAssinaturaService(PrazoAssinaturaRepository prazoAssinaturaRepository, PrazoAssinaturaMapper prazoAssinaturaMapper) {
        this.prazoAssinaturaRepository = prazoAssinaturaRepository;
        this.prazoAssinaturaMapper = prazoAssinaturaMapper;
    }

    /**
     * Save a prazoAssinatura.
     *
     * @param prazoAssinaturaDTO the entity to save.
     * @return the persisted entity.
     */
    public PrazoAssinaturaDTO save(PrazoAssinaturaDTO prazoAssinaturaDTO) {
        log.debug("Request to save PrazoAssinatura : {}", prazoAssinaturaDTO);
        PrazoAssinatura prazoAssinatura = prazoAssinaturaMapper.toEntity(prazoAssinaturaDTO);
        prazoAssinatura = prazoAssinaturaRepository.save(prazoAssinatura);
        return prazoAssinaturaMapper.toDto(prazoAssinatura);
    }

    /**
     * Update a prazoAssinatura.
     *
     * @param prazoAssinaturaDTO the entity to save.
     * @return the persisted entity.
     */
    public PrazoAssinaturaDTO update(PrazoAssinaturaDTO prazoAssinaturaDTO) {
        log.debug("Request to update PrazoAssinatura : {}", prazoAssinaturaDTO);
        PrazoAssinatura prazoAssinatura = prazoAssinaturaMapper.toEntity(prazoAssinaturaDTO);
        prazoAssinatura = prazoAssinaturaRepository.save(prazoAssinatura);
        return prazoAssinaturaMapper.toDto(prazoAssinatura);
    }

    /**
     * Partially update a prazoAssinatura.
     *
     * @param prazoAssinaturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PrazoAssinaturaDTO> partialUpdate(PrazoAssinaturaDTO prazoAssinaturaDTO) {
        log.debug("Request to partially update PrazoAssinatura : {}", prazoAssinaturaDTO);

        return prazoAssinaturaRepository
            .findById(prazoAssinaturaDTO.getId())
            .map(existingPrazoAssinatura -> {
                prazoAssinaturaMapper.partialUpdate(existingPrazoAssinatura, prazoAssinaturaDTO);

                return existingPrazoAssinatura;
            })
            .map(prazoAssinaturaRepository::save)
            .map(prazoAssinaturaMapper::toDto);
    }

    /**
     * Get one prazoAssinatura by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrazoAssinaturaDTO> findOne(Long id) {
        log.debug("Request to get PrazoAssinatura : {}", id);
        return prazoAssinaturaRepository.findById(id).map(prazoAssinaturaMapper::toDto);
    }

    /**
     * Delete the prazoAssinatura by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PrazoAssinatura : {}", id);
        prazoAssinaturaRepository.deleteById(id);
    }
}
