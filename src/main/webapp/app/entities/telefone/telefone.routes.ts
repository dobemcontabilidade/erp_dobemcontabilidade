import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TelefoneComponent } from './list/telefone.component';
import { TelefoneDetailComponent } from './detail/telefone-detail.component';
import { TelefoneUpdateComponent } from './update/telefone-update.component';
import TelefoneResolve from './route/telefone-routing-resolve.service';

const telefoneRoute: Routes = [
  {
    path: '',
    component: TelefoneComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TelefoneDetailComponent,
    resolve: {
      telefone: TelefoneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TelefoneUpdateComponent,
    resolve: {
      telefone: TelefoneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TelefoneUpdateComponent,
    resolve: {
      telefone: TelefoneResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default telefoneRoute;
