package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.repository.ProfissaoRepository;
import com.dobemcontabilidade.service.dto.ProfissaoDTO;
import com.dobemcontabilidade.service.mapper.ProfissaoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Profissao}.
 */
@Service
@Transactional
public class ProfissaoService {

    private static final Logger log = LoggerFactory.getLogger(ProfissaoService.class);

    private final ProfissaoRepository profissaoRepository;

    private final ProfissaoMapper profissaoMapper;

    public ProfissaoService(ProfissaoRepository profissaoRepository, ProfissaoMapper profissaoMapper) {
        this.profissaoRepository = profissaoRepository;
        this.profissaoMapper = profissaoMapper;
    }

    /**
     * Save a profissao.
     *
     * @param profissaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfissaoDTO save(ProfissaoDTO profissaoDTO) {
        log.debug("Request to save Profissao : {}", profissaoDTO);
        Profissao profissao = profissaoMapper.toEntity(profissaoDTO);
        profissao = profissaoRepository.save(profissao);
        return profissaoMapper.toDto(profissao);
    }

    /**
     * Update a profissao.
     *
     * @param profissaoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfissaoDTO update(ProfissaoDTO profissaoDTO) {
        log.debug("Request to update Profissao : {}", profissaoDTO);
        Profissao profissao = profissaoMapper.toEntity(profissaoDTO);
        profissao = profissaoRepository.save(profissao);
        return profissaoMapper.toDto(profissao);
    }

    /**
     * Partially update a profissao.
     *
     * @param profissaoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProfissaoDTO> partialUpdate(ProfissaoDTO profissaoDTO) {
        log.debug("Request to partially update Profissao : {}", profissaoDTO);

        return profissaoRepository
            .findById(profissaoDTO.getId())
            .map(existingProfissao -> {
                profissaoMapper.partialUpdate(existingProfissao, profissaoDTO);

                return existingProfissao;
            })
            .map(profissaoRepository::save)
            .map(profissaoMapper::toDto);
    }

    /**
     * Get all the profissaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ProfissaoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return profissaoRepository.findAllWithEagerRelationships(pageable).map(profissaoMapper::toDto);
    }

    /**
     * Get one profissao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfissaoDTO> findOne(Long id) {
        log.debug("Request to get Profissao : {}", id);
        return profissaoRepository.findOneWithEagerRelationships(id).map(profissaoMapper::toDto);
    }

    /**
     * Delete the profissao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Profissao : {}", id);
        profissaoRepository.deleteById(id);
    }
}
