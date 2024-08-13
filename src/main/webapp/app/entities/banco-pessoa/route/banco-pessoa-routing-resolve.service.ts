import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBancoPessoa } from '../banco-pessoa.model';
import { BancoPessoaService } from '../service/banco-pessoa.service';

const bancoPessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IBancoPessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(BancoPessoaService)
      .find(id)
      .pipe(
        mergeMap((bancoPessoa: HttpResponse<IBancoPessoa>) => {
          if (bancoPessoa.body) {
            return of(bancoPessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bancoPessoaResolve;
