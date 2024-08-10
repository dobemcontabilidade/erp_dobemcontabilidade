package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SegmentoCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SegmentoCnae entity.
 *
 * When extending this class, extend SegmentoCnaeRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SegmentoCnaeRepository
    extends SegmentoCnaeRepositoryWithBagRelationships, JpaRepository<SegmentoCnae, Long>, JpaSpecificationExecutor<SegmentoCnae> {
    default Optional<SegmentoCnae> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<SegmentoCnae> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<SegmentoCnae> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select segmentoCnae from SegmentoCnae segmentoCnae left join fetch segmentoCnae.ramo",
        countQuery = "select count(segmentoCnae) from SegmentoCnae segmentoCnae"
    )
    Page<SegmentoCnae> findAllWithToOneRelationships(Pageable pageable);

    @Query("select segmentoCnae from SegmentoCnae segmentoCnae left join fetch segmentoCnae.ramo")
    List<SegmentoCnae> findAllWithToOneRelationships();

    @Query("select segmentoCnae from SegmentoCnae segmentoCnae left join fetch segmentoCnae.ramo where segmentoCnae.id =:id")
    Optional<SegmentoCnae> findOneWithToOneRelationships(@Param("id") Long id);
}
