import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPessoaFisica } from '../pessoa-fisica.model';
import { PessoaFisicaService } from '../service/pessoa-fisica.service';

const pessoaFisicaResolve = (route: ActivatedRouteSnapshot): Observable<null | IPessoaFisica> => {
  const id = route.params['id'];
  if (id) {
    return inject(PessoaFisicaService)
      .find(id)
      .pipe(
        mergeMap((pessoaFisica: HttpResponse<IPessoaFisica>) => {
          if (pessoaFisica.body) {
            return of(pessoaFisica.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default pessoaFisicaResolve;
