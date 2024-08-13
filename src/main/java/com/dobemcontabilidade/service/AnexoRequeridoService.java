package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.repository.AnexoRequeridoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AnexoRequerido}.
 */
@Service
@Transactional
public class AnexoRequeridoService {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoService.class);

    private final AnexoRequeridoRepository anexoRequeridoRepository;

    public AnexoRequeridoService(AnexoRequeridoRepository anexoRequeridoRepository) {
        this.anexoRequeridoRepository = anexoRequeridoRepository;
    }

    /**
     * Save a anexoRequerido.
     *
     * @param anexoRequerido the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequerido save(AnexoRequerido anexoRequerido) {
        log.debug("Request to save AnexoRequerido : {}", anexoRequerido);
        return anexoRequeridoRepository.save(anexoRequerido);
    }

    /**
     * Update a anexoRequerido.
     *
     * @param anexoRequerido the entity to save.
     * @return the persisted entity.
     */
    public AnexoRequerido update(AnexoRequerido anexoRequerido) {
        log.debug("Request to update AnexoRequerido : {}", anexoRequerido);
        return anexoRequeridoRepository.save(anexoRequerido);
    }

    /**
     * Partially update a anexoRequerido.
     *
     * @param anexoRequerido the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AnexoRequerido> partialUpdate(AnexoRequerido anexoRequerido) {
        log.debug("Request to partially update AnexoRequerido : {}", anexoRequerido);

        return anexoRequeridoRepository
            .findById(anexoRequerido.getId())
            .map(existingAnexoRequerido -> {
                if (anexoRequerido.getNome() != null) {
                    existingAnexoRequerido.setNome(anexoRequerido.getNome());
                }
                if (anexoRequerido.getTipo() != null) {
                    existingAnexoRequerido.setTipo(anexoRequerido.getTipo());
                }
                if (anexoRequerido.getDescricao() != null) {
                    existingAnexoRequerido.setDescricao(anexoRequerido.getDescricao());
                }

                return existingAnexoRequerido;
            })
            .map(anexoRequeridoRepository::save);
    }

    /**
     * Get one anexoRequerido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AnexoRequerido> findOne(Long id) {
        log.debug("Request to get AnexoRequerido : {}", id);
        return anexoRequeridoRepository.findById(id);
    }

    /**
     * Delete the anexoRequerido by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AnexoRequerido : {}", id);
        anexoRequeridoRepository.deleteById(id);
    }
}
