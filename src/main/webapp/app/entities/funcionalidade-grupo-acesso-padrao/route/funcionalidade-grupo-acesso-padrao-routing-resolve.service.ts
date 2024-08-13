import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';
import { FuncionalidadeGrupoAcessoPadraoService } from '../service/funcionalidade-grupo-acesso-padrao.service';

const funcionalidadeGrupoAcessoPadraoResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuncionalidadeGrupoAcessoPadrao> => {
  const id = route.params['id'];
  if (id) {
    return inject(FuncionalidadeGrupoAcessoPadraoService)
      .find(id)
      .pipe(
        mergeMap((funcionalidadeGrupoAcessoPadrao: HttpResponse<IFuncionalidadeGrupoAcessoPadrao>) => {
          if (funcionalidadeGrupoAcessoPadrao.body) {
            return of(funcionalidadeGrupoAcessoPadrao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default funcionalidadeGrupoAcessoPadraoResolve;
