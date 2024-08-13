import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDepartamentoEmpresa } from '../departamento-empresa.model';
import { DepartamentoEmpresaService } from '../service/departamento-empresa.service';

const departamentoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IDepartamentoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(DepartamentoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((departamentoEmpresa: HttpResponse<IDepartamentoEmpresa>) => {
          if (departamentoEmpresa.body) {
            return of(departamentoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default departamentoEmpresaResolve;
