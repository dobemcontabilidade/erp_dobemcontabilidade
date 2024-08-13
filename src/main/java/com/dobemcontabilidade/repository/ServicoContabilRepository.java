package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ServicoContabil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ServicoContabil entity.
 */
@Repository
public interface ServicoContabilRepository extends JpaRepository<ServicoContabil, Long> {
    default Optional<ServicoContabil> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ServicoContabil> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ServicoContabil> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select servicoContabil from ServicoContabil servicoContabil left join fetch servicoContabil.areaContabil left join fetch servicoContabil.esfera",
        countQuery = "select count(servicoContabil) from ServicoContabil servicoContabil"
    )
    Page<ServicoContabil> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select servicoContabil from ServicoContabil servicoContabil left join fetch servicoContabil.areaContabil left join fetch servicoContabil.esfera"
    )
    List<ServicoContabil> findAllWithToOneRelationships();

    @Query(
        "select servicoContabil from ServicoContabil servicoContabil left join fetch servicoContabil.areaContabil left join fetch servicoContabil.esfera where servicoContabil.id =:id"
    )
    Optional<ServicoContabil> findOneWithToOneRelationships(@Param("id") Long id);
}
