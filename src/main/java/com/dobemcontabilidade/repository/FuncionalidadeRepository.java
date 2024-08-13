package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Funcionalidade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Funcionalidade entity.
 */
@Repository
public interface FuncionalidadeRepository extends JpaRepository<Funcionalidade, Long>, JpaSpecificationExecutor<Funcionalidade> {
    default Optional<Funcionalidade> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Funcionalidade> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Funcionalidade> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select funcionalidade from Funcionalidade funcionalidade left join fetch funcionalidade.modulo",
        countQuery = "select count(funcionalidade) from Funcionalidade funcionalidade"
    )
    Page<Funcionalidade> findAllWithToOneRelationships(Pageable pageable);

    @Query("select funcionalidade from Funcionalidade funcionalidade left join fetch funcionalidade.modulo")
    List<Funcionalidade> findAllWithToOneRelationships();

    @Query("select funcionalidade from Funcionalidade funcionalidade left join fetch funcionalidade.modulo where funcionalidade.id =:id")
    Optional<Funcionalidade> findOneWithToOneRelationships(@Param("id") Long id);
}
