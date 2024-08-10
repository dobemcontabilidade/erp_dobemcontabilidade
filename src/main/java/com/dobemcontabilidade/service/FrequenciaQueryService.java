package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.repository.FrequenciaRepository;
import com.dobemcontabilidade.service.criteria.FrequenciaCriteria;
import com.dobemcontabilidade.service.dto.FrequenciaDTO;
import com.dobemcontabilidade.service.mapper.FrequenciaMapper;
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
 * Service for executing complex queries for {@link Frequencia} entities in the database.
 * The main input is a {@link FrequenciaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FrequenciaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FrequenciaQueryService extends QueryService<Frequencia> {

    private static final Logger log = LoggerFactory.getLogger(FrequenciaQueryService.class);

    private final FrequenciaRepository frequenciaRepository;

    private final FrequenciaMapper frequenciaMapper;

    public FrequenciaQueryService(FrequenciaRepository frequenciaRepository, FrequenciaMapper frequenciaMapper) {
        this.frequenciaRepository = frequenciaRepository;
        this.frequenciaMapper = frequenciaMapper;
    }

    /**
     * Return a {@link Page} of {@link FrequenciaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FrequenciaDTO> findByCriteria(FrequenciaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Frequencia> specification = createSpecification(criteria);
        return frequenciaRepository.findAll(specification, page).map(frequenciaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FrequenciaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Frequencia> specification = createSpecification(criteria);
        return frequenciaRepository.count(specification);
    }

    /**
     * Function to convert {@link FrequenciaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Frequencia> createSpecification(FrequenciaCriteria criteria) {
        Specification<Frequencia> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Frequencia_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Frequencia_.nome));
            }
            if (criteria.getPrioridade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrioridade(), Frequencia_.prioridade));
            }
            if (criteria.getNumeroDias() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumeroDias(), Frequencia_.numeroDias));
            }
            if (criteria.getTarefaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getTarefaId(), root -> root.join(Frequencia_.tarefas, JoinType.LEFT).get(Tarefa_.id))
                );
            }
        }
        return specification;
    }
}
