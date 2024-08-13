package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.repository.PerfilContadorRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilContador}.
 */
@Service
@Transactional
public class PerfilContadorService {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorService.class);

    private final PerfilContadorRepository perfilContadorRepository;

    public PerfilContadorService(PerfilContadorRepository perfilContadorRepository) {
        this.perfilContadorRepository = perfilContadorRepository;
    }

    /**
     * Save a perfilContador.
     *
     * @param perfilContador the entity to save.
     * @return the persisted entity.
     */
    public PerfilContador save(PerfilContador perfilContador) {
        log.debug("Request to save PerfilContador : {}", perfilContador);
        return perfilContadorRepository.save(perfilContador);
    }

    /**
     * Update a perfilContador.
     *
     * @param perfilContador the entity to save.
     * @return the persisted entity.
     */
    public PerfilContador update(PerfilContador perfilContador) {
        log.debug("Request to update PerfilContador : {}", perfilContador);
        return perfilContadorRepository.save(perfilContador);
    }

    /**
     * Partially update a perfilContador.
     *
     * @param perfilContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilContador> partialUpdate(PerfilContador perfilContador) {
        log.debug("Request to partially update PerfilContador : {}", perfilContador);

        return perfilContadorRepository
            .findById(perfilContador.getId())
            .map(existingPerfilContador -> {
                if (perfilContador.getPerfil() != null) {
                    existingPerfilContador.setPerfil(perfilContador.getPerfil());
                }
                if (perfilContador.getDescricao() != null) {
                    existingPerfilContador.setDescricao(perfilContador.getDescricao());
                }
                if (perfilContador.getLimiteEmpresas() != null) {
                    existingPerfilContador.setLimiteEmpresas(perfilContador.getLimiteEmpresas());
                }
                if (perfilContador.getLimiteDepartamentos() != null) {
                    existingPerfilContador.setLimiteDepartamentos(perfilContador.getLimiteDepartamentos());
                }
                if (perfilContador.getLimiteFaturamento() != null) {
                    existingPerfilContador.setLimiteFaturamento(perfilContador.getLimiteFaturamento());
                }

                return existingPerfilContador;
            })
            .map(perfilContadorRepository::save);
    }

    /**
     * Get one perfilContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilContador> findOne(Long id) {
        log.debug("Request to get PerfilContador : {}", id);
        return perfilContadorRepository.findById(id);
    }

    /**
     * Delete the perfilContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilContador : {}", id);
        perfilContadorRepository.deleteById(id);
    }
}
