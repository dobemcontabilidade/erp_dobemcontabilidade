package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.repository.ContadorRepository;
import com.dobemcontabilidade.service.criteria.ContadorCriteria;
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
 * Service for executing complex queries for {@link Contador} entities in the database.
 * The main input is a {@link ContadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Contador} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ContadorQueryService extends QueryService<Contador> {

    private static final Logger log = LoggerFactory.getLogger(ContadorQueryService.class);

    private final ContadorRepository contadorRepository;

    public ContadorQueryService(ContadorRepository contadorRepository) {
        this.contadorRepository = contadorRepository;
    }

    /**
     * Return a {@link Page} of {@link Contador} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Contador> findByCriteria(ContadorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Contador> specification = createSpecification(criteria);
        return contadorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ContadorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Contador> specification = createSpecification(criteria);
        return contadorRepository.count(specification);
    }

    /**
     * Function to convert {@link ContadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Contador> createSpecification(ContadorCriteria criteria) {
        Specification<Contador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Contador_.id));
            }
            if (criteria.getCrc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCrc(), Contador_.crc));
            }
            if (criteria.getLimiteEmpresas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimiteEmpresas(), Contador_.limiteEmpresas));
            }
            if (criteria.getLimiteDepartamentos() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLimiteDepartamentos(), Contador_.limiteDepartamentos)
                );
            }
            if (criteria.getLimiteFaturamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimiteFaturamento(), Contador_.limiteFaturamento));
            }
            if (criteria.getSituacaoContador() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacaoContador(), Contador_.situacaoContador));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(Contador_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
            if (criteria.getUsuarioContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getUsuarioContadorId(),
                        root -> root.join(Contador_.usuarioContador, JoinType.LEFT).get(UsuarioContador_.id)
                    )
                );
            }
            if (criteria.getAreaContabilAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAreaContabilAssinaturaEmpresaId(),
                        root -> root.join(Contador_.areaContabilAssinaturaEmpresas, JoinType.LEFT).get(AreaContabilAssinaturaEmpresa_.id)
                    )
                );
            }
            if (criteria.getFeedBackContadorParaUsuarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getFeedBackContadorParaUsuarioId(),
                        root -> root.join(Contador_.feedBackContadorParaUsuarios, JoinType.LEFT).get(FeedBackContadorParaUsuario_.id)
                    )
                );
            }
            if (criteria.getOrdemServicoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getOrdemServicoId(),
                        root -> root.join(Contador_.ordemServicos, JoinType.LEFT).get(OrdemServico_.id)
                    )
                );
            }
            if (criteria.getAreaContabilContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAreaContabilContadorId(),
                        root -> root.join(Contador_.areaContabilContadors, JoinType.LEFT).get(AreaContabilContador_.id)
                    )
                );
            }
            if (criteria.getContadorResponsavelOrdemServicoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorResponsavelOrdemServicoId(),
                        root ->
                            root.join(Contador_.contadorResponsavelOrdemServicos, JoinType.LEFT).get(ContadorResponsavelOrdemServico_.id)
                    )
                );
            }
            if (criteria.getContadorResponsavelTarefaRecorrenteId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorResponsavelTarefaRecorrenteId(),
                        root ->
                            root
                                .join(Contador_.contadorResponsavelTarefaRecorrentes, JoinType.LEFT)
                                .get(ContadorResponsavelTarefaRecorrente_.id)
                    )
                );
            }
            if (criteria.getDepartamentoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoEmpresaId(),
                        root -> root.join(Contador_.departamentoEmpresas, JoinType.LEFT).get(DepartamentoEmpresa_.id)
                    )
                );
            }
            if (criteria.getDepartamentoContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoContadorId(),
                        root -> root.join(Contador_.departamentoContadors, JoinType.LEFT).get(DepartamentoContador_.id)
                    )
                );
            }
            if (criteria.getTermoAdesaoContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTermoAdesaoContadorId(),
                        root -> root.join(Contador_.termoAdesaoContadors, JoinType.LEFT).get(TermoAdesaoContador_.id)
                    )
                );
            }
            if (criteria.getAvaliacaoContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAvaliacaoContadorId(),
                        root -> root.join(Contador_.avaliacaoContadors, JoinType.LEFT).get(AvaliacaoContador_.id)
                    )
                );
            }
            if (criteria.getTarefaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTarefaEmpresaId(),
                        root -> root.join(Contador_.tarefaEmpresas, JoinType.LEFT).get(TarefaEmpresa_.id)
                    )
                );
            }
            if (criteria.getPerfilContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPerfilContadorId(),
                        root -> root.join(Contador_.perfilContador, JoinType.LEFT).get(PerfilContador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
