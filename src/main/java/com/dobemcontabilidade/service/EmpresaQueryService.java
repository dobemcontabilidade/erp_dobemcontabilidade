package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.EmpresaRepository;
import com.dobemcontabilidade.service.criteria.EmpresaCriteria;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.mapper.EmpresaMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Empresa} entities in the database.
 * The main input is a {@link EmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EmpresaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpresaQueryService extends QueryService<Empresa> {

    private static final Logger log = LoggerFactory.getLogger(EmpresaQueryService.class);

    private final EmpresaRepository empresaRepository;

    private final EmpresaMapper empresaMapper;

    public EmpresaQueryService(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    /**
     * Return a {@link Page} of {@link EmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmpresaDTO> findByCriteria(EmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.findAll(specification, page).map(empresaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.count(specification);
    }

    /**
     * Function to convert {@link EmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Empresa> createSpecification(EmpresaCriteria criteria) {
        Specification<Empresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Empresa_.id));
            }
            if (criteria.getRazaoSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazaoSocial(), Empresa_.razaoSocial));
            }
            if (criteria.getNomeFantasia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeFantasia(), Empresa_.nomeFantasia));
            }
            if (criteria.getCnpj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCnpj(), Empresa_.cnpj));
            }
            if (criteria.getDataAbertura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAbertura(), Empresa_.dataAbertura));
            }
            if (criteria.getUrlContratoSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlContratoSocial(), Empresa_.urlContratoSocial));
            }
            if (criteria.getCapitalSocial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapitalSocial(), Empresa_.capitalSocial));
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(Empresa_.assinaturaEmpresas, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFuncionarioId(),
                        root -> root.join(Empresa_.funcionarios, JoinType.LEFT).get(Funcionario_.id)
                    )
                );
            }
            if (criteria.getDepartamentoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoEmpresaId(),
                        root -> root.join(Empresa_.departamentoEmpresas, JoinType.LEFT).get(DepartamentoEmpresa_.id)
                    )
                );
            }
            if (criteria.getTarefaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTarefaEmpresaId(),
                        root -> root.join(Empresa_.tarefaEmpresas, JoinType.LEFT).get(TarefaEmpresa_.id)
                    )
                );
            }
            if (criteria.getEnderecoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnderecoEmpresaId(),
                        root -> root.join(Empresa_.enderecoEmpresas, JoinType.LEFT).get(EnderecoEmpresa_.id)
                    )
                );
            }
            if (criteria.getAtividadeEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAtividadeEmpresaId(),
                        root -> root.join(Empresa_.atividadeEmpresas, JoinType.LEFT).get(AtividadeEmpresa_.id)
                    )
                );
            }
            if (criteria.getSocioId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSocioId(), root -> root.join(Empresa_.socios, JoinType.LEFT).get(Socio_.id))
                );
            }
            if (criteria.getAnexoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoEmpresaId(),
                        root -> root.join(Empresa_.anexoEmpresas, JoinType.LEFT).get(AnexoEmpresa_.id)
                    )
                );
            }
            if (criteria.getCertificadoDigitalId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCertificadoDigitalId(),
                        root -> root.join(Empresa_.certificadoDigitals, JoinType.LEFT).get(CertificadoDigital_.id)
                    )
                );
            }
            if (criteria.getUsuarioEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getUsuarioEmpresaId(),
                        root -> root.join(Empresa_.usuarioEmpresas, JoinType.LEFT).get(UsuarioEmpresa_.id)
                    )
                );
            }
            if (criteria.getOpcaoRazaoSocialEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getOpcaoRazaoSocialEmpresaId(),
                        root -> root.join(Empresa_.opcaoRazaoSocialEmpresas, JoinType.LEFT).get(OpcaoRazaoSocialEmpresa_.id)
                    )
                );
            }
            if (criteria.getOpcaoNomeFantasiaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getOpcaoNomeFantasiaEmpresaId(),
                        root -> root.join(Empresa_.opcaoNomeFantasiaEmpresas, JoinType.LEFT).get(OpcaoNomeFantasiaEmpresa_.id)
                    )
                );
            }
            if (criteria.getRamoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRamoId(), root -> root.join(Empresa_.ramo, JoinType.LEFT).get(Ramo_.id))
                );
            }
            if (criteria.getTributacaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTributacaoId(),
                        root -> root.join(Empresa_.tributacao, JoinType.LEFT).get(Tributacao_.id)
                    )
                );
            }
            if (criteria.getEnquadramentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnquadramentoId(),
                        root -> root.join(Empresa_.enquadramento, JoinType.LEFT).get(Enquadramento_.id)
                    )
                );
            }
        }
        return specification;
    }
}
