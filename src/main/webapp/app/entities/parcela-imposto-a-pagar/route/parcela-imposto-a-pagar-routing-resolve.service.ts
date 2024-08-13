import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';
import { ParcelaImpostoAPagarService } from '../service/parcela-imposto-a-pagar.service';

const parcelaImpostoAPagarResolve = (route: ActivatedRouteSnapshot): Observable<null | IParcelaImpostoAPagar> => {
  const id = route.params['id'];
  if (id) {
    return inject(ParcelaImpostoAPagarService)
      .find(id)
      .pipe(
        mergeMap((parcelaImpostoAPagar: HttpResponse<IParcelaImpostoAPagar>) => {
          if (parcelaImpostoAPagar.body) {
            return of(parcelaImpostoAPagar.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default parcelaImpostoAPagarResolve;
