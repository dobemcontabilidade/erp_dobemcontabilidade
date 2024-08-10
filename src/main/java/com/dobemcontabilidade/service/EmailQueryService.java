package com.dobemcontabilidade.service;

import com.dobemcontabilidade.domain.*; // for static metamodels
import com.dobemcontabilidade.domain.Email;
import com.dobemcontabilidade.repository.EmailRepository;
import com.dobemcontabilidade.service.criteria.EmailCriteria;
import com.dobemcontabilidade.service.dto.EmailDTO;
import com.dobemcontabilidade.service.mapper.EmailMapper;
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
 * Service for executing complex queries for {@link Email} entities in the database.
 * The main input is a {@link EmailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link EmailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailQueryService extends QueryService<Email> {

    private static final Logger log = LoggerFactory.getLogger(EmailQueryService.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailQueryService(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Return a {@link Page} of {@link EmailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailDTO> findByCriteria(EmailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Email> specification = createSpecification(criteria);
        return emailRepository.findAll(specification, page).map(emailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Email> specification = createSpecification(criteria);
        return emailRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Email> createSpecification(EmailCriteria criteria) {
        Specification<Email> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Email_.id));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Email_.email));
            }
            if (criteria.getPrincipal() != null) {
                specification = specification.and(buildSpecification(criteria.getPrincipal(), Email_.principal));
            }
            if (criteria.getPessoaId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getPessoaId(), root -> root.join(Email_.pessoa, JoinType.LEFT).get(Pessoa_.id))
                );
            }
        }
        return specification;
    }
}
