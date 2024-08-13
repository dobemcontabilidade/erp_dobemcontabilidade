package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GrupoCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoCnae entity.
 */
@Repository
public interface GrupoCnaeRepository extends JpaRepository<GrupoCnae, Long>, JpaSpecificationExecutor<GrupoCnae> {
    default Optional<GrupoCnae> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GrupoCnae> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GrupoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select grupoCnae from GrupoCnae grupoCnae left join fetch grupoCnae.divisao",
        countQuery = "select count(grupoCnae) from GrupoCnae grupoCnae"
    )
    Page<GrupoCnae> findAllWithToOneRelationships(Pageable pageable);

    @Query("select grupoCnae from GrupoCnae grupoCnae left join fetch grupoCnae.divisao")
    List<GrupoCnae> findAllWithToOneRelationships();

    @Query("select grupoCnae from GrupoCnae grupoCnae left join fetch grupoCnae.divisao where grupoCnae.id =:id")
    Optional<GrupoCnae> findOneWithToOneRelationships(@Param("id") Long id);
}
