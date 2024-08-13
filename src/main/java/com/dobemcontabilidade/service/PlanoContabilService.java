package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.repository.PlanoContabilRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PlanoContabil}.
 */
@Service
@Transactional
public class PlanoContabilService {

    private static final Logger log = LoggerFactory.getLogger(PlanoContabilService.class);

    private final PlanoContabilRepository planoContabilRepository;

    public PlanoContabilService(PlanoContabilRepository planoContabilRepository) {
        this.planoContabilRepository = planoContabilRepository;
    }

    /**
     * Save a planoContabil.
     *
     * @param planoContabil the entity to save.
     * @return the persisted entity.
     */
    public PlanoContabil save(PlanoContabil planoContabil) {
        log.debug("Request to save PlanoContabil : {}", planoContabil);
        return planoContabilRepository.save(planoContabil);
    }

    /**
     * Update a planoContabil.
     *
     * @param planoContabil the entity to save.
     * @return the persisted entity.
     */
    public PlanoContabil update(PlanoContabil planoContabil) {
        log.debug("Request to update PlanoContabil : {}", planoContabil);
        return planoContabilRepository.save(planoContabil);
    }

    /**
     * Partially update a planoContabil.
     *
     * @param planoContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlanoContabil> partialUpdate(PlanoContabil planoContabil) {
        log.debug("Request to partially update PlanoContabil : {}", planoContabil);

        return planoContabilRepository
            .findById(planoContabil.getId())
            .map(existingPlanoContabil -> {
                if (planoContabil.getNome() != null) {
                    existingPlanoContabil.setNome(planoContabil.getNome());
                }
                if (planoContabil.getAdicionalSocio() != null) {
                    existingPlanoContabil.setAdicionalSocio(planoContabil.getAdicionalSocio());
                }
                if (planoContabil.getAdicionalFuncionario() != null) {
                    existingPlanoContabil.setAdicionalFuncionario(planoContabil.getAdicionalFuncionario());
                }
                if (planoContabil.getSociosIsentos() != null) {
                    existingPlanoContabil.setSociosIsentos(planoContabil.getSociosIsentos());
                }
                if (planoContabil.getAdicionalFaturamento() != null) {
                    existingPlanoContabil.setAdicionalFaturamento(planoContabil.getAdicionalFaturamento());
                }
                if (planoContabil.getValorBaseFaturamento() != null) {
                    existingPlanoContabil.setValorBaseFaturamento(planoContabil.getValorBaseFaturamento());
                }
                if (planoContabil.getValorBaseAbertura() != null) {
                    existingPlanoContabil.setValorBaseAbertura(planoContabil.getValorBaseAbertura());
                }
                if (planoContabil.getSituacao() != null) {
                    existingPlanoContabil.setSituacao(planoContabil.getSituacao());
                }

                return existingPlanoContabil;
            })
            .map(planoContabilRepository::save);
    }

    /**
     * Get one planoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoContabil> findOne(Long id) {
        log.debug("Request to get PlanoContabil : {}", id);
        return planoContabilRepository.findById(id);
    }

    /**
     * Delete the planoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoContabil : {}", id);
        planoContabilRepository.deleteById(id);
    }
}
