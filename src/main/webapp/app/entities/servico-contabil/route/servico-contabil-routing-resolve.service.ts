import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IServicoContabil } from '../servico-contabil.model';
import { ServicoContabilService } from '../service/servico-contabil.service';

const servicoContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IServicoContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(ServicoContabilService)
      .find(id)
      .pipe(
        mergeMap((servicoContabil: HttpResponse<IServicoContabil>) => {
          if (servicoContabil.body) {
            return of(servicoContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default servicoContabilResolve;
