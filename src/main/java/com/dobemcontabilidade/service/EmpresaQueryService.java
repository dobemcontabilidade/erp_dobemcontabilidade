package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.repository.EmpresaRepository;
import com.dobemcontabilidade.service.criteria.EmpresaCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Empresa} entities in the database.
 * The main input is a {@link EmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Empresa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmpresaQueryService extends QueryService<Empresa> {

    private static final Logger log = LoggerFactory.getLogger(EmpresaQueryService.class);

    private final EmpresaRepository empresaRepository;

    public EmpresaQueryService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /**
     * Return a {@link List} of {@link Empresa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Empresa> findByCriteria(EmpresaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Empresa> specification = createSpecification(criteria);
        return empresaRepository.findAll(specification);
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
            if (criteria.getDataAbertura() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataAbertura(), Empresa_.dataAbertura));
            }
            if (criteria.getUrlContratoSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlContratoSocial(), Empresa_.urlContratoSocial));
            }
            if (criteria.getCapitalSocial() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCapitalSocial(), Empresa_.capitalSocial));
            }
            if (criteria.getCnae() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCnae(), Empresa_.cnae));
            }
            if (criteria.getPessoaJuridicaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPessoaJuridicaId(),
                        root -> root.join(Empresa_.pessoaJuridica, JoinType.LEFT).get(Pessoajuridica_.id)
                    )
                );
            }
            if (criteria.getSocioId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSocioId(), root -> root.join(Empresa_.socios, JoinType.LEFT).get(Socio_.id))
                );
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(Empresa_.assinaturaEmpresas, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
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
            if (criteria.getRamoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getRamoId(), root -> root.join(Empresa_.ramo, JoinType.LEFT).get(Ramo_.id))
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
