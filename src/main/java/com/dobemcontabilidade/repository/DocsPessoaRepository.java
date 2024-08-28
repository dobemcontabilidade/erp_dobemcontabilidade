package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.DocsPessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DocsPessoa entity.
 */
@Repository
public interface DocsPessoaRepository extends JpaRepository<DocsPessoa, Long> {
    default Optional<DocsPessoa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DocsPessoa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DocsPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select docsPessoa from DocsPessoa docsPessoa left join fetch docsPessoa.pessoa",
        countQuery = "select count(docsPessoa) from DocsPessoa docsPessoa"
    )
    Page<DocsPessoa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select docsPessoa from DocsPessoa docsPessoa left join fetch docsPessoa.pessoa")
    List<DocsPessoa> findAllWithToOneRelationships();

    @Query("select docsPessoa from DocsPessoa docsPessoa left join fetch docsPessoa.pessoa where docsPessoa.id =:id")
    Optional<DocsPessoa> findOneWithToOneRelationships(@Param("id") Long id);
}
