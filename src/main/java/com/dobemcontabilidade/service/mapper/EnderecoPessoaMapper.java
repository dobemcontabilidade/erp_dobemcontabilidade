package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.EnderecoPessoa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.CidadeDTO;
import com.dobemcontabilidade.service.dto.EnderecoPessoaDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnderecoPessoa} and its DTO {@link EnderecoPessoaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoPessoaMapper extends EntityMapper<EnderecoPessoaDTO, EnderecoPessoa> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidadeNome")
    EnderecoPessoaDTO toDto(EnderecoPessoa s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);

    @Named("cidadeNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CidadeDTO toDtoCidadeNome(Cidade cidade);
}
