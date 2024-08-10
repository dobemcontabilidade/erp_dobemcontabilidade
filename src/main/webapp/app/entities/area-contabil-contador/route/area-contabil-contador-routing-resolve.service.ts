import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaContabilContador } from '../area-contabil-contador.model';
import { AreaContabilContadorService } from '../service/area-contabil-contador.service';

const areaContabilContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IAreaContabilContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(AreaContabilContadorService)
      .find(id)
      .pipe(
        mergeMap((areaContabilContador: HttpResponse<IAreaContabilContador>) => {
          if (areaContabilContador.body) {
            return of(areaContabilContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default areaContabilContadorResolve;
