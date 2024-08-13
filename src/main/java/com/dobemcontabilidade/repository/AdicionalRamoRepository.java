package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AdicionalRamo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdicionalRamo entity.
 */
@Repository
public interface AdicionalRamoRepository extends JpaRepository<AdicionalRamo, Long>, JpaSpecificationExecutor<AdicionalRamo> {
    default Optional<AdicionalRamo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AdicionalRamo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AdicionalRamo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select adicionalRamo from AdicionalRamo adicionalRamo left join fetch adicionalRamo.ramo left join fetch adicionalRamo.planoContabil",
        countQuery = "select count(adicionalRamo) from AdicionalRamo adicionalRamo"
    )
    Page<AdicionalRamo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select adicionalRamo from AdicionalRamo adicionalRamo left join fetch adicionalRamo.ramo left join fetch adicionalRamo.planoContabil"
    )
    List<AdicionalRamo> findAllWithToOneRelationships();

    @Query(
        "select adicionalRamo from AdicionalRamo adicionalRamo left join fetch adicionalRamo.ramo left join fetch adicionalRamo.planoContabil where adicionalRamo.id =:id"
    )
    Optional<AdicionalRamo> findOneWithToOneRelationships(@Param("id") Long id);
}
