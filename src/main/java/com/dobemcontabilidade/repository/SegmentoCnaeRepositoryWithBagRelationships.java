package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SegmentoCnae;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SegmentoCnaeRepositoryWithBagRelationships {
    Optional<SegmentoCnae> fetchBagRelationships(Optional<SegmentoCnae> segmentoCnae);

    List<SegmentoCnae> fetchBagRelationships(List<SegmentoCnae> segmentoCnaes);

    Page<SegmentoCnae> fetchBagRelationships(Page<SegmentoCnae> segmentoCnaes);
}
