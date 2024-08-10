import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdicionalRamo } from '../adicional-ramo.model';
import { AdicionalRamoService } from '../service/adicional-ramo.service';

const adicionalRamoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAdicionalRamo> => {
  const id = route.params['id'];
  if (id) {
    return inject(AdicionalRamoService)
      .find(id)
      .pipe(
        mergeMap((adicionalRamo: HttpResponse<IAdicionalRamo>) => {
          if (adicionalRamo.body) {
            return of(adicionalRamo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default adicionalRamoResolve;
