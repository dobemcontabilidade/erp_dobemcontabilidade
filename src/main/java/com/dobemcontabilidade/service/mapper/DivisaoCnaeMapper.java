package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.DivisaoCnae;
import com.dobemcontabilidade.domain.SecaoCnae;
import com.dobemcontabilidade.service.dto.DivisaoCnaeDTO;
import com.dobemcontabilidade.service.dto.SecaoCnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DivisaoCnae} and its DTO {@link DivisaoCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface DivisaoCnaeMapper extends EntityMapper<DivisaoCnaeDTO, DivisaoCnae> {
    @Mapping(target = "secao", source = "secao", qualifiedByName = "secaoCnaeDescricao")
    DivisaoCnaeDTO toDto(DivisaoCnae s);

    @Named("secaoCnaeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    SecaoCnaeDTO toDtoSecaoCnaeDescricao(SecaoCnae secaoCnae);

    default String map(byte[] value) {
        return new String(value);
    }
}
