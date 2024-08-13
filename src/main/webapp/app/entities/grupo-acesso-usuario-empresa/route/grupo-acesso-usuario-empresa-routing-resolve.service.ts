import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';
import { GrupoAcessoUsuarioEmpresaService } from '../service/grupo-acesso-usuario-empresa.service';

const grupoAcessoUsuarioEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoAcessoUsuarioEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(GrupoAcessoUsuarioEmpresaService)
      .find(id)
      .pipe(
        mergeMap((grupoAcessoUsuarioEmpresa: HttpResponse<IGrupoAcessoUsuarioEmpresa>) => {
          if (grupoAcessoUsuarioEmpresa.body) {
            return of(grupoAcessoUsuarioEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default grupoAcessoUsuarioEmpresaResolve;
