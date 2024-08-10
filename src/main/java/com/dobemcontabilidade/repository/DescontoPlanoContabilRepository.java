package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DescontoPlanoContabil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DescontoPlanoContabil entity.
 */
@Repository
public interface DescontoPlanoContabilRepository
    extends JpaRepository<DescontoPlanoContabil, Long>, JpaSpecificationExecutor<DescontoPlanoContabil> {
    default Optional<DescontoPlanoContabil> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DescontoPlanoContabil> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DescontoPlanoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select descontoPlanoContabil from DescontoPlanoContabil descontoPlanoContabil left join fetch descontoPlanoContabil.periodoPagamento left join fetch descontoPlanoContabil.planoContabil",
        countQuery = "select count(descontoPlanoContabil) from DescontoPlanoContabil descontoPlanoContabil"
    )
    Page<DescontoPlanoContabil> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select descontoPlanoContabil from DescontoPlanoContabil descontoPlanoContabil left join fetch descontoPlanoContabil.periodoPagamento left join fetch descontoPlanoContabil.planoContabil"
    )
    List<DescontoPlanoContabil> findAllWithToOneRelationships();

    @Query(
        "select descontoPlanoContabil from DescontoPlanoContabil descontoPlanoContabil left join fetch descontoPlanoContabil.periodoPagamento left join fetch descontoPlanoContabil.planoContabil where descontoPlanoContabil.id =:id"
    )
    Optional<DescontoPlanoContabil> findOneWithToOneRelationships(@Param("id") Long id);
}
