package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Contador} and its DTO {@link ContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContadorMapper extends EntityMapper<ContadorDTO, Contador> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    @Mapping(target = "perfilContador", source = "perfilContador", qualifiedByName = "perfilContadorPerfil")
    ContadorDTO toDto(Contador s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);

    @Named("perfilContadorPerfil")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "perfil", source = "perfil")
    PerfilContadorDTO toDtoPerfilContadorPerfil(PerfilContador perfilContador);
}
