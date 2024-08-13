package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModelo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServicoContabilEtapaFluxoModelo entity.
 */
@Repository
public interface ServicoContabilEtapaFluxoModeloRepository extends JpaRepository<ServicoContabilEtapaFluxoModelo, Long> {
    default Optional<ServicoContabilEtapaFluxoModelo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ServicoContabilEtapaFluxoModelo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ServicoContabilEtapaFluxoModelo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select servicoContabilEtapaFluxoModelo from ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo left join fetch servicoContabilEtapaFluxoModelo.etapaFluxoModelo left join fetch servicoContabilEtapaFluxoModelo.servicoContabil",
        countQuery = "select count(servicoContabilEtapaFluxoModelo) from ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo"
    )
    Page<ServicoContabilEtapaFluxoModelo> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select servicoContabilEtapaFluxoModelo from ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo left join fetch servicoContabilEtapaFluxoModelo.etapaFluxoModelo left join fetch servicoContabilEtapaFluxoModelo.servicoContabil"
    )
    List<ServicoContabilEtapaFluxoModelo> findAllWithToOneRelationships();

    @Query(
        "select servicoContabilEtapaFluxoModelo from ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo left join fetch servicoContabilEtapaFluxoModelo.etapaFluxoModelo left join fetch servicoContabilEtapaFluxoModelo.servicoContabil where servicoContabilEtapaFluxoModelo.id =:id"
    )
    Optional<ServicoContabilEtapaFluxoModelo> findOneWithToOneRelationships(@Param("id") Long id);
}
