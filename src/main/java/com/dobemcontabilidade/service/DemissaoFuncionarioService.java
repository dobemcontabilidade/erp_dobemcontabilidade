package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.DemissaoFuncionario;
import com.dobemcontabilidade.repository.DemissaoFuncionarioRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.DemissaoFuncionario}.
 */
@Service
@Transactional
public class DemissaoFuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(DemissaoFuncionarioService.class);

    private final DemissaoFuncionarioRepository demissaoFuncionarioRepository;

    public DemissaoFuncionarioService(DemissaoFuncionarioRepository demissaoFuncionarioRepository) {
        this.demissaoFuncionarioRepository = demissaoFuncionarioRepository;
    }

    /**
     * Save a demissaoFuncionario.
     *
     * @param demissaoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public DemissaoFuncionario save(DemissaoFuncionario demissaoFuncionario) {
        log.debug("Request to save DemissaoFuncionario : {}", demissaoFuncionario);
        return demissaoFuncionarioRepository.save(demissaoFuncionario);
    }

    /**
     * Update a demissaoFuncionario.
     *
     * @param demissaoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public DemissaoFuncionario update(DemissaoFuncionario demissaoFuncionario) {
        log.debug("Request to update DemissaoFuncionario : {}", demissaoFuncionario);
        return demissaoFuncionarioRepository.save(demissaoFuncionario);
    }

    /**
     * Partially update a demissaoFuncionario.
     *
     * @param demissaoFuncionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemissaoFuncionario> partialUpdate(DemissaoFuncionario demissaoFuncionario) {
        log.debug("Request to partially update DemissaoFuncionario : {}", demissaoFuncionario);

        return demissaoFuncionarioRepository
            .findById(demissaoFuncionario.getId())
            .map(existingDemissaoFuncionario -> {
                if (demissaoFuncionario.getNumeroCertidaoObito() != null) {
                    existingDemissaoFuncionario.setNumeroCertidaoObito(demissaoFuncionario.getNumeroCertidaoObito());
                }
                if (demissaoFuncionario.getCnpjEmpresaSucessora() != null) {
                    existingDemissaoFuncionario.setCnpjEmpresaSucessora(demissaoFuncionario.getCnpjEmpresaSucessora());
                }
                if (demissaoFuncionario.getSaldoFGTS() != null) {
                    existingDemissaoFuncionario.setSaldoFGTS(demissaoFuncionario.getSaldoFGTS());
                }
                if (demissaoFuncionario.getValorPensao() != null) {
                    existingDemissaoFuncionario.setValorPensao(demissaoFuncionario.getValorPensao());
                }
                if (demissaoFuncionario.getValorPensaoFgts() != null) {
                    existingDemissaoFuncionario.setValorPensaoFgts(demissaoFuncionario.getValorPensaoFgts());
                }
                if (demissaoFuncionario.getPercentualPensao() != null) {
                    existingDemissaoFuncionario.setPercentualPensao(demissaoFuncionario.getPercentualPensao());
                }
                if (demissaoFuncionario.getPercentualFgts() != null) {
                    existingDemissaoFuncionario.setPercentualFgts(demissaoFuncionario.getPercentualFgts());
                }
                if (demissaoFuncionario.getDiasAvisoPrevio() != null) {
                    existingDemissaoFuncionario.setDiasAvisoPrevio(demissaoFuncionario.getDiasAvisoPrevio());
                }
                if (demissaoFuncionario.getDataAvisoPrevio() != null) {
                    existingDemissaoFuncionario.setDataAvisoPrevio(demissaoFuncionario.getDataAvisoPrevio());
                }
                if (demissaoFuncionario.getDataPagamento() != null) {
                    existingDemissaoFuncionario.setDataPagamento(demissaoFuncionario.getDataPagamento());
                }
                if (demissaoFuncionario.getDataAfastamento() != null) {
                    existingDemissaoFuncionario.setDataAfastamento(demissaoFuncionario.getDataAfastamento());
                }
                if (demissaoFuncionario.getUrlDemissional() != null) {
                    existingDemissaoFuncionario.setUrlDemissional(demissaoFuncionario.getUrlDemissional());
                }
                if (demissaoFuncionario.getCalcularRecisao() != null) {
                    existingDemissaoFuncionario.setCalcularRecisao(demissaoFuncionario.getCalcularRecisao());
                }
                if (demissaoFuncionario.getPagar13Recisao() != null) {
                    existingDemissaoFuncionario.setPagar13Recisao(demissaoFuncionario.getPagar13Recisao());
                }
                if (demissaoFuncionario.getJornadaTrabalhoCumpridaSemana() != null) {
                    existingDemissaoFuncionario.setJornadaTrabalhoCumpridaSemana(demissaoFuncionario.getJornadaTrabalhoCumpridaSemana());
                }
                if (demissaoFuncionario.getSabadoCompesado() != null) {
                    existingDemissaoFuncionario.setSabadoCompesado(demissaoFuncionario.getSabadoCompesado());
                }
                if (demissaoFuncionario.getNovoVinculoComprovado() != null) {
                    existingDemissaoFuncionario.setNovoVinculoComprovado(demissaoFuncionario.getNovoVinculoComprovado());
                }
                if (demissaoFuncionario.getDispensaAvisoPrevio() != null) {
                    existingDemissaoFuncionario.setDispensaAvisoPrevio(demissaoFuncionario.getDispensaAvisoPrevio());
                }
                if (demissaoFuncionario.getFgtsArrecadadoGuia() != null) {
                    existingDemissaoFuncionario.setFgtsArrecadadoGuia(demissaoFuncionario.getFgtsArrecadadoGuia());
                }
                if (demissaoFuncionario.getAvisoPrevioTrabalhadoRecebido() != null) {
                    existingDemissaoFuncionario.setAvisoPrevioTrabalhadoRecebido(demissaoFuncionario.getAvisoPrevioTrabalhadoRecebido());
                }
                if (demissaoFuncionario.getRecolherFgtsMesAnterior() != null) {
                    existingDemissaoFuncionario.setRecolherFgtsMesAnterior(demissaoFuncionario.getRecolherFgtsMesAnterior());
                }
                if (demissaoFuncionario.getAvisoPrevioIndenizado() != null) {
                    existingDemissaoFuncionario.setAvisoPrevioIndenizado(demissaoFuncionario.getAvisoPrevioIndenizado());
                }
                if (demissaoFuncionario.getCumprimentoAvisoPrevio() != null) {
                    existingDemissaoFuncionario.setCumprimentoAvisoPrevio(demissaoFuncionario.getCumprimentoAvisoPrevio());
                }
                if (demissaoFuncionario.getAvisoPrevio() != null) {
                    existingDemissaoFuncionario.setAvisoPrevio(demissaoFuncionario.getAvisoPrevio());
                }
                if (demissaoFuncionario.getSituacaoDemissao() != null) {
                    existingDemissaoFuncionario.setSituacaoDemissao(demissaoFuncionario.getSituacaoDemissao());
                }
                if (demissaoFuncionario.getTipoDemissao() != null) {
                    existingDemissaoFuncionario.setTipoDemissao(demissaoFuncionario.getTipoDemissao());
                }

                return existingDemissaoFuncionario;
            })
            .map(demissaoFuncionarioRepository::save);
    }

    /**
     * Get all the demissaoFuncionarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DemissaoFuncionario> findAll() {
        log.debug("Request to get all DemissaoFuncionarios");
        return demissaoFuncionarioRepository.findAll();
    }

    /**
     * Get one demissaoFuncionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemissaoFuncionario> findOne(Long id) {
        log.debug("Request to get DemissaoFuncionario : {}", id);
        return demissaoFuncionarioRepository.findById(id);
    }

    /**
     * Delete the demissaoFuncionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemissaoFuncionario : {}", id);
        demissaoFuncionarioRepository.deleteById(id);
    }
}
