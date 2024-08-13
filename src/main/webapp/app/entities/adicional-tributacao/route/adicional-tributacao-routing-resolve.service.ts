import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAdicionalTributacao } from '../adicional-tributacao.model';
import { AdicionalTributacaoService } from '../service/adicional-tributacao.service';

const adicionalTributacaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAdicionalTributacao> => {
  const id = route.params['id'];
  if (id) {
    return inject(AdicionalTributacaoService)
      .find(id)
      .pipe(
        mergeMap((adicionalTributacao: HttpResponse<IAdicionalTributacao>) => {
          if (adicionalTributacao.body) {
            return of(adicionalTributacao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default adicionalTributacaoResolve;
