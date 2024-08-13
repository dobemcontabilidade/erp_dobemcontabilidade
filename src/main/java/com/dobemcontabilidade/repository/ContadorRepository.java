package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Contador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contador entity.
 */
@Repository
public interface ContadorRepository extends JpaRepository<Contador, Long>, JpaSpecificationExecutor<Contador> {
    default Optional<Contador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Contador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Contador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select contador from Contador contador left join fetch contador.pessoa left join fetch contador.usuarioContador left join fetch contador.perfilContador",
        countQuery = "select count(contador) from Contador contador"
    )
    Page<Contador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select contador from Contador contador left join fetch contador.pessoa left join fetch contador.usuarioContador left join fetch contador.perfilContador"
    )
    List<Contador> findAllWithToOneRelationships();

    @Query(
        "select contador from Contador contador left join fetch contador.pessoa left join fetch contador.usuarioContador left join fetch contador.perfilContador where contador.id =:id"
    )
    Optional<Contador> findOneWithToOneRelationships(@Param("id") Long id);
}
