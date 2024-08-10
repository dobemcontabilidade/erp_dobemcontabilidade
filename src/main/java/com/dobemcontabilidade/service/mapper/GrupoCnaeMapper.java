package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.DivisaoCnae;
import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.service.dto.DivisaoCnaeDTO;
import com.dobemcontabilidade.service.dto.GrupoCnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GrupoCnae} and its DTO {@link GrupoCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface GrupoCnaeMapper extends EntityMapper<GrupoCnaeDTO, GrupoCnae> {
    @Mapping(target = "divisao", source = "divisao", qualifiedByName = "divisaoCnaeDescricao")
    GrupoCnaeDTO toDto(GrupoCnae s);

    @Named("divisaoCnaeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    DivisaoCnaeDTO toDtoDivisaoCnaeDescricao(DivisaoCnae divisaoCnae);

    default String map(byte[] value) {
        return new String(value);
    }
}
