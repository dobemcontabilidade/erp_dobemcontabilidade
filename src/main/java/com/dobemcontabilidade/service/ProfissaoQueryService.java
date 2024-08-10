package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.repository.ProfissaoRepository;
import com.dobemcontabilidade.service.criteria.ProfissaoCriteria;
import com.dobemcontabilidade.service.dto.ProfissaoDTO;
import com.dobemcontabilidade.service.mapper.ProfissaoMapper;
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
 * Service for executing complex queries for {@link Profissao} entities in the database.
 * The main input is a {@link ProfissaoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link ProfissaoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProfissaoQueryService extends QueryService<Profissao> {

    private static final Logger log = LoggerFactory.getLogger(ProfissaoQueryService.class);

    private final ProfissaoRepository profissaoRepository;

    private final ProfissaoMapper profissaoMapper;

    public ProfissaoQueryService(ProfissaoRepository profissaoRepository, ProfissaoMapper profissaoMapper) {
        this.profissaoRepository = profissaoRepository;
        this.profissaoMapper = profissaoMapper;
    }

    /**
     * Return a {@link Page} of {@link ProfissaoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfissaoDTO> findByCriteria(ProfissaoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Profissao> specification = createSpecification(criteria);
        return profissaoRepository.findAll(specification, page).map(profissaoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProfissaoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Profissao> specification = createSpecification(criteria);
        return profissaoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProfissaoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Profissao> createSpecification(ProfissaoCriteria criteria) {
        Specification<Profissao> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Profissao_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Profissao_.nome));
            }
            if (criteria.getSocioId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSocioId(), root -> root.join(Profissao_.socio, JoinType.LEFT).get(Socio_.id))
                );
            }
        }
        return specification;
    }
}
