package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TermoAdesaoEmpresa;
import com.dobemcontabilidade.repository.TermoAdesaoEmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TermoAdesaoEmpresa}.
 */
@Service
@Transactional
public class TermoAdesaoEmpresaService {

    private static final Logger log = LoggerFactory.getLogger(TermoAdesaoEmpresaService.class);

    private final TermoAdesaoEmpresaRepository termoAdesaoEmpresaRepository;

    public TermoAdesaoEmpresaService(TermoAdesaoEmpresaRepository termoAdesaoEmpresaRepository) {
        this.termoAdesaoEmpresaRepository = termoAdesaoEmpresaRepository;
    }

    /**
     * Save a termoAdesaoEmpresa.
     *
     * @param termoAdesaoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public TermoAdesaoEmpresa save(TermoAdesaoEmpresa termoAdesaoEmpresa) {
        log.debug("Request to save TermoAdesaoEmpresa : {}", termoAdesaoEmpresa);
        return termoAdesaoEmpresaRepository.save(termoAdesaoEmpresa);
    }

    /**
     * Update a termoAdesaoEmpresa.
     *
     * @param termoAdesaoEmpresa the entity to save.
     * @return the persisted entity.
     */
    public TermoAdesaoEmpresa update(TermoAdesaoEmpresa termoAdesaoEmpresa) {
        log.debug("Request to update TermoAdesaoEmpresa : {}", termoAdesaoEmpresa);
        return termoAdesaoEmpresaRepository.save(termoAdesaoEmpresa);
    }

    /**
     * Partially update a termoAdesaoEmpresa.
     *
     * @param termoAdesaoEmpresa the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TermoAdesaoEmpresa> partialUpdate(TermoAdesaoEmpresa termoAdesaoEmpresa) {
        log.debug("Request to partially update TermoAdesaoEmpresa : {}", termoAdesaoEmpresa);

        return termoAdesaoEmpresaRepository
            .findById(termoAdesaoEmpresa.getId())
            .map(existingTermoAdesaoEmpresa -> {
                if (termoAdesaoEmpresa.getDataAdesao() != null) {
                    existingTermoAdesaoEmpresa.setDataAdesao(termoAdesaoEmpresa.getDataAdesao());
                }
                if (termoAdesaoEmpresa.getChecked() != null) {
                    existingTermoAdesaoEmpresa.setChecked(termoAdesaoEmpresa.getChecked());
                }
                if (termoAdesaoEmpresa.getUrlDoc() != null) {
                    existingTermoAdesaoEmpresa.setUrlDoc(termoAdesaoEmpresa.getUrlDoc());
                }

                return existingTermoAdesaoEmpresa;
            })
            .map(termoAdesaoEmpresaRepository::save);
    }

    /**
     * Get all the termoAdesaoEmpresas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TermoAdesaoEmpresa> findAll() {
        log.debug("Request to get all TermoAdesaoEmpresas");
        return termoAdesaoEmpresaRepository.findAll();
    }

    /**
     * Get all the termoAdesaoEmpresas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<TermoAdesaoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return termoAdesaoEmpresaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one termoAdesaoEmpresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TermoAdesaoEmpresa> findOne(Long id) {
        log.debug("Request to get TermoAdesaoEmpresa : {}", id);
        return termoAdesaoEmpresaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the termoAdesaoEmpresa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TermoAdesaoEmpresa : {}", id);
        termoAdesaoEmpresaRepository.deleteById(id);
    }
}
