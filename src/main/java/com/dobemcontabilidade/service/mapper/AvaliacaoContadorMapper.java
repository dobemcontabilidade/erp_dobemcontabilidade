package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Avaliacao;
import com.dobemcontabilidade.domain.AvaliacaoContador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.service.dto.AvaliacaoContadorDTO;
import com.dobemcontabilidade.service.dto.AvaliacaoDTO;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AvaliacaoContador} and its DTO {@link AvaliacaoContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AvaliacaoContadorMapper extends EntityMapper<AvaliacaoContadorDTO, AvaliacaoContador> {
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    @Mapping(target = "avaliacao", source = "avaliacao", qualifiedByName = "avaliacaoNome")
    AvaliacaoContadorDTO toDto(AvaliacaoContador s);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);

    @Named("avaliacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AvaliacaoDTO toDtoAvaliacaoNome(Avaliacao avaliacao);
}
