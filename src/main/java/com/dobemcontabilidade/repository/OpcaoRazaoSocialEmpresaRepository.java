package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OpcaoRazaoSocialEmpresa entity.
 */
@Repository
public interface OpcaoRazaoSocialEmpresaRepository
    extends JpaRepository<OpcaoRazaoSocialEmpresa, Long>, JpaSpecificationExecutor<OpcaoRazaoSocialEmpresa> {
    default Optional<OpcaoRazaoSocialEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OpcaoRazaoSocialEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OpcaoRazaoSocialEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select opcaoRazaoSocialEmpresa from OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa left join fetch opcaoRazaoSocialEmpresa.empresa",
        countQuery = "select count(opcaoRazaoSocialEmpresa) from OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa"
    )
    Page<OpcaoRazaoSocialEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select opcaoRazaoSocialEmpresa from OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa left join fetch opcaoRazaoSocialEmpresa.empresa"
    )
    List<OpcaoRazaoSocialEmpresa> findAllWithToOneRelationships();

    @Query(
        "select opcaoRazaoSocialEmpresa from OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa left join fetch opcaoRazaoSocialEmpresa.empresa where opcaoRazaoSocialEmpresa.id =:id"
    )
    Optional<OpcaoRazaoSocialEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
