package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DivisaoCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DivisaoCnae entity.
 */
@Repository
public interface DivisaoCnaeRepository extends JpaRepository<DivisaoCnae, Long>, JpaSpecificationExecutor<DivisaoCnae> {
    default Optional<DivisaoCnae> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DivisaoCnae> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DivisaoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select divisaoCnae from DivisaoCnae divisaoCnae left join fetch divisaoCnae.secao",
        countQuery = "select count(divisaoCnae) from DivisaoCnae divisaoCnae"
    )
    Page<DivisaoCnae> findAllWithToOneRelationships(Pageable pageable);

    @Query("select divisaoCnae from DivisaoCnae divisaoCnae left join fetch divisaoCnae.secao")
    List<DivisaoCnae> findAllWithToOneRelationships();

    @Query("select divisaoCnae from DivisaoCnae divisaoCnae left join fetch divisaoCnae.secao where divisaoCnae.id =:id")
    Optional<DivisaoCnae> findOneWithToOneRelationships(@Param("id") Long id);
}
