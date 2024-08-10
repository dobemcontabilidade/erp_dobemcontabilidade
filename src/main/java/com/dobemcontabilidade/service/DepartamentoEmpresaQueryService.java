package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.DepartamentoEmpresa;
import com.dobemcontabilidade.repository.DepartamentoEmpresaRepository;
import com.dobemcontabilidade.service.criteria.DepartamentoEmpresaCriteria;
import com.dobemcontabilidade.service.dto.DepartamentoEmpresaDTO;
import com.dobemcontabilidade.service.mapper.DepartamentoEmpresaMapper;
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
 * Service for executing complex queries for {@link DepartamentoEmpresa} entities in the database.
 * The main input is a {@link DepartamentoEmpresaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DepartamentoEmpresaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepartamentoEmpresaQueryService extends QueryService<DepartamentoEmpresa> {

    private static final Logger log = LoggerFactory.getLogger(DepartamentoEmpresaQueryService.class);

    private final DepartamentoEmpresaRepository departamentoEmpresaRepository;

    private final DepartamentoEmpresaMapper departamentoEmpresaMapper;

    public DepartamentoEmpresaQueryService(
        DepartamentoEmpresaRepository departamentoEmpresaRepository,
        DepartamentoEmpresaMapper departamentoEmpresaMapper
    ) {
        this.departamentoEmpresaRepository = departamentoEmpresaRepository;
        this.departamentoEmpresaMapper = departamentoEmpresaMapper;
    }

    /**
     * Return a {@link Page} of {@link DepartamentoEmpresaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepartamentoEmpresaDTO> findByCriteria(DepartamentoEmpresaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepartamentoEmpresa> specification = createSpecification(criteria);
        return departamentoEmpresaRepository.findAll(specification, page).map(departamentoEmpresaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepartamentoEmpresaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepartamentoEmpresa> specification = createSpecification(criteria);
        return departamentoEmpresaRepository.count(specification);
    }

    /**
     * Function to convert {@link DepartamentoEmpresaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DepartamentoEmpresa> createSpecification(DepartamentoEmpresaCriteria criteria) {
        Specification<DepartamentoEmpresa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DepartamentoEmpresa_.id));
            }
            if (criteria.getPontuacao() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPontuacao(), DepartamentoEmpresa_.pontuacao));
            }
            if (criteria.getDepoimento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepoimento(), DepartamentoEmpresa_.depoimento));
            }
            if (criteria.getReclamacao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReclamacao(), DepartamentoEmpresa_.reclamacao));
            }
            if (criteria.getDepartamentoId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoId(),
                        root -> root.join(DepartamentoEmpresa_.departamento, JoinType.LEFT).get(Departamento_.id)
                    )
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEmpresaId(),
                        root -> root.join(DepartamentoEmpresa_.empresa, JoinType.LEFT).get(Empresa_.id)
                    )
                );
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorId(),
                        root -> root.join(DepartamentoEmpresa_.contador, JoinType.LEFT).get(Contador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
