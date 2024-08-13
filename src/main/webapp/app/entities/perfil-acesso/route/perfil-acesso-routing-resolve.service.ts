import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPerfilAcesso } from '../perfil-acesso.model';
import { PerfilAcessoService } from '../service/perfil-acesso.service';

const perfilAcessoResolve = (route: ActivatedRouteSnapshot): Observable<null | IPerfilAcesso> => {
  const id = route.params['id'];
  if (id) {
    return inject(PerfilAcessoService)
      .find(id)
      .pipe(
        mergeMap((perfilAcesso: HttpResponse<IPerfilAcesso>) => {
          if (perfilAcesso.body) {
            return of(perfilAcesso.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default perfilAcessoResolve;
