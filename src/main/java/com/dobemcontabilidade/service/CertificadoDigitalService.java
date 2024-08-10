package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
import com.dobemcontabilidade.service.dto.CertificadoDigitalDTO;
import com.dobemcontabilidade.service.mapper.CertificadoDigitalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.CertificadoDigital}.
 */
@Service
@Transactional
public class CertificadoDigitalService {

    private static final Logger log = LoggerFactory.getLogger(CertificadoDigitalService.class);

    private final CertificadoDigitalRepository certificadoDigitalRepository;

    private final CertificadoDigitalMapper certificadoDigitalMapper;

    public CertificadoDigitalService(
        CertificadoDigitalRepository certificadoDigitalRepository,
        CertificadoDigitalMapper certificadoDigitalMapper
    ) {
        this.certificadoDigitalRepository = certificadoDigitalRepository;
        this.certificadoDigitalMapper = certificadoDigitalMapper;
    }

    /**
     * Save a certificadoDigital.
     *
     * @param certificadoDigitalDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificadoDigitalDTO save(CertificadoDigitalDTO certificadoDigitalDTO) {
        log.debug("Request to save CertificadoDigital : {}", certificadoDigitalDTO);
        CertificadoDigital certificadoDigital = certificadoDigitalMapper.toEntity(certificadoDigitalDTO);
        certificadoDigital = certificadoDigitalRepository.save(certificadoDigital);
        return certificadoDigitalMapper.toDto(certificadoDigital);
    }

    /**
     * Update a certificadoDigital.
     *
     * @param certificadoDigitalDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificadoDigitalDTO update(CertificadoDigitalDTO certificadoDigitalDTO) {
        log.debug("Request to update CertificadoDigital : {}", certificadoDigitalDTO);
        CertificadoDigital certificadoDigital = certificadoDigitalMapper.toEntity(certificadoDigitalDTO);
        certificadoDigital = certificadoDigitalRepository.save(certificadoDigital);
        return certificadoDigitalMapper.toDto(certificadoDigital);
    }

    /**
     * Partially update a certificadoDigital.
     *
     * @param certificadoDigitalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CertificadoDigitalDTO> partialUpdate(CertificadoDigitalDTO certificadoDigitalDTO) {
        log.debug("Request to partially update CertificadoDigital : {}", certificadoDigitalDTO);

        return certificadoDigitalRepository
            .findById(certificadoDigitalDTO.getId())
            .map(existingCertificadoDigital -> {
                certificadoDigitalMapper.partialUpdate(existingCertificadoDigital, certificadoDigitalDTO);

                return existingCertificadoDigital;
            })
            .map(certificadoDigitalRepository::save)
            .map(certificadoDigitalMapper::toDto);
    }

    /**
     * Get all the certificadoDigitals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CertificadoDigitalDTO> findAllWithEagerRelationships(Pageable pageable) {
        return certificadoDigitalRepository.findAllWithEagerRelationships(pageable).map(certificadoDigitalMapper::toDto);
    }

    /**
     * Get one certificadoDigital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CertificadoDigitalDTO> findOne(Long id) {
        log.debug("Request to get CertificadoDigital : {}", id);
        return certificadoDigitalRepository.findOneWithEagerRelationships(id).map(certificadoDigitalMapper::toDto);
    }

    /**
     * Delete the certificadoDigital by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CertificadoDigital : {}", id);
        certificadoDigitalRepository.deleteById(id);
    }
}
