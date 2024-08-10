package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SubclasseCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SubclasseCnae entity.
 */
@Repository
public interface SubclasseCnaeRepository extends JpaRepository<SubclasseCnae, Long>, JpaSpecificationExecutor<SubclasseCnae> {
    default Optional<SubclasseCnae> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SubclasseCnae> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SubclasseCnae> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select subclasseCnae from SubclasseCnae subclasseCnae left join fetch subclasseCnae.classe",
        countQuery = "select count(subclasseCnae) from SubclasseCnae subclasseCnae"
    )
    Page<SubclasseCnae> findAllWithToOneRelationships(Pageable pageable);

    @Query("select subclasseCnae from SubclasseCnae subclasseCnae left join fetch subclasseCnae.classe")
    List<SubclasseCnae> findAllWithToOneRelationships();

    @Query("select subclasseCnae from SubclasseCnae subclasseCnae left join fetch subclasseCnae.classe where subclasseCnae.id =:id")
    Optional<SubclasseCnae> findOneWithToOneRelationships(@Param("id") Long id);
}
