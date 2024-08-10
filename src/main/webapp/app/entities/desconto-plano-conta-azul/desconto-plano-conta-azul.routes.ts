import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DescontoPlanoContaAzulComponent } from './list/desconto-plano-conta-azul.component';
import { DescontoPlanoContaAzulDetailComponent } from './detail/desconto-plano-conta-azul-detail.component';
import { DescontoPlanoContaAzulUpdateComponent } from './update/desconto-plano-conta-azul-update.component';
import DescontoPlanoContaAzulResolve from './route/desconto-plano-conta-azul-routing-resolve.service';

const descontoPlanoContaAzulRoute: Routes = [
  {
    path: '',
    component: DescontoPlanoContaAzulComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DescontoPlanoContaAzulDetailComponent,
    resolve: {
      descontoPlanoContaAzul: DescontoPlanoContaAzulResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DescontoPlanoContaAzulUpdateComponent,
    resolve: {
      descontoPlanoContaAzul: DescontoPlanoContaAzulResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DescontoPlanoContaAzulUpdateComponent,
    resolve: {
      descontoPlanoContaAzul: DescontoPlanoContaAzulResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default descontoPlanoContaAzulRoute;
