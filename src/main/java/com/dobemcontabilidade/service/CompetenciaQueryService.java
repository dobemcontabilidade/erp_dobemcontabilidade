package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.repository.CompetenciaRepository;
import com.dobemcontabilidade.service.criteria.CompetenciaCriteria;
import com.dobemcontabilidade.service.dto.CompetenciaDTO;
import com.dobemcontabilidade.service.mapper.CompetenciaMapper;
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
 * Service for executing complex queries for {@link Competencia} entities in the database.
 * The main input is a {@link CompetenciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link CompetenciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompetenciaQueryService extends QueryService<Competencia> {

    private static final Logger log = LoggerFactory.getLogger(CompetenciaQueryService.class);

    private final CompetenciaRepository competenciaRepository;

    private final CompetenciaMapper competenciaMapper;

    public CompetenciaQueryService(CompetenciaRepository competenciaRepository, CompetenciaMapper competenciaMapper) {
        this.competenciaRepository = competenciaRepository;
        this.competenciaMapper = competenciaMapper;
    }

    /**
     * Return a {@link Page} of {@link CompetenciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompetenciaDTO> findByCriteria(CompetenciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Competencia> specification = createSpecification(criteria);
        return competenciaRepository.findAll(specification, page).map(competenciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompetenciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Competencia> specification = createSpecification(criteria);
        return competenciaRepository.count(specification);
    }

    /**
     * Function to convert {@link CompetenciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Competencia> createSpecification(CompetenciaCriteria criteria) {
        Specification<Competencia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Competencia_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Competencia_.nome));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Competencia_.numero));
            }
            if (criteria.getTarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTarefaId(), root -> root.join(Competencia_.tarefas, JoinType.LEFT).get(Tarefa_.id))
                );
            }
        }
        return specification;
    }
}
