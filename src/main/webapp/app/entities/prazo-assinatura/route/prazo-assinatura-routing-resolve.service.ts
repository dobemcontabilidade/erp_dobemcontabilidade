import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrazoAssinatura } from '../prazo-assinatura.model';
import { PrazoAssinaturaService } from '../service/prazo-assinatura.service';

const prazoAssinaturaResolve = (route: ActivatedRouteSnapshot): Observable<null | IPrazoAssinatura> => {
  const id = route.params['id'];
  if (id) {
    return inject(PrazoAssinaturaService)
      .find(id)
      .pipe(
        mergeMap((prazoAssinatura: HttpResponse<IPrazoAssinatura>) => {
          if (prazoAssinatura.body) {
            return of(prazoAssinatura.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default prazoAssinaturaResolve;
