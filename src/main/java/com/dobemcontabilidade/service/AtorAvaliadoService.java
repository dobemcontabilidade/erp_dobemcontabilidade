package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AtorAvaliado;
import com.dobemcontabilidade.repository.AtorAvaliadoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AtorAvaliado}.
 */
@Service
@Transactional
public class AtorAvaliadoService {

    private static final Logger log = LoggerFactory.getLogger(AtorAvaliadoService.class);

    private final AtorAvaliadoRepository atorAvaliadoRepository;

    public AtorAvaliadoService(AtorAvaliadoRepository atorAvaliadoRepository) {
        this.atorAvaliadoRepository = atorAvaliadoRepository;
    }

    /**
     * Save a atorAvaliado.
     *
     * @param atorAvaliado the entity to save.
     * @return the persisted entity.
     */
    public AtorAvaliado save(AtorAvaliado atorAvaliado) {
        log.debug("Request to save AtorAvaliado : {}", atorAvaliado);
        return atorAvaliadoRepository.save(atorAvaliado);
    }

    /**
     * Update a atorAvaliado.
     *
     * @param atorAvaliado the entity to save.
     * @return the persisted entity.
     */
    public AtorAvaliado update(AtorAvaliado atorAvaliado) {
        log.debug("Request to update AtorAvaliado : {}", atorAvaliado);
        return atorAvaliadoRepository.save(atorAvaliado);
    }

    /**
     * Partially update a atorAvaliado.
     *
     * @param atorAvaliado the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AtorAvaliado> partialUpdate(AtorAvaliado atorAvaliado) {
        log.debug("Request to partially update AtorAvaliado : {}", atorAvaliado);

        return atorAvaliadoRepository
            .findById(atorAvaliado.getId())
            .map(existingAtorAvaliado -> {
                if (atorAvaliado.getNome() != null) {
                    existingAtorAvaliado.setNome(atorAvaliado.getNome());
                }
                if (atorAvaliado.getDescricao() != null) {
                    existingAtorAvaliado.setDescricao(atorAvaliado.getDescricao());
                }
                if (atorAvaliado.getAtivo() != null) {
                    existingAtorAvaliado.setAtivo(atorAvaliado.getAtivo());
                }

                return existingAtorAvaliado;
            })
            .map(atorAvaliadoRepository::save);
    }

    /**
     * Get all the atorAvaliados.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AtorAvaliado> findAll() {
        log.debug("Request to get all AtorAvaliados");
        return atorAvaliadoRepository.findAll();
    }

    /**
     * Get one atorAvaliado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AtorAvaliado> findOne(Long id) {
        log.debug("Request to get AtorAvaliado : {}", id);
        return atorAvaliadoRepository.findById(id);
    }

    /**
     * Delete the atorAvaliado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AtorAvaliado : {}", id);
        atorAvaliadoRepository.deleteById(id);
    }
}
