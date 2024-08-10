import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPerfilContador } from '../perfil-contador.model';
import { PerfilContadorService } from '../service/perfil-contador.service';

const perfilContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IPerfilContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(PerfilContadorService)
      .find(id)
      .pipe(
        mergeMap((perfilContador: HttpResponse<IPerfilContador>) => {
          if (perfilContador.body) {
            return of(perfilContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default perfilContadorResolve;
