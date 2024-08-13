import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ObservacaoCnaeComponent } from './list/observacao-cnae.component';
import { ObservacaoCnaeDetailComponent } from './detail/observacao-cnae-detail.component';
import { ObservacaoCnaeUpdateComponent } from './update/observacao-cnae-update.component';
import ObservacaoCnaeResolve from './route/observacao-cnae-routing-resolve.service';

const observacaoCnaeRoute: Routes = [
  {
    path: '',
    component: ObservacaoCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ObservacaoCnaeDetailComponent,
    resolve: {
      observacaoCnae: ObservacaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ObservacaoCnaeUpdateComponent,
    resolve: {
      observacaoCnae: ObservacaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ObservacaoCnaeUpdateComponent,
    resolve: {
      observacaoCnae: ObservacaoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default observacaoCnaeRoute;
