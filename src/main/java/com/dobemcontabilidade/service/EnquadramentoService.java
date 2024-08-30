package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.repository.EnquadramentoRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.Enquadramento}.
 */
@Service
@Transactional
public class EnquadramentoService {

    private static final Logger log = LoggerFactory.getLogger(EnquadramentoService.class);

    private final EnquadramentoRepository enquadramentoRepository;

    public EnquadramentoService(EnquadramentoRepository enquadramentoRepository) {
        this.enquadramentoRepository = enquadramentoRepository;
    }

    /**
     * Save a enquadramento.
     *
     * @param enquadramento the entity to save.
     * @return the persisted entity.
     */
    public Enquadramento save(Enquadramento enquadramento) {
        log.debug("Request to save Enquadramento : {}", enquadramento);
        return enquadramentoRepository.save(enquadramento);
    }

    /**
     * Update a enquadramento.
     *
     * @param enquadramento the entity to save.
     * @return the persisted entity.
     */
    public Enquadramento update(Enquadramento enquadramento) {
        log.debug("Request to update Enquadramento : {}", enquadramento);
        return enquadramentoRepository.save(enquadramento);
    }

    /**
     * Partially update a enquadramento.
     *
     * @param enquadramento the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Enquadramento> partialUpdate(Enquadramento enquadramento) {
        log.debug("Request to partially update Enquadramento : {}", enquadramento);

        return enquadramentoRepository
            .findById(enquadramento.getId())
            .map(existingEnquadramento -> {
                if (enquadramento.getNome() != null) {
                    existingEnquadramento.setNome(enquadramento.getNome());
                }
                if (enquadramento.getSigla() != null) {
                    existingEnquadramento.setSigla(enquadramento.getSigla());
                }
                if (enquadramento.getLimiteInicial() != null) {
                    existingEnquadramento.setLimiteInicial(enquadramento.getLimiteInicial());
                }
                if (enquadramento.getLimiteFinal() != null) {
                    existingEnquadramento.setLimiteFinal(enquadramento.getLimiteFinal());
                }
                if (enquadramento.getDescricao() != null) {
                    existingEnquadramento.setDescricao(enquadramento.getDescricao());
                }

                return existingEnquadramento;
            })
            .map(enquadramentoRepository::save);
    }

    /**
     * Get one enquadramento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Enquadramento> findOne(Long id) {
        log.debug("Request to get Enquadramento : {}", id);
        return enquadramentoRepository.findById(id);
    }

    /**
     * Delete the enquadramento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Enquadramento : {}", id);
        enquadramentoRepository.deleteById(id);
    }
}
