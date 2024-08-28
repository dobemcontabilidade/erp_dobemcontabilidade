import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PessoajuridicaComponent } from './list/pessoajuridica.component';
import { PessoajuridicaDetailComponent } from './detail/pessoajuridica-detail.component';
import { PessoajuridicaUpdateComponent } from './update/pessoajuridica-update.component';
import PessoajuridicaResolve from './route/pessoajuridica-routing-resolve.service';

const pessoajuridicaRoute: Routes = [
  {
    path: '',
    component: PessoajuridicaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PessoajuridicaDetailComponent,
    resolve: {
      pessoajuridica: PessoajuridicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PessoajuridicaUpdateComponent,
    resolve: {
      pessoajuridica: PessoajuridicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PessoajuridicaUpdateComponent,
    resolve: {
      pessoajuridica: PessoajuridicaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pessoajuridicaRoute;
