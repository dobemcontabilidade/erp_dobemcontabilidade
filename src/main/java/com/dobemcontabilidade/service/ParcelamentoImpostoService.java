package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ParcelamentoImposto;
import com.dobemcontabilidade.repository.ParcelamentoImpostoRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ParcelamentoImposto}.
 */
@Service
@Transactional
public class ParcelamentoImpostoService {

    private static final Logger log = LoggerFactory.getLogger(ParcelamentoImpostoService.class);

    private final ParcelamentoImpostoRepository parcelamentoImpostoRepository;

    public ParcelamentoImpostoService(ParcelamentoImpostoRepository parcelamentoImpostoRepository) {
        this.parcelamentoImpostoRepository = parcelamentoImpostoRepository;
    }

    /**
     * Save a parcelamentoImposto.
     *
     * @param parcelamentoImposto the entity to save.
     * @return the persisted entity.
     */
    public ParcelamentoImposto save(ParcelamentoImposto parcelamentoImposto) {
        log.debug("Request to save ParcelamentoImposto : {}", parcelamentoImposto);
        return parcelamentoImpostoRepository.save(parcelamentoImposto);
    }

    /**
     * Update a parcelamentoImposto.
     *
     * @param parcelamentoImposto the entity to save.
     * @return the persisted entity.
     */
    public ParcelamentoImposto update(ParcelamentoImposto parcelamentoImposto) {
        log.debug("Request to update ParcelamentoImposto : {}", parcelamentoImposto);
        return parcelamentoImpostoRepository.save(parcelamentoImposto);
    }

    /**
     * Partially update a parcelamentoImposto.
     *
     * @param parcelamentoImposto the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParcelamentoImposto> partialUpdate(ParcelamentoImposto parcelamentoImposto) {
        log.debug("Request to partially update ParcelamentoImposto : {}", parcelamentoImposto);

        return parcelamentoImpostoRepository
            .findById(parcelamentoImposto.getId())
            .map(existingParcelamentoImposto -> {
                if (parcelamentoImposto.getDiaVencimento() != null) {
                    existingParcelamentoImposto.setDiaVencimento(parcelamentoImposto.getDiaVencimento());
                }
                if (parcelamentoImposto.getNumeroParcelas() != null) {
                    existingParcelamentoImposto.setNumeroParcelas(parcelamentoImposto.getNumeroParcelas());
                }
                if (parcelamentoImposto.getUrlArquivoNegociacao() != null) {
                    existingParcelamentoImposto.setUrlArquivoNegociacao(parcelamentoImposto.getUrlArquivoNegociacao());
                }
                if (parcelamentoImposto.getNumeroParcelasPagas() != null) {
                    existingParcelamentoImposto.setNumeroParcelasPagas(parcelamentoImposto.getNumeroParcelasPagas());
                }
                if (parcelamentoImposto.getNumeroParcelasRegatantes() != null) {
                    existingParcelamentoImposto.setNumeroParcelasRegatantes(parcelamentoImposto.getNumeroParcelasRegatantes());
                }
                if (parcelamentoImposto.getSituacaoSolicitacaoParcelamentoEnum() != null) {
                    existingParcelamentoImposto.setSituacaoSolicitacaoParcelamentoEnum(
                        parcelamentoImposto.getSituacaoSolicitacaoParcelamentoEnum()
                    );
                }

                return existingParcelamentoImposto;
            })
            .map(parcelamentoImpostoRepository::save);
    }

    /**
     * Get all the parcelamentoImpostos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ParcelamentoImposto> findAll() {
        log.debug("Request to get all ParcelamentoImpostos");
        return parcelamentoImpostoRepository.findAll();
    }

    /**
     * Get all the parcelamentoImpostos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ParcelamentoImposto> findAllWithEagerRelationships(Pageable pageable) {
        return parcelamentoImpostoRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one parcelamentoImposto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParcelamentoImposto> findOne(Long id) {
        log.debug("Request to get ParcelamentoImposto : {}", id);
        return parcelamentoImpostoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the parcelamentoImposto by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParcelamentoImposto : {}", id);
        parcelamentoImpostoRepository.deleteById(id);
    }
}
