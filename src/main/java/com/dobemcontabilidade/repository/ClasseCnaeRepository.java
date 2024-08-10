package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.ClasseCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ClasseCnae entity.
 */
@Repository
public interface ClasseCnaeRepository extends JpaRepository<ClasseCnae, Long>, JpaSpecificationExecutor<ClasseCnae> {
    default Optional<ClasseCnae> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ClasseCnae> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ClasseCnae> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select classeCnae from ClasseCnae classeCnae left join fetch classeCnae.grupo",
        countQuery = "select count(classeCnae) from ClasseCnae classeCnae"
    )
    Page<ClasseCnae> findAllWithToOneRelationships(Pageable pageable);

    @Query("select classeCnae from ClasseCnae classeCnae left join fetch classeCnae.grupo")
    List<ClasseCnae> findAllWithToOneRelationships();

    @Query("select classeCnae from ClasseCnae classeCnae left join fetch classeCnae.grupo where classeCnae.id =:id")
    Optional<ClasseCnae> findOneWithToOneRelationships(@Param("id") Long id);
}
