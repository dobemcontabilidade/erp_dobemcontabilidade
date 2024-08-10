package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ValorBaseRamo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ValorBaseRamo entity.
 */
@Repository
public interface ValorBaseRamoRepository extends JpaRepository<ValorBaseRamo, Long>, JpaSpecificationExecutor<ValorBaseRamo> {
    default Optional<ValorBaseRamo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ValorBaseRamo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ValorBaseRamo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select valorBaseRamo from ValorBaseRamo valorBaseRamo left join fetch valorBaseRamo.ramo left join fetch valorBaseRamo.planoContabil",
        countQuery = "select count(valorBaseRamo) from ValorBaseRamo valorBaseRamo"
    )
    Page<ValorBaseRamo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select valorBaseRamo from ValorBaseRamo valorBaseRamo left join fetch valorBaseRamo.ramo left join fetch valorBaseRamo.planoContabil"
    )
    List<ValorBaseRamo> findAllWithToOneRelationships();

    @Query(
        "select valorBaseRamo from ValorBaseRamo valorBaseRamo left join fetch valorBaseRamo.ramo left join fetch valorBaseRamo.planoContabil where valorBaseRamo.id =:id"
    )
    Optional<ValorBaseRamo> findOneWithToOneRelationships(@Param("id") Long id);
}
