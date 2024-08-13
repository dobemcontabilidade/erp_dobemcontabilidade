package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DescontoPlanoContaAzul;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DescontoPlanoContaAzul entity.
 */
@Repository
public interface DescontoPlanoContaAzulRepository
    extends JpaRepository<DescontoPlanoContaAzul, Long>, JpaSpecificationExecutor<DescontoPlanoContaAzul> {
    default Optional<DescontoPlanoContaAzul> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DescontoPlanoContaAzul> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DescontoPlanoContaAzul> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select descontoPlanoContaAzul from DescontoPlanoContaAzul descontoPlanoContaAzul left join fetch descontoPlanoContaAzul.planoContaAzul left join fetch descontoPlanoContaAzul.periodoPagamento",
        countQuery = "select count(descontoPlanoContaAzul) from DescontoPlanoContaAzul descontoPlanoContaAzul"
    )
    Page<DescontoPlanoContaAzul> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select descontoPlanoContaAzul from DescontoPlanoContaAzul descontoPlanoContaAzul left join fetch descontoPlanoContaAzul.planoContaAzul left join fetch descontoPlanoContaAzul.periodoPagamento"
    )
    List<DescontoPlanoContaAzul> findAllWithToOneRelationships();

    @Query(
        "select descontoPlanoContaAzul from DescontoPlanoContaAzul descontoPlanoContaAzul left join fetch descontoPlanoContaAzul.planoContaAzul left join fetch descontoPlanoContaAzul.periodoPagamento where descontoPlanoContaAzul.id =:id"
    )
    Optional<DescontoPlanoContaAzul> findOneWithToOneRelationships(@Param("id") Long id);
}
