import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';
import { GrupoAcessoEmpresaUsuarioContadorService } from '../service/grupo-acesso-empresa-usuario-contador.service';

const grupoAcessoEmpresaUsuarioContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoAcessoEmpresaUsuarioContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(GrupoAcessoEmpresaUsuarioContadorService)
      .find(id)
      .pipe(
        mergeMap((grupoAcessoEmpresaUsuarioContador: HttpResponse<IGrupoAcessoEmpresaUsuarioContador>) => {
          if (grupoAcessoEmpresaUsuarioContador.body) {
            return of(grupoAcessoEmpresaUsuarioContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default grupoAcessoEmpresaUsuarioContadorResolve;
