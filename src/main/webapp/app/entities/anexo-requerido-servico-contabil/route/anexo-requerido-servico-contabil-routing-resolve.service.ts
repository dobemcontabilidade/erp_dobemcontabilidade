import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';
import { AnexoRequeridoServicoContabilService } from '../service/anexo-requerido-servico-contabil.service';

const anexoRequeridoServicoContabilResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoRequeridoServicoContabil> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoRequeridoServicoContabilService)
      .find(id)
      .pipe(
        mergeMap((anexoRequeridoServicoContabil: HttpResponse<IAnexoRequeridoServicoContabil>) => {
          if (anexoRequeridoServicoContabil.body) {
            return of(anexoRequeridoServicoContabil.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoRequeridoServicoContabilResolve;
