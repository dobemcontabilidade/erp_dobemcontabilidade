import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocsPessoa } from '../docs-pessoa.model';
import { DocsPessoaService } from '../service/docs-pessoa.service';

const docsPessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocsPessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(DocsPessoaService)
      .find(id)
      .pipe(
        mergeMap((docsPessoa: HttpResponse<IDocsPessoa>) => {
          if (docsPessoa.body) {
            return of(docsPessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default docsPessoaResolve;
