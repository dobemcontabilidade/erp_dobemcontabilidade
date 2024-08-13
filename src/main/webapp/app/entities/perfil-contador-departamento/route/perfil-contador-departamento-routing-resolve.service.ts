import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPerfilContadorDepartamento } from '../perfil-contador-departamento.model';
import { PerfilContadorDepartamentoService } from '../service/perfil-contador-departamento.service';

const perfilContadorDepartamentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IPerfilContadorDepartamento> => {
  const id = route.params['id'];
  if (id) {
    return inject(PerfilContadorDepartamentoService)
      .find(id)
      .pipe(
        mergeMap((perfilContadorDepartamento: HttpResponse<IPerfilContadorDepartamento>) => {
          if (perfilContadorDepartamento.body) {
            return of(perfilContadorDepartamento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default perfilContadorDepartamentoResolve;
