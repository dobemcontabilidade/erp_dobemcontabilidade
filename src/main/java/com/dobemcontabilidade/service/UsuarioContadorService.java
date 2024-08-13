package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.repository.UsuarioContadorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioContador}.
 */
@Service
@Transactional
public class UsuarioContadorService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioContadorService.class);

    private final UsuarioContadorRepository usuarioContadorRepository;

    public UsuarioContadorService(UsuarioContadorRepository usuarioContadorRepository) {
        this.usuarioContadorRepository = usuarioContadorRepository;
    }

    /**
     * Save a usuarioContador.
     *
     * @param usuarioContador the entity to save.
     * @return the persisted entity.
     */
    public UsuarioContador save(UsuarioContador usuarioContador) {
        log.debug("Request to save UsuarioContador : {}", usuarioContador);
        return usuarioContadorRepository.save(usuarioContador);
    }

    /**
     * Update a usuarioContador.
     *
     * @param usuarioContador the entity to save.
     * @return the persisted entity.
     */
    public UsuarioContador update(UsuarioContador usuarioContador) {
        log.debug("Request to update UsuarioContador : {}", usuarioContador);
        return usuarioContadorRepository.save(usuarioContador);
    }

    /**
     * Partially update a usuarioContador.
     *
     * @param usuarioContador the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioContador> partialUpdate(UsuarioContador usuarioContador) {
        log.debug("Request to partially update UsuarioContador : {}", usuarioContador);

        return usuarioContadorRepository
            .findById(usuarioContador.getId())
            .map(existingUsuarioContador -> {
                if (usuarioContador.getEmail() != null) {
                    existingUsuarioContador.setEmail(usuarioContador.getEmail());
                }
                if (usuarioContador.getSenha() != null) {
                    existingUsuarioContador.setSenha(usuarioContador.getSenha());
                }
                if (usuarioContador.getToken() != null) {
                    existingUsuarioContador.setToken(usuarioContador.getToken());
                }
                if (usuarioContador.getAtivo() != null) {
                    existingUsuarioContador.setAtivo(usuarioContador.getAtivo());
                }
                if (usuarioContador.getDataHoraAtivacao() != null) {
                    existingUsuarioContador.setDataHoraAtivacao(usuarioContador.getDataHoraAtivacao());
                }
                if (usuarioContador.getDataLimiteAcesso() != null) {
                    existingUsuarioContador.setDataLimiteAcesso(usuarioContador.getDataLimiteAcesso());
                }
                if (usuarioContador.getSituacao() != null) {
                    existingUsuarioContador.setSituacao(usuarioContador.getSituacao());
                }

                return existingUsuarioContador;
            })
            .map(usuarioContadorRepository::save);
    }

    /**
     *  Get all the usuarioContadors where Contador is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioContador> findAllWhereContadorIsNull() {
        log.debug("Request to get all usuarioContadors where Contador is null");
        return StreamSupport.stream(usuarioContadorRepository.findAll().spliterator(), false)
            .filter(usuarioContador -> usuarioContador.getContador() == null)
            .toList();
    }

    /**
     * Get one usuarioContador by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioContador> findOne(Long id) {
        log.debug("Request to get UsuarioContador : {}", id);
        return usuarioContadorRepository.findById(id);
    }

    /**
     * Delete the usuarioContador by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioContador : {}", id);
        usuarioContadorRepository.deleteById(id);
    }
}
