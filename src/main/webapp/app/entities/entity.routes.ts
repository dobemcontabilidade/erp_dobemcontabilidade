import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'adicional-ramo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adicionalRamo.home.title' },
    loadChildren: () => import('./adicional-ramo/adicional-ramo.routes'),
  },
  {
    path: 'adicional-enquadramento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adicionalEnquadramento.home.title' },
    loadChildren: () => import('./adicional-enquadramento/adicional-enquadramento.routes'),
  },
  {
    path: 'adicional-tributacao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adicionalTributacao.home.title' },
    loadChildren: () => import('./adicional-tributacao/adicional-tributacao.routes'),
  },
  {
    path: 'administrador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.administrador.home.title' },
    loadChildren: () => import('./administrador/administrador.routes'),
  },
  {
    path: 'area-contabil-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.areaContabilContador.home.title' },
    loadChildren: () => import('./area-contabil-contador/area-contabil-contador.routes'),
  },
  {
    path: 'area-contabil-assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.areaContabilAssinaturaEmpresa.home.title' },
    loadChildren: () => import('./area-contabil-assinatura-empresa/area-contabil-assinatura-empresa.routes'),
  },
  {
    path: 'anexo-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoEmpresa.home.title' },
    loadChildren: () => import('./anexo-empresa/anexo-empresa.routes'),
  },
  {
    path: 'anexo-requerido-servico-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoRequeridoServicoContabil.home.title' },
    loadChildren: () => import('./anexo-requerido-servico-contabil/anexo-requerido-servico-contabil.routes'),
  },
  {
    path: 'anexo-servico-contabil-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoServicoContabilEmpresa.home.title' },
    loadChildren: () => import('./anexo-servico-contabil-empresa/anexo-servico-contabil-empresa.routes'),
  },
  {
    path: 'anexo-requerido-tarefa-recorrente',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoRequeridoTarefaRecorrente.home.title' },
    loadChildren: () => import('./anexo-requerido-tarefa-recorrente/anexo-requerido-tarefa-recorrente.routes'),
  },
  {
    path: 'anexo-tarefa-recorrente-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoTarefaRecorrenteExecucao.home.title' },
    loadChildren: () => import('./anexo-tarefa-recorrente-execucao/anexo-tarefa-recorrente-execucao.routes'),
  },
  {
    path: 'anexo-requerido-tarefa-ordem-servico',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoRequeridoTarefaOrdemServico.home.title' },
    loadChildren: () => import('./anexo-requerido-tarefa-ordem-servico/anexo-requerido-tarefa-ordem-servico.routes'),
  },
  {
    path: 'anexo-ordem-servico-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoOrdemServicoExecucao.home.title' },
    loadChildren: () => import('./anexo-ordem-servico-execucao/anexo-ordem-servico-execucao.routes'),
  },
  {
    path: 'assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.assinaturaEmpresa.home.title' },
    loadChildren: () => import('./assinatura-empresa/assinatura-empresa.routes'),
  },
  {
    path: 'agenda-tarefa-recorrente-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.agendaTarefaRecorrenteExecucao.home.title' },
    loadChildren: () => import('./agenda-tarefa-recorrente-execucao/agenda-tarefa-recorrente-execucao.routes'),
  },
  {
    path: 'agenda-contador-config',
    data: { pageTitle: 'erpDobemcontabilidadeApp.agendaContadorConfig.home.title' },
    loadChildren: () => import('./agenda-contador-config/agenda-contador-config.routes'),
  },
  {
    path: 'agenda-tarefa-ordem-servico-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.agendaTarefaOrdemServicoExecucao.home.title' },
    loadChildren: () => import('./agenda-tarefa-ordem-servico-execucao/agenda-tarefa-ordem-servico-execucao.routes'),
  },
  {
    path: 'agente-integracao-estagio',
    data: { pageTitle: 'erpDobemcontabilidadeApp.agenteIntegracaoEstagio.home.title' },
    loadChildren: () => import('./agente-integracao-estagio/agente-integracao-estagio.routes'),
  },
  {
    path: 'atividade-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.atividadeEmpresa.home.title' },
    loadChildren: () => import('./atividade-empresa/atividade-empresa.routes'),
  },
  {
    path: 'ator-avaliado',
    data: { pageTitle: 'erpDobemcontabilidadeApp.atorAvaliado.home.title' },
    loadChildren: () => import('./ator-avaliado/ator-avaliado.routes'),
  },
  {
    path: 'avaliacao-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.avaliacaoContador.home.title' },
    loadChildren: () => import('./avaliacao-contador/avaliacao-contador.routes'),
  },
  {
    path: 'avaliacao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.avaliacao.home.title' },
    loadChildren: () => import('./avaliacao/avaliacao.routes'),
  },
  {
    path: 'area-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.areaContabil.home.title' },
    loadChildren: () => import('./area-contabil/area-contabil.routes'),
  },
  {
    path: 'anexo-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoPessoa.home.title' },
    loadChildren: () => import('./anexo-pessoa/anexo-pessoa.routes'),
  },
  {
    path: 'anexo-requerido',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoRequerido.home.title' },
    loadChildren: () => import('./anexo-requerido/anexo-requerido.routes'),
  },
  {
    path: 'anexo-requerido-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoRequeridoPessoa.home.title' },
    loadChildren: () => import('./anexo-requerido-pessoa/anexo-requerido-pessoa.routes'),
  },
  {
    path: 'anexo-requerido-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoRequeridoEmpresa.home.title' },
    loadChildren: () => import('./anexo-requerido-empresa/anexo-requerido-empresa.routes'),
  },
  {
    path: 'banco-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.bancoPessoa.home.title' },
    loadChildren: () => import('./banco-pessoa/banco-pessoa.routes'),
  },
  {
    path: 'banco',
    data: { pageTitle: 'erpDobemcontabilidadeApp.banco.home.title' },
    loadChildren: () => import('./banco/banco.routes'),
  },
  {
    path: 'criterio-avaliacao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.criterioAvaliacao.home.title' },
    loadChildren: () => import('./criterio-avaliacao/criterio-avaliacao.routes'),
  },
  {
    path: 'criterio-avaliacao-ator',
    data: { pageTitle: 'erpDobemcontabilidadeApp.criterioAvaliacaoAtor.home.title' },
    loadChildren: () => import('./criterio-avaliacao-ator/criterio-avaliacao-ator.routes'),
  },
  {
    path: 'contador-responsavel-ordem-servico',
    data: { pageTitle: 'erpDobemcontabilidadeApp.contadorResponsavelOrdemServico.home.title' },
    loadChildren: () => import('./contador-responsavel-ordem-servico/contador-responsavel-ordem-servico.routes'),
  },
  {
    path: 'cobranca-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.cobrancaEmpresa.home.title' },
    loadChildren: () => import('./cobranca-empresa/cobranca-empresa.routes'),
  },
  {
    path: 'certificado-digital',
    data: { pageTitle: 'erpDobemcontabilidadeApp.certificadoDigital.home.title' },
    loadChildren: () => import('./certificado-digital/certificado-digital.routes'),
  },
  {
    path: 'cidade',
    data: { pageTitle: 'erpDobemcontabilidadeApp.cidade.home.title' },
    loadChildren: () => import('./cidade/cidade.routes'),
  },
  {
    path: 'competencia',
    data: { pageTitle: 'erpDobemcontabilidadeApp.competencia.home.title' },
    loadChildren: () => import('./competencia/competencia.routes'),
  },
  {
    path: 'contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.contador.home.title' },
    loadChildren: () => import('./contador/contador.routes'),
  },
  {
    path: 'calculo-plano-assinatura',
    data: { pageTitle: 'erpDobemcontabilidadeApp.calculoPlanoAssinatura.home.title' },
    loadChildren: () => import('./calculo-plano-assinatura/calculo-plano-assinatura.routes'),
  },
  {
    path: 'contrato-funcionario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.contratoFuncionario.home.title' },
    loadChildren: () => import('./contrato-funcionario/contrato-funcionario.routes'),
  },
  {
    path: 'classe-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.classeCnae.home.title' },
    loadChildren: () => import('./classe-cnae/classe-cnae.routes'),
  },
  {
    path: 'contador-responsavel-tarefa-recorrente',
    data: { pageTitle: 'erpDobemcontabilidadeApp.contadorResponsavelTarefaRecorrente.home.title' },
    loadChildren: () => import('./contador-responsavel-tarefa-recorrente/contador-responsavel-tarefa-recorrente.routes'),
  },
  {
    path: 'denuncia',
    data: { pageTitle: 'erpDobemcontabilidadeApp.denuncia.home.title' },
    loadChildren: () => import('./denuncia/denuncia.routes'),
  },
  {
    path: 'departamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.departamento.home.title' },
    loadChildren: () => import('./departamento/departamento.routes'),
  },
  {
    path: 'departamento-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.departamentoContador.home.title' },
    loadChildren: () => import('./departamento-contador/departamento-contador.routes'),
  },
  {
    path: 'departamento-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.departamentoEmpresa.home.title' },
    loadChildren: () => import('./departamento-empresa/departamento-empresa.routes'),
  },
  {
    path: 'departamento-funcionario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.departamentoFuncionario.home.title' },
    loadChildren: () => import('./departamento-funcionario/departamento-funcionario.routes'),
  },
  {
    path: 'desconto-plano-conta-azul',
    data: { pageTitle: 'erpDobemcontabilidadeApp.descontoPlanoContaAzul.home.title' },
    loadChildren: () => import('./desconto-plano-conta-azul/desconto-plano-conta-azul.routes'),
  },
  {
    path: 'desconto-plano-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.descontoPlanoContabil.home.title' },
    loadChildren: () => import('./desconto-plano-contabil/desconto-plano-contabil.routes'),
  },
  {
    path: 'dependentes-funcionario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.dependentesFuncionario.home.title' },
    loadChildren: () => import('./dependentes-funcionario/dependentes-funcionario.routes'),
  },
  {
    path: 'divisao-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.divisaoCnae.home.title' },
    loadChildren: () => import('./divisao-cnae/divisao-cnae.routes'),
  },
  {
    path: 'email',
    data: { pageTitle: 'erpDobemcontabilidadeApp.email.home.title' },
    loadChildren: () => import('./email/email.routes'),
  },
  {
    path: 'empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.empresa.home.title' },
    loadChildren: () => import('./empresa/empresa.routes'),
  },
  {
    path: 'empresa-vinculada',
    data: { pageTitle: 'erpDobemcontabilidadeApp.empresaVinculada.home.title' },
    loadChildren: () => import('./empresa-vinculada/empresa-vinculada.routes'),
  },
  {
    path: 'escolaridade',
    data: { pageTitle: 'erpDobemcontabilidadeApp.escolaridade.home.title' },
    loadChildren: () => import('./escolaridade/escolaridade.routes'),
  },
  {
    path: 'escolaridade-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.escolaridadePessoa.home.title' },
    loadChildren: () => import('./escolaridade-pessoa/escolaridade-pessoa.routes'),
  },
  {
    path: 'estrangeiro',
    data: { pageTitle: 'erpDobemcontabilidadeApp.estrangeiro.home.title' },
    loadChildren: () => import('./estrangeiro/estrangeiro.routes'),
  },
  {
    path: 'endereco-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enderecoEmpresa.home.title' },
    loadChildren: () => import('./endereco-empresa/endereco-empresa.routes'),
  },
  {
    path: 'endereco-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enderecoPessoa.home.title' },
    loadChildren: () => import('./endereco-pessoa/endereco-pessoa.routes'),
  },
  {
    path: 'enquadramento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enquadramento.home.title' },
    loadChildren: () => import('./enquadramento/enquadramento.routes'),
  },
  {
    path: 'esfera',
    data: { pageTitle: 'erpDobemcontabilidadeApp.esfera.home.title' },
    loadChildren: () => import('./esfera/esfera.routes'),
  },
  {
    path: 'estado',
    data: { pageTitle: 'erpDobemcontabilidadeApp.estado.home.title' },
    loadChildren: () => import('./estado/estado.routes'),
  },
  {
    path: 'etapa-fluxo-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.etapaFluxoExecucao.home.title' },
    loadChildren: () => import('./etapa-fluxo-execucao/etapa-fluxo-execucao.routes'),
  },
  {
    path: 'empresa-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.empresaModelo.home.title' },
    loadChildren: () => import('./empresa-modelo/empresa-modelo.routes'),
  },
  {
    path: 'feed-back-usuario-para-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.feedBackUsuarioParaContador.home.title' },
    loadChildren: () => import('./feed-back-usuario-para-contador/feed-back-usuario-para-contador.routes'),
  },
  {
    path: 'feed-back-contador-para-usuario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.feedBackContadorParaUsuario.home.title' },
    loadChildren: () => import('./feed-back-contador-para-usuario/feed-back-contador-para-usuario.routes'),
  },
  {
    path: 'funcionalidade',
    data: { pageTitle: 'erpDobemcontabilidadeApp.funcionalidade.home.title' },
    loadChildren: () => import('./funcionalidade/funcionalidade.routes'),
  },
  {
    path: 'fluxo-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.fluxoModelo.home.title' },
    loadChildren: () => import('./fluxo-modelo/fluxo-modelo.routes'),
  },
  {
    path: 'fluxo-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.fluxoExecucao.home.title' },
    loadChildren: () => import('./fluxo-execucao/fluxo-execucao.routes'),
  },
  {
    path: 'forma-de-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.formaDePagamento.home.title' },
    loadChildren: () => import('./forma-de-pagamento/forma-de-pagamento.routes'),
  },
  {
    path: 'funcionalidade-grupo-acesso-padrao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.funcionalidadeGrupoAcessoPadrao.home.title' },
    loadChildren: () => import('./funcionalidade-grupo-acesso-padrao/funcionalidade-grupo-acesso-padrao.routes'),
  },
  {
    path: 'frequencia',
    data: { pageTitle: 'erpDobemcontabilidadeApp.frequencia.home.title' },
    loadChildren: () => import('./frequencia/frequencia.routes'),
  },
  {
    path: 'funcionario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.funcionario.home.title' },
    loadChildren: () => import('./funcionario/funcionario.routes'),
  },
  {
    path: 'grupo-acesso-padrao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoAcessoPadrao.home.title' },
    loadChildren: () => import('./grupo-acesso-padrao/grupo-acesso-padrao.routes'),
  },
  {
    path: 'grupo-acesso-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoAcessoEmpresa.home.title' },
    loadChildren: () => import('./grupo-acesso-empresa/grupo-acesso-empresa.routes'),
  },
  {
    path: 'grupo-acesso-usuario-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoAcessoUsuarioEmpresa.home.title' },
    loadChildren: () => import('./grupo-acesso-usuario-empresa/grupo-acesso-usuario-empresa.routes'),
  },
  {
    path: 'grupo-acesso-empresa-usuario-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoAcessoEmpresaUsuarioContador.home.title' },
    loadChildren: () => import('./grupo-acesso-empresa-usuario-contador/grupo-acesso-empresa-usuario-contador.routes'),
  },
  {
    path: 'grupo-acesso-usuario-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoAcessoUsuarioContador.home.title' },
    loadChildren: () => import('./grupo-acesso-usuario-contador/grupo-acesso-usuario-contador.routes'),
  },
  {
    path: 'funcionalidade-grupo-acesso-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.funcionalidadeGrupoAcessoEmpresa.home.title' },
    loadChildren: () => import('./funcionalidade-grupo-acesso-empresa/funcionalidade-grupo-acesso-empresa.routes'),
  },
  {
    path: 'gateway-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.gatewayPagamento.home.title' },
    loadChildren: () => import('./gateway-pagamento/gateway-pagamento.routes'),
  },
  {
    path: 'gateway-assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.gatewayAssinaturaEmpresa.home.title' },
    loadChildren: () => import('./gateway-assinatura-empresa/gateway-assinatura-empresa.routes'),
  },
  {
    path: 'secao-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.secaoCnae.home.title' },
    loadChildren: () => import('./secao-cnae/secao-cnae.routes'),
  },
  {
    path: 'grupo-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoCnae.home.title' },
    loadChildren: () => import('./grupo-cnae/grupo-cnae.routes'),
  },
  {
    path: 'instituicao-ensino',
    data: { pageTitle: 'erpDobemcontabilidadeApp.instituicaoEnsino.home.title' },
    loadChildren: () => import('./instituicao-ensino/instituicao-ensino.routes'),
  },
  {
    path: 'observacao-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.observacaoCnae.home.title' },
    loadChildren: () => import('./observacao-cnae/observacao-cnae.routes'),
  },
  {
    path: 'opcao-razao-social-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.opcaoRazaoSocialEmpresa.home.title' },
    loadChildren: () => import('./opcao-razao-social-empresa/opcao-razao-social-empresa.routes'),
  },
  {
    path: 'opcao-nome-fantasia-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.opcaoNomeFantasiaEmpresa.home.title' },
    loadChildren: () => import('./opcao-nome-fantasia-empresa/opcao-nome-fantasia-empresa.routes'),
  },
  {
    path: 'servico-contabil-empresa-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.servicoContabilEmpresaModelo.home.title' },
    loadChildren: () => import('./servico-contabil-empresa-modelo/servico-contabil-empresa-modelo.routes'),
  },
  {
    path: 'permisao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.permisao.home.title' },
    loadChildren: () => import('./permisao/permisao.routes'),
  },
  {
    path: 'pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pagamento.home.title' },
    loadChildren: () => import('./pagamento/pagamento.routes'),
  },
  {
    path: 'pais',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pais.home.title' },
    loadChildren: () => import('./pais/pais.routes'),
  },
  {
    path: 'perfil-acesso',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilAcesso.home.title' },
    loadChildren: () => import('./perfil-acesso/perfil-acesso.routes'),
  },
  {
    path: 'perfil-acesso-usuario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilAcessoUsuario.home.title' },
    loadChildren: () => import('./perfil-acesso-usuario/perfil-acesso-usuario.routes'),
  },
  {
    path: 'perfil-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilContador.home.title' },
    loadChildren: () => import('./perfil-contador/perfil-contador.routes'),
  },
  {
    path: 'perfil-contador-departamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilContadorDepartamento.home.title' },
    loadChildren: () => import('./perfil-contador-departamento/perfil-contador-departamento.routes'),
  },
  {
    path: 'perfil-rede-social',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilRedeSocial.home.title' },
    loadChildren: () => import('./perfil-rede-social/perfil-rede-social.routes'),
  },
  {
    path: 'periodo-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.periodoPagamento.home.title' },
    loadChildren: () => import('./periodo-pagamento/periodo-pagamento.routes'),
  },
  {
    path: 'pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pessoa.home.title' },
    loadChildren: () => import('./pessoa/pessoa.routes'),
  },
  {
    path: 'plano-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.planoContabil.home.title' },
    loadChildren: () => import('./plano-contabil/plano-contabil.routes'),
  },
  {
    path: 'profissao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.profissao.home.title' },
    loadChildren: () => import('./profissao/profissao.routes'),
  },
  {
    path: 'plano-conta-azul',
    data: { pageTitle: 'erpDobemcontabilidadeApp.planoContaAzul.home.title' },
    loadChildren: () => import('./plano-conta-azul/plano-conta-azul.routes'),
  },
  {
    path: 'prazo-assinatura',
    data: { pageTitle: 'erpDobemcontabilidadeApp.prazoAssinatura.home.title' },
    loadChildren: () => import('./prazo-assinatura/prazo-assinatura.routes'),
  },
  {
    path: 'ramo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.ramo.home.title' },
    loadChildren: () => import('./ramo/ramo.routes'),
  },
  {
    path: 'subclasse-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.subclasseCnae.home.title' },
    loadChildren: () => import('./subclasse-cnae/subclasse-cnae.routes'),
  },
  {
    path: 'segmento-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.segmentoCnae.home.title' },
    loadChildren: () => import('./segmento-cnae/segmento-cnae.routes'),
  },
  {
    path: 'socio',
    data: { pageTitle: 'erpDobemcontabilidadeApp.socio.home.title' },
    loadChildren: () => import('./socio/socio.routes'),
  },
  {
    path: 'subtarefa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.subtarefa.home.title' },
    loadChildren: () => import('./subtarefa/subtarefa.routes'),
  },
  {
    path: 'tarefa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefa.home.title' },
    loadChildren: () => import('./tarefa/tarefa.routes'),
  },
  {
    path: 'tarefa-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaEmpresa.home.title' },
    loadChildren: () => import('./tarefa-empresa/tarefa-empresa.routes'),
  },
  {
    path: 'telefone',
    data: { pageTitle: 'erpDobemcontabilidadeApp.telefone.home.title' },
    loadChildren: () => import('./telefone/telefone.routes'),
  },
  {
    path: 'termo-adesao-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoAdesaoContador.home.title' },
    loadChildren: () => import('./termo-adesao-contador/termo-adesao-contador.routes'),
  },
  {
    path: 'termo-adesao-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoAdesaoEmpresa.home.title' },
    loadChildren: () => import('./termo-adesao-empresa/termo-adesao-empresa.routes'),
  },
  {
    path: 'tarefa-recorrente-empresa-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaRecorrenteEmpresaModelo.home.title' },
    loadChildren: () => import('./tarefa-recorrente-empresa-modelo/tarefa-recorrente-empresa-modelo.routes'),
  },
  {
    path: 'termo-de-adesao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoDeAdesao.home.title' },
    loadChildren: () => import('./termo-de-adesao/termo-de-adesao.routes'),
  },
  {
    path: 'tipo-denuncia',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tipoDenuncia.home.title' },
    loadChildren: () => import('./tipo-denuncia/tipo-denuncia.routes'),
  },
  {
    path: 'tributacao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tributacao.home.title' },
    loadChildren: () => import('./tributacao/tributacao.routes'),
  },
  {
    path: 'usuario-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.usuarioContador.home.title' },
    loadChildren: () => import('./usuario-contador/usuario-contador.routes'),
  },
  {
    path: 'usuario-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.usuarioEmpresa.home.title' },
    loadChildren: () => import('./usuario-empresa/usuario-empresa.routes'),
  },
  {
    path: 'usuario-gestao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.usuarioGestao.home.title' },
    loadChildren: () => import('./usuario-gestao/usuario-gestao.routes'),
  },
  {
    path: 'valor-base-ramo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.valorBaseRamo.home.title' },
    loadChildren: () => import('./valor-base-ramo/valor-base-ramo.routes'),
  },
  {
    path: 'demissao-funcionario',
    data: { pageTitle: 'erpDobemcontabilidadeApp.demissaoFuncionario.home.title' },
    loadChildren: () => import('./demissao-funcionario/demissao-funcionario.routes'),
  },
  {
    path: 'imposto',
    data: { pageTitle: 'erpDobemcontabilidadeApp.imposto.home.title' },
    loadChildren: () => import('./imposto/imposto.routes'),
  },
  {
    path: 'modulo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.modulo.home.title' },
    loadChildren: () => import('./modulo/modulo.routes'),
  },
  {
    path: 'ordem-servico',
    data: { pageTitle: 'erpDobemcontabilidadeApp.ordemServico.home.title' },
    loadChildren: () => import('./ordem-servico/ordem-servico.routes'),
  },
  {
    path: 'sistema',
    data: { pageTitle: 'erpDobemcontabilidadeApp.sistema.home.title' },
    loadChildren: () => import('./sistema/sistema.routes'),
  },
  {
    path: 'servico-contabil-ordem-servico',
    data: { pageTitle: 'erpDobemcontabilidadeApp.servicoContabilOrdemServico.home.title' },
    loadChildren: () => import('./servico-contabil-ordem-servico/servico-contabil-ordem-servico.routes'),
  },
  {
    path: 'parcelamento-imposto',
    data: { pageTitle: 'erpDobemcontabilidadeApp.parcelamentoImposto.home.title' },
    loadChildren: () => import('./parcelamento-imposto/parcelamento-imposto.routes'),
  },
  {
    path: 'parcela-imposto-a-pagar',
    data: { pageTitle: 'erpDobemcontabilidadeApp.parcelaImpostoAPagar.home.title' },
    loadChildren: () => import('./parcela-imposto-a-pagar/parcela-imposto-a-pagar.routes'),
  },
  {
    path: 'imposto-a-pagar-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.impostoAPagarEmpresa.home.title' },
    loadChildren: () => import('./imposto-a-pagar-empresa/imposto-a-pagar-empresa.routes'),
  },
  {
    path: 'imposto-parcelado',
    data: { pageTitle: 'erpDobemcontabilidadeApp.impostoParcelado.home.title' },
    loadChildren: () => import('./imposto-parcelado/imposto-parcelado.routes'),
  },
  {
    path: 'servico-contabil-etapa-fluxo-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.servicoContabilEtapaFluxoModelo.home.title' },
    loadChildren: () => import('./servico-contabil-etapa-fluxo-modelo/servico-contabil-etapa-fluxo-modelo.routes'),
  },
  {
    path: 'etapa-fluxo-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.etapaFluxoModelo.home.title' },
    loadChildren: () => import('./etapa-fluxo-modelo/etapa-fluxo-modelo.routes'),
  },
  {
    path: 'imposto-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.impostoEmpresa.home.title' },
    loadChildren: () => import('./imposto-empresa/imposto-empresa.routes'),
  },
  {
    path: 'sub-tarefa-ordem-servico',
    data: { pageTitle: 'erpDobemcontabilidadeApp.subTarefaOrdemServico.home.title' },
    loadChildren: () => import('./sub-tarefa-ordem-servico/sub-tarefa-ordem-servico.routes'),
  },
  {
    path: 'tarefa-recorrente',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaRecorrente.home.title' },
    loadChildren: () => import('./tarefa-recorrente/tarefa-recorrente.routes'),
  },
  {
    path: 'tarefa-recorrente-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaRecorrenteExecucao.home.title' },
    loadChildren: () => import('./tarefa-recorrente-execucao/tarefa-recorrente-execucao.routes'),
  },
  {
    path: 'tarefa-ordem-servico',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaOrdemServico.home.title' },
    loadChildren: () => import('./tarefa-ordem-servico/tarefa-ordem-servico.routes'),
  },
  {
    path: 'tarefa-ordem-servico-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaOrdemServicoExecucao.home.title' },
    loadChildren: () => import('./tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.routes'),
  },
  {
    path: 'termo-contrato-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoContratoContabil.home.title' },
    loadChildren: () => import('./termo-contrato-contabil/termo-contrato-contabil.routes'),
  },
  {
    path: 'sub-tarefa-recorrente',
    data: { pageTitle: 'erpDobemcontabilidadeApp.subTarefaRecorrente.home.title' },
    loadChildren: () => import('./sub-tarefa-recorrente/sub-tarefa-recorrente.routes'),
  },
  {
    path: 'tarefa-empresa-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tarefaEmpresaModelo.home.title' },
    loadChildren: () => import('./tarefa-empresa-modelo/tarefa-empresa-modelo.routes'),
  },
  {
    path: 'servico-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.servicoContabil.home.title' },
    loadChildren: () => import('./servico-contabil/servico-contabil.routes'),
  },
  {
    path: 'servico-contabil-etapa-fluxo-execucao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.servicoContabilEtapaFluxoExecucao.home.title' },
    loadChildren: () => import('./servico-contabil-etapa-fluxo-execucao/servico-contabil-etapa-fluxo-execucao.routes'),
  },
  {
    path: 'servico-contabil-assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.servicoContabilAssinaturaEmpresa.home.title' },
    loadChildren: () => import('./servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.routes'),
  },
  {
    path: 'imposto-empresa-modelo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.impostoEmpresaModelo.home.title' },
    loadChildren: () => import('./imposto-empresa-modelo/imposto-empresa-modelo.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
