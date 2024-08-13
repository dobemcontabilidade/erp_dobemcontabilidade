package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.repository.UsuarioEmpresaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.UsuarioEmpresa}.
 */
@Service
@Transactional
public class UsuarioEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioEmpresaService.class);

    private final UsuarioEmpresaRepository usuarioEmpresaRepository;

    public UsuarioEmpresaService(UsuarioEmpresaRepository usuarioEmpresaRepository) {
        this.usuarioEmpresaRepository = usuarioEmpresaRepository;
    }

    /**
     * Save a usuarioEmpresa.
     *
     * @param usuarioEmpresa the entity to save.
     * @return the persisted entity.
     */
    public UsuarioEmpresa save(UsuarioEmpresa usuarioEmpresa) {
        log.debug("Request to save UsuarioEmpresa : {}", usuarioEmpresa);
        return usuarioEmpresaRepository.save(usuarioEmpresa);
    }

    /**
     * Update a usuarioEmpresa.
     *
     * @param usuarioEmpresa the entity to save.
     * @return the persisted entity.
     */
    public UsuarioEmpresa update(UsuarioEmpresa usuarioEmpresa) {
        log.debug("Request to update UsuarioEmpresa : {}", usuarioEmpresa);
        return usuarioEmpresaRepository.save(usuarioEmpresa);
    }

    /**
     * Partially update a usuarioEmpresa.
     *
     * @param usuarioEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsuarioEmpresa> partialUpdate(UsuarioEmpresa usuarioEmpresa) {
        log.debug("Request to partially update UsuarioEmpresa : {}", usuarioEmpresa);

        return usuarioEmpresaRepository
            .findById(usuarioEmpresa.getId())
            .map(existingUsuarioEmpresa -> {
                if (usuarioEmpresa.getEmail() != null) {
                    existingUsuarioEmpresa.setEmail(usuarioEmpresa.getEmail());
                }
                if (usuarioEmpresa.getSenha() != null) {
                    existingUsuarioEmpresa.setSenha(usuarioEmpresa.getSenha());
                }
                if (usuarioEmpresa.getToken() != null) {
                    existingUsuarioEmpresa.setToken(usuarioEmpresa.getToken());
                }
                if (usuarioEmpresa.getAtivo() != null) {
                    existingUsuarioEmpresa.setAtivo(usuarioEmpresa.getAtivo());
                }
                if (usuarioEmpresa.getDataHoraAtivacao() != null) {
                    existingUsuarioEmpresa.setDataHoraAtivacao(usuarioEmpresa.getDataHoraAtivacao());
                }
                if (usuarioEmpresa.getDataLimiteAcesso() != null) {
                    existingUsuarioEmpresa.setDataLimiteAcesso(usuarioEmpresa.getDataLimiteAcesso());
                }
                if (usuarioEmpresa.getSituacaoUsuarioEmpresa() != null) {
                    existingUsuarioEmpresa.setSituacaoUsuarioEmpresa(usuarioEmpresa.getSituacaoUsuarioEmpresa());
                }
                if (usuarioEmpresa.getTipoUsuarioEmpresaEnum() != null) {
                    existingUsuarioEmpresa.setTipoUsuarioEmpresaEnum(usuarioEmpresa.getTipoUsuarioEmpresaEnum());
                }

                return existingUsuarioEmpresa;
            })
            .map(usuarioEmpresaRepository::save);
    }

    /**
     * Get all the usuarioEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UsuarioEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return usuarioEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     *  Get all the usuarioEmpresas where Funcionario is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioEmpresa> findAllWhereFuncionarioIsNull() {
        log.debug("Request to get all usuarioEmpresas where Funcionario is null");
        return StreamSupport.stream(usuarioEmpresaRepository.findAll().spliterator(), false)
            .filter(usuarioEmpresa -> usuarioEmpresa.getFuncionario() == null)
            .toList();
    }

    /**
     *  Get all the usuarioEmpresas where Socio is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioEmpresa> findAllWhereSocioIsNull() {
        log.debug("Request to get all usuarioEmpresas where Socio is null");
        return StreamSupport.stream(usuarioEmpresaRepository.findAll().spliterator(), false)
            .filter(usuarioEmpresa -> usuarioEmpresa.getSocio() == null)
            .toList();
    }

    /**
     * Get one usuarioEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsuarioEmpresa> findOne(Long id) {
        log.debug("Request to get UsuarioEmpresa : {}", id);
        return usuarioEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the usuarioEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UsuarioEmpresa : {}", id);
        usuarioEmpresaRepository.deleteById(id);
    }
}
