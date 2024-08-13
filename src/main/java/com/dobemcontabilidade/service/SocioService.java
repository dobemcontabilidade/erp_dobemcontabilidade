package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.repository.SocioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Socio}.
 */
@Service
@Transactional
public class SocioService {

    private static final Logger log = LoggerFactory.getLogger(SocioService.class);

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    /**
     * Save a socio.
     *
     * @param socio the entity to save.
     * @return the persisted entity.
     */
    public Socio save(Socio socio) {
        log.debug("Request to save Socio : {}", socio);
        return socioRepository.save(socio);
    }

    /**
     * Update a socio.
     *
     * @param socio the entity to save.
     * @return the persisted entity.
     */
    public Socio update(Socio socio) {
        log.debug("Request to update Socio : {}", socio);
        return socioRepository.save(socio);
    }

    /**
     * Partially update a socio.
     *
     * @param socio the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Socio> partialUpdate(Socio socio) {
        log.debug("Request to partially update Socio : {}", socio);

        return socioRepository
            .findById(socio.getId())
            .map(existingSocio -> {
                if (socio.getProlabore() != null) {
                    existingSocio.setProlabore(socio.getProlabore());
                }
                if (socio.getPercentualSociedade() != null) {
                    existingSocio.setPercentualSociedade(socio.getPercentualSociedade());
                }
                if (socio.getAdminstrador() != null) {
                    existingSocio.setAdminstrador(socio.getAdminstrador());
                }
                if (socio.getDistribuicaoLucro() != null) {
                    existingSocio.setDistribuicaoLucro(socio.getDistribuicaoLucro());
                }
                if (socio.getResponsavelReceita() != null) {
                    existingSocio.setResponsavelReceita(socio.getResponsavelReceita());
                }
                if (socio.getPercentualDistribuicaoLucro() != null) {
                    existingSocio.setPercentualDistribuicaoLucro(socio.getPercentualDistribuicaoLucro());
                }
                if (socio.getFuncaoSocio() != null) {
                    existingSocio.setFuncaoSocio(socio.getFuncaoSocio());
                }

                return existingSocio;
            })
            .map(socioRepository::save);
    }

    /**
     * Get all the socios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Socio> findAllWithEagerRelationships(Pageable pageable) {
        return socioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one socio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Socio> findOne(Long id) {
        log.debug("Request to get Socio : {}", id);
        return socioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the socio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Socio : {}", id);
        socioRepository.deleteById(id);
    }
}
