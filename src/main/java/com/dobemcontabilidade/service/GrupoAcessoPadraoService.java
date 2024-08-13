package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.GrupoAcessoPadrao;
import com.dobemcontabilidade.repository.GrupoAcessoPadraoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.GrupoAcessoPadrao}.
 */
@Service
@Transactional
public class GrupoAcessoPadraoService {

    private static final Logger log = LoggerFactory.getLogger(GrupoAcessoPadraoService.class);

    private final GrupoAcessoPadraoRepository grupoAcessoPadraoRepository;

    public GrupoAcessoPadraoService(GrupoAcessoPadraoRepository grupoAcessoPadraoRepository) {
        this.grupoAcessoPadraoRepository = grupoAcessoPadraoRepository;
    }

    /**
     * Save a grupoAcessoPadrao.
     *
     * @param grupoAcessoPadrao the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoPadrao save(GrupoAcessoPadrao grupoAcessoPadrao) {
        log.debug("Request to save GrupoAcessoPadrao : {}", grupoAcessoPadrao);
        return grupoAcessoPadraoRepository.save(grupoAcessoPadrao);
    }

    /**
     * Update a grupoAcessoPadrao.
     *
     * @param grupoAcessoPadrao the entity to save.
     * @return the persisted entity.
     */
    public GrupoAcessoPadrao update(GrupoAcessoPadrao grupoAcessoPadrao) {
        log.debug("Request to update GrupoAcessoPadrao : {}", grupoAcessoPadrao);
        return grupoAcessoPadraoRepository.save(grupoAcessoPadrao);
    }

    /**
     * Partially update a grupoAcessoPadrao.
     *
     * @param grupoAcessoPadrao the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GrupoAcessoPadrao> partialUpdate(GrupoAcessoPadrao grupoAcessoPadrao) {
        log.debug("Request to partially update GrupoAcessoPadrao : {}", grupoAcessoPadrao);

        return grupoAcessoPadraoRepository
            .findById(grupoAcessoPadrao.getId())
            .map(existingGrupoAcessoPadrao -> {
                if (grupoAcessoPadrao.getNome() != null) {
                    existingGrupoAcessoPadrao.setNome(grupoAcessoPadrao.getNome());
                }

                return existingGrupoAcessoPadrao;
            })
            .map(grupoAcessoPadraoRepository::save);
    }

    /**
     * Get all the grupoAcessoPadraos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GrupoAcessoPadrao> findAll() {
        log.debug("Request to get all GrupoAcessoPadraos");
        return grupoAcessoPadraoRepository.findAll();
    }

    /**
     * Get one grupoAcessoPadrao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GrupoAcessoPadrao> findOne(Long id) {
        log.debug("Request to get GrupoAcessoPadrao : {}", id);
        return grupoAcessoPadraoRepository.findById(id);
    }

    /**
     * Delete the grupoAcessoPadrao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GrupoAcessoPadrao : {}", id);
        grupoAcessoPadraoRepository.deleteById(id);
    }
}
