package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Telefone;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Telefone entity.
 */
@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long>, JpaSpecificationExecutor<Telefone> {
    default Optional<Telefone> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Telefone> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Telefone> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select telefone from Telefone telefone left join fetch telefone.pessoa",
        countQuery = "select count(telefone) from Telefone telefone"
    )
    Page<Telefone> findAllWithToOneRelationships(Pageable pageable);

    @Query("select telefone from Telefone telefone left join fetch telefone.pessoa")
    List<Telefone> findAllWithToOneRelationships();

    @Query("select telefone from Telefone telefone left join fetch telefone.pessoa where telefone.id =:id")
    Optional<Telefone> findOneWithToOneRelationships(@Param("id") Long id);
}
