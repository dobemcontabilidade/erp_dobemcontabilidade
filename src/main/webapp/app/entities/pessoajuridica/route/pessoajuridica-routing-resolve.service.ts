import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPessoajuridica } from '../pessoajuridica.model';
import { PessoajuridicaService } from '../service/pessoajuridica.service';

const pessoajuridicaResolve = (route: ActivatedRouteSnapshot): Observable<null | IPessoajuridica> => {
  const id = route.params['id'];
  if (id) {
    return inject(PessoajuridicaService)
      .find(id)
      .pipe(
        mergeMap((pessoajuridica: HttpResponse<IPessoajuridica>) => {
          if (pessoajuridica.body) {
            return of(pessoajuridica.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default pessoajuridicaResolve;
