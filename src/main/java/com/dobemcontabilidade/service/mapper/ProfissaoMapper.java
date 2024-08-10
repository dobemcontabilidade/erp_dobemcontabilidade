package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Profissao;
import com.dobemcontabilidade.domain.Socio;
import com.dobemcontabilidade.service.dto.ProfissaoDTO;
import com.dobemcontabilidade.service.dto.SocioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Profissao} and its DTO {@link ProfissaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfissaoMapper extends EntityMapper<ProfissaoDTO, Profissao> {
    @Mapping(target = "socio", source = "socio", qualifiedByName = "socioFuncaoSocio")
    ProfissaoDTO toDto(Profissao s);

    @Named("socioFuncaoSocio")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "funcaoSocio", source = "funcaoSocio")
    SocioDTO toDtoSocioFuncaoSocio(Socio socio);
}
