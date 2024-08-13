package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.repository.SubclasseCnaeRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.SubclasseCnae}.
 */
@Service
@Transactional
public class SubclasseCnaeService {

    private static final Logger log = LoggerFactory.getLogger(SubclasseCnaeService.class);

    private final SubclasseCnaeRepository subclasseCnaeRepository;

    public SubclasseCnaeService(SubclasseCnaeRepository subclasseCnaeRepository) {
        this.subclasseCnaeRepository = subclasseCnaeRepository;
    }

    /**
     * Save a subclasseCnae.
     *
     * @param subclasseCnae the entity to save.
     * @return the persisted entity.
     */
    public SubclasseCnae save(SubclasseCnae subclasseCnae) {
        log.debug("Request to save SubclasseCnae : {}", subclasseCnae);
        return subclasseCnaeRepository.save(subclasseCnae);
    }

    /**
     * Update a subclasseCnae.
     *
     * @param subclasseCnae the entity to save.
     * @return the persisted entity.
     */
    public SubclasseCnae update(SubclasseCnae subclasseCnae) {
        log.debug("Request to update SubclasseCnae : {}", subclasseCnae);
        return subclasseCnaeRepository.save(subclasseCnae);
    }

    /**
     * Partially update a subclasseCnae.
     *
     * @param subclasseCnae the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubclasseCnae> partialUpdate(SubclasseCnae subclasseCnae) {
        log.debug("Request to partially update SubclasseCnae : {}", subclasseCnae);

        return subclasseCnaeRepository
            .findById(subclasseCnae.getId())
            .map(existingSubclasseCnae -> {
                if (subclasseCnae.getCodigo() != null) {
                    existingSubclasseCnae.setCodigo(subclasseCnae.getCodigo());
                }
                if (subclasseCnae.getDescricao() != null) {
                    existingSubclasseCnae.setDescricao(subclasseCnae.getDescricao());
                }
                if (subclasseCnae.getAnexo() != null) {
                    existingSubclasseCnae.setAnexo(subclasseCnae.getAnexo());
                }
                if (subclasseCnae.getAtendidoFreemium() != null) {
                    existingSubclasseCnae.setAtendidoFreemium(subclasseCnae.getAtendidoFreemium());
                }
                if (subclasseCnae.getAtendido() != null) {
                    existingSubclasseCnae.setAtendido(subclasseCnae.getAtendido());
                }
                if (subclasseCnae.getOptanteSimples() != null) {
                    existingSubclasseCnae.setOptanteSimples(subclasseCnae.getOptanteSimples());
                }
                if (subclasseCnae.getAceitaMEI() != null) {
                    existingSubclasseCnae.setAceitaMEI(subclasseCnae.getAceitaMEI());
                }
                if (subclasseCnae.getCategoria() != null) {
                    existingSubclasseCnae.setCategoria(subclasseCnae.getCategoria());
                }

                return existingSubclasseCnae;
            })
            .map(subclasseCnaeRepository::save);
    }

    /**
     * Get all the subclasseCnaes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubclasseCnae> findAllWithEagerRelationships(Pageable pageable) {
        return subclasseCnaeRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one subclasseCnae by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubclasseCnae> findOne(Long id) {
        log.debug("Request to get SubclasseCnae : {}", id);
        return subclasseCnaeRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the subclasseCnae by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubclasseCnae : {}", id);
        subclasseCnaeRepository.deleteById(id);
    }
}
