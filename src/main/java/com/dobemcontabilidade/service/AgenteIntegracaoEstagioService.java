package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.AgenteIntegracaoEstagio;
import com.dobemcontabilidade.repository.AgenteIntegracaoEstagioRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.AgenteIntegracaoEstagio}.
 */
@Service
@Transactional
public class AgenteIntegracaoEstagioService {

    private static final Logger log = LoggerFactory.getLogger(AgenteIntegracaoEstagioService.class);

    private final AgenteIntegracaoEstagioRepository agenteIntegracaoEstagioRepository;

    public AgenteIntegracaoEstagioService(AgenteIntegracaoEstagioRepository agenteIntegracaoEstagioRepository) {
        this.agenteIntegracaoEstagioRepository = agenteIntegracaoEstagioRepository;
    }

    /**
     * Save a agenteIntegracaoEstagio.
     *
     * @param agenteIntegracaoEstagio the entity to save.
     * @return the persisted entity.
     */
    public AgenteIntegracaoEstagio save(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        log.debug("Request to save AgenteIntegracaoEstagio : {}", agenteIntegracaoEstagio);
        return agenteIntegracaoEstagioRepository.save(agenteIntegracaoEstagio);
    }

    /**
     * Update a agenteIntegracaoEstagio.
     *
     * @param agenteIntegracaoEstagio the entity to save.
     * @return the persisted entity.
     */
    public AgenteIntegracaoEstagio update(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        log.debug("Request to update AgenteIntegracaoEstagio : {}", agenteIntegracaoEstagio);
        return agenteIntegracaoEstagioRepository.save(agenteIntegracaoEstagio);
    }

    /**
     * Partially update a agenteIntegracaoEstagio.
     *
     * @param agenteIntegracaoEstagio the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AgenteIntegracaoEstagio> partialUpdate(AgenteIntegracaoEstagio agenteIntegracaoEstagio) {
        log.debug("Request to partially update AgenteIntegracaoEstagio : {}", agenteIntegracaoEstagio);

        return agenteIntegracaoEstagioRepository
            .findById(agenteIntegracaoEstagio.getId())
            .map(existingAgenteIntegracaoEstagio -> {
                if (agenteIntegracaoEstagio.getCnpj() != null) {
                    existingAgenteIntegracaoEstagio.setCnpj(agenteIntegracaoEstagio.getCnpj());
                }
                if (agenteIntegracaoEstagio.getRazaoSocial() != null) {
                    existingAgenteIntegracaoEstagio.setRazaoSocial(agenteIntegracaoEstagio.getRazaoSocial());
                }
                if (agenteIntegracaoEstagio.getCoordenador() != null) {
                    existingAgenteIntegracaoEstagio.setCoordenador(agenteIntegracaoEstagio.getCoordenador());
                }
                if (agenteIntegracaoEstagio.getCpfCoordenadorEstagio() != null) {
                    existingAgenteIntegracaoEstagio.setCpfCoordenadorEstagio(agenteIntegracaoEstagio.getCpfCoordenadorEstagio());
                }
                if (agenteIntegracaoEstagio.getLogradouro() != null) {
                    existingAgenteIntegracaoEstagio.setLogradouro(agenteIntegracaoEstagio.getLogradouro());
                }
                if (agenteIntegracaoEstagio.getNumero() != null) {
                    existingAgenteIntegracaoEstagio.setNumero(agenteIntegracaoEstagio.getNumero());
                }
                if (agenteIntegracaoEstagio.getComplemento() != null) {
                    existingAgenteIntegracaoEstagio.setComplemento(agenteIntegracaoEstagio.getComplemento());
                }
                if (agenteIntegracaoEstagio.getBairro() != null) {
                    existingAgenteIntegracaoEstagio.setBairro(agenteIntegracaoEstagio.getBairro());
                }
                if (agenteIntegracaoEstagio.getCep() != null) {
                    existingAgenteIntegracaoEstagio.setCep(agenteIntegracaoEstagio.getCep());
                }
                if (agenteIntegracaoEstagio.getPrincipal() != null) {
                    existingAgenteIntegracaoEstagio.setPrincipal(agenteIntegracaoEstagio.getPrincipal());
                }

                return existingAgenteIntegracaoEstagio;
            })
            .map(agenteIntegracaoEstagioRepository::save);
    }

    /**
     * Get all the agenteIntegracaoEstagios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AgenteIntegracaoEstagio> findAll() {
        log.debug("Request to get all AgenteIntegracaoEstagios");
        return agenteIntegracaoEstagioRepository.findAll();
    }

    /**
     * Get all the agenteIntegracaoEstagios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AgenteIntegracaoEstagio> findAllWithEagerRelationships(Pageable pageable) {
        return agenteIntegracaoEstagioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one agenteIntegracaoEstagio by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AgenteIntegracaoEstagio> findOne(Long id) {
        log.debug("Request to get AgenteIntegracaoEstagio : {}", id);
        return agenteIntegracaoEstagioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the agenteIntegracaoEstagio by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AgenteIntegracaoEstagio : {}", id);
        agenteIntegracaoEstagioRepository.deleteById(id);
    }
}
