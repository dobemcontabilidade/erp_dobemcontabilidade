package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DocumentoTarefaAsserts.*;
import static com.dobemcontabilidade.domain.DocumentoTarefaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentoTarefaMapperTest {

    private DocumentoTarefaMapper documentoTarefaMapper;

    @BeforeEach
    void setUp() {
        documentoTarefaMapper = new DocumentoTarefaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDocumentoTarefaSample1();
        var actual = documentoTarefaMapper.toEntity(documentoTarefaMapper.toDto(expected));
        assertDocumentoTarefaAllPropertiesEquals(expected, actual);
    }
}
