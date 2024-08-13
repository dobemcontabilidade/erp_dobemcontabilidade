package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.BancoPessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BancoPessoa entity.
 */
@Repository
public interface BancoPessoaRepository extends JpaRepository<BancoPessoa, Long> {
    default Optional<BancoPessoa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<BancoPessoa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<BancoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select bancoPessoa from BancoPessoa bancoPessoa left join fetch bancoPessoa.pessoa left join fetch bancoPessoa.banco",
        countQuery = "select count(bancoPessoa) from BancoPessoa bancoPessoa"
    )
    Page<BancoPessoa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select bancoPessoa from BancoPessoa bancoPessoa left join fetch bancoPessoa.pessoa left join fetch bancoPessoa.banco")
    List<BancoPessoa> findAllWithToOneRelationships();

    @Query(
        "select bancoPessoa from BancoPessoa bancoPessoa left join fetch bancoPessoa.pessoa left join fetch bancoPessoa.banco where bancoPessoa.id =:id"
    )
    Optional<BancoPessoa> findOneWithToOneRelationships(@Param("id") Long id);
}
