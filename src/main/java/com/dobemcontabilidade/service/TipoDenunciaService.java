package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TipoDenuncia;
import com.dobemcontabilidade.repository.TipoDenunciaRepository;
import com.dobemcontabilidade.service.dto.TipoDenunciaDTO;
import com.dobemcontabilidade.service.mapper.TipoDenunciaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TipoDenuncia}.
 */
@Service
@Transactional
public class TipoDenunciaService {

    private static final Logger log = LoggerFactory.getLogger(TipoDenunciaService.class);

    private final TipoDenunciaRepository tipoDenunciaRepository;

    private final TipoDenunciaMapper tipoDenunciaMapper;

    public TipoDenunciaService(TipoDenunciaRepository tipoDenunciaRepository, TipoDenunciaMapper tipoDenunciaMapper) {
        this.tipoDenunciaRepository = tipoDenunciaRepository;
        this.tipoDenunciaMapper = tipoDenunciaMapper;
    }

    /**
     * Save a tipoDenuncia.
     *
     * @param tipoDenunciaDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoDenunciaDTO save(TipoDenunciaDTO tipoDenunciaDTO) {
        log.debug("Request to save TipoDenuncia : {}", tipoDenunciaDTO);
        TipoDenuncia tipoDenuncia = tipoDenunciaMapper.toEntity(tipoDenunciaDTO);
        tipoDenuncia = tipoDenunciaRepository.save(tipoDenuncia);
        return tipoDenunciaMapper.toDto(tipoDenuncia);
    }

    /**
     * Update a tipoDenuncia.
     *
     * @param tipoDenunciaDTO the entity to save.
     * @return the persisted entity.
     */
    public TipoDenunciaDTO update(TipoDenunciaDTO tipoDenunciaDTO) {
        log.debug("Request to update TipoDenuncia : {}", tipoDenunciaDTO);
        TipoDenuncia tipoDenuncia = tipoDenunciaMapper.toEntity(tipoDenunciaDTO);
        tipoDenuncia = tipoDenunciaRepository.save(tipoDenuncia);
        return tipoDenunciaMapper.toDto(tipoDenuncia);
    }

    /**
     * Partially update a tipoDenuncia.
     *
     * @param tipoDenunciaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoDenunciaDTO> partialUpdate(TipoDenunciaDTO tipoDenunciaDTO) {
        log.debug("Request to partially update TipoDenuncia : {}", tipoDenunciaDTO);

        return tipoDenunciaRepository
            .findById(tipoDenunciaDTO.getId())
            .map(existingTipoDenuncia -> {
                tipoDenunciaMapper.partialUpdate(existingTipoDenuncia, tipoDenunciaDTO);

                return existingTipoDenuncia;
            })
            .map(tipoDenunciaRepository::save)
            .map(tipoDenunciaMapper::toDto);
    }

    /**
     * Get one tipoDenuncia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDenunciaDTO> findOne(Long id) {
        log.debug("Request to get TipoDenuncia : {}", id);
        return tipoDenunciaRepository.findById(id).map(tipoDenunciaMapper::toDto);
    }

    /**
     * Delete the tipoDenuncia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDenuncia : {}", id);
        tipoDenunciaRepository.deleteById(id);
    }
}
