package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AnexoRequerido;
import com.dobemcontabilidade.repository.AnexoRequeridoRepository;
import com.dobemcontabilidade.service.criteria.AnexoRequeridoCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AnexoRequerido} entities in the database.
 * The main input is a {@link AnexoRequeridoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnexoRequerido} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnexoRequeridoQueryService extends QueryService<AnexoRequerido> {

    private static final Logger log = LoggerFactory.getLogger(AnexoRequeridoQueryService.class);

    private final AnexoRequeridoRepository anexoRequeridoRepository;

    public AnexoRequeridoQueryService(AnexoRequeridoRepository anexoRequeridoRepository) {
        this.anexoRequeridoRepository = anexoRequeridoRepository;
    }

    /**
     * Return a {@link List} of {@link AnexoRequerido} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoRequerido> findByCriteria(AnexoRequeridoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnexoRequerido> specification = createSpecification(criteria);
        return anexoRequeridoRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnexoRequeridoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnexoRequerido> specification = createSpecification(criteria);
        return anexoRequeridoRepository.count(specification);
    }

    /**
     * Function to convert {@link AnexoRequeridoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnexoRequerido> createSpecification(AnexoRequeridoCriteria criteria) {
        Specification<AnexoRequerido> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnexoRequerido_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), AnexoRequerido_.nome));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildSpecification(criteria.getTipo(), AnexoRequerido_.tipo));
            }
            if (criteria.getAnexoRequeridoPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoRequeridoPessoaId(),
                        root -> root.join(AnexoRequerido_.anexoRequeridoPessoas, JoinType.LEFT).get(AnexoRequeridoPessoa_.id)
                    )
                );
            }
            if (criteria.getAnexoRequeridoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoRequeridoEmpresaId(),
                        root -> root.join(AnexoRequerido_.anexoRequeridoEmpresas, JoinType.LEFT).get(AnexoRequeridoEmpresa_.id)
                    )
                );
            }
            if (criteria.getAnexoRequeridoServicoContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoRequeridoServicoContabilId(),
                        root ->
                            root.join(AnexoRequerido_.anexoRequeridoServicoContabils, JoinType.LEFT).get(AnexoRequeridoServicoContabil_.id)
                    )
                );
            }
            if (criteria.getAnexoServicoContabilEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoServicoContabilEmpresaId(),
                        root -> root.join(AnexoRequerido_.anexoServicoContabilEmpresas, JoinType.LEFT).get(AnexoServicoContabilEmpresa_.id)
                    )
                );
            }
            if (criteria.getAnexoRequeridoTarefaOrdemServicoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoRequeridoTarefaOrdemServicoId(),
                        root ->
                            root
                                .join(AnexoRequerido_.anexoRequeridoTarefaOrdemServicos, JoinType.LEFT)
                                .get(AnexoRequeridoTarefaOrdemServico_.id)
                    )
                );
            }
            if (criteria.getAnexoRequeridoTarefaRecorrenteId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAnexoRequeridoTarefaRecorrenteId(),
                        root ->
                            root
                                .join(AnexoRequerido_.anexoRequeridoTarefaRecorrentes, JoinType.LEFT)
                                .get(AnexoRequeridoTarefaRecorrente_.id)
                    )
                );
            }
        }
        return specification;
    }
}
