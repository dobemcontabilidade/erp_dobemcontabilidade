package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoPessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoPessoa entity.
 */
@Repository
public interface AnexoPessoaRepository extends JpaRepository<AnexoPessoa, Long>, JpaSpecificationExecutor<AnexoPessoa> {
    default Optional<AnexoPessoa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AnexoPessoa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AnexoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select anexoPessoa from AnexoPessoa anexoPessoa left join fetch anexoPessoa.pessoa",
        countQuery = "select count(anexoPessoa) from AnexoPessoa anexoPessoa"
    )
    Page<AnexoPessoa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select anexoPessoa from AnexoPessoa anexoPessoa left join fetch anexoPessoa.pessoa")
    List<AnexoPessoa> findAllWithToOneRelationships();

    @Query("select anexoPessoa from AnexoPessoa anexoPessoa left join fetch anexoPessoa.pessoa where anexoPessoa.id =:id")
    Optional<AnexoPessoa> findOneWithToOneRelationships(@Param("id") Long id);
}
