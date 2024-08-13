import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';
import { GrupoAcessoUsuarioContadorService } from '../service/grupo-acesso-usuario-contador.service';

const grupoAcessoUsuarioContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoAcessoUsuarioContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(GrupoAcessoUsuarioContadorService)
      .find(id)
      .pipe(
        mergeMap((grupoAcessoUsuarioContador: HttpResponse<IGrupoAcessoUsuarioContador>) => {
          if (grupoAcessoUsuarioContador.body) {
            return of(grupoAcessoUsuarioContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default grupoAcessoUsuarioContadorResolve;
