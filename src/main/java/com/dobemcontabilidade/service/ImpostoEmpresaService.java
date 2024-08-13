package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ImpostoEmpresa;
import com.dobemcontabilidade.repository.ImpostoEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ImpostoEmpresa}.
 */
@Service
@Transactional
public class ImpostoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(ImpostoEmpresaService.class);

    private final ImpostoEmpresaRepository impostoEmpresaRepository;

    public ImpostoEmpresaService(ImpostoEmpresaRepository impostoEmpresaRepository) {
        this.impostoEmpresaRepository = impostoEmpresaRepository;
    }

    /**
     * Save a impostoEmpresa.
     *
     * @param impostoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public ImpostoEmpresa save(ImpostoEmpresa impostoEmpresa) {
        log.debug("Request to save ImpostoEmpresa : {}", impostoEmpresa);
        return impostoEmpresaRepository.save(impostoEmpresa);
    }

    /**
     * Update a impostoEmpresa.
     *
     * @param impostoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public ImpostoEmpresa update(ImpostoEmpresa impostoEmpresa) {
        log.debug("Request to update ImpostoEmpresa : {}", impostoEmpresa);
        return impostoEmpresaRepository.save(impostoEmpresa);
    }

    /**
     * Partially update a impostoEmpresa.
     *
     * @param impostoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImpostoEmpresa> partialUpdate(ImpostoEmpresa impostoEmpresa) {
        log.debug("Request to partially update ImpostoEmpresa : {}", impostoEmpresa);

        return impostoEmpresaRepository
            .findById(impostoEmpresa.getId())
            .map(existingImpostoEmpresa -> {
                if (impostoEmpresa.getDiaVencimento() != null) {
                    existingImpostoEmpresa.setDiaVencimento(impostoEmpresa.getDiaVencimento());
                }

                return existingImpostoEmpresa;
            })
            .map(impostoEmpresaRepository::save);
    }

    /**
     * Get all the impostoEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImpostoEmpresa> findAll() {
        log.debug("Request to get all ImpostoEmpresas");
        return impostoEmpresaRepository.findAll();
    }

    /**
     * Get all the impostoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ImpostoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return impostoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one impostoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImpostoEmpresa> findOne(Long id) {
        log.debug("Request to get ImpostoEmpresa : {}", id);
        return impostoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the impostoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImpostoEmpresa : {}", id);
        impostoEmpresaRepository.deleteById(id);
    }
}
