package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.repository.UsuarioContadorRepository;
import com.dobemcontabilidade.service.criteria.UsuarioContadorCriteria;
import com.dobemcontabilidade.service.dto.UsuarioContadorDTO;
import com.dobemcontabilidade.service.mapper.UsuarioContadorMapper;
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
 * Service for executing complex queries for {@link UsuarioContador} entities in the database.
 * The main input is a {@link UsuarioContadorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UsuarioContadorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioContadorQueryService extends QueryService<UsuarioContador> {

    private static final Logger log = LoggerFactory.getLogger(UsuarioContadorQueryService.class);

    private final UsuarioContadorRepository usuarioContadorRepository;

    private final UsuarioContadorMapper usuarioContadorMapper;

    public UsuarioContadorQueryService(UsuarioContadorRepository usuarioContadorRepository, UsuarioContadorMapper usuarioContadorMapper) {
        this.usuarioContadorRepository = usuarioContadorRepository;
        this.usuarioContadorMapper = usuarioContadorMapper;
    }

    /**
     * Return a {@link Page} of {@link UsuarioContadorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioContadorDTO> findByCriteria(UsuarioContadorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UsuarioContador> specification = createSpecification(criteria);
        return usuarioContadorRepository.findAll(specification, page).map(usuarioContadorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioContadorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UsuarioContador> specification = createSpecification(criteria);
        return usuarioContadorRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioContadorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UsuarioContador> createSpecification(UsuarioContadorCriteria criteria) {
        Specification<UsuarioContador> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UsuarioContador_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), UsuarioContador_.email));
            }
            if (criteria.getDataHoraAtivacao() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataHoraAtivacao(), UsuarioContador_.dataHoraAtivacao)
                );
            }
            if (criteria.getDataLimiteAcesso() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDataLimiteAcesso(), UsuarioContador_.dataLimiteAcesso)
                );
            }
            if (criteria.getSituacao() != null) {
                specification = specification.and(buildSpecification(criteria.getSituacao(), UsuarioContador_.situacao));
            }
            if (criteria.getContadorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getContadorId(),
                        root -> root.join(UsuarioContador_.contador, JoinType.LEFT).get(Contador_.id)
                    )
                );
            }
            if (criteria.getAdministradorId() != null) {
                specification = specification.and(
                    buildSpecification(
                        criteria.getAdministradorId(),
                        root -> root.join(UsuarioContador_.administrador, JoinType.LEFT).get(Administrador_.id)
                    )
                );
            }
        }
        return specification;
    }
}
