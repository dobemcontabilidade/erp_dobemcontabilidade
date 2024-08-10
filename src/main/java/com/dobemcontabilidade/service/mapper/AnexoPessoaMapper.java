package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AnexoPessoa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.AnexoPessoaDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnexoPessoa} and its DTO {@link AnexoPessoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnexoPessoaMapper extends EntityMapper<AnexoPessoaDTO, AnexoPessoa> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    AnexoPessoaDTO toDto(AnexoPessoa s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);
}
