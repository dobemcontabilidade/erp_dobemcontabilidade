package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AdicionalTributacao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AdicionalTributacao entity.
 */
@Repository
public interface AdicionalTributacaoRepository
    extends JpaRepository<AdicionalTributacao, Long>, JpaSpecificationExecutor<AdicionalTributacao> {
    default Optional<AdicionalTributacao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AdicionalTributacao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AdicionalTributacao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select adicionalTributacao from AdicionalTributacao adicionalTributacao left join fetch adicionalTributacao.tributacao left join fetch adicionalTributacao.planoAssinaturaContabil",
        countQuery = "select count(adicionalTributacao) from AdicionalTributacao adicionalTributacao"
    )
    Page<AdicionalTributacao> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select adicionalTributacao from AdicionalTributacao adicionalTributacao left join fetch adicionalTributacao.tributacao left join fetch adicionalTributacao.planoAssinaturaContabil"
    )
    List<AdicionalTributacao> findAllWithToOneRelationships();

    @Query(
        "select adicionalTributacao from AdicionalTributacao adicionalTributacao left join fetch adicionalTributacao.tributacao left join fetch adicionalTributacao.planoAssinaturaContabil where adicionalTributacao.id =:id"
    )
    Optional<AdicionalTributacao> findOneWithToOneRelationships(@Param("id") Long id);
}
