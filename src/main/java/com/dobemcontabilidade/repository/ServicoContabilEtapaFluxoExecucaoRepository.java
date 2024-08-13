package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServicoContabilEtapaFluxoExecucao entity.
 */
@Repository
public interface ServicoContabilEtapaFluxoExecucaoRepository extends JpaRepository<ServicoContabilEtapaFluxoExecucao, Long> {
    default Optional<ServicoContabilEtapaFluxoExecucao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ServicoContabilEtapaFluxoExecucao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ServicoContabilEtapaFluxoExecucao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select servicoContabilEtapaFluxoExecucao from ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao left join fetch servicoContabilEtapaFluxoExecucao.servicoContabil left join fetch servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao",
        countQuery = "select count(servicoContabilEtapaFluxoExecucao) from ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao"
    )
    Page<ServicoContabilEtapaFluxoExecucao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select servicoContabilEtapaFluxoExecucao from ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao left join fetch servicoContabilEtapaFluxoExecucao.servicoContabil left join fetch servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao"
    )
    List<ServicoContabilEtapaFluxoExecucao> findAllWithToOneRelationships();

    @Query(
        "select servicoContabilEtapaFluxoExecucao from ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao left join fetch servicoContabilEtapaFluxoExecucao.servicoContabil left join fetch servicoContabilEtapaFluxoExecucao.etapaFluxoExecucao where servicoContabilEtapaFluxoExecucao.id =:id"
    )
    Optional<ServicoContabilEtapaFluxoExecucao> findOneWithToOneRelationships(@Param("id") Long id);
}
