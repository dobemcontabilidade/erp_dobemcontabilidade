package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ObservacaoCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ObservacaoCnae entity.
 */
@Repository
public interface ObservacaoCnaeRepository extends JpaRepository<ObservacaoCnae, Long>, JpaSpecificationExecutor<ObservacaoCnae> {
    default Optional<ObservacaoCnae> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ObservacaoCnae> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ObservacaoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select observacaoCnae from ObservacaoCnae observacaoCnae left join fetch observacaoCnae.subclasse",
        countQuery = "select count(observacaoCnae) from ObservacaoCnae observacaoCnae"
    )
    Page<ObservacaoCnae> findAllWithToOneRelationships(Pageable pageable);

    @Query("select observacaoCnae from ObservacaoCnae observacaoCnae left join fetch observacaoCnae.subclasse")
    List<ObservacaoCnae> findAllWithToOneRelationships();

    @Query("select observacaoCnae from ObservacaoCnae observacaoCnae left join fetch observacaoCnae.subclasse where observacaoCnae.id =:id")
    Optional<ObservacaoCnae> findOneWithToOneRelationships(@Param("id") Long id);
}
