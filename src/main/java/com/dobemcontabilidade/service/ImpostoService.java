package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Imposto;
import com.dobemcontabilidade.repository.ImpostoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Imposto}.
 */
@Service
@Transactional
public class ImpostoService {

    private static final Logger log = LoggerFactory.getLogger(ImpostoService.class);

    private final ImpostoRepository impostoRepository;

    public ImpostoService(ImpostoRepository impostoRepository) {
        this.impostoRepository = impostoRepository;
    }

    /**
     * Save a imposto.
     *
     * @param imposto the entity to save.
     * @return the persisted entity.
     */
    public Imposto save(Imposto imposto) {
        log.debug("Request to save Imposto : {}", imposto);
        return impostoRepository.save(imposto);
    }

    /**
     * Update a imposto.
     *
     * @param imposto the entity to save.
     * @return the persisted entity.
     */
    public Imposto update(Imposto imposto) {
        log.debug("Request to update Imposto : {}", imposto);
        return impostoRepository.save(imposto);
    }

    /**
     * Partially update a imposto.
     *
     * @param imposto the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Imposto> partialUpdate(Imposto imposto) {
        log.debug("Request to partially update Imposto : {}", imposto);

        return impostoRepository
            .findById(imposto.getId())
            .map(existingImposto -> {
                if (imposto.getNome() != null) {
                    existingImposto.setNome(imposto.getNome());
                }
                if (imposto.getSigla() != null) {
                    existingImposto.setSigla(imposto.getSigla());
                }
                if (imposto.getDescricao() != null) {
                    existingImposto.setDescricao(imposto.getDescricao());
                }

                return existingImposto;
            })
            .map(impostoRepository::save);
    }

    /**
     * Get all the impostos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Imposto> findAll() {
        log.debug("Request to get all Impostos");
        return impostoRepository.findAll();
    }

    /**
     * Get all the impostos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Imposto> findAllWithEagerRelationships(Pageable pageable) {
        return impostoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one imposto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Imposto> findOne(Long id) {
        log.debug("Request to get Imposto : {}", id);
        return impostoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the imposto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Imposto : {}", id);
        impostoRepository.deleteById(id);
    }
}
