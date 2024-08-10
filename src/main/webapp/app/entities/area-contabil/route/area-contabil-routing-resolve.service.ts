import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaContabil } from '../area-contabil.model';
import { AreaContabilService } from '../service/area-contabil.service';

const areaContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IAreaContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(AreaContabilService)
      .find(id)
      .pipe(
        mergeMap((areaContabil: HttpResponse<IAreaContabil>) => {
          if (areaContabil.body) {
            return of(areaContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default areaContabilResolve;
