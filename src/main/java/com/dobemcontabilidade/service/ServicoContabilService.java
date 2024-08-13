package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ServicoContabil;
import com.dobemcontabilidade.repository.ServicoContabilRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ServicoContabil}.
 */
@Service
@Transactional
public class ServicoContabilService {

    private static final Logger log = LoggerFactory.getLogger(ServicoContabilService.class);

    private final ServicoContabilRepository servicoContabilRepository;

    public ServicoContabilService(ServicoContabilRepository servicoContabilRepository) {
        this.servicoContabilRepository = servicoContabilRepository;
    }

    /**
     * Save a servicoContabil.
     *
     * @param servicoContabil the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabil save(ServicoContabil servicoContabil) {
        log.debug("Request to save ServicoContabil : {}", servicoContabil);
        return servicoContabilRepository.save(servicoContabil);
    }

    /**
     * Update a servicoContabil.
     *
     * @param servicoContabil the entity to save.
     * @return the persisted entity.
     */
    public ServicoContabil update(ServicoContabil servicoContabil) {
        log.debug("Request to update ServicoContabil : {}", servicoContabil);
        return servicoContabilRepository.save(servicoContabil);
    }

    /**
     * Partially update a servicoContabil.
     *
     * @param servicoContabil the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ServicoContabil> partialUpdate(ServicoContabil servicoContabil) {
        log.debug("Request to partially update ServicoContabil : {}", servicoContabil);

        return servicoContabilRepository
            .findById(servicoContabil.getId())
            .map(existingServicoContabil -> {
                if (servicoContabil.getNome() != null) {
                    existingServicoContabil.setNome(servicoContabil.getNome());
                }
                if (servicoContabil.getValor() != null) {
                    existingServicoContabil.setValor(servicoContabil.getValor());
                }
                if (servicoContabil.getDescricao() != null) {
                    existingServicoContabil.setDescricao(servicoContabil.getDescricao());
                }
                if (servicoContabil.getDiasExecucao() != null) {
                    existingServicoContabil.setDiasExecucao(servicoContabil.getDiasExecucao());
                }
                if (servicoContabil.getGeraMulta() != null) {
                    existingServicoContabil.setGeraMulta(servicoContabil.getGeraMulta());
                }
                if (servicoContabil.getPeriodoExecucao() != null) {
                    existingServicoContabil.setPeriodoExecucao(servicoContabil.getPeriodoExecucao());
                }
                if (servicoContabil.getDiaLegal() != null) {
                    existingServicoContabil.setDiaLegal(servicoContabil.getDiaLegal());
                }
                if (servicoContabil.getMesLegal() != null) {
                    existingServicoContabil.setMesLegal(servicoContabil.getMesLegal());
                }
                if (servicoContabil.getValorRefMulta() != null) {
                    existingServicoContabil.setValorRefMulta(servicoContabil.getValorRefMulta());
                }

                return existingServicoContabil;
            })
            .map(servicoContabilRepository::save);
    }

    /**
     * Get all the servicoContabils.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ServicoContabil> findAll() {
        log.debug("Request to get all ServicoContabils");
        return servicoContabilRepository.findAll();
    }

    /**
     * Get all the servicoContabils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ServicoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return servicoContabilRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one servicoContabil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ServicoContabil> findOne(Long id) {
        log.debug("Request to get ServicoContabil : {}", id);
        return servicoContabilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the servicoContabil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ServicoContabil : {}", id);
        servicoContabilRepository.deleteById(id);
    }
}
