package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.repository.FuncionarioRepository;
import com.dobemcontabilidade.service.criteria.FuncionarioCriteria;
import com.dobemcontabilidade.service.dto.FuncionarioDTO;
import com.dobemcontabilidade.service.mapper.FuncionarioMapper;
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
 * Service for executing complex queries for {@link Funcionario} entities in the database.
 * The main input is a {@link FuncionarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FuncionarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuncionarioQueryService extends QueryService<Funcionario> {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioQueryService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioQueryService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Return a {@link Page} of {@link FuncionarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> findByCriteria(FuncionarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.findAll(specification, page).map(funcionarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuncionarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.count(specification);
    }

    /**
     * Function to convert {@link FuncionarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Funcionario> createSpecification(FuncionarioCriteria criteria) {
        Specification<Funcionario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Funcionario_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Funcionario_.nome));
            }
            if (criteria.getSalario() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalario(), Funcionario_.salario));
            }
            if (criteria.getCtps() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCtps(), Funcionario_.ctps));
            }
            if (criteria.getCargo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCargo(), Funcionario_.cargo));
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), Funcionario_.situacao));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(Funcionario_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
            if (criteria.getDepartamentoFuncionarioId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getDepartamentoFuncionarioId(),
                        root -> root.join(Funcionario_.departamentoFuncionarios, JoinType.LEFT).get(DepartamentoFuncionario_.id)
                    )
                );
            }
            if (criteria.getEmpresaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getEmpresaId(), root -> root.join(Funcionario_.empresa, JoinType.LEFT).get(Empresa_.id))
                );
            }
        }
        return specification;
    }
}
