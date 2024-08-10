package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Banco;
import com.dobemcontabilidade.domain.BancoContador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.service.dto.BancoContadorDTO;
import com.dobemcontabilidade.service.dto.BancoDTO;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BancoContador} and its DTO {@link BancoContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface BancoContadorMapper extends EntityMapper<BancoContadorDTO, BancoContador> {
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    @Mapping(target = "banco", source = "banco", qualifiedByName = "bancoNome")
    BancoContadorDTO toDto(BancoContador s);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);

    @Named("bancoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    BancoDTO toDtoBancoNome(Banco banco);
}
