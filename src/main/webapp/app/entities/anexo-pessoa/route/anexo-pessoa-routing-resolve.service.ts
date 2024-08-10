import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoPessoa } from '../anexo-pessoa.model';
import { AnexoPessoaService } from '../service/anexo-pessoa.service';

const anexoPessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoPessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoPessoaService)
      .find(id)
      .pipe(
        mergeMap((anexoPessoa: HttpResponse<IAnexoPessoa>) => {
          if (anexoPessoa.body) {
            return of(anexoPessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoPessoaResolve;
