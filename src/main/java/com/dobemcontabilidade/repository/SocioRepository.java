package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Socio;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Socio entity.
 */
@Repository
public interface SocioRepository extends JpaRepository<Socio, Long>, JpaSpecificationExecutor<Socio> {
    default Optional<Socio> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Socio> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Socio> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select socio from Socio socio left join fetch socio.pessoa left join fetch socio.usuarioEmpresa left join fetch socio.empresa left join fetch socio.profissao",
        countQuery = "select count(socio) from Socio socio"
    )
    Page<Socio> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select socio from Socio socio left join fetch socio.pessoa left join fetch socio.usuarioEmpresa left join fetch socio.empresa left join fetch socio.profissao"
    )
    List<Socio> findAllWithToOneRelationships();

    @Query(
        "select socio from Socio socio left join fetch socio.pessoa left join fetch socio.usuarioEmpresa left join fetch socio.empresa left join fetch socio.profissao where socio.id =:id"
    )
    Optional<Socio> findOneWithToOneRelationships(@Param("id") Long id);
}
