import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';
import { CalculoPlanoAssinaturaService } from '../service/calculo-plano-assinatura.service';

const calculoPlanoAssinaturaResolve = (route: ActivatedRouteSnapshot): Observable<null | ICalculoPlanoAssinatura> => {
  const id = route.params['id'];
  if (id) {
    return inject(CalculoPlanoAssinaturaService)
      .find(id)
      .pipe(
        mergeMap((calculoPlanoAssinatura: HttpResponse<ICalculoPlanoAssinatura>) => {
          if (calculoPlanoAssinatura.body) {
            return of(calculoPlanoAssinatura.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default calculoPlanoAssinaturaResolve;
