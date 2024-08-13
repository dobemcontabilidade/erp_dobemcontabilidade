import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImpostoParcelado } from '../imposto-parcelado.model';
import { ImpostoParceladoService } from '../service/imposto-parcelado.service';

const impostoParceladoResolve = (route: ActivatedRouteSnapshot): Observable<null | IImpostoParcelado> => {
  const id = route.params['id'];
  if (id) {
    return inject(ImpostoParceladoService)
      .find(id)
      .pipe(
        mergeMap((impostoParcelado: HttpResponse<IImpostoParcelado>) => {
          if (impostoParcelado.body) {
            return of(impostoParcelado.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default impostoParceladoResolve;
