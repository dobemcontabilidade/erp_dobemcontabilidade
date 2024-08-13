package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Esfera;
import com.dobemcontabilidade.repository.EsferaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Esfera}.
 */
@Service
@Transactional
public class EsferaService {

    private static final Logger log = LoggerFactory.getLogger(EsferaService.class);

    private final EsferaRepository esferaRepository;

    public EsferaService(EsferaRepository esferaRepository) {
        this.esferaRepository = esferaRepository;
    }

    /**
     * Save a esfera.
     *
     * @param esfera the entity to save.
     * @return the persisted entity.
     */
    public Esfera save(Esfera esfera) {
        log.debug("Request to save Esfera : {}", esfera);
        return esferaRepository.save(esfera);
    }

    /**
     * Update a esfera.
     *
     * @param esfera the entity to save.
     * @return the persisted entity.
     */
    public Esfera update(Esfera esfera) {
        log.debug("Request to update Esfera : {}", esfera);
        return esferaRepository.save(esfera);
    }

    /**
     * Partially update a esfera.
     *
     * @param esfera the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Esfera> partialUpdate(Esfera esfera) {
        log.debug("Request to partially update Esfera : {}", esfera);

        return esferaRepository
            .findById(esfera.getId())
            .map(existingEsfera -> {
                if (esfera.getNome() != null) {
                    existingEsfera.setNome(esfera.getNome());
                }
                if (esfera.getDescricao() != null) {
                    existingEsfera.setDescricao(esfera.getDescricao());
                }

                return existingEsfera;
            })
            .map(esferaRepository::save);
    }

    /**
     * Get one esfera by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Esfera> findOne(Long id) {
        log.debug("Request to get Esfera : {}", id);
        return esferaRepository.findById(id);
    }

    /**
     * Delete the esfera by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Esfera : {}", id);
        esferaRepository.deleteById(id);
    }
}
