package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Cnae;
import com.dobemcontabilidade.repository.CnaeRepository;
import com.dobemcontabilidade.service.dto.CnaeDTO;
import com.dobemcontabilidade.service.mapper.CnaeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Cnae}.
 */
@Service
@Transactional
public class CnaeService {

    private static final Logger log = LoggerFactory.getLogger(CnaeService.class);

    private final CnaeRepository cnaeRepository;

    private final CnaeMapper cnaeMapper;

    public CnaeService(CnaeRepository cnaeRepository, CnaeMapper cnaeMapper) {
        this.cnaeRepository = cnaeRepository;
        this.cnaeMapper = cnaeMapper;
    }

    /**
     * Save a cnae.
     *
     * @param cnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public CnaeDTO save(CnaeDTO cnaeDTO) {
        log.debug("Request to save Cnae : {}", cnaeDTO);
        Cnae cnae = cnaeMapper.toEntity(cnaeDTO);
        cnae = cnaeRepository.save(cnae);
        return cnaeMapper.toDto(cnae);
    }

    /**
     * Update a cnae.
     *
     * @param cnaeDTO the entity to save.
     * @return the persisted entity.
     */
    public CnaeDTO update(CnaeDTO cnaeDTO) {
        log.debug("Request to update Cnae : {}", cnaeDTO);
        Cnae cnae = cnaeMapper.toEntity(cnaeDTO);
        cnae = cnaeRepository.save(cnae);
        return cnaeMapper.toDto(cnae);
    }

    /**
     * Partially update a cnae.
     *
     * @param cnaeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CnaeDTO> partialUpdate(CnaeDTO cnaeDTO) {
        log.debug("Request to partially update Cnae : {}", cnaeDTO);

        return cnaeRepository
            .findById(cnaeDTO.getId())
            .map(existingCnae -> {
                cnaeMapper.partialUpdate(existingCnae, cnaeDTO);

                return existingCnae;
            })
            .map(cnaeRepository::save)
            .map(cnaeMapper::toDto);
    }

    /**
     * Get all the cnaes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CnaeDTO> findAll() {
        log.debug("Request to get all Cnaes");
        return cnaeRepository.findAll().stream().map(cnaeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CnaeDTO> findOne(Long id) {
        log.debug("Request to get Cnae : {}", id);
        return cnaeRepository.findById(id).map(cnaeMapper::toDto);
    }

    /**
     * Delete the cnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Cnae : {}", id);
        cnaeRepository.deleteById(id);
    }
}
