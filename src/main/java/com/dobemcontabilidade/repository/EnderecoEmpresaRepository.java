package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EnderecoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EnderecoEmpresa entity.
 */
@Repository
public interface EnderecoEmpresaRepository extends JpaRepository<EnderecoEmpresa, Long>, JpaSpecificationExecutor<EnderecoEmpresa> {
    default Optional<EnderecoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EnderecoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EnderecoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select enderecoEmpresa from EnderecoEmpresa enderecoEmpresa left join fetch enderecoEmpresa.empresa left join fetch enderecoEmpresa.cidade",
        countQuery = "select count(enderecoEmpresa) from EnderecoEmpresa enderecoEmpresa"
    )
    Page<EnderecoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select enderecoEmpresa from EnderecoEmpresa enderecoEmpresa left join fetch enderecoEmpresa.empresa left join fetch enderecoEmpresa.cidade"
    )
    List<EnderecoEmpresa> findAllWithToOneRelationships();

    @Query(
        "select enderecoEmpresa from EnderecoEmpresa enderecoEmpresa left join fetch enderecoEmpresa.empresa left join fetch enderecoEmpresa.cidade where enderecoEmpresa.id =:id"
    )
    Optional<EnderecoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
