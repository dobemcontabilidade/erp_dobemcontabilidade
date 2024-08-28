import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from '../service/plano-assinatura-contabil.service';

const planoAssinaturaContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IPlanoAssinaturaContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(PlanoAssinaturaContabilService)
      .find(id)
      .pipe(
        mergeMap((planoAssinaturaContabil: HttpResponse<IPlanoAssinaturaContabil>) => {
          if (planoAssinaturaContabil.body) {
            return of(planoAssinaturaContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default planoAssinaturaContabilResolve;
