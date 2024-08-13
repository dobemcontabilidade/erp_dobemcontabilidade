import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from '../service/desconto-plano-contabil.service';

const descontoPlanoContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IDescontoPlanoContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(DescontoPlanoContabilService)
      .find(id)
      .pipe(
        mergeMap((descontoPlanoContabil: HttpResponse<IDescontoPlanoContabil>) => {
          if (descontoPlanoContabil.body) {
            return of(descontoPlanoContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default descontoPlanoContabilResolve;
