package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoServicoContabilEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoServicoContabilEmpresa entity.
 */
@Repository
public interface AnexoServicoContabilEmpresaRepository extends JpaRepository<AnexoServicoContabilEmpresa, Long> {
    default Optional<AnexoServicoContabilEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoServicoContabilEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoServicoContabilEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoServicoContabilEmpresa from AnexoServicoContabilEmpresa anexoServicoContabilEmpresa left join fetch anexoServicoContabilEmpresa.anexoRequerido",
        countQuery = "select count(anexoServicoContabilEmpresa) from AnexoServicoContabilEmpresa anexoServicoContabilEmpresa"
    )
    Page<AnexoServicoContabilEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select anexoServicoContabilEmpresa from AnexoServicoContabilEmpresa anexoServicoContabilEmpresa left join fetch anexoServicoContabilEmpresa.anexoRequerido"
    )
    List<AnexoServicoContabilEmpresa> findAllWithToOneRelationships();

    @Query(
        "select anexoServicoContabilEmpresa from AnexoServicoContabilEmpresa anexoServicoContabilEmpresa left join fetch anexoServicoContabilEmpresa.anexoRequerido where anexoServicoContabilEmpresa.id =:id"
    )
    Optional<AnexoServicoContabilEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
