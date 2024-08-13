package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioGestao;
import com.dobemcontabilidade.repository.UsuarioGestaoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioGestao}.
 */
@Service
@Transactional
public class UsuarioGestaoService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioGestaoService.class);

    private final UsuarioGestaoRepository usuarioGestaoRepository;

    public UsuarioGestaoService(UsuarioGestaoRepository usuarioGestaoRepository) {
        this.usuarioGestaoRepository = usuarioGestaoRepository;
    }

    /**
     * Save a usuarioGestao.
     *
     * @param usuarioGestao the entity to save.
     * @return the persisted entity.
     */
    public UsuarioGestao save(UsuarioGestao usuarioGestao) {
        log.debug("Request to save UsuarioGestao : {}", usuarioGestao);
        return usuarioGestaoRepository.save(usuarioGestao);
    }

    /**
     * Update a usuarioGestao.
     *
     * @param usuarioGestao the entity to save.
     * @return the persisted entity.
     */
    public UsuarioGestao update(UsuarioGestao usuarioGestao) {
        log.debug("Request to update UsuarioGestao : {}", usuarioGestao);
        return usuarioGestaoRepository.save(usuarioGestao);
    }

    /**
     * Partially update a usuarioGestao.
     *
     * @param usuarioGestao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioGestao> partialUpdate(UsuarioGestao usuarioGestao) {
        log.debug("Request to partially update UsuarioGestao : {}", usuarioGestao);

        return usuarioGestaoRepository
            .findById(usuarioGestao.getId())
            .map(existingUsuarioGestao -> {
                if (usuarioGestao.getEmail() != null) {
                    existingUsuarioGestao.setEmail(usuarioGestao.getEmail());
                }
                if (usuarioGestao.getSenha() != null) {
                    existingUsuarioGestao.setSenha(usuarioGestao.getSenha());
                }
                if (usuarioGestao.getToken() != null) {
                    existingUsuarioGestao.setToken(usuarioGestao.getToken());
                }
                if (usuarioGestao.getAtivo() != null) {
                    existingUsuarioGestao.setAtivo(usuarioGestao.getAtivo());
                }
                if (usuarioGestao.getDataHoraAtivacao() != null) {
                    existingUsuarioGestao.setDataHoraAtivacao(usuarioGestao.getDataHoraAtivacao());
                }
                if (usuarioGestao.getDataLimiteAcesso() != null) {
                    existingUsuarioGestao.setDataLimiteAcesso(usuarioGestao.getDataLimiteAcesso());
                }
                if (usuarioGestao.getSituacaoUsuarioGestao() != null) {
                    existingUsuarioGestao.setSituacaoUsuarioGestao(usuarioGestao.getSituacaoUsuarioGestao());
                }

                return existingUsuarioGestao;
            })
            .map(usuarioGestaoRepository::save);
    }

    /**
     * Get all the usuarioGestaos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsuarioGestao> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioGestaoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one usuarioGestao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioGestao> findOne(Long id) {
        log.debug("Request to get UsuarioGestao : {}", id);
        return usuarioGestaoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the usuarioGestao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioGestao : {}", id);
        usuarioGestaoRepository.deleteById(id);
    }
}
