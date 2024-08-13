package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.OrdemServico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrdemServico entity.
 */
@Repository
public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    default Optional<OrdemServico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OrdemServico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select ordemServico from OrdemServico ordemServico left join fetch ordemServico.empresa left join fetch ordemServico.contador left join fetch ordemServico.fluxoModelo",
        countQuery = "select count(ordemServico) from OrdemServico ordemServico"
    )
    Page<OrdemServico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select ordemServico from OrdemServico ordemServico left join fetch ordemServico.empresa left join fetch ordemServico.contador left join fetch ordemServico.fluxoModelo"
    )
    List<OrdemServico> findAllWithToOneRelationships();

    @Query(
        "select ordemServico from OrdemServico ordemServico left join fetch ordemServico.empresa left join fetch ordemServico.contador left join fetch ordemServico.fluxoModelo where ordemServico.id =:id"
    )
    Optional<OrdemServico> findOneWithToOneRelationships(@Param("id") Long id);
}
