package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Pessoajuridica;
import com.dobemcontabilidade.repository.PessoajuridicaRepository;
import com.dobemcontabilidade.service.criteria.PessoajuridicaCriteria;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Pessoajuridica} entities in the database.
 * The main input is a {@link PessoajuridicaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Pessoajuridica} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PessoajuridicaQueryService extends QueryService<Pessoajuridica> {

    private static final Logger log = LoggerFactory.getLogger(PessoajuridicaQueryService.class);

    private final PessoajuridicaRepository pessoajuridicaRepository;

    public PessoajuridicaQueryService(PessoajuridicaRepository pessoajuridicaRepository) {
        this.pessoajuridicaRepository = pessoajuridicaRepository;
    }

    /**
     * Return a {@link List} of {@link Pessoajuridica} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Pessoajuridica> findByCriteria(PessoajuridicaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pessoajuridica> specification = createSpecification(criteria);
        return pessoajuridicaRepository.findAll(specification);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PessoajuridicaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pessoajuridica> specification = createSpecification(criteria);
        return pessoajuridicaRepository.count(specification);
    }

    /**
     * Function to convert {@link PessoajuridicaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pessoajuridica> createSpecification(PessoajuridicaCriteria criteria) {
        Specification<Pessoajuridica> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pessoajuridica_.id));
            }
            if (criteria.getRazaoSocial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRazaoSocial(), Pessoajuridica_.razaoSocial));
            }
            if (criteria.getNomeFantasia() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomeFantasia(), Pessoajuridica_.nomeFantasia));
            }
            if (criteria.getCnpj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCnpj(), Pessoajuridica_.cnpj));
            }
            if (criteria.getRedeSocialEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getRedeSocialEmpresaId(),
                        root -> root.join(Pessoajuridica_.redeSocialEmpresas, JoinType.LEFT).get(RedeSocialEmpresa_.id)
                    )
                );
            }
            if (criteria.getCertificadoDigitalEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getCertificadoDigitalEmpresaId(),
                        root -> root.join(Pessoajuridica_.certificadoDigitalEmpresas, JoinType.LEFT).get(CertificadoDigitalEmpresa_.id)
                    )
                );
            }
            if (criteria.getDocsEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDocsEmpresaId(),
                        root -> root.join(Pessoajuridica_.docsEmpresas, JoinType.LEFT).get(DocsEmpresa_.id)
                    )
                );
            }
            if (criteria.getEnderecoEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getEnderecoEmpresaId(),
                        root -> root.join(Pessoajuridica_.enderecoEmpresas, JoinType.LEFT).get(EnderecoEmpresa_.id)
                    )
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Pessoajuridica_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
        }
        return specification;
    }
}
