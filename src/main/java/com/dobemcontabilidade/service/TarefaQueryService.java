package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.repository.TarefaRepository;
import com.dobemcontabilidade.service.criteria.TarefaCriteria;
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
 * Service for executing complex queries for {@link Tarefa} entities in the database.
 * The main input is a {@link TarefaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Tarefa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TarefaQueryService extends QueryService<Tarefa> {

    private static final Logger log = LoggerFactory.getLogger(TarefaQueryService.class);

    private final TarefaRepository tarefaRepository;

    public TarefaQueryService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    /**
     * Return a {@link Page} of {@link Tarefa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tarefa> findByCriteria(TarefaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tarefa> specification = createSpecification(criteria);
        return tarefaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TarefaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tarefa> specification = createSpecification(criteria);
        return tarefaRepository.count(specification);
    }

    /**
     * Function to convert {@link TarefaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tarefa> createSpecification(TarefaCriteria criteria) {
        Specification<Tarefa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tarefa_.id));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Tarefa_.titulo));
            }
            if (criteria.getNumeroDias() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroDias(), Tarefa_.numeroDias));
            }
            if (criteria.getDiaUtil() != null) {
                specification = specification.and(buildSpecification(criteria.getDiaUtil(), Tarefa_.diaUtil));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Tarefa_.valor));
            }
            if (criteria.getNotificarCliente() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificarCliente(), Tarefa_.notificarCliente));
            }
            if (criteria.getGeraMulta() != null) {
                specification = specification.and(buildSpecification(criteria.getGeraMulta(), Tarefa_.geraMulta));
            }
            if (criteria.getExibirEmpresa() != null) {
                specification = specification.and(buildSpecification(criteria.getExibirEmpresa(), Tarefa_.exibirEmpresa));
            }
            if (criteria.getDataLegal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataLegal(), Tarefa_.dataLegal));
            }
            if (criteria.getPontos() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPontos(), Tarefa_.pontos));
            }
            if (criteria.getTipoTarefa() != null) {
                specification = specification.and(buildSpecification(criteria.getTipoTarefa(), Tarefa_.tipoTarefa));
            }
            if (criteria.getTarefaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getTarefaEmpresaId(),
                        root -> root.join(Tarefa_.tarefaEmpresas, JoinType.LEFT).get(TarefaEmpresa_.id)
                    )
                );
            }
            if (criteria.getSubtarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSubtarefaId(), root -> root.join(Tarefa_.subtarefas, JoinType.LEFT).get(Subtarefa_.id))
                );
            }
            if (criteria.getEsferaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEsferaId(), root -> root.join(Tarefa_.esfera, JoinType.LEFT).get(Esfera_.id))
                );
            }
            if (criteria.getFrequenciaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getFrequenciaId(), root -> root.join(Tarefa_.frequencia, JoinType.LEFT).get(Frequencia_.id))
                );
            }
            if (criteria.getCompetenciaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCompetenciaId(),
                        root -> root.join(Tarefa_.competencia, JoinType.LEFT).get(Competencia_.id)
                    )
                );
            }
        }
        return specification;
    }
}
