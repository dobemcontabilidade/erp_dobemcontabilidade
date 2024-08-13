import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';
import { GrupoAcessoPadraoService } from '../service/grupo-acesso-padrao.service';

const grupoAcessoPadraoResolve = (route: ActivatedRouteSnapshot): Observable<null | IGrupoAcessoPadrao> => {
  const id = route.params['id'];
  if (id) {
    return inject(GrupoAcessoPadraoService)
      .find(id)
      .pipe(
        mergeMap((grupoAcessoPadrao: HttpResponse<IGrupoAcessoPadrao>) => {
          if (grupoAcessoPadrao.body) {
            return of(grupoAcessoPadrao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default grupoAcessoPadraoResolve;
