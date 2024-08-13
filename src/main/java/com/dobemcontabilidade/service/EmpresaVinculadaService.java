package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.EmpresaVinculada;
import com.dobemcontabilidade.repository.EmpresaVinculadaRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.EmpresaVinculada}.
 */
@Service
@Transactional
public class EmpresaVinculadaService {

    private static final Logger log = LoggerFactory.getLogger(EmpresaVinculadaService.class);

    private final EmpresaVinculadaRepository empresaVinculadaRepository;

    public EmpresaVinculadaService(EmpresaVinculadaRepository empresaVinculadaRepository) {
        this.empresaVinculadaRepository = empresaVinculadaRepository;
    }

    /**
     * Save a empresaVinculada.
     *
     * @param empresaVinculada the entity to save.
     * @return the persisted entity.
     */
    public EmpresaVinculada save(EmpresaVinculada empresaVinculada) {
        log.debug("Request to save EmpresaVinculada : {}", empresaVinculada);
        return empresaVinculadaRepository.save(empresaVinculada);
    }

    /**
     * Update a empresaVinculada.
     *
     * @param empresaVinculada the entity to save.
     * @return the persisted entity.
     */
    public EmpresaVinculada update(EmpresaVinculada empresaVinculada) {
        log.debug("Request to update EmpresaVinculada : {}", empresaVinculada);
        return empresaVinculadaRepository.save(empresaVinculada);
    }

    /**
     * Partially update a empresaVinculada.
     *
     * @param empresaVinculada the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmpresaVinculada> partialUpdate(EmpresaVinculada empresaVinculada) {
        log.debug("Request to partially update EmpresaVinculada : {}", empresaVinculada);

        return empresaVinculadaRepository
            .findById(empresaVinculada.getId())
            .map(existingEmpresaVinculada -> {
                if (empresaVinculada.getNomeEmpresa() != null) {
                    existingEmpresaVinculada.setNomeEmpresa(empresaVinculada.getNomeEmpresa());
                }
                if (empresaVinculada.getCnpj() != null) {
                    existingEmpresaVinculada.setCnpj(empresaVinculada.getCnpj());
                }
                if (empresaVinculada.getRemuneracaoEmpresa() != null) {
                    existingEmpresaVinculada.setRemuneracaoEmpresa(empresaVinculada.getRemuneracaoEmpresa());
                }
                if (empresaVinculada.getObservacoes() != null) {
                    existingEmpresaVinculada.setObservacoes(empresaVinculada.getObservacoes());
                }
                if (empresaVinculada.getSalarioFixo() != null) {
                    existingEmpresaVinculada.setSalarioFixo(empresaVinculada.getSalarioFixo());
                }
                if (empresaVinculada.getSalarioVariavel() != null) {
                    existingEmpresaVinculada.setSalarioVariavel(empresaVinculada.getSalarioVariavel());
                }
                if (empresaVinculada.getValorSalarioFixo() != null) {
                    existingEmpresaVinculada.setValorSalarioFixo(empresaVinculada.getValorSalarioFixo());
                }
                if (empresaVinculada.getDataTerminoContrato() != null) {
                    existingEmpresaVinculada.setDataTerminoContrato(empresaVinculada.getDataTerminoContrato());
                }
                if (empresaVinculada.getNumeroInscricao() != null) {
                    existingEmpresaVinculada.setNumeroInscricao(empresaVinculada.getNumeroInscricao());
                }
                if (empresaVinculada.getCodigoLotacao() != null) {
                    existingEmpresaVinculada.setCodigoLotacao(empresaVinculada.getCodigoLotacao());
                }
                if (empresaVinculada.getDescricaoComplementar() != null) {
                    existingEmpresaVinculada.setDescricaoComplementar(empresaVinculada.getDescricaoComplementar());
                }
                if (empresaVinculada.getDescricaoCargo() != null) {
                    existingEmpresaVinculada.setDescricaoCargo(empresaVinculada.getDescricaoCargo());
                }
                if (empresaVinculada.getObservacaoJornadaTrabalho() != null) {
                    existingEmpresaVinculada.setObservacaoJornadaTrabalho(empresaVinculada.getObservacaoJornadaTrabalho());
                }
                if (empresaVinculada.getMediaHorasTrabalhadasSemana() != null) {
                    existingEmpresaVinculada.setMediaHorasTrabalhadasSemana(empresaVinculada.getMediaHorasTrabalhadasSemana());
                }
                if (empresaVinculada.getRegimePrevidenciario() != null) {
                    existingEmpresaVinculada.setRegimePrevidenciario(empresaVinculada.getRegimePrevidenciario());
                }
                if (empresaVinculada.getUnidadePagamentoSalario() != null) {
                    existingEmpresaVinculada.setUnidadePagamentoSalario(empresaVinculada.getUnidadePagamentoSalario());
                }
                if (empresaVinculada.getJornadaEspecial() != null) {
                    existingEmpresaVinculada.setJornadaEspecial(empresaVinculada.getJornadaEspecial());
                }
                if (empresaVinculada.getTipoInscricaoEmpresaVinculada() != null) {
                    existingEmpresaVinculada.setTipoInscricaoEmpresaVinculada(empresaVinculada.getTipoInscricaoEmpresaVinculada());
                }
                if (empresaVinculada.getTipoContratoTrabalho() != null) {
                    existingEmpresaVinculada.setTipoContratoTrabalho(empresaVinculada.getTipoContratoTrabalho());
                }
                if (empresaVinculada.getTipoRegimeTrabalho() != null) {
                    existingEmpresaVinculada.setTipoRegimeTrabalho(empresaVinculada.getTipoRegimeTrabalho());
                }
                if (empresaVinculada.getDiasDaSemana() != null) {
                    existingEmpresaVinculada.setDiasDaSemana(empresaVinculada.getDiasDaSemana());
                }
                if (empresaVinculada.getTipoJornadaEmpresaVinculada() != null) {
                    existingEmpresaVinculada.setTipoJornadaEmpresaVinculada(empresaVinculada.getTipoJornadaEmpresaVinculada());
                }

                return existingEmpresaVinculada;
            })
            .map(empresaVinculadaRepository::save);
    }

    /**
     * Get all the empresaVinculadas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EmpresaVinculada> findAll() {
        log.debug("Request to get all EmpresaVinculadas");
        return empresaVinculadaRepository.findAll();
    }

    /**
     * Get one empresaVinculada by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmpresaVinculada> findOne(Long id) {
        log.debug("Request to get EmpresaVinculada : {}", id);
        return empresaVinculadaRepository.findById(id);
    }

    /**
     * Delete the empresaVinculada by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmpresaVinculada : {}", id);
        empresaVinculadaRepository.deleteById(id);
    }
}
