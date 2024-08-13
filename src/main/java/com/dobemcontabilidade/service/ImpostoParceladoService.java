package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ImpostoParcelado;
import com.dobemcontabilidade.repository.ImpostoParceladoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ImpostoParcelado}.
 */
@Service
@Transactional
public class ImpostoParceladoService {

    private static final Logger log = LoggerFactory.getLogger(ImpostoParceladoService.class);

    private final ImpostoParceladoRepository impostoParceladoRepository;

    public ImpostoParceladoService(ImpostoParceladoRepository impostoParceladoRepository) {
        this.impostoParceladoRepository = impostoParceladoRepository;
    }

    /**
     * Save a impostoParcelado.
     *
     * @param impostoParcelado the entity to save.
     * @return the persisted entity.
     */
    public ImpostoParcelado save(ImpostoParcelado impostoParcelado) {
        log.debug("Request to save ImpostoParcelado : {}", impostoParcelado);
        return impostoParceladoRepository.save(impostoParcelado);
    }

    /**
     * Update a impostoParcelado.
     *
     * @param impostoParcelado the entity to save.
     * @return the persisted entity.
     */
    public ImpostoParcelado update(ImpostoParcelado impostoParcelado) {
        log.debug("Request to update ImpostoParcelado : {}", impostoParcelado);
        return impostoParceladoRepository.save(impostoParcelado);
    }

    /**
     * Partially update a impostoParcelado.
     *
     * @param impostoParcelado the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ImpostoParcelado> partialUpdate(ImpostoParcelado impostoParcelado) {
        log.debug("Request to partially update ImpostoParcelado : {}", impostoParcelado);

        return impostoParceladoRepository
            .findById(impostoParcelado.getId())
            .map(existingImpostoParcelado -> {
                if (impostoParcelado.getDiasAtraso() != null) {
                    existingImpostoParcelado.setDiasAtraso(impostoParcelado.getDiasAtraso());
                }

                return existingImpostoParcelado;
            })
            .map(impostoParceladoRepository::save);
    }

    /**
     * Get all the impostoParcelados.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImpostoParcelado> findAll() {
        log.debug("Request to get all ImpostoParcelados");
        return impostoParceladoRepository.findAll();
    }

    /**
     * Get one impostoParcelado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImpostoParcelado> findOne(Long id) {
        log.debug("Request to get ImpostoParcelado : {}", id);
        return impostoParceladoRepository.findById(id);
    }

    /**
     * Delete the impostoParcelado by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImpostoParcelado : {}", id);
        impostoParceladoRepository.deleteById(id);
    }
}
