package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.ContratoFuncionario;
import com.dobemcontabilidade.repository.ContratoFuncionarioRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.dobemcontabilidade.domain.ContratoFuncionario}.
 */
@Service
@Transactional
public class ContratoFuncionarioService {

    private static final Logger log = LoggerFactory.getLogger(ContratoFuncionarioService.class);

    private final ContratoFuncionarioRepository contratoFuncionarioRepository;

    public ContratoFuncionarioService(ContratoFuncionarioRepository contratoFuncionarioRepository) {
        this.contratoFuncionarioRepository = contratoFuncionarioRepository;
    }

    /**
     * Save a contratoFuncionario.
     *
     * @param contratoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public ContratoFuncionario save(ContratoFuncionario contratoFuncionario) {
        log.debug("Request to save ContratoFuncionario : {}", contratoFuncionario);
        return contratoFuncionarioRepository.save(contratoFuncionario);
    }

    /**
     * Update a contratoFuncionario.
     *
     * @param contratoFuncionario the entity to save.
     * @return the persisted entity.
     */
    public ContratoFuncionario update(ContratoFuncionario contratoFuncionario) {
        log.debug("Request to update ContratoFuncionario : {}", contratoFuncionario);
        return contratoFuncionarioRepository.save(contratoFuncionario);
    }

    /**
     * Partially update a contratoFuncionario.
     *
     * @param contratoFuncionario the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContratoFuncionario> partialUpdate(ContratoFuncionario contratoFuncionario) {
        log.debug("Request to partially update ContratoFuncionario : {}", contratoFuncionario);

        return contratoFuncionarioRepository
            .findById(contratoFuncionario.getId())
            .map(existingContratoFuncionario -> {
                if (contratoFuncionario.getSalarioFixo() != null) {
                    existingContratoFuncionario.setSalarioFixo(contratoFuncionario.getSalarioFixo());
                }
                if (contratoFuncionario.getSalarioVariavel() != null) {
                    existingContratoFuncionario.setSalarioVariavel(contratoFuncionario.getSalarioVariavel());
                }
                if (contratoFuncionario.getEstagio() != null) {
                    existingContratoFuncionario.setEstagio(contratoFuncionario.getEstagio());
                }
                if (contratoFuncionario.getNaturezaEstagioEnum() != null) {
                    existingContratoFuncionario.setNaturezaEstagioEnum(contratoFuncionario.getNaturezaEstagioEnum());
                }
                if (contratoFuncionario.getCtps() != null) {
                    existingContratoFuncionario.setCtps(contratoFuncionario.getCtps());
                }
                if (contratoFuncionario.getSerieCtps() != null) {
                    existingContratoFuncionario.setSerieCtps(contratoFuncionario.getSerieCtps());
                }
                if (contratoFuncionario.getOrgaoEmissorDocumento() != null) {
                    existingContratoFuncionario.setOrgaoEmissorDocumento(contratoFuncionario.getOrgaoEmissorDocumento());
                }
                if (contratoFuncionario.getDataValidadeDocumento() != null) {
                    existingContratoFuncionario.setDataValidadeDocumento(contratoFuncionario.getDataValidadeDocumento());
                }
                if (contratoFuncionario.getDataAdmissao() != null) {
                    existingContratoFuncionario.setDataAdmissao(contratoFuncionario.getDataAdmissao());
                }
                if (contratoFuncionario.getCargo() != null) {
                    existingContratoFuncionario.setCargo(contratoFuncionario.getCargo());
                }
                if (contratoFuncionario.getDescricaoAtividades() != null) {
                    existingContratoFuncionario.setDescricaoAtividades(contratoFuncionario.getDescricaoAtividades());
                }
                if (contratoFuncionario.getSituacao() != null) {
                    existingContratoFuncionario.setSituacao(contratoFuncionario.getSituacao());
                }
                if (contratoFuncionario.getValorSalarioFixo() != null) {
                    existingContratoFuncionario.setValorSalarioFixo(contratoFuncionario.getValorSalarioFixo());
                }
                if (contratoFuncionario.getValorSalarioVariavel() != null) {
                    existingContratoFuncionario.setValorSalarioVariavel(contratoFuncionario.getValorSalarioVariavel());
                }
                if (contratoFuncionario.getDataTerminoContrato() != null) {
                    existingContratoFuncionario.setDataTerminoContrato(contratoFuncionario.getDataTerminoContrato());
                }
                if (contratoFuncionario.getDatainicioContrato() != null) {
                    existingContratoFuncionario.setDatainicioContrato(contratoFuncionario.getDatainicioContrato());
                }
                if (contratoFuncionario.getHorasATrabalhadar() != null) {
                    existingContratoFuncionario.setHorasATrabalhadar(contratoFuncionario.getHorasATrabalhadar());
                }
                if (contratoFuncionario.getCodigoCargo() != null) {
                    existingContratoFuncionario.setCodigoCargo(contratoFuncionario.getCodigoCargo());
                }
                if (contratoFuncionario.getCategoriaTrabalhador() != null) {
                    existingContratoFuncionario.setCategoriaTrabalhador(contratoFuncionario.getCategoriaTrabalhador());
                }
                if (contratoFuncionario.getTipoVinculoTrabalho() != null) {
                    existingContratoFuncionario.setTipoVinculoTrabalho(contratoFuncionario.getTipoVinculoTrabalho());
                }
                if (contratoFuncionario.getFgtsOpcao() != null) {
                    existingContratoFuncionario.setFgtsOpcao(contratoFuncionario.getFgtsOpcao());
                }
                if (contratoFuncionario.gettIpoDocumentoEnum() != null) {
                    existingContratoFuncionario.settIpoDocumentoEnum(contratoFuncionario.gettIpoDocumentoEnum());
                }
                if (contratoFuncionario.getPeriodoExperiencia() != null) {
                    existingContratoFuncionario.setPeriodoExperiencia(contratoFuncionario.getPeriodoExperiencia());
                }
                if (contratoFuncionario.getTipoAdmisaoEnum() != null) {
                    existingContratoFuncionario.setTipoAdmisaoEnum(contratoFuncionario.getTipoAdmisaoEnum());
                }
                if (contratoFuncionario.getPeriodoIntermitente() != null) {
                    existingContratoFuncionario.setPeriodoIntermitente(contratoFuncionario.getPeriodoIntermitente());
                }
                if (contratoFuncionario.getIndicativoAdmissao() != null) {
                    existingContratoFuncionario.setIndicativoAdmissao(contratoFuncionario.getIndicativoAdmissao());
                }
                if (contratoFuncionario.getNumeroPisNisPasep() != null) {
                    existingContratoFuncionario.setNumeroPisNisPasep(contratoFuncionario.getNumeroPisNisPasep());
                }

                return existingContratoFuncionario;
            })
            .map(contratoFuncionarioRepository::save);
    }

    /**
     * Get all the contratoFuncionarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ContratoFuncionario> findAll() {
        log.debug("Request to get all ContratoFuncionarios");
        return contratoFuncionarioRepository.findAll();
    }

    /**
     * Get all the contratoFuncionarios with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ContratoFuncionario> findAllWithEagerRelationships(Pageable pageable) {
        return contratoFuncionarioRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one contratoFuncionario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContratoFuncionario> findOne(Long id) {
        log.debug("Request to get ContratoFuncionario : {}", id);
        return contratoFuncionarioRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the contratoFuncionario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContratoFuncionario : {}", id);
        contratoFuncionarioRepository.deleteById(id);
    }
}
