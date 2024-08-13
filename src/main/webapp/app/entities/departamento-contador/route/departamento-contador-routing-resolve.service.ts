import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDepartamentoContador } from '../departamento-contador.model';
import { DepartamentoContadorService } from '../service/departamento-contador.service';

const departamentoContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IDepartamentoContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(DepartamentoContadorService)
      .find(id)
      .pipe(
        mergeMap((departamentoContador: HttpResponse<IDepartamentoContador>) => {
          if (departamentoContador.body) {
            return of(departamentoContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default departamentoContadorResolve;
