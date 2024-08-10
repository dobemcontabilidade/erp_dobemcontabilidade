package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.Telefone;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import com.dobemcontabilidade.service.dto.TelefoneDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telefone} and its DTO {@link TelefoneDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelefoneMapper extends EntityMapper<TelefoneDTO, Telefone> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    TelefoneDTO toDto(Telefone s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);
}
