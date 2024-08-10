import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { SegmentoCnaeComponent } from './list/segmento-cnae.component';
import { SegmentoCnaeDetailComponent } from './detail/segmento-cnae-detail.component';
import { SegmentoCnaeUpdateComponent } from './update/segmento-cnae-update.component';
import SegmentoCnaeResolve from './route/segmento-cnae-routing-resolve.service';

const segmentoCnaeRoute: Routes = [
  {
    path: '',
    component: SegmentoCnaeComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SegmentoCnaeDetailComponent,
    resolve: {
      segmentoCnae: SegmentoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SegmentoCnaeUpdateComponent,
    resolve: {
      segmentoCnae: SegmentoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SegmentoCnaeUpdateComponent,
    resolve: {
      segmentoCnae: SegmentoCnaeResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default segmentoCnaeRoute;
