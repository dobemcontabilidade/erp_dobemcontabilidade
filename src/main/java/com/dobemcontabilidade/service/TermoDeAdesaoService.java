package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.repository.TermoDeAdesaoRepository;
import com.dobemcontabilidade.service.dto.TermoDeAdesaoDTO;
import com.dobemcontabilidade.service.mapper.TermoDeAdesaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoDeAdesao}.
 */
@Service
@Transactional
public class TermoDeAdesaoService {

    private static final Logger log = LoggerFactory.getLogger(TermoDeAdesaoService.class);

    private final TermoDeAdesaoRepository termoDeAdesaoRepository;

    private final TermoDeAdesaoMapper termoDeAdesaoMapper;

    public TermoDeAdesaoService(TermoDeAdesaoRepository termoDeAdesaoRepository, TermoDeAdesaoMapper termoDeAdesaoMapper) {
        this.termoDeAdesaoRepository = termoDeAdesaoRepository;
        this.termoDeAdesaoMapper = termoDeAdesaoMapper;
    }

    /**
     * Save a termoDeAdesao.
     *
     * @param termoDeAdesaoDTO the entity to save.
     * @return the persisted entity.
     */
    public TermoDeAdesaoDTO save(TermoDeAdesaoDTO termoDeAdesaoDTO) {
        log.debug("Request to save TermoDeAdesao : {}", termoDeAdesaoDTO);
        TermoDeAdesao termoDeAdesao = termoDeAdesaoMapper.toEntity(termoDeAdesaoDTO);
        termoDeAdesao = termoDeAdesaoRepository.save(termoDeAdesao);
        return termoDeAdesaoMapper.toDto(termoDeAdesao);
    }

    /**
     * Update a termoDeAdesao.
     *
     * @param termoDeAdesaoDTO the entity to save.
     * @return the persisted entity.
     */
    public TermoDeAdesaoDTO update(TermoDeAdesaoDTO termoDeAdesaoDTO) {
        log.debug("Request to update TermoDeAdesao : {}", termoDeAdesaoDTO);
        TermoDeAdesao termoDeAdesao = termoDeAdesaoMapper.toEntity(termoDeAdesaoDTO);
        termoDeAdesao = termoDeAdesaoRepository.save(termoDeAdesao);
        return termoDeAdesaoMapper.toDto(termoDeAdesao);
    }

    /**
     * Partially update a termoDeAdesao.
     *
     * @param termoDeAdesaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoDeAdesaoDTO> partialUpdate(TermoDeAdesaoDTO termoDeAdesaoDTO) {
        log.debug("Request to partially update TermoDeAdesao : {}", termoDeAdesaoDTO);

        return termoDeAdesaoRepository
            .findById(termoDeAdesaoDTO.getId())
            .map(existingTermoDeAdesao -> {
                termoDeAdesaoMapper.partialUpdate(existingTermoDeAdesao, termoDeAdesaoDTO);

                return existingTermoDeAdesao;
            })
            .map(termoDeAdesaoRepository::save)
            .map(termoDeAdesaoMapper::toDto);
    }

    /**
     * Get one termoDeAdesao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoDeAdesaoDTO> findOne(Long id) {
        log.debug("Request to get TermoDeAdesao : {}", id);
        return termoDeAdesaoRepository.findById(id).map(termoDeAdesaoMapper::toDto);
    }

    /**
     * Delete the termoDeAdesao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoDeAdesao : {}", id);
        termoDeAdesaoRepository.deleteById(id);
    }
}
