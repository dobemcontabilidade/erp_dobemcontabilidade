package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.AdministradorDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Administrador} and its DTO {@link AdministradorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministradorMapper extends EntityMapper<AdministradorDTO, Administrador> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    AdministradorDTO toDto(Administrador s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);
}
