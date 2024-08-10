import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import { AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';

const areaContabilEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAreaContabilEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AreaContabilEmpresaService)
      .find(id)
      .pipe(
        mergeMap((areaContabilEmpresa: HttpResponse<IAreaContabilEmpresa>) => {
          if (areaContabilEmpresa.body) {
            return of(areaContabilEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default areaContabilEmpresaResolve;
