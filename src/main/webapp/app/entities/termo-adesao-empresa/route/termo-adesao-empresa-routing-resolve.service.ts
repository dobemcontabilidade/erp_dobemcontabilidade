import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITermoAdesaoEmpresa } from '../termo-adesao-empresa.model';
import { TermoAdesaoEmpresaService } from '../service/termo-adesao-empresa.service';

const termoAdesaoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | ITermoAdesaoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(TermoAdesaoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((termoAdesaoEmpresa: HttpResponse<ITermoAdesaoEmpresa>) => {
          if (termoAdesaoEmpresa.body) {
            return of(termoAdesaoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default termoAdesaoEmpresaResolve;
