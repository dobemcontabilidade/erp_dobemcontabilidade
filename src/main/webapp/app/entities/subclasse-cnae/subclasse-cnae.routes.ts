import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SubclasseCnaeComponent } from './list/subclasse-cnae.component';
import { SubclasseCnaeDetailComponent } from './detail/subclasse-cnae-detail.component';
import { SubclasseCnaeUpdateComponent } from './update/subclasse-cnae-update.component';
import SubclasseCnaeResolve from './route/subclasse-cnae-routing-resolve.service';

const subclasseCnaeRoute: Routes = [
  {
    path: '',
    component: SubclasseCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubclasseCnaeDetailComponent,
    resolve: {
      subclasseCnae: SubclasseCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubclasseCnaeUpdateComponent,
    resolve: {
      subclasseCnae: SubclasseCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubclasseCnaeUpdateComponent,
    resolve: {
      subclasseCnae: SubclasseCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default subclasseCnaeRoute;
