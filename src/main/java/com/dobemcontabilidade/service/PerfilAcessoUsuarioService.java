package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.PerfilAcessoUsuario;
import com.dobemcontabilidade.repository.PerfilAcessoUsuarioRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.PerfilAcessoUsuario}.
 */
@Service
@Transactional
public class PerfilAcessoUsuarioService {

    private static final Logger log = LoggerFactory.getLogger(PerfilAcessoUsuarioService.class);

    private final PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository;

    public PerfilAcessoUsuarioService(PerfilAcessoUsuarioRepository perfilAcessoUsuarioRepository) {
        this.perfilAcessoUsuarioRepository = perfilAcessoUsuarioRepository;
    }

    /**
     * Save a perfilAcessoUsuario.
     *
     * @param perfilAcessoUsuario the entity to save.
     * @return the persisted entity.
     */
    public PerfilAcessoUsuario save(PerfilAcessoUsuario perfilAcessoUsuario) {
        log.debug("Request to save PerfilAcessoUsuario : {}", perfilAcessoUsuario);
        return perfilAcessoUsuarioRepository.save(perfilAcessoUsuario);
    }

    /**
     * Update a perfilAcessoUsuario.
     *
     * @param perfilAcessoUsuario the entity to save.
     * @return the persisted entity.
     */
    public PerfilAcessoUsuario update(PerfilAcessoUsuario perfilAcessoUsuario) {
        log.debug("Request to update PerfilAcessoUsuario : {}", perfilAcessoUsuario);
        return perfilAcessoUsuarioRepository.save(perfilAcessoUsuario);
    }

    /**
     * Partially update a perfilAcessoUsuario.
     *
     * @param perfilAcessoUsuario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PerfilAcessoUsuario> partialUpdate(PerfilAcessoUsuario perfilAcessoUsuario) {
        log.debug("Request to partially update PerfilAcessoUsuario : {}", perfilAcessoUsuario);

        return perfilAcessoUsuarioRepository
            .findById(perfilAcessoUsuario.getId())
            .map(existingPerfilAcessoUsuario -> {
                if (perfilAcessoUsuario.getNome() != null) {
                    existingPerfilAcessoUsuario.setNome(perfilAcessoUsuario.getNome());
                }
                if (perfilAcessoUsuario.getAutorizado() != null) {
                    existingPerfilAcessoUsuario.setAutorizado(perfilAcessoUsuario.getAutorizado());
                }
                if (perfilAcessoUsuario.getDataExpiracao() != null) {
                    existingPerfilAcessoUsuario.setDataExpiracao(perfilAcessoUsuario.getDataExpiracao());
                }

                return existingPerfilAcessoUsuario;
            })
            .map(perfilAcessoUsuarioRepository::save);
    }

    /**
     * Get one perfilAcessoUsuario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerfilAcessoUsuario> findOne(Long id) {
        log.debug("Request to get PerfilAcessoUsuario : {}", id);
        return perfilAcessoUsuarioRepository.findById(id);
    }

    /**
     * Delete the perfilAcessoUsuario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerfilAcessoUsuario : {}", id);
        perfilAcessoUsuarioRepository.deleteById(id);
    }
}
