package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.repository.PlanoContaAzulRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PlanoContaAzul}.
 */
@Service
@Transactional
public class PlanoContaAzulService {

    private static final Logger log = LoggerFactory.getLogger(PlanoContaAzulService.class);

    private final PlanoContaAzulRepository planoContaAzulRepository;

    public PlanoContaAzulService(PlanoContaAzulRepository planoContaAzulRepository) {
        this.planoContaAzulRepository = planoContaAzulRepository;
    }

    /**
     * Save a planoContaAzul.
     *
     * @param planoContaAzul the entity to save.
     * @return the persisted entity.
     */
    public PlanoContaAzul save(PlanoContaAzul planoContaAzul) {
        log.debug("Request to save PlanoContaAzul : {}", planoContaAzul);
        return planoContaAzulRepository.save(planoContaAzul);
    }

    /**
     * Update a planoContaAzul.
     *
     * @param planoContaAzul the entity to save.
     * @return the persisted entity.
     */
    public PlanoContaAzul update(PlanoContaAzul planoContaAzul) {
        log.debug("Request to update PlanoContaAzul : {}", planoContaAzul);
        return planoContaAzulRepository.save(planoContaAzul);
    }

    /**
     * Partially update a planoContaAzul.
     *
     * @param planoContaAzul the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlanoContaAzul> partialUpdate(PlanoContaAzul planoContaAzul) {
        log.debug("Request to partially update PlanoContaAzul : {}", planoContaAzul);

        return planoContaAzulRepository
            .findById(planoContaAzul.getId())
            .map(existingPlanoContaAzul -> {
                if (planoContaAzul.getNome() != null) {
                    existingPlanoContaAzul.setNome(planoContaAzul.getNome());
                }
                if (planoContaAzul.getValorBase() != null) {
                    existingPlanoContaAzul.setValorBase(planoContaAzul.getValorBase());
                }
                if (planoContaAzul.getUsuarios() != null) {
                    existingPlanoContaAzul.setUsuarios(planoContaAzul.getUsuarios());
                }
                if (planoContaAzul.getBoletos() != null) {
                    existingPlanoContaAzul.setBoletos(planoContaAzul.getBoletos());
                }
                if (planoContaAzul.getNotaFiscalProduto() != null) {
                    existingPlanoContaAzul.setNotaFiscalProduto(planoContaAzul.getNotaFiscalProduto());
                }
                if (planoContaAzul.getNotaFiscalServico() != null) {
                    existingPlanoContaAzul.setNotaFiscalServico(planoContaAzul.getNotaFiscalServico());
                }
                if (planoContaAzul.getNotaFiscalCe() != null) {
                    existingPlanoContaAzul.setNotaFiscalCe(planoContaAzul.getNotaFiscalCe());
                }
                if (planoContaAzul.getSuporte() != null) {
                    existingPlanoContaAzul.setSuporte(planoContaAzul.getSuporte());
                }
                if (planoContaAzul.getSituacao() != null) {
                    existingPlanoContaAzul.setSituacao(planoContaAzul.getSituacao());
                }

                return existingPlanoContaAzul;
            })
            .map(planoContaAzulRepository::save);
    }

    /**
     * Get one planoContaAzul by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PlanoContaAzul> findOne(Long id) {
        log.debug("Request to get PlanoContaAzul : {}", id);
        return planoContaAzulRepository.findById(id);
    }

    /**
     * Delete the planoContaAzul by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PlanoContaAzul : {}", id);
        planoContaAzulRepository.deleteById(id);
    }
}
