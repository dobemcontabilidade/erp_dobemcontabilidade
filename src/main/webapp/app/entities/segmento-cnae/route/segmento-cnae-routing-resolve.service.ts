import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISegmentoCnae } from '../segmento-cnae.model';
import { SegmentoCnaeService } from '../service/segmento-cnae.service';

const segmentoCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | ISegmentoCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(SegmentoCnaeService)
      .find(id)
      .pipe(
        mergeMap((segmentoCnae: HttpResponse<ISegmentoCnae>) => {
          if (segmentoCnae.body) {
            return of(segmentoCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default segmentoCnaeResolve;
