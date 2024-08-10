package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.ObservacaoCnae;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.service.dto.ObservacaoCnaeDTO;
import com.dobemcontabilidade.service.dto.SubclasseCnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ObservacaoCnae} and its DTO {@link ObservacaoCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ObservacaoCnaeMapper extends EntityMapper<ObservacaoCnaeDTO, ObservacaoCnae> {
    @Mapping(target = "subclasse", source = "subclasse", qualifiedByName = "subclasseCnaeDescricao")
    ObservacaoCnaeDTO toDto(ObservacaoCnae s);

    @Named("subclasseCnaeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    SubclasseCnaeDTO toDtoSubclasseCnaeDescricao(SubclasseCnae subclasseCnae);

    default String map(byte[] value) {
        return new String(value);
    }
}
