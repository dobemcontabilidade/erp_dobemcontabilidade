package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.repository.PerfilContadorRepository;
import com.dobemcontabilidade.service.criteria.PerfilContadorCriteria;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
import com.dobemcontabilidade.service.mapper.PerfilContadorMapper;
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
 * Service for executing complex queries for {@link PerfilContador} entities in the database.
 * The main input is a {@link PerfilContadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link PerfilContadorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PerfilContadorQueryService extends QueryService<PerfilContador> {

    private static final Logger log = LoggerFactory.getLogger(PerfilContadorQueryService.class);

    private final PerfilContadorRepository perfilContadorRepository;

    private final PerfilContadorMapper perfilContadorMapper;

    public PerfilContadorQueryService(PerfilContadorRepository perfilContadorRepository, PerfilContadorMapper perfilContadorMapper) {
        this.perfilContadorRepository = perfilContadorRepository;
        this.perfilContadorMapper = perfilContadorMapper;
    }

    /**
     * Return a {@link Page} of {@link PerfilContadorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PerfilContadorDTO> findByCriteria(PerfilContadorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PerfilContador> specification = createSpecification(criteria);
        return perfilContadorRepository.findAll(specification, page).map(perfilContadorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PerfilContadorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PerfilContador> specification = createSpecification(criteria);
        return perfilContadorRepository.count(specification);
    }

    /**
     * Function to convert {@link PerfilContadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PerfilContador> createSpecification(PerfilContadorCriteria criteria) {
        Specification<PerfilContador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PerfilContador_.id));
            }
            if (criteria.getPerfil() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPerfil(), PerfilContador_.perfil));
            }
            if (criteria.getDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescricao(), PerfilContador_.descricao));
            }
            if (criteria.getLimiteEmpresas() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLimiteEmpresas(), PerfilContador_.limiteEmpresas));
            }
            if (criteria.getLimiteDepartamentos() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLimiteDepartamentos(), PerfilContador_.limiteDepartamentos)
                );
            }
            if (criteria.getLimiteAreaContabils() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLimiteAreaContabils(), PerfilContador_.limiteAreaContabils)
                );
            }
            if (criteria.getLimiteFaturamento() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getLimiteFaturamento(), PerfilContador_.limiteFaturamento)
                );
            }
            if (criteria.getPerfilContadorAreaContabilId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPerfilContadorAreaContabilId(),
                        root -> root.join(PerfilContador_.perfilContadorAreaContabils, JoinType.LEFT).get(PerfilContadorAreaContabil_.id)
                    )
                );
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorId(),
                        root -> root.join(PerfilContador_.contadors, JoinType.LEFT).get(Contador_.id)
                    )
                );
            }
            if (criteria.getPerfilContadorDepartamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getPerfilContadorDepartamentoId(),
                        root -> root.join(PerfilContador_.perfilContadorDepartamentos, JoinType.LEFT).get(PerfilContadorDepartamento_.id)
                    )
                );
            }
        }
        return specification;
    }
}
