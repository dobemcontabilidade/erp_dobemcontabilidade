import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';
import { PerfilAcessoUsuarioService } from '../service/perfil-acesso-usuario.service';

const perfilAcessoUsuarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IPerfilAcessoUsuario> => {
  const id = route.params['id'];
  if (id) {
    return inject(PerfilAcessoUsuarioService)
      .find(id)
      .pipe(
        mergeMap((perfilAcessoUsuario: HttpResponse<IPerfilAcessoUsuario>) => {
          if (perfilAcessoUsuario.body) {
            return of(perfilAcessoUsuario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default perfilAcessoUsuarioResolve;
