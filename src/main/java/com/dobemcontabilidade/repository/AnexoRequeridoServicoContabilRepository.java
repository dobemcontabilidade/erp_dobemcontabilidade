package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoRequeridoServicoContabil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoRequeridoServicoContabil entity.
 */
@Repository
public interface AnexoRequeridoServicoContabilRepository extends JpaRepository<AnexoRequeridoServicoContabil, Long> {
    default Optional<AnexoRequeridoServicoContabil> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoRequeridoServicoContabil> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoRequeridoServicoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoRequeridoServicoContabil from AnexoRequeridoServicoContabil anexoRequeridoServicoContabil left join fetch anexoRequeridoServicoContabil.servicoContabil left join fetch anexoRequeridoServicoContabil.anexoRequerido",
        countQuery = "select count(anexoRequeridoServicoContabil) from AnexoRequeridoServicoContabil anexoRequeridoServicoContabil"
    )
    Page<AnexoRequeridoServicoContabil> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoRequeridoServicoContabil from AnexoRequeridoServicoContabil anexoRequeridoServicoContabil left join fetch anexoRequeridoServicoContabil.servicoContabil left join fetch anexoRequeridoServicoContabil.anexoRequerido"
    )
    List<AnexoRequeridoServicoContabil> findAllWithToOneRelationships();

    @Query(
        "select anexoRequeridoServicoContabil from AnexoRequeridoServicoContabil anexoRequeridoServicoContabil left join fetch anexoRequeridoServicoContabil.servicoContabil left join fetch anexoRequeridoServicoContabil.anexoRequerido where anexoRequeridoServicoContabil.id =:id"
    )
    Optional<AnexoRequeridoServicoContabil> findOneWithToOneRelationships(@Param("id") Long id);
}
