package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.repository.AnexoPessoaRepository;
import com.dobemcontabilidade.service.criteria.AnexoPessoaCriteria;
import com.dobemcontabilidade.service.dto.AnexoPessoaDTO;
import com.dobemcontabilidade.service.mapper.AnexoPessoaMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link AnexoPessoa} entities in the database.
 * The main input is a {@link AnexoPessoaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AnexoPessoaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnexoPessoaQueryService extends QueryService<AnexoPessoa> {

    private static final Logger log = LoggerFactory.getLogger(AnexoPessoaQueryService.class);

    private final AnexoPessoaRepository anexoPessoaRepository;

    private final AnexoPessoaMapper anexoPessoaMapper;

    public AnexoPessoaQueryService(AnexoPessoaRepository anexoPessoaRepository, AnexoPessoaMapper anexoPessoaMapper) {
        this.anexoPessoaRepository = anexoPessoaRepository;
        this.anexoPessoaMapper = anexoPessoaMapper;
    }

    /**
     * Return a {@link List} of {@link AnexoPessoaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AnexoPessoaDTO> findByCriteria(AnexoPessoaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AnexoPessoa> specification = createSpecification(criteria);
        return anexoPessoaMapper.toDto(anexoPessoaRepository.findAll(specification));
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AnexoPessoaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AnexoPessoa> specification = createSpecification(criteria);
        return anexoPessoaRepository.count(specification);
    }

    /**
     * Function to convert {@link AnexoPessoaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AnexoPessoa> createSpecification(AnexoPessoaCriteria criteria) {
        Specification<AnexoPessoa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AnexoPessoa_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), AnexoPessoa_.tipo));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(AnexoPessoa_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
        }
        return specification;
    }
}
