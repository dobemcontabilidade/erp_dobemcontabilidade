package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.repository.FormaDePagamentoRepository;
import com.dobemcontabilidade.service.criteria.FormaDePagamentoCriteria;
import com.dobemcontabilidade.service.dto.FormaDePagamentoDTO;
import com.dobemcontabilidade.service.mapper.FormaDePagamentoMapper;
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
 * Service for executing complex queries for {@link FormaDePagamento} entities in the database.
 * The main input is a {@link FormaDePagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FormaDePagamentoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FormaDePagamentoQueryService extends QueryService<FormaDePagamento> {

    private static final Logger log = LoggerFactory.getLogger(FormaDePagamentoQueryService.class);

    private final FormaDePagamentoRepository formaDePagamentoRepository;

    private final FormaDePagamentoMapper formaDePagamentoMapper;

    public FormaDePagamentoQueryService(
        FormaDePagamentoRepository formaDePagamentoRepository,
        FormaDePagamentoMapper formaDePagamentoMapper
    ) {
        this.formaDePagamentoRepository = formaDePagamentoRepository;
        this.formaDePagamentoMapper = formaDePagamentoMapper;
    }

    /**
     * Return a {@link Page} of {@link FormaDePagamentoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FormaDePagamentoDTO> findByCriteria(FormaDePagamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FormaDePagamento> specification = createSpecification(criteria);
        return formaDePagamentoRepository.findAll(specification, page).map(formaDePagamentoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FormaDePagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FormaDePagamento> specification = createSpecification(criteria);
        return formaDePagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link FormaDePagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FormaDePagamento> createSpecification(FormaDePagamentoCriteria criteria) {
        Specification<FormaDePagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FormaDePagamento_.id));
            }
            if (criteria.getForma() != null) {
                specification = specification.and(buildStringSpecification(criteria.getForma(), FormaDePagamento_.forma));
            }
            if (criteria.getDisponivel() != null) {
                specification = specification.and(buildSpecification(criteria.getDisponivel(), FormaDePagamento_.disponivel));
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(FormaDePagamento_.assinaturaEmpresas, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
