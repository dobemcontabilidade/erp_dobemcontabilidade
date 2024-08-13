package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.ContadorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Contador}.
 */
@Service
@Transactional
public class ContadorService {

    private static final Logger log = LoggerFactory.getLogger(ContadorService.class);

    private final ContadorRepository contadorRepository;

    public ContadorService(ContadorRepository contadorRepository) {
        this.contadorRepository = contadorRepository;
    }

    /**
     * Save a contador.
     *
     * @param contador the entity to save.
     * @return the persisted entity.
     */
    public Contador save(Contador contador) {
        log.debug("Request to save Contador : {}", contador);
        return contadorRepository.save(contador);
    }

    /**
     * Update a contador.
     *
     * @param contador the entity to save.
     * @return the persisted entity.
     */
    public Contador update(Contador contador) {
        log.debug("Request to update Contador : {}", contador);
        return contadorRepository.save(contador);
    }

    /**
     * Partially update a contador.
     *
     * @param contador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Contador> partialUpdate(Contador contador) {
        log.debug("Request to partially update Contador : {}", contador);

        return contadorRepository
            .findById(contador.getId())
            .map(existingContador -> {
                if (contador.getCrc() != null) {
                    existingContador.setCrc(contador.getCrc());
                }
                if (contador.getLimiteEmpresas() != null) {
                    existingContador.setLimiteEmpresas(contador.getLimiteEmpresas());
                }
                if (contador.getLimiteDepartamentos() != null) {
                    existingContador.setLimiteDepartamentos(contador.getLimiteDepartamentos());
                }
                if (contador.getLimiteFaturamento() != null) {
                    existingContador.setLimiteFaturamento(contador.getLimiteFaturamento());
                }
                if (contador.getSituacaoContador() != null) {
                    existingContador.setSituacaoContador(contador.getSituacaoContador());
                }

                return existingContador;
            })
            .map(contadorRepository::save);
    }

    /**
     * Get all the contadors with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Contador> findAllWithEagerRelationships(Pageable pageable) {
        return contadorRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one contador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Contador> findOne(Long id) {
        log.debug("Request to get Contador : {}", id);
        return contadorRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the contador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Contador : {}", id);
        contadorRepository.deleteById(id);
    }
}
