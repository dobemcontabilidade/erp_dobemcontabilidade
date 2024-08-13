package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CertificadoDigital;
import com.dobemcontabilidade.repository.CertificadoDigitalRepository;
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

    public CertificadoDigitalService(CertificadoDigitalRepository certificadoDigitalRepository) {
        this.certificadoDigitalRepository = certificadoDigitalRepository;
    }

    /**
     * Save a certificadoDigital.
     *
     * @param certificadoDigital the entity to save.
     * @return the persisted entity.
     */
    public CertificadoDigital save(CertificadoDigital certificadoDigital) {
        log.debug("Request to save CertificadoDigital : {}", certificadoDigital);
        return certificadoDigitalRepository.save(certificadoDigital);
    }

    /**
     * Update a certificadoDigital.
     *
     * @param certificadoDigital the entity to save.
     * @return the persisted entity.
     */
    public CertificadoDigital update(CertificadoDigital certificadoDigital) {
        log.debug("Request to update CertificadoDigital : {}", certificadoDigital);
        return certificadoDigitalRepository.save(certificadoDigital);
    }

    /**
     * Partially update a certificadoDigital.
     *
     * @param certificadoDigital the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CertificadoDigital> partialUpdate(CertificadoDigital certificadoDigital) {
        log.debug("Request to partially update CertificadoDigital : {}", certificadoDigital);

        return certificadoDigitalRepository
            .findById(certificadoDigital.getId())
            .map(existingCertificadoDigital -> {
                if (certificadoDigital.getUrlCertificado() != null) {
                    existingCertificadoDigital.setUrlCertificado(certificadoDigital.getUrlCertificado());
                }
                if (certificadoDigital.getDataContratacao() != null) {
                    existingCertificadoDigital.setDataContratacao(certificadoDigital.getDataContratacao());
                }
                if (certificadoDigital.getValidade() != null) {
                    existingCertificadoDigital.setValidade(certificadoDigital.getValidade());
                }
                if (certificadoDigital.getTipoCertificado() != null) {
                    existingCertificadoDigital.setTipoCertificado(certificadoDigital.getTipoCertificado());
                }

                return existingCertificadoDigital;
            })
            .map(certificadoDigitalRepository::save);
    }

    /**
     * Get all the certificadoDigitals with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CertificadoDigital> findAllWithEagerRelationships(Pageable pageable) {
        return certificadoDigitalRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one certificadoDigital by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CertificadoDigital> findOne(Long id) {
        log.debug("Request to get CertificadoDigital : {}", id);
        return certificadoDigitalRepository.findOneWithEagerRelationships(id);
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
