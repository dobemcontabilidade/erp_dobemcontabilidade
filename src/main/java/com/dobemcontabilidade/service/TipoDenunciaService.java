package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TipoDenuncia;
import com.dobemcontabilidade.repository.TipoDenunciaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TipoDenuncia}.
 */
@Service
@Transactional
public class TipoDenunciaService {

    private static final Logger log = LoggerFactory.getLogger(TipoDenunciaService.class);

    private final TipoDenunciaRepository tipoDenunciaRepository;

    public TipoDenunciaService(TipoDenunciaRepository tipoDenunciaRepository) {
        this.tipoDenunciaRepository = tipoDenunciaRepository;
    }

    /**
     * Save a tipoDenuncia.
     *
     * @param tipoDenuncia the entity to save.
     * @return the persisted entity.
     */
    public TipoDenuncia save(TipoDenuncia tipoDenuncia) {
        log.debug("Request to save TipoDenuncia : {}", tipoDenuncia);
        return tipoDenunciaRepository.save(tipoDenuncia);
    }

    /**
     * Update a tipoDenuncia.
     *
     * @param tipoDenuncia the entity to save.
     * @return the persisted entity.
     */
    public TipoDenuncia update(TipoDenuncia tipoDenuncia) {
        log.debug("Request to update TipoDenuncia : {}", tipoDenuncia);
        return tipoDenunciaRepository.save(tipoDenuncia);
    }

    /**
     * Partially update a tipoDenuncia.
     *
     * @param tipoDenuncia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TipoDenuncia> partialUpdate(TipoDenuncia tipoDenuncia) {
        log.debug("Request to partially update TipoDenuncia : {}", tipoDenuncia);

        return tipoDenunciaRepository
            .findById(tipoDenuncia.getId())
            .map(existingTipoDenuncia -> {
                if (tipoDenuncia.getTipo() != null) {
                    existingTipoDenuncia.setTipo(tipoDenuncia.getTipo());
                }
                if (tipoDenuncia.getDescricao() != null) {
                    existingTipoDenuncia.setDescricao(tipoDenuncia.getDescricao());
                }

                return existingTipoDenuncia;
            })
            .map(tipoDenunciaRepository::save);
    }

    /**
     * Get one tipoDenuncia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDenuncia> findOne(Long id) {
        log.debug("Request to get TipoDenuncia : {}", id);
        return tipoDenunciaRepository.findById(id);
    }

    /**
     * Delete the tipoDenuncia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDenuncia : {}", id);
        tipoDenunciaRepository.deleteById(id);
    }
}
