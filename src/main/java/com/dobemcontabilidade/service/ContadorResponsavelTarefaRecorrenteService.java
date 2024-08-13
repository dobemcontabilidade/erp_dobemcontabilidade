package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente;
import com.dobemcontabilidade.repository.ContadorResponsavelTarefaRecorrenteRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ContadorResponsavelTarefaRecorrente}.
 */
@Service
@Transactional
public class ContadorResponsavelTarefaRecorrenteService {

    private static final Logger log = LoggerFactory.getLogger(ContadorResponsavelTarefaRecorrenteService.class);

    private final ContadorResponsavelTarefaRecorrenteRepository contadorResponsavelTarefaRecorrenteRepository;

    public ContadorResponsavelTarefaRecorrenteService(
        ContadorResponsavelTarefaRecorrenteRepository contadorResponsavelTarefaRecorrenteRepository
    ) {
        this.contadorResponsavelTarefaRecorrenteRepository = contadorResponsavelTarefaRecorrenteRepository;
    }

    /**
     * Save a contadorResponsavelTarefaRecorrente.
     *
     * @param contadorResponsavelTarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public ContadorResponsavelTarefaRecorrente save(ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente) {
        log.debug("Request to save ContadorResponsavelTarefaRecorrente : {}", contadorResponsavelTarefaRecorrente);
        return contadorResponsavelTarefaRecorrenteRepository.save(contadorResponsavelTarefaRecorrente);
    }

    /**
     * Update a contadorResponsavelTarefaRecorrente.
     *
     * @param contadorResponsavelTarefaRecorrente the entity to save.
     * @return the persisted entity.
     */
    public ContadorResponsavelTarefaRecorrente update(ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente) {
        log.debug("Request to update ContadorResponsavelTarefaRecorrente : {}", contadorResponsavelTarefaRecorrente);
        return contadorResponsavelTarefaRecorrenteRepository.save(contadorResponsavelTarefaRecorrente);
    }

    /**
     * Partially update a contadorResponsavelTarefaRecorrente.
     *
     * @param contadorResponsavelTarefaRecorrente the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContadorResponsavelTarefaRecorrente> partialUpdate(
        ContadorResponsavelTarefaRecorrente contadorResponsavelTarefaRecorrente
    ) {
        log.debug("Request to partially update ContadorResponsavelTarefaRecorrente : {}", contadorResponsavelTarefaRecorrente);

        return contadorResponsavelTarefaRecorrenteRepository
            .findById(contadorResponsavelTarefaRecorrente.getId())
            .map(existingContadorResponsavelTarefaRecorrente -> {
                if (contadorResponsavelTarefaRecorrente.getDataAtribuicao() != null) {
                    existingContadorResponsavelTarefaRecorrente.setDataAtribuicao(contadorResponsavelTarefaRecorrente.getDataAtribuicao());
                }
                if (contadorResponsavelTarefaRecorrente.getDataRevogacao() != null) {
                    existingContadorResponsavelTarefaRecorrente.setDataRevogacao(contadorResponsavelTarefaRecorrente.getDataRevogacao());
                }
                if (contadorResponsavelTarefaRecorrente.getConcluida() != null) {
                    existingContadorResponsavelTarefaRecorrente.setConcluida(contadorResponsavelTarefaRecorrente.getConcluida());
                }

                return existingContadorResponsavelTarefaRecorrente;
            })
            .map(contadorResponsavelTarefaRecorrenteRepository::save);
    }

    /**
     * Get all the contadorResponsavelTarefaRecorrentes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContadorResponsavelTarefaRecorrente> findAll() {
        log.debug("Request to get all ContadorResponsavelTarefaRecorrentes");
        return contadorResponsavelTarefaRecorrenteRepository.findAll();
    }

    /**
     * Get all the contadorResponsavelTarefaRecorrentes with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ContadorResponsavelTarefaRecorrente> findAllWithEagerRelationships(Pageable pageable) {
        return contadorResponsavelTarefaRecorrenteRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one contadorResponsavelTarefaRecorrente by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContadorResponsavelTarefaRecorrente> findOne(Long id) {
        log.debug("Request to get ContadorResponsavelTarefaRecorrente : {}", id);
        return contadorResponsavelTarefaRecorrenteRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the contadorResponsavelTarefaRecorrente by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContadorResponsavelTarefaRecorrente : {}", id);
        contadorResponsavelTarefaRecorrenteRepository.deleteById(id);
    }
}
