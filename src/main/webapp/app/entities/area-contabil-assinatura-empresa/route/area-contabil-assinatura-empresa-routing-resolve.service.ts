import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';
import { AreaContabilAssinaturaEmpresaService } from '../service/area-contabil-assinatura-empresa.service';

const areaContabilAssinaturaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAreaContabilAssinaturaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AreaContabilAssinaturaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((areaContabilAssinaturaEmpresa: HttpResponse<IAreaContabilAssinaturaEmpresa>) => {
          if (areaContabilAssinaturaEmpresa.body) {
            return of(areaContabilAssinaturaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default areaContabilAssinaturaEmpresaResolve;
