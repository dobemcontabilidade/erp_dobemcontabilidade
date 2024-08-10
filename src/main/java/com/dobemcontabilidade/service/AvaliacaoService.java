package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Avaliacao;
import com.dobemcontabilidade.repository.AvaliacaoRepository;
import com.dobemcontabilidade.service.dto.AvaliacaoDTO;
import com.dobemcontabilidade.service.mapper.AvaliacaoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Avaliacao}.
 */
@Service
@Transactional
public class AvaliacaoService {

    private static final Logger log = LoggerFactory.getLogger(AvaliacaoService.class);

    private final AvaliacaoRepository avaliacaoRepository;

    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    /**
     * Save a avaliacao.
     *
     * @param avaliacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AvaliacaoDTO save(AvaliacaoDTO avaliacaoDTO) {
        log.debug("Request to save Avaliacao : {}", avaliacaoDTO);
        Avaliacao avaliacao = avaliacaoMapper.toEntity(avaliacaoDTO);
        avaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDto(avaliacao);
    }

    /**
     * Update a avaliacao.
     *
     * @param avaliacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AvaliacaoDTO update(AvaliacaoDTO avaliacaoDTO) {
        log.debug("Request to update Avaliacao : {}", avaliacaoDTO);
        Avaliacao avaliacao = avaliacaoMapper.toEntity(avaliacaoDTO);
        avaliacao = avaliacaoRepository.save(avaliacao);
        return avaliacaoMapper.toDto(avaliacao);
    }

    /**
     * Partially update a avaliacao.
     *
     * @param avaliacaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AvaliacaoDTO> partialUpdate(AvaliacaoDTO avaliacaoDTO) {
        log.debug("Request to partially update Avaliacao : {}", avaliacaoDTO);

        return avaliacaoRepository
            .findById(avaliacaoDTO.getId())
            .map(existingAvaliacao -> {
                avaliacaoMapper.partialUpdate(existingAvaliacao, avaliacaoDTO);

                return existingAvaliacao;
            })
            .map(avaliacaoRepository::save)
            .map(avaliacaoMapper::toDto);
    }

    /**
     * Get all the avaliacaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AvaliacaoDTO> findAll() {
        log.debug("Request to get all Avaliacaos");
        return avaliacaoRepository.findAll().stream().map(avaliacaoMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one avaliacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AvaliacaoDTO> findOne(Long id) {
        log.debug("Request to get Avaliacao : {}", id);
        return avaliacaoRepository.findById(id).map(avaliacaoMapper::toDto);
    }

    /**
     * Delete the avaliacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Avaliacao : {}", id);
        avaliacaoRepository.deleteById(id);
    }
}
