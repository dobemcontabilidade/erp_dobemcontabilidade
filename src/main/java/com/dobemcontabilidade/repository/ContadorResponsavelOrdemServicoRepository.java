package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ContadorResponsavelOrdemServico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContadorResponsavelOrdemServico entity.
 */
@Repository
public interface ContadorResponsavelOrdemServicoRepository extends JpaRepository<ContadorResponsavelOrdemServico, Long> {
    default Optional<ContadorResponsavelOrdemServico> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ContadorResponsavelOrdemServico> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ContadorResponsavelOrdemServico> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select contadorResponsavelOrdemServico from ContadorResponsavelOrdemServico contadorResponsavelOrdemServico left join fetch contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao left join fetch contadorResponsavelOrdemServico.contador",
        countQuery = "select count(contadorResponsavelOrdemServico) from ContadorResponsavelOrdemServico contadorResponsavelOrdemServico"
    )
    Page<ContadorResponsavelOrdemServico> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select contadorResponsavelOrdemServico from ContadorResponsavelOrdemServico contadorResponsavelOrdemServico left join fetch contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao left join fetch contadorResponsavelOrdemServico.contador"
    )
    List<ContadorResponsavelOrdemServico> findAllWithToOneRelationships();

    @Query(
        "select contadorResponsavelOrdemServico from ContadorResponsavelOrdemServico contadorResponsavelOrdemServico left join fetch contadorResponsavelOrdemServico.tarefaOrdemServicoExecucao left join fetch contadorResponsavelOrdemServico.contador where contadorResponsavelOrdemServico.id =:id"
    )
    Optional<ContadorResponsavelOrdemServico> findOneWithToOneRelationships(@Param("id") Long id);
}
