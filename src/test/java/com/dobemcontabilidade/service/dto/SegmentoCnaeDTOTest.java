package com.dobemcontabilidade.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SegmentoCnaeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentoCnaeDTO.class);
        SegmentoCnaeDTO segmentoCnaeDTO1 = new SegmentoCnaeDTO();
        segmentoCnaeDTO1.setId(1L);
        SegmentoCnaeDTO segmentoCnaeDTO2 = new SegmentoCnaeDTO();
        assertThat(segmentoCnaeDTO1).isNotEqualTo(segmentoCnaeDTO2);
        segmentoCnaeDTO2.setId(segmentoCnaeDTO1.getId());
        assertThat(segmentoCnaeDTO1).isEqualTo(segmentoCnaeDTO2);
        segmentoCnaeDTO2.setId(2L);
        assertThat(segmentoCnaeDTO1).isNotEqualTo(segmentoCnaeDTO2);
        segmentoCnaeDTO1.setId(null);
        assertThat(segmentoCnaeDTO1).isNotEqualTo(segmentoCnaeDTO2);
    }
}
