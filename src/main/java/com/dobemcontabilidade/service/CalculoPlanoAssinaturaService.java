package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import com.dobemcontabilidade.repository.CalculoPlanoAssinaturaRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.CalculoPlanoAssinatura}.
 */
@Service
@Transactional
public class CalculoPlanoAssinaturaService {

    private static final Logger log = LoggerFactory.getLogger(CalculoPlanoAssinaturaService.class);

    private final CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository;

    public CalculoPlanoAssinaturaService(CalculoPlanoAssinaturaRepository calculoPlanoAssinaturaRepository) {
        this.calculoPlanoAssinaturaRepository = calculoPlanoAssinaturaRepository;
    }

    /**
     * Save a calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinatura the entity to save.
     * @return the persisted entity.
     */
    public CalculoPlanoAssinatura save(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        log.debug("Request to save CalculoPlanoAssinatura : {}", calculoPlanoAssinatura);
        return calculoPlanoAssinaturaRepository.save(calculoPlanoAssinatura);
    }

    /**
     * Update a calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinatura the entity to save.
     * @return the persisted entity.
     */
    public CalculoPlanoAssinatura update(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        log.debug("Request to update CalculoPlanoAssinatura : {}", calculoPlanoAssinatura);
        return calculoPlanoAssinaturaRepository.save(calculoPlanoAssinatura);
    }

    /**
     * Partially update a calculoPlanoAssinatura.
     *
     * @param calculoPlanoAssinatura the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CalculoPlanoAssinatura> partialUpdate(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        log.debug("Request to partially update CalculoPlanoAssinatura : {}", calculoPlanoAssinatura);

        return calculoPlanoAssinaturaRepository
            .findById(calculoPlanoAssinatura.getId())
            .map(existingCalculoPlanoAssinatura -> {
                if (calculoPlanoAssinatura.getCodigoAtendimento() != null) {
                    existingCalculoPlanoAssinatura.setCodigoAtendimento(calculoPlanoAssinatura.getCodigoAtendimento());
                }
                if (calculoPlanoAssinatura.getValorEnquadramento() != null) {
                    existingCalculoPlanoAssinatura.setValorEnquadramento(calculoPlanoAssinatura.getValorEnquadramento());
                }
                if (calculoPlanoAssinatura.getValorTributacao() != null) {
                    existingCalculoPlanoAssinatura.setValorTributacao(calculoPlanoAssinatura.getValorTributacao());
                }
                if (calculoPlanoAssinatura.getValorRamo() != null) {
                    existingCalculoPlanoAssinatura.setValorRamo(calculoPlanoAssinatura.getValorRamo());
                }
                if (calculoPlanoAssinatura.getValorFuncionarios() != null) {
                    existingCalculoPlanoAssinatura.setValorFuncionarios(calculoPlanoAssinatura.getValorFuncionarios());
                }
                if (calculoPlanoAssinatura.getValorSocios() != null) {
                    existingCalculoPlanoAssinatura.setValorSocios(calculoPlanoAssinatura.getValorSocios());
                }
                if (calculoPlanoAssinatura.getValorFaturamento() != null) {
                    existingCalculoPlanoAssinatura.setValorFaturamento(calculoPlanoAssinatura.getValorFaturamento());
                }
                if (calculoPlanoAssinatura.getValorPlanoContabil() != null) {
                    existingCalculoPlanoAssinatura.setValorPlanoContabil(calculoPlanoAssinatura.getValorPlanoContabil());
                }
                if (calculoPlanoAssinatura.getValorPlanoContabilComDesconto() != null) {
                    existingCalculoPlanoAssinatura.setValorPlanoContabilComDesconto(
                        calculoPlanoAssinatura.getValorPlanoContabilComDesconto()
                    );
                }
                if (calculoPlanoAssinatura.getValorPlanoContaAzulComDesconto() != null) {
                    existingCalculoPlanoAssinatura.setValorPlanoContaAzulComDesconto(
                        calculoPlanoAssinatura.getValorPlanoContaAzulComDesconto()
                    );
                }
                if (calculoPlanoAssinatura.getValorMensalidade() != null) {
                    existingCalculoPlanoAssinatura.setValorMensalidade(calculoPlanoAssinatura.getValorMensalidade());
                }
                if (calculoPlanoAssinatura.getValorPeriodo() != null) {
                    existingCalculoPlanoAssinatura.setValorPeriodo(calculoPlanoAssinatura.getValorPeriodo());
                }
                if (calculoPlanoAssinatura.getValorAno() != null) {
                    existingCalculoPlanoAssinatura.setValorAno(calculoPlanoAssinatura.getValorAno());
                }

                return existingCalculoPlanoAssinatura;
            })
            .map(calculoPlanoAssinaturaRepository::save);
    }

    /**
     * Get all the calculoPlanoAssinaturas with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CalculoPlanoAssinatura> findAllWithEagerRelationships(Pageable pageable) {
        return calculoPlanoAssinaturaRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one calculoPlanoAssinatura by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CalculoPlanoAssinatura> findOne(Long id) {
        log.debug("Request to get CalculoPlanoAssinatura : {}", id);
        return calculoPlanoAssinaturaRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the calculoPlanoAssinatura by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CalculoPlanoAssinatura : {}", id);
        calculoPlanoAssinaturaRepository.deleteById(id);
    }
}
