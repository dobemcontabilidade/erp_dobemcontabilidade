package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AdicionalEnquadramento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdicionalEnquadramento entity.
 */
@Repository
public interface AdicionalEnquadramentoRepository
    extends JpaRepository<AdicionalEnquadramento, Long>, JpaSpecificationExecutor<AdicionalEnquadramento> {
    default Optional<AdicionalEnquadramento> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AdicionalEnquadramento> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AdicionalEnquadramento> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select adicionalEnquadramento from AdicionalEnquadramento adicionalEnquadramento left join fetch adicionalEnquadramento.enquadramento left join fetch adicionalEnquadramento.planoContabil",
        countQuery = "select count(adicionalEnquadramento) from AdicionalEnquadramento adicionalEnquadramento"
    )
    Page<AdicionalEnquadramento> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select adicionalEnquadramento from AdicionalEnquadramento adicionalEnquadramento left join fetch adicionalEnquadramento.enquadramento left join fetch adicionalEnquadramento.planoContabil"
    )
    List<AdicionalEnquadramento> findAllWithToOneRelationships();

    @Query(
        "select adicionalEnquadramento from AdicionalEnquadramento adicionalEnquadramento left join fetch adicionalEnquadramento.enquadramento left join fetch adicionalEnquadramento.planoContabil where adicionalEnquadramento.id =:id"
    )
    Optional<AdicionalEnquadramento> findOneWithToOneRelationships(@Param("id") Long id);
}
