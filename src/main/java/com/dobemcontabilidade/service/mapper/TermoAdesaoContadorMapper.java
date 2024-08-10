package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.TermoAdesaoContador;
import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.dto.TermoAdesaoContadorDTO;
import com.dobemcontabilidade.service.dto.TermoDeAdesaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TermoAdesaoContador} and its DTO {@link TermoAdesaoContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface TermoAdesaoContadorMapper extends EntityMapper<TermoAdesaoContadorDTO, TermoAdesaoContador> {
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    @Mapping(target = "termoDeAdesao", source = "termoDeAdesao", qualifiedByName = "termoDeAdesaoTitulo")
    TermoAdesaoContadorDTO toDto(TermoAdesaoContador s);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);

    @Named("termoDeAdesaoTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    TermoDeAdesaoDTO toDtoTermoDeAdesaoTitulo(TermoDeAdesao termoDeAdesao);
}
