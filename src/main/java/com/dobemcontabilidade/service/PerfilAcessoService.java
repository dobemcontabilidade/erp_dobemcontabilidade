package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilAcesso;
import com.dobemcontabilidade.repository.PerfilAcessoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilAcesso}.
 */
@Service
@Transactional
public class PerfilAcessoService {

    private static final Logger log = LoggerFactory.getLogger(PerfilAcessoService.class);

    private final PerfilAcessoRepository perfilAcessoRepository;

    public PerfilAcessoService(PerfilAcessoRepository perfilAcessoRepository) {
        this.perfilAcessoRepository = perfilAcessoRepository;
    }

    /**
     * Save a perfilAcesso.
     *
     * @param perfilAcesso the entity to save.
     * @return the persisted entity.
     */
    public PerfilAcesso save(PerfilAcesso perfilAcesso) {
        log.debug("Request to save PerfilAcesso : {}", perfilAcesso);
        return perfilAcessoRepository.save(perfilAcesso);
    }

    /**
     * Update a perfilAcesso.
     *
     * @param perfilAcesso the entity to save.
     * @return the persisted entity.
     */
    public PerfilAcesso update(PerfilAcesso perfilAcesso) {
        log.debug("Request to update PerfilAcesso : {}", perfilAcesso);
        return perfilAcessoRepository.save(perfilAcesso);
    }

    /**
     * Partially update a perfilAcesso.
     *
     * @param perfilAcesso the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilAcesso> partialUpdate(PerfilAcesso perfilAcesso) {
        log.debug("Request to partially update PerfilAcesso : {}", perfilAcesso);

        return perfilAcessoRepository
            .findById(perfilAcesso.getId())
            .map(existingPerfilAcesso -> {
                if (perfilAcesso.getNome() != null) {
                    existingPerfilAcesso.setNome(perfilAcesso.getNome());
                }
                if (perfilAcesso.getDescricao() != null) {
                    existingPerfilAcesso.setDescricao(perfilAcesso.getDescricao());
                }

                return existingPerfilAcesso;
            })
            .map(perfilAcessoRepository::save);
    }

    /**
     * Get one perfilAcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilAcesso> findOne(Long id) {
        log.debug("Request to get PerfilAcesso : {}", id);
        return perfilAcessoRepository.findById(id);
    }

    /**
     * Delete the perfilAcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilAcesso : {}", id);
        perfilAcessoRepository.deleteById(id);
    }
}
