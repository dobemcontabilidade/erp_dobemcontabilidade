import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IValorBaseRamo } from '../valor-base-ramo.model';
import { ValorBaseRamoService } from '../service/valor-base-ramo.service';

const valorBaseRamoResolve = (route: ActivatedRouteSnapshot): Observable<null | IValorBaseRamo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ValorBaseRamoService)
      .find(id)
      .pipe(
        mergeMap((valorBaseRamo: HttpResponse<IValorBaseRamo>) => {
          if (valorBaseRamo.body) {
            return of(valorBaseRamo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default valorBaseRamoResolve;
