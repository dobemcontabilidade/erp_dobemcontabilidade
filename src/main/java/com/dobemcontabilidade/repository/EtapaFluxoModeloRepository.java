package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EtapaFluxoModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EtapaFluxoModelo entity.
 */
@Repository
public interface EtapaFluxoModeloRepository extends JpaRepository<EtapaFluxoModelo, Long> {
    default Optional<EtapaFluxoModelo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EtapaFluxoModelo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EtapaFluxoModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select etapaFluxoModelo from EtapaFluxoModelo etapaFluxoModelo left join fetch etapaFluxoModelo.fluxoModelo",
        countQuery = "select count(etapaFluxoModelo) from EtapaFluxoModelo etapaFluxoModelo"
    )
    Page<EtapaFluxoModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query("select etapaFluxoModelo from EtapaFluxoModelo etapaFluxoModelo left join fetch etapaFluxoModelo.fluxoModelo")
    List<EtapaFluxoModelo> findAllWithToOneRelationships();

    @Query(
        "select etapaFluxoModelo from EtapaFluxoModelo etapaFluxoModelo left join fetch etapaFluxoModelo.fluxoModelo where etapaFluxoModelo.id =:id"
    )
    Optional<EtapaFluxoModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
