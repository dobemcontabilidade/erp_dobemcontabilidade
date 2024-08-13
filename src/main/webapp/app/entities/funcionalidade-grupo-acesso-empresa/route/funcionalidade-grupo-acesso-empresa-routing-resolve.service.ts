import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';
import { FuncionalidadeGrupoAcessoEmpresaService } from '../service/funcionalidade-grupo-acesso-empresa.service';

const funcionalidadeGrupoAcessoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuncionalidadeGrupoAcessoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(FuncionalidadeGrupoAcessoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((funcionalidadeGrupoAcessoEmpresa: HttpResponse<IFuncionalidadeGrupoAcessoEmpresa>) => {
          if (funcionalidadeGrupoAcessoEmpresa.body) {
            return of(funcionalidadeGrupoAcessoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default funcionalidadeGrupoAcessoEmpresaResolve;
