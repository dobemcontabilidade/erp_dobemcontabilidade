import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IUsuarioGestao } from '../usuario-gestao.model';
import { UsuarioGestaoService } from '../service/usuario-gestao.service';

const usuarioGestaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IUsuarioGestao> => {
  const id = route.params['id'];
  if (id) {
    return inject(UsuarioGestaoService)
      .find(id)
      .pipe(
        mergeMap((usuarioGestao: HttpResponse<IUsuarioGestao>) => {
          if (usuarioGestao.body) {
            return of(usuarioGestao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default usuarioGestaoResolve;
