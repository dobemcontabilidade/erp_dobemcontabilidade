package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServicoContabilAssinaturaEmpresa entity.
 */
@Repository
public interface ServicoContabilAssinaturaEmpresaRepository extends JpaRepository<ServicoContabilAssinaturaEmpresa, Long> {
    default Optional<ServicoContabilAssinaturaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ServicoContabilAssinaturaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ServicoContabilAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select servicoContabilAssinaturaEmpresa from ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa left join fetch servicoContabilAssinaturaEmpresa.servicoContabil left join fetch servicoContabilAssinaturaEmpresa.assinaturaEmpresa",
        countQuery = "select count(servicoContabilAssinaturaEmpresa) from ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa"
    )
    Page<ServicoContabilAssinaturaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select servicoContabilAssinaturaEmpresa from ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa left join fetch servicoContabilAssinaturaEmpresa.servicoContabil left join fetch servicoContabilAssinaturaEmpresa.assinaturaEmpresa"
    )
    List<ServicoContabilAssinaturaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select servicoContabilAssinaturaEmpresa from ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa left join fetch servicoContabilAssinaturaEmpresa.servicoContabil left join fetch servicoContabilAssinaturaEmpresa.assinaturaEmpresa where servicoContabilAssinaturaEmpresa.id =:id"
    )
    Optional<ServicoContabilAssinaturaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
