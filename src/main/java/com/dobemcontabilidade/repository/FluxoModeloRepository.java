package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.FluxoModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FluxoModelo entity.
 */
@Repository
public interface FluxoModeloRepository extends JpaRepository<FluxoModelo, Long> {
    default Optional<FluxoModelo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FluxoModelo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FluxoModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select fluxoModelo from FluxoModelo fluxoModelo left join fetch fluxoModelo.cidade",
        countQuery = "select count(fluxoModelo) from FluxoModelo fluxoModelo"
    )
    Page<FluxoModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query("select fluxoModelo from FluxoModelo fluxoModelo left join fetch fluxoModelo.cidade")
    List<FluxoModelo> findAllWithToOneRelationships();

    @Query("select fluxoModelo from FluxoModelo fluxoModelo left join fetch fluxoModelo.cidade where fluxoModelo.id =:id")
    Optional<FluxoModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
