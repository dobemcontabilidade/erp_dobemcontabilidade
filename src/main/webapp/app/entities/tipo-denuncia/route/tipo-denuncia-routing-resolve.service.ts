import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITipoDenuncia } from '../tipo-denuncia.model';
import { TipoDenunciaService } from '../service/tipo-denuncia.service';

const tipoDenunciaResolve = (route: ActivatedRouteSnapshot): Observable<null | ITipoDenuncia> => {
  const id = route.params['id'];
  if (id) {
    return inject(TipoDenunciaService)
      .find(id)
      .pipe(
        mergeMap((tipoDenuncia: HttpResponse<ITipoDenuncia>) => {
          if (tipoDenuncia.body) {
            return of(tipoDenuncia.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tipoDenunciaResolve;
