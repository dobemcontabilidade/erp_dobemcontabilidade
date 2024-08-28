package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EnderecoPessoa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EnderecoPessoa entity.
 */
@Repository
public interface EnderecoPessoaRepository extends JpaRepository<EnderecoPessoa, Long> {
    default Optional<EnderecoPessoa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<EnderecoPessoa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<EnderecoPessoa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select enderecoPessoa from EnderecoPessoa enderecoPessoa left join fetch enderecoPessoa.pessoa left join fetch enderecoPessoa.cidade",
        countQuery = "select count(enderecoPessoa) from EnderecoPessoa enderecoPessoa"
    )
    Page<EnderecoPessoa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select enderecoPessoa from EnderecoPessoa enderecoPessoa left join fetch enderecoPessoa.pessoa left join fetch enderecoPessoa.cidade"
    )
    List<EnderecoPessoa> findAllWithToOneRelationships();

    @Query(
        "select enderecoPessoa from EnderecoPessoa enderecoPessoa left join fetch enderecoPessoa.pessoa left join fetch enderecoPessoa.cidade where enderecoPessoa.id =:id"
    )
    Optional<EnderecoPessoa> findOneWithToOneRelationships(@Param("id") Long id);
}
