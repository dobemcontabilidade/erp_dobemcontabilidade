package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SegmentoCnae;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SegmentoCnaeRepositoryWithBagRelationshipsImpl implements SegmentoCnaeRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String SEGMENTOCNAES_PARAMETER = "segmentoCnaes";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<SegmentoCnae> fetchBagRelationships(Optional<SegmentoCnae> segmentoCnae) {
        return segmentoCnae.map(this::fetchSubclasseCnaes);
    }

    @Override
    public Page<SegmentoCnae> fetchBagRelationships(Page<SegmentoCnae> segmentoCnaes) {
        return new PageImpl<>(
            fetchBagRelationships(segmentoCnaes.getContent()),
            segmentoCnaes.getPageable(),
            segmentoCnaes.getTotalElements()
        );
    }

    @Override
    public List<SegmentoCnae> fetchBagRelationships(List<SegmentoCnae> segmentoCnaes) {
        return Optional.of(segmentoCnaes).map(this::fetchSubclasseCnaes).orElse(Collections.emptyList());
    }

    SegmentoCnae fetchSubclasseCnaes(SegmentoCnae result) {
        return entityManager
            .createQuery(
                "select segmentoCnae from SegmentoCnae segmentoCnae left join fetch segmentoCnae.subclasseCnaes where segmentoCnae.id = :id",
                SegmentoCnae.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<SegmentoCnae> fetchSubclasseCnaes(List<SegmentoCnae> segmentoCnaes) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, segmentoCnaes.size()).forEach(index -> order.put(segmentoCnaes.get(index).getId(), index));
        List<SegmentoCnae> result = entityManager
            .createQuery(
                "select segmentoCnae from SegmentoCnae segmentoCnae left join fetch segmentoCnae.subclasseCnaes where segmentoCnae in :segmentoCnaes",
                SegmentoCnae.class
            )
            .setParameter(SEGMENTOCNAES_PARAMETER, segmentoCnaes)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
