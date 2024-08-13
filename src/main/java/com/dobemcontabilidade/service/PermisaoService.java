package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Permisao;
import com.dobemcontabilidade.repository.PermisaoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Permisao}.
 */
@Service
@Transactional
public class PermisaoService {

    private static final Logger log = LoggerFactory.getLogger(PermisaoService.class);

    private final PermisaoRepository permisaoRepository;

    public PermisaoService(PermisaoRepository permisaoRepository) {
        this.permisaoRepository = permisaoRepository;
    }

    /**
     * Save a permisao.
     *
     * @param permisao the entity to save.
     * @return the persisted entity.
     */
    public Permisao save(Permisao permisao) {
        log.debug("Request to save Permisao : {}", permisao);
        return permisaoRepository.save(permisao);
    }

    /**
     * Update a permisao.
     *
     * @param permisao the entity to save.
     * @return the persisted entity.
     */
    public Permisao update(Permisao permisao) {
        log.debug("Request to update Permisao : {}", permisao);
        return permisaoRepository.save(permisao);
    }

    /**
     * Partially update a permisao.
     *
     * @param permisao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Permisao> partialUpdate(Permisao permisao) {
        log.debug("Request to partially update Permisao : {}", permisao);

        return permisaoRepository
            .findById(permisao.getId())
            .map(existingPermisao -> {
                if (permisao.getNome() != null) {
                    existingPermisao.setNome(permisao.getNome());
                }
                if (permisao.getDescricao() != null) {
                    existingPermisao.setDescricao(permisao.getDescricao());
                }
                if (permisao.getLabel() != null) {
                    existingPermisao.setLabel(permisao.getLabel());
                }

                return existingPermisao;
            })
            .map(permisaoRepository::save);
    }

    /**
     * Get all the permisaos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Permisao> findAll() {
        log.debug("Request to get all Permisaos");
        return permisaoRepository.findAll();
    }

    /**
     * Get one permisao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Permisao> findOne(Long id) {
        log.debug("Request to get Permisao : {}", id);
        return permisaoRepository.findById(id);
    }

    /**
     * Delete the permisao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Permisao : {}", id);
        permisaoRepository.deleteById(id);
    }
}
