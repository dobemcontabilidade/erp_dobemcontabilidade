package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.repository.EnderecoPessoaRepository;
import com.dobemcontabilidade.service.criteria.EnderecoPessoaCriteria;
import com.dobemcontabilidade.service.dto.EnderecoPessoaDTO;
import com.dobemcontabilidade.service.mapper.EnderecoPessoaMapper;
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
 * Service for executing complex queries for {@link EnderecoPessoa} entities in the database.
 * The main input is a {@link EnderecoPessoaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EnderecoPessoaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnderecoPessoaQueryService extends QueryService<EnderecoPessoa> {

    private static final Logger log = LoggerFactory.getLogger(EnderecoPessoaQueryService.class);

    private final EnderecoPessoaRepository enderecoPessoaRepository;

    private final EnderecoPessoaMapper enderecoPessoaMapper;

    public EnderecoPessoaQueryService(EnderecoPessoaRepository enderecoPessoaRepository, EnderecoPessoaMapper enderecoPessoaMapper) {
        this.enderecoPessoaRepository = enderecoPessoaRepository;
        this.enderecoPessoaMapper = enderecoPessoaMapper;
    }

    /**
     * Return a {@link Page} of {@link EnderecoPessoaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnderecoPessoaDTO> findByCriteria(EnderecoPessoaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnderecoPessoa> specification = createSpecification(criteria);
        return enderecoPessoaRepository.findAll(specification, page).map(enderecoPessoaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnderecoPessoaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnderecoPessoa> specification = createSpecification(criteria);
        return enderecoPessoaRepository.count(specification);
    }

    /**
     * Function to convert {@link EnderecoPessoaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnderecoPessoa> createSpecification(EnderecoPessoaCriteria criteria) {
        Specification<EnderecoPessoa> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnderecoPessoa_.id));
            }
            if (criteria.getLogradouro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLogradouro(), EnderecoPessoa_.logradouro));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumero(), EnderecoPessoa_.numero));
            }
            if (criteria.getComplemento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComplemento(), EnderecoPessoa_.complemento));
            }
            if (criteria.getBairro() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBairro(), EnderecoPessoa_.bairro));
            }
            if (criteria.getCep() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCep(), EnderecoPessoa_.cep));
            }
            if (criteria.getPrincipal() != null) {
                specification = specification.and(buildSpecification(criteria.getPrincipal(), EnderecoPessoa_.principal));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(EnderecoPessoa_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
            if (criteria.getCidadeId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getCidadeId(), root -> root.join(EnderecoPessoa_.cidade, JoinType.LEFT).get(Cidade_.id))
                );
            }
        }
        return specification;
    }
}
