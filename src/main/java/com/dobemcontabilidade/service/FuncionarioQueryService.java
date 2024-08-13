package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.FuncionarioRepository;
import com.dobemcontabilidade.service.criteria.FuncionarioCriteria;
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
 * Service for executing complex queries for {@link Funcionario} entities in the database.
 * The main input is a {@link FuncionarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Funcionario} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuncionarioQueryService extends QueryService<Funcionario> {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioQueryService.class);

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioQueryService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Return a {@link Page} of {@link Funcionario} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Funcionario> findByCriteria(FuncionarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuncionarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.count(specification);
    }

    /**
     * Function to convert {@link FuncionarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Funcionario> createSpecification(FuncionarioCriteria criteria) {
        Specification<Funcionario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Funcionario_.id));
            }
            if (criteria.getNumeroPisNisPasep() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroPisNisPasep(), Funcionario_.numeroPisNisPasep));
            }
            if (criteria.getReintegrado() != null) {
                specification = specification.and(buildSpecification(criteria.getReintegrado(), Funcionario_.reintegrado));
            }
            if (criteria.getPrimeiroEmprego() != null) {
                specification = specification.and(buildSpecification(criteria.getPrimeiroEmprego(), Funcionario_.primeiroEmprego));
            }
            if (criteria.getMultiploVinculos() != null) {
                specification = specification.and(buildSpecification(criteria.getMultiploVinculos(), Funcionario_.multiploVinculos));
            }
            if (criteria.getDataOpcaoFgts() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDataOpcaoFgts(), Funcionario_.dataOpcaoFgts));
            }
            if (criteria.getFiliacaoSindical() != null) {
                specification = specification.and(buildSpecification(criteria.getFiliacaoSindical(), Funcionario_.filiacaoSindical));
            }
            if (criteria.getCnpjSindicato() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCnpjSindicato(), Funcionario_.cnpjSindicato));
            }
            if (criteria.getTipoFuncionarioEnum() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoFuncionarioEnum(), Funcionario_.tipoFuncionarioEnum));
            }
            if (criteria.getUsuarioEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getUsuarioEmpresaId(),
                        root -> root.join(Funcionario_.usuarioEmpresa, JoinType.LEFT).get(UsuarioEmpresa_.id)
                    )
                );
            }
            if (criteria.getEstrangeiroId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEstrangeiroId(),
                        root -> root.join(Funcionario_.estrangeiros, JoinType.LEFT).get(Estrangeiro_.id)
                    )
                );
            }
            if (criteria.getContratoFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContratoFuncionarioId(),
                        root -> root.join(Funcionario_.contratoFuncionarios, JoinType.LEFT).get(ContratoFuncionario_.id)
                    )
                );
            }
            if (criteria.getDemissaoFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDemissaoFuncionarioId(),
                        root -> root.join(Funcionario_.demissaoFuncionarios, JoinType.LEFT).get(DemissaoFuncionario_.id)
                    )
                );
            }
            if (criteria.getDependentesFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDependentesFuncionarioId(),
                        root -> root.join(Funcionario_.dependentesFuncionarios, JoinType.LEFT).get(DependentesFuncionario_.id)
                    )
                );
            }
            if (criteria.getEmpresaVinculadaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaVinculadaId(),
                        root -> root.join(Funcionario_.empresaVinculadas, JoinType.LEFT).get(EmpresaVinculada_.id)
                    )
                );
            }
            if (criteria.getDepartamentoFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoFuncionarioId(),
                        root -> root.join(Funcionario_.departamentoFuncionarios, JoinType.LEFT).get(DepartamentoFuncionario_.id)
                    )
                );
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(Funcionario_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Funcionario_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
            if (criteria.getProfissaoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getProfissaoId(),
                        root -> root.join(Funcionario_.profissao, JoinType.LEFT).get(Profissao_.id)
                    )
                );
            }
        }
        return specification;
    }
}
