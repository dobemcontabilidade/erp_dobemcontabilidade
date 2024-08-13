package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.repository.CidadeRepository;
import com.dobemcontabilidade.service.criteria.CidadeCriteria;
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
 * Service for executing complex queries for {@link Cidade} entities in the database.
 * The main input is a {@link CidadeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Cidade} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CidadeQueryService extends QueryService<Cidade> {

    private static final Logger log = LoggerFactory.getLogger(CidadeQueryService.class);

    private final CidadeRepository cidadeRepository;

    public CidadeQueryService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    /**
     * Return a {@link Page} of {@link Cidade} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cidade> findByCriteria(CidadeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CidadeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cidade> specification = createSpecification(criteria);
        return cidadeRepository.count(specification);
    }

    /**
     * Function to convert {@link CidadeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cidade> createSpecification(CidadeCriteria criteria) {
        Specification<Cidade> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cidade_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Cidade_.nome));
            }
            if (criteria.getContratacao() != null) {
                specification = specification.and(buildSpecification(criteria.getContratacao(), Cidade_.contratacao));
            }
            if (criteria.getAbertura() != null) {
                specification = specification.and(buildSpecification(criteria.getAbertura(), Cidade_.abertura));
            }
            if (criteria.getInstituicaoEnsinoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getInstituicaoEnsinoId(),
                        root -> root.join(Cidade_.instituicaoEnsinos, JoinType.LEFT).get(InstituicaoEnsino_.id)
                    )
                );
            }
            if (criteria.getAgenteIntegracaoEstagioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAgenteIntegracaoEstagioId(),
                        root -> root.join(Cidade_.agenteIntegracaoEstagios, JoinType.LEFT).get(AgenteIntegracaoEstagio_.id)
                    )
                );
            }
            if (criteria.getEmpresaModeloId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaModeloId(),
                        root -> root.join(Cidade_.empresaModelos, JoinType.LEFT).get(EmpresaModelo_.id)
                    )
                );
            }
            if (criteria.getFluxoModeloId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFluxoModeloId(),
                        root -> root.join(Cidade_.fluxoModelos, JoinType.LEFT).get(FluxoModelo_.id)
                    )
                );
            }
            if (criteria.getEnderecoPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnderecoPessoaId(),
                        root -> root.join(Cidade_.enderecoPessoas, JoinType.LEFT).get(EnderecoPessoa_.id)
                    )
                );
            }
            if (criteria.getEnderecoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnderecoEmpresaId(),
                        root -> root.join(Cidade_.enderecoEmpresas, JoinType.LEFT).get(EnderecoEmpresa_.id)
                    )
                );
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEstadoId(), root -> root.join(Cidade_.estado, JoinType.LEFT).get(Estado_.id))
                );
            }
        }
        return specification;
    }
}
