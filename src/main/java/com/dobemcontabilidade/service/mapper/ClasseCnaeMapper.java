package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.ClasseCnae;
import com.dobemcontabilidade.domain.GrupoCnae;
import com.dobemcontabilidade.service.dto.ClasseCnaeDTO;
import com.dobemcontabilidade.service.dto.GrupoCnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClasseCnae} and its DTO {@link ClasseCnaeDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClasseCnaeMapper extends EntityMapper<ClasseCnaeDTO, ClasseCnae> {
    @Mapping(target = "grupo", source = "grupo", qualifiedByName = "grupoCnaeDescricao")
    ClasseCnaeDTO toDto(ClasseCnae s);

    @Named("grupoCnaeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    GrupoCnaeDTO toDtoGrupoCnaeDescricao(GrupoCnae grupoCnae);

    default String map(byte[] value) {
        return new String(value);
    }
}
