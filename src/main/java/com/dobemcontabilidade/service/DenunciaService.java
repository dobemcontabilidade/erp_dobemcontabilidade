package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Denuncia;
import com.dobemcontabilidade.repository.DenunciaRepository;
import com.dobemcontabilidade.service.dto.DenunciaDTO;
import com.dobemcontabilidade.service.mapper.DenunciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Denuncia}.
 */
@Service
@Transactional
public class DenunciaService {

    private static final Logger log = LoggerFactory.getLogger(DenunciaService.class);

    private final DenunciaRepository denunciaRepository;

    private final DenunciaMapper denunciaMapper;

    public DenunciaService(DenunciaRepository denunciaRepository, DenunciaMapper denunciaMapper) {
        this.denunciaRepository = denunciaRepository;
        this.denunciaMapper = denunciaMapper;
    }

    /**
     * Save a denuncia.
     *
     * @param denunciaDTO the entity to save.
     * @return the persisted entity.
     */
    public DenunciaDTO save(DenunciaDTO denunciaDTO) {
        log.debug("Request to save Denuncia : {}", denunciaDTO);
        Denuncia denuncia = denunciaMapper.toEntity(denunciaDTO);
        denuncia = denunciaRepository.save(denuncia);
        return denunciaMapper.toDto(denuncia);
    }

    /**
     * Update a denuncia.
     *
     * @param denunciaDTO the entity to save.
     * @return the persisted entity.
     */
    public DenunciaDTO update(DenunciaDTO denunciaDTO) {
        log.debug("Request to update Denuncia : {}", denunciaDTO);
        Denuncia denuncia = denunciaMapper.toEntity(denunciaDTO);
        denuncia = denunciaRepository.save(denuncia);
        return denunciaMapper.toDto(denuncia);
    }

    /**
     * Partially update a denuncia.
     *
     * @param denunciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DenunciaDTO> partialUpdate(DenunciaDTO denunciaDTO) {
        log.debug("Request to partially update Denuncia : {}", denunciaDTO);

        return denunciaRepository
            .findById(denunciaDTO.getId())
            .map(existingDenuncia -> {
                denunciaMapper.partialUpdate(existingDenuncia, denunciaDTO);

                return existingDenuncia;
            })
            .map(denunciaRepository::save)
            .map(denunciaMapper::toDto);
    }

    /**
     * Get one denuncia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DenunciaDTO> findOne(Long id) {
        log.debug("Request to get Denuncia : {}", id);
        return denunciaRepository.findById(id).map(denunciaMapper::toDto);
    }

    /**
     * Delete the denuncia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Denuncia : {}", id);
        denunciaRepository.deleteById(id);
    }
}
