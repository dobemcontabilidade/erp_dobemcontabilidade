package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ParcelaImpostoAPagar;
import com.dobemcontabilidade.repository.ParcelaImpostoAPagarRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ParcelaImpostoAPagar}.
 */
@Service
@Transactional
public class ParcelaImpostoAPagarService {

    private static final Logger log = LoggerFactory.getLogger(ParcelaImpostoAPagarService.class);

    private final ParcelaImpostoAPagarRepository parcelaImpostoAPagarRepository;

    public ParcelaImpostoAPagarService(ParcelaImpostoAPagarRepository parcelaImpostoAPagarRepository) {
        this.parcelaImpostoAPagarRepository = parcelaImpostoAPagarRepository;
    }

    /**
     * Save a parcelaImpostoAPagar.
     *
     * @param parcelaImpostoAPagar the entity to save.
     * @return the persisted entity.
     */
    public ParcelaImpostoAPagar save(ParcelaImpostoAPagar parcelaImpostoAPagar) {
        log.debug("Request to save ParcelaImpostoAPagar : {}", parcelaImpostoAPagar);
        return parcelaImpostoAPagarRepository.save(parcelaImpostoAPagar);
    }

    /**
     * Update a parcelaImpostoAPagar.
     *
     * @param parcelaImpostoAPagar the entity to save.
     * @return the persisted entity.
     */
    public ParcelaImpostoAPagar update(ParcelaImpostoAPagar parcelaImpostoAPagar) {
        log.debug("Request to update ParcelaImpostoAPagar : {}", parcelaImpostoAPagar);
        return parcelaImpostoAPagarRepository.save(parcelaImpostoAPagar);
    }

    /**
     * Partially update a parcelaImpostoAPagar.
     *
     * @param parcelaImpostoAPagar the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParcelaImpostoAPagar> partialUpdate(ParcelaImpostoAPagar parcelaImpostoAPagar) {
        log.debug("Request to partially update ParcelaImpostoAPagar : {}", parcelaImpostoAPagar);

        return parcelaImpostoAPagarRepository
            .findById(parcelaImpostoAPagar.getId())
            .map(existingParcelaImpostoAPagar -> {
                if (parcelaImpostoAPagar.getNumeroParcela() != null) {
                    existingParcelaImpostoAPagar.setNumeroParcela(parcelaImpostoAPagar.getNumeroParcela());
                }
                if (parcelaImpostoAPagar.getDataVencimento() != null) {
                    existingParcelaImpostoAPagar.setDataVencimento(parcelaImpostoAPagar.getDataVencimento());
                }
                if (parcelaImpostoAPagar.getDataPagamento() != null) {
                    existingParcelaImpostoAPagar.setDataPagamento(parcelaImpostoAPagar.getDataPagamento());
                }
                if (parcelaImpostoAPagar.getValor() != null) {
                    existingParcelaImpostoAPagar.setValor(parcelaImpostoAPagar.getValor());
                }
                if (parcelaImpostoAPagar.getValorMulta() != null) {
                    existingParcelaImpostoAPagar.setValorMulta(parcelaImpostoAPagar.getValorMulta());
                }
                if (parcelaImpostoAPagar.getUrlArquivoPagamento() != null) {
                    existingParcelaImpostoAPagar.setUrlArquivoPagamento(parcelaImpostoAPagar.getUrlArquivoPagamento());
                }
                if (parcelaImpostoAPagar.getUrlArquivoComprovante() != null) {
                    existingParcelaImpostoAPagar.setUrlArquivoComprovante(parcelaImpostoAPagar.getUrlArquivoComprovante());
                }
                if (parcelaImpostoAPagar.getMesCompetencia() != null) {
                    existingParcelaImpostoAPagar.setMesCompetencia(parcelaImpostoAPagar.getMesCompetencia());
                }

                return existingParcelaImpostoAPagar;
            })
            .map(parcelaImpostoAPagarRepository::save);
    }

    /**
     * Get all the parcelaImpostoAPagars.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ParcelaImpostoAPagar> findAll() {
        log.debug("Request to get all ParcelaImpostoAPagars");
        return parcelaImpostoAPagarRepository.findAll();
    }

    /**
     * Get one parcelaImpostoAPagar by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParcelaImpostoAPagar> findOne(Long id) {
        log.debug("Request to get ParcelaImpostoAPagar : {}", id);
        return parcelaImpostoAPagarRepository.findById(id);
    }

    /**
     * Delete the parcelaImpostoAPagar by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParcelaImpostoAPagar : {}", id);
        parcelaImpostoAPagarRepository.deleteById(id);
    }
}
