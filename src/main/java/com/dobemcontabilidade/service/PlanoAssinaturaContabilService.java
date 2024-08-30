package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import com.dobemcontabilidade.repository.PlanoAssinaturaContabilRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PlanoAssinaturaContabil}.
 */
@Service
@Transactional
public class PlanoAssinaturaContabilService {

    private static final Logger log = LoggerFactory.getLogger(PlanoAssinaturaContabilService.class);

    private final PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository;

    public PlanoAssinaturaContabilService(PlanoAssinaturaContabilRepository planoAssinaturaContabilRepository) {
        this.planoAssinaturaContabilRepository = planoAssinaturaContabilRepository;
    }

    /**
     * Save a planoAssinaturaContabil.
     *
     * @param planoAssinaturaContabil the entity to save.
     * @return the persisted entity.
     */
    public PlanoAssinaturaContabil save(PlanoAssinaturaContabil planoAssinaturaContabil) {
        log.debug("Request to save PlanoAssinaturaContabil : {}", planoAssinaturaContabil);
        return planoAssinaturaContabilRepository.save(planoAssinaturaContabil);
    }

    /**
     * Update a planoAssinaturaContabil.
     *
     * @param planoAssinaturaContabil the entity to save.
     * @return the persisted entity.
     */
    public PlanoAssinaturaContabil update(PlanoAssinaturaContabil planoAssinaturaContabil) {
        log.debug("Request to update PlanoAssinaturaContabil : {}", planoAssinaturaContabil);
        return planoAssinaturaContabilRepository.save(planoAssinaturaContabil);
    }

    /**
     * Partially update a planoAssinaturaContabil.
     *
     * @param planoAssinaturaContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlanoAssinaturaContabil> partialUpdate(PlanoAssinaturaContabil planoAssinaturaContabil) {
        log.debug("Request to partially update PlanoAssinaturaContabil : {}", planoAssinaturaContabil);

        return planoAssinaturaContabilRepository
            .findById(planoAssinaturaContabil.getId())
            .map(existingPlanoAssinaturaContabil -> {
                if (planoAssinaturaContabil.getNome() != null) {
                    existingPlanoAssinaturaContabil.setNome(planoAssinaturaContabil.getNome());
                }
                if (planoAssinaturaContabil.getAdicionalSocio() != null) {
                    existingPlanoAssinaturaContabil.setAdicionalSocio(planoAssinaturaContabil.getAdicionalSocio());
                }
                if (planoAssinaturaContabil.getAdicionalFuncionario() != null) {
                    existingPlanoAssinaturaContabil.setAdicionalFuncionario(planoAssinaturaContabil.getAdicionalFuncionario());
                }
                if (planoAssinaturaContabil.getSociosIsentos() != null) {
                    existingPlanoAssinaturaContabil.setSociosIsentos(planoAssinaturaContabil.getSociosIsentos());
                }
                if (planoAssinaturaContabil.getAdicionalFaturamento() != null) {
                    existingPlanoAssinaturaContabil.setAdicionalFaturamento(planoAssinaturaContabil.getAdicionalFaturamento());
                }
                if (planoAssinaturaContabil.getValorBaseFaturamento() != null) {
                    existingPlanoAssinaturaContabil.setValorBaseFaturamento(planoAssinaturaContabil.getValorBaseFaturamento());
                }
                if (planoAssinaturaContabil.getValorBaseAbertura() != null) {
                    existingPlanoAssinaturaContabil.setValorBaseAbertura(planoAssinaturaContabil.getValorBaseAbertura());
                }
                if (planoAssinaturaContabil.getSituacao() != null) {
                    existingPlanoAssinaturaContabil.setSituacao(planoAssinaturaContabil.getSituacao());
                }

                return existingPlanoAssinaturaContabil;
            })
            .map(planoAssinaturaContabilRepository::save);
    }

    /**
     * Get one planoAssinaturaContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoAssinaturaContabil> findOne(Long id) {
        log.debug("Request to get PlanoAssinaturaContabil : {}", id);
        return planoAssinaturaContabilRepository.findById(id);
    }

    /**
     * Delete the planoAssinaturaContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoAssinaturaContabil : {}", id);
        planoAssinaturaContabilRepository.deleteById(id);
    }
}
