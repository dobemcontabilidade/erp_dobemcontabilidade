package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo;
import com.dobemcontabilidade.repository.TarefaRecorrenteEmpresaModeloRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModelo}.
 */
@Service
@Transactional
public class TarefaRecorrenteEmpresaModeloService {

    private static final Logger log = LoggerFactory.getLogger(TarefaRecorrenteEmpresaModeloService.class);

    private final TarefaRecorrenteEmpresaModeloRepository tarefaRecorrenteEmpresaModeloRepository;

    public TarefaRecorrenteEmpresaModeloService(TarefaRecorrenteEmpresaModeloRepository tarefaRecorrenteEmpresaModeloRepository) {
        this.tarefaRecorrenteEmpresaModeloRepository = tarefaRecorrenteEmpresaModeloRepository;
    }

    /**
     * Save a tarefaRecorrenteEmpresaModelo.
     *
     * @param tarefaRecorrenteEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public TarefaRecorrenteEmpresaModelo save(TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo) {
        log.debug("Request to save TarefaRecorrenteEmpresaModelo : {}", tarefaRecorrenteEmpresaModelo);
        return tarefaRecorrenteEmpresaModeloRepository.save(tarefaRecorrenteEmpresaModelo);
    }

    /**
     * Update a tarefaRecorrenteEmpresaModelo.
     *
     * @param tarefaRecorrenteEmpresaModelo the entity to save.
     * @return the persisted entity.
     */
    public TarefaRecorrenteEmpresaModelo update(TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo) {
        log.debug("Request to update TarefaRecorrenteEmpresaModelo : {}", tarefaRecorrenteEmpresaModelo);
        return tarefaRecorrenteEmpresaModeloRepository.save(tarefaRecorrenteEmpresaModelo);
    }

    /**
     * Partially update a tarefaRecorrenteEmpresaModelo.
     *
     * @param tarefaRecorrenteEmpresaModelo the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TarefaRecorrenteEmpresaModelo> partialUpdate(TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo) {
        log.debug("Request to partially update TarefaRecorrenteEmpresaModelo : {}", tarefaRecorrenteEmpresaModelo);

        return tarefaRecorrenteEmpresaModeloRepository
            .findById(tarefaRecorrenteEmpresaModelo.getId())
            .map(existingTarefaRecorrenteEmpresaModelo -> {
                if (tarefaRecorrenteEmpresaModelo.getDiaAdmin() != null) {
                    existingTarefaRecorrenteEmpresaModelo.setDiaAdmin(tarefaRecorrenteEmpresaModelo.getDiaAdmin());
                }
                if (tarefaRecorrenteEmpresaModelo.getMesLegal() != null) {
                    existingTarefaRecorrenteEmpresaModelo.setMesLegal(tarefaRecorrenteEmpresaModelo.getMesLegal());
                }
                if (tarefaRecorrenteEmpresaModelo.getRecorrencia() != null) {
                    existingTarefaRecorrenteEmpresaModelo.setRecorrencia(tarefaRecorrenteEmpresaModelo.getRecorrencia());
                }

                return existingTarefaRecorrenteEmpresaModelo;
            })
            .map(tarefaRecorrenteEmpresaModeloRepository::save);
    }

    /**
     * Get all the tarefaRecorrenteEmpresaModelos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TarefaRecorrenteEmpresaModelo> findAll() {
        log.debug("Request to get all TarefaRecorrenteEmpresaModelos");
        return tarefaRecorrenteEmpresaModeloRepository.findAll();
    }

    /**
     * Get one tarefaRecorrenteEmpresaModelo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TarefaRecorrenteEmpresaModelo> findOne(Long id) {
        log.debug("Request to get TarefaRecorrenteEmpresaModelo : {}", id);
        return tarefaRecorrenteEmpresaModeloRepository.findById(id);
    }

    /**
     * Delete the tarefaRecorrenteEmpresaModelo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TarefaRecorrenteEmpresaModelo : {}", id);
        tarefaRecorrenteEmpresaModeloRepository.deleteById(id);
    }
}
