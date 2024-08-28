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
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
