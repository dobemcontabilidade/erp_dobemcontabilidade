import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';
import { GrupoAcessoEmpresaService } from '../service/grupo-acesso-empresa.service';

const grupoAcessoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoAcessoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(GrupoAcessoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((grupoAcessoEmpresa: HttpResponse<IGrupoAcessoEmpresa>) => {
          if (grupoAcessoEmpresa.body) {
            return of(grupoAcessoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default grupoAcessoEmpresaResolve;
