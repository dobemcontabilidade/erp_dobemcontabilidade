package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Pagamento;
import com.dobemcontabilidade.repository.PagamentoRepository;
import com.dobemcontabilidade.service.criteria.PagamentoCriteria;
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
 * Service for executing complex queries for {@link Pagamento} entities in the database.
 * The main input is a {@link PagamentoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Pagamento} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PagamentoQueryService extends QueryService<Pagamento> {

    private static final Logger log = LoggerFactory.getLogger(PagamentoQueryService.class);

    private final PagamentoRepository pagamentoRepository;

    public PagamentoQueryService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    /**
     * Return a {@link Page} of {@link Pagamento} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Pagamento> findByCriteria(PagamentoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pagamento> specification = createSpecification(criteria);
        return pagamentoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PagamentoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pagamento> specification = createSpecification(criteria);
        return pagamentoRepository.count(specification);
    }

    /**
     * Function to convert {@link PagamentoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pagamento> createSpecification(PagamentoCriteria criteria) {
        Specification<Pagamento> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pagamento_.id));
            }
            if (criteria.getDataCobranca() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataCobranca(), Pagamento_.dataCobranca));
            }
            if (criteria.getDataVencimento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataVencimento(), Pagamento_.dataVencimento));
            }
            if (criteria.getDataPagamento() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDataPagamento(), Pagamento_.dataPagamento));
            }
            if (criteria.getValorPago() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorPago(), Pagamento_.valorPago));
            }
            if (criteria.getValorCobrado() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValorCobrado(), Pagamento_.valorCobrado));
            }
            if (criteria.getAcrescimo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcrescimo(), Pagamento_.acrescimo));
            }
            if (criteria.getMulta() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMulta(), Pagamento_.multa));
            }
            if (criteria.getJuros() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJuros(), Pagamento_.juros));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), Pagamento_.situacao));
            }
            if (criteria.getAssinaturaEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAssinaturaEmpresaId(),
                        root -> root.join(Pagamento_.assinaturaEmpresa, JoinType.LEFT).get(AssinaturaEmpresa_.id)
                    )
                );
            }
        }
        return specification;
    }
}
