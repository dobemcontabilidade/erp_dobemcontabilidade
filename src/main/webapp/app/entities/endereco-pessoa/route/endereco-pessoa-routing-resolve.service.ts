import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnderecoPessoa } from '../endereco-pessoa.model';
import { EnderecoPessoaService } from '../service/endereco-pessoa.service';

const enderecoPessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEnderecoPessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(EnderecoPessoaService)
      .find(id)
      .pipe(
        mergeMap((enderecoPessoa: HttpResponse<IEnderecoPessoa>) => {
          if (enderecoPessoa.body) {
            return of(enderecoPessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default enderecoPessoaResolve;
