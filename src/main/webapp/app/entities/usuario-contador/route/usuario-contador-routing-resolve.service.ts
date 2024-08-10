import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUsuarioContador } from '../usuario-contador.model';
import { UsuarioContadorService } from '../service/usuario-contador.service';

const usuarioContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IUsuarioContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(UsuarioContadorService)
      .find(id)
      .pipe(
        mergeMap((usuarioContador: HttpResponse<IUsuarioContador>) => {
          if (usuarioContador.body) {
            return of(usuarioContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default usuarioContadorResolve;
