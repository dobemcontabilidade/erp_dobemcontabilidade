import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEscolaridadePessoa } from '../escolaridade-pessoa.model';
import { EscolaridadePessoaService } from '../service/escolaridade-pessoa.service';

const escolaridadePessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEscolaridadePessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(EscolaridadePessoaService)
      .find(id)
      .pipe(
        mergeMap((escolaridadePessoa: HttpResponse<IEscolaridadePessoa>) => {
          if (escolaridadePessoa.body) {
            return of(escolaridadePessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default escolaridadePessoaResolve;
