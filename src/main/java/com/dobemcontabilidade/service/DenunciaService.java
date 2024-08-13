package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Denuncia;
import com.dobemcontabilidade.repository.DenunciaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Denuncia}.
 */
@Service
@Transactional
public class DenunciaService {

    private static final Logger log = LoggerFactory.getLogger(DenunciaService.class);

    private final DenunciaRepository denunciaRepository;

    public DenunciaService(DenunciaRepository denunciaRepository) {
        this.denunciaRepository = denunciaRepository;
    }

    /**
     * Save a denuncia.
     *
     * @param denuncia the entity to save.
     * @return the persisted entity.
     */
    public Denuncia save(Denuncia denuncia) {
        log.debug("Request to save Denuncia : {}", denuncia);
        return denunciaRepository.save(denuncia);
    }

    /**
     * Update a denuncia.
     *
     * @param denuncia the entity to save.
     * @return the persisted entity.
     */
    public Denuncia update(Denuncia denuncia) {
        log.debug("Request to update Denuncia : {}", denuncia);
        return denunciaRepository.save(denuncia);
    }

    /**
     * Partially update a denuncia.
     *
     * @param denuncia the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Denuncia> partialUpdate(Denuncia denuncia) {
        log.debug("Request to partially update Denuncia : {}", denuncia);

        return denunciaRepository
            .findById(denuncia.getId())
            .map(existingDenuncia -> {
                if (denuncia.getTitulo() != null) {
                    existingDenuncia.setTitulo(denuncia.getTitulo());
                }
                if (denuncia.getMensagem() != null) {
                    existingDenuncia.setMensagem(denuncia.getMensagem());
                }
                if (denuncia.getDescricao() != null) {
                    existingDenuncia.setDescricao(denuncia.getDescricao());
                }

                return existingDenuncia;
            })
            .map(denunciaRepository::save);
    }

    /**
     * Get one denuncia by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Denuncia> findOne(Long id) {
        log.debug("Request to get Denuncia : {}", id);
        return denunciaRepository.findById(id);
    }

    /**
     * Delete the denuncia by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Denuncia : {}", id);
        denunciaRepository.deleteById(id);
    }
}
