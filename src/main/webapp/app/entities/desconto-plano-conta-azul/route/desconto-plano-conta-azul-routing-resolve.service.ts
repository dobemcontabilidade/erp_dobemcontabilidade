import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';
import { DescontoPlanoContaAzulService } from '../service/desconto-plano-conta-azul.service';

const descontoPlanoContaAzulResolve = (route: ActivatedRouteSnapshot): Observable<null | IDescontoPlanoContaAzul> => {
  const id = route.params['id'];
  if (id) {
    return inject(DescontoPlanoContaAzulService)
      .find(id)
      .pipe(
        mergeMap((descontoPlanoContaAzul: HttpResponse<IDescontoPlanoContaAzul>) => {
          if (descontoPlanoContaAzul.body) {
            return of(descontoPlanoContaAzul.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default descontoPlanoContaAzulResolve;
