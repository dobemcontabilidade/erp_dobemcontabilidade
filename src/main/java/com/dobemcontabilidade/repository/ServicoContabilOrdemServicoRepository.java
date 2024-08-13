package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ServicoContabilOrdemServico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServicoContabilOrdemServico entity.
 */
@Repository
public interface ServicoContabilOrdemServicoRepository extends JpaRepository<ServicoContabilOrdemServico, Long> {
    default Optional<ServicoContabilOrdemServico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ServicoContabilOrdemServico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ServicoContabilOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select servicoContabilOrdemServico from ServicoContabilOrdemServico servicoContabilOrdemServico left join fetch servicoContabilOrdemServico.servicoContabil left join fetch servicoContabilOrdemServico.ordemServico",
        countQuery = "select count(servicoContabilOrdemServico) from ServicoContabilOrdemServico servicoContabilOrdemServico"
    )
    Page<ServicoContabilOrdemServico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select servicoContabilOrdemServico from ServicoContabilOrdemServico servicoContabilOrdemServico left join fetch servicoContabilOrdemServico.servicoContabil left join fetch servicoContabilOrdemServico.ordemServico"
    )
    List<ServicoContabilOrdemServico> findAllWithToOneRelationships();

    @Query(
        "select servicoContabilOrdemServico from ServicoContabilOrdemServico servicoContabilOrdemServico left join fetch servicoContabilOrdemServico.servicoContabil left join fetch servicoContabilOrdemServico.ordemServico where servicoContabilOrdemServico.id =:id"
    )
    Optional<ServicoContabilOrdemServico> findOneWithToOneRelationships(@Param("id") Long id);
}
