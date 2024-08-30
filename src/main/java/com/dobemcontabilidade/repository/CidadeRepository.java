package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Cidade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cidade entity.
 */
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>, JpaSpecificationExecutor<Cidade> {
    default Optional<Cidade> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Cidade> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Cidade> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(value = "select cidade from Cidade cidade left join fetch cidade.estado", countQuery = "select count(cidade) from Cidade cidade")
    Page<Cidade> findAllWithToOneRelationships(Pageable pageable);

    @Query("select cidade from Cidade cidade left join fetch cidade.estado")
    List<Cidade> findAllWithToOneRelationships();

    @Query("select cidade from Cidade cidade left join fetch cidade.estado where cidade.id =:id")
    Optional<Cidade> findOneWithToOneRelationships(@Param("id") Long id);
}
