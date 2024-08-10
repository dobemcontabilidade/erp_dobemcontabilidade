import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ClasseCnaeComponent } from './list/classe-cnae.component';
import { ClasseCnaeDetailComponent } from './detail/classe-cnae-detail.component';
import { ClasseCnaeUpdateComponent } from './update/classe-cnae-update.component';
import ClasseCnaeResolve from './route/classe-cnae-routing-resolve.service';

const classeCnaeRoute: Routes = [
  {
    path: '',
    component: ClasseCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClasseCnaeDetailComponent,
    resolve: {
      classeCnae: ClasseCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClasseCnaeUpdateComponent,
    resolve: {
      classeCnae: ClasseCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClasseCnaeUpdateComponent,
    resolve: {
      classeCnae: ClasseCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default classeCnaeRoute;
