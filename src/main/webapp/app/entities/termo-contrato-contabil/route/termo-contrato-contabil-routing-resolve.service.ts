import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITermoContratoContabil } from '../termo-contrato-contabil.model';
import { TermoContratoContabilService } from '../service/termo-contrato-contabil.service';

const termoContratoContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | ITermoContratoContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(TermoContratoContabilService)
      .find(id)
      .pipe(
        mergeMap((termoContratoContabil: HttpResponse<ITermoContratoContabil>) => {
          if (termoContratoContabil.body) {
            return of(termoContratoContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default termoContratoContabilResolve;
