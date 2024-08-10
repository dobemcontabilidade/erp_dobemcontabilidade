import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'pais',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pais.home.title' },
    loadChildren: () => import('./pais/pais.routes'),
  },
  {
    path: 'estado',
    data: { pageTitle: 'erpDobemcontabilidadeApp.estado.home.title' },
    loadChildren: () => import('./estado/estado.routes'),
  },
  {
    path: 'cidade',
    data: { pageTitle: 'erpDobemcontabilidadeApp.cidade.home.title' },
    loadChildren: () => import('./cidade/cidade.routes'),
  },
  {
    path: 'banco',
    data: { pageTitle: 'erpDobemcontabilidadeApp.banco.home.title' },
    loadChildren: () => import('./banco/banco.routes'),
  },
  {
    path: 'secao-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.secaoCnae.home.title' },
    loadChildren: () => import('./secao-cnae/secao-cnae.routes'),
  },
  {
    path: 'divisao-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.divisaoCnae.home.title' },
    loadChildren: () => import('./divisao-cnae/divisao-cnae.routes'),
  },
  {
    path: 'grupo-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.grupoCnae.home.title' },
    loadChildren: () => import('./grupo-cnae/grupo-cnae.routes'),
  },
  {
    path: 'classe-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.classeCnae.home.title' },
    loadChildren: () => import('./classe-cnae/classe-cnae.routes'),
  },
  {
    path: 'subclasse-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.subclasseCnae.home.title' },
    loadChildren: () => import('./subclasse-cnae/subclasse-cnae.routes'),
  },
  {
    path: 'observacao-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.observacaoCnae.home.title' },
    loadChildren: () => import('./observacao-cnae/observacao-cnae.routes'),
  },
  {
    path: 'endereco-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enderecoPessoa.home.title' },
    loadChildren: () => import('./endereco-pessoa/endereco-pessoa.routes'),
  },
  {
    path: 'usuario-erp',
    data: { pageTitle: 'erpDobemcontabilidadeApp.usuarioErp.home.title' },
    loadChildren: () => import('./usuario-erp/usuario-erp.routes'),
  },
  {
    path: 'segmento-cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.segmentoCnae.home.title' },
    loadChildren: () => import('./segmento-cnae/segmento-cnae.routes'),
  },
  {
    path: 'cnae',
    data: { pageTitle: 'erpDobemcontabilidadeApp.cnae.home.title' },
    loadChildren: () => import('./cnae/cnae.routes'),
  },
  {
    path: 'adicional-enquadramento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adicionalEnquadramento.home.title' },
    loadChildren: () => import('./adicional-enquadramento/adicional-enquadramento.routes'),
  },
  {
    path: 'adicional-ramo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adicionalRamo.home.title' },
    loadChildren: () => import('./adicional-ramo/adicional-ramo.routes'),
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
    path: 'anexo-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoEmpresa.home.title' },
    loadChildren: () => import('./anexo-empresa/anexo-empresa.routes'),
  },
  {
    path: 'assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.assinaturaEmpresa.home.title' },
    loadChildren: () => import('./assinatura-empresa/assinatura-empresa.routes'),
  },
  {
    path: 'atividade-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.atividadeEmpresa.home.title' },
    loadChildren: () => import('./atividade-empresa/atividade-empresa.routes'),
  },
  {
    path: 'avaliacao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.avaliacao.home.title' },
    loadChildren: () => import('./avaliacao/avaliacao.routes'),
  },
  {
    path: 'avaliacao-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.avaliacaoContador.home.title' },
    loadChildren: () => import('./avaliacao-contador/avaliacao-contador.routes'),
  },
  {
    path: 'area-contabil-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.areaContabilEmpresa.home.title' },
    loadChildren: () => import('./area-contabil-empresa/area-contabil-empresa.routes'),
  },
  {
    path: 'banco-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.bancoContador.home.title' },
    loadChildren: () => import('./banco-contador/banco-contador.routes'),
  },
  {
    path: 'certificado-digital',
    data: { pageTitle: 'erpDobemcontabilidadeApp.certificadoDigital.home.title' },
    loadChildren: () => import('./certificado-digital/certificado-digital.routes'),
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
    path: 'area-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.areaContabil.home.title' },
    loadChildren: () => import('./area-contabil/area-contabil.routes'),
  },
  {
    path: 'area-contabil-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.areaContabilContador.home.title' },
    loadChildren: () => import('./area-contabil-contador/area-contabil-contador.routes'),
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
    path: 'endereco-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enderecoEmpresa.home.title' },
    loadChildren: () => import('./endereco-empresa/endereco-empresa.routes'),
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
    path: 'forma-de-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.formaDePagamento.home.title' },
    loadChildren: () => import('./forma-de-pagamento/forma-de-pagamento.routes'),
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
    path: 'pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pagamento.home.title' },
    loadChildren: () => import('./pagamento/pagamento.routes'),
  },
  {
    path: 'perfil-contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilContador.home.title' },
    loadChildren: () => import('./perfil-contador/perfil-contador.routes'),
  },
  {
    path: 'perfil-contador-area-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.perfilContadorAreaContabil.home.title' },
    loadChildren: () => import('./perfil-contador-area-contabil/perfil-contador-area-contabil.routes'),
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
    path: 'profissao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.profissao.home.title' },
    loadChildren: () => import('./profissao/profissao.routes'),
  },
  {
    path: 'ramo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.ramo.home.title' },
    loadChildren: () => import('./ramo/ramo.routes'),
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
    path: 'documento-tarefa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.documentoTarefa.home.title' },
    loadChildren: () => import('./documento-tarefa/documento-tarefa.routes'),
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
    path: 'termo-contrato-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoContratoContabil.home.title' },
    loadChildren: () => import('./termo-contrato-contabil/termo-contrato-contabil.routes'),
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
    path: 'calculo-plano-assinatura',
    data: { pageTitle: 'erpDobemcontabilidadeApp.calculoPlanoAssinatura.home.title' },
    loadChildren: () => import('./calculo-plano-assinatura/calculo-plano-assinatura.routes'),
  },
  {
    path: 'anexo-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.anexoPessoa.home.title' },
    loadChildren: () => import('./anexo-pessoa/anexo-pessoa.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
