import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'erpDobemcontabilidadeApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'contador',
    data: { pageTitle: 'erpDobemcontabilidadeApp.contador.home.title' },
    loadChildren: () => import('./contador/contador.routes'),
  },
  {
    path: 'pessoajuridica',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pessoajuridica.home.title' },
    loadChildren: () => import('./pessoajuridica/pessoajuridica.routes'),
  },
  {
    path: 'empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.empresa.home.title' },
    loadChildren: () => import('./empresa/empresa.routes'),
  },
  {
    path: 'socio',
    data: { pageTitle: 'erpDobemcontabilidadeApp.socio.home.title' },
    loadChildren: () => import('./socio/socio.routes'),
  },
  {
    path: 'certificado-digital',
    data: { pageTitle: 'erpDobemcontabilidadeApp.certificadoDigital.home.title' },
    loadChildren: () => import('./certificado-digital/certificado-digital.routes'),
  },
  {
    path: 'certificado-digital-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.certificadoDigitalEmpresa.home.title' },
    loadChildren: () => import('./certificado-digital-empresa/certificado-digital-empresa.routes'),
  },
  {
    path: 'fornecedor-certificado',
    data: { pageTitle: 'erpDobemcontabilidadeApp.fornecedorCertificado.home.title' },
    loadChildren: () => import('./fornecedor-certificado/fornecedor-certificado.routes'),
  },
  {
    path: 'docs-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.docsEmpresa.home.title' },
    loadChildren: () => import('./docs-empresa/docs-empresa.routes'),
  },
  {
    path: 'rede-social',
    data: { pageTitle: 'erpDobemcontabilidadeApp.redeSocial.home.title' },
    loadChildren: () => import('./rede-social/rede-social.routes'),
  },
  {
    path: 'rede-social-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.redeSocialEmpresa.home.title' },
    loadChildren: () => import('./rede-social-empresa/rede-social-empresa.routes'),
  },
  {
    path: 'pessoa-fisica',
    data: { pageTitle: 'erpDobemcontabilidadeApp.pessoaFisica.home.title' },
    loadChildren: () => import('./pessoa-fisica/pessoa-fisica.routes'),
  },
  {
    path: 'assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.assinaturaEmpresa.home.title' },
    loadChildren: () => import('./assinatura-empresa/assinatura-empresa.routes'),
  },
  {
    path: 'endereco-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enderecoEmpresa.home.title' },
    loadChildren: () => import('./endereco-empresa/endereco-empresa.routes'),
  },
  {
    path: 'ramo',
    data: { pageTitle: 'erpDobemcontabilidadeApp.ramo.home.title' },
    loadChildren: () => import('./ramo/ramo.routes'),
  },
  {
    path: 'tributacao',
    data: { pageTitle: 'erpDobemcontabilidadeApp.tributacao.home.title' },
    loadChildren: () => import('./tributacao/tributacao.routes'),
  },
  {
    path: 'enquadramento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enquadramento.home.title' },
    loadChildren: () => import('./enquadramento/enquadramento.routes'),
  },
  {
    path: 'forma-de-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.formaDePagamento.home.title' },
    loadChildren: () => import('./forma-de-pagamento/forma-de-pagamento.routes'),
  },
  {
    path: 'termo-contrato-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoContratoContabil.home.title' },
    loadChildren: () => import('./termo-contrato-contabil/termo-contrato-contabil.routes'),
  },
  {
    path: 'termo-contrato-assinatura-empresa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.termoContratoAssinaturaEmpresa.home.title' },
    loadChildren: () => import('./termo-contrato-assinatura-empresa/termo-contrato-assinatura-empresa.routes'),
  },
  {
    path: 'periodo-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.periodoPagamento.home.title' },
    loadChildren: () => import('./periodo-pagamento/periodo-pagamento.routes'),
  },
  {
    path: 'desconto-periodo-pagamento',
    data: { pageTitle: 'erpDobemcontabilidadeApp.descontoPeriodoPagamento.home.title' },
    loadChildren: () => import('./desconto-periodo-pagamento/desconto-periodo-pagamento.routes'),
  },
  {
    path: 'plano-assinatura-contabil',
    data: { pageTitle: 'erpDobemcontabilidadeApp.planoAssinaturaContabil.home.title' },
    loadChildren: () => import('./plano-assinatura-contabil/plano-assinatura-contabil.routes'),
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
    path: 'endereco-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.enderecoPessoa.home.title' },
    loadChildren: () => import('./endereco-pessoa/endereco-pessoa.routes'),
  },
  {
    path: 'email',
    data: { pageTitle: 'erpDobemcontabilidadeApp.email.home.title' },
    loadChildren: () => import('./email/email.routes'),
  },
  {
    path: 'telefone',
    data: { pageTitle: 'erpDobemcontabilidadeApp.telefone.home.title' },
    loadChildren: () => import('./telefone/telefone.routes'),
  },
  {
    path: 'docs-pessoa',
    data: { pageTitle: 'erpDobemcontabilidadeApp.docsPessoa.home.title' },
    loadChildren: () => import('./docs-pessoa/docs-pessoa.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
