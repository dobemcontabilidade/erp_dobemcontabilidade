import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRamo } from '../ramo.model';
import { RamoService } from '../service/ramo.service';

const ramoResolve = (route: ActivatedRouteSnapshot): Observable<null | IRamo> => {
  const id = route.params['id'];
  if (id) {
    return inject(RamoService)
      .find(id)
      .pipe(
        mergeMap((ramo: HttpResponse<IRamo>) => {
          if (ramo.body) {
            return of(ramo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default ramoResolve;
