import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParcelamentoImposto } from '../parcelamento-imposto.model';
import { ParcelamentoImpostoService } from '../service/parcelamento-imposto.service';

const parcelamentoImpostoResolve = (route: ActivatedRouteSnapshot): Observable<null | IParcelamentoImposto> => {
  const id = route.params['id'];
  if (id) {
    return inject(ParcelamentoImpostoService)
      .find(id)
      .pipe(
        mergeMap((parcelamentoImposto: HttpResponse<IParcelamentoImposto>) => {
          if (parcelamentoImposto.body) {
            return of(parcelamentoImposto.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default parcelamentoImpostoResolve;
