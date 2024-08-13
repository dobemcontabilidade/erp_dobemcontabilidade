import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';
import { AnexoRequeridoPessoaService } from '../service/anexo-requerido-pessoa.service';

const anexoRequeridoPessoaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoRequeridoPessoa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoRequeridoPessoaService)
      .find(id)
      .pipe(
        mergeMap((anexoRequeridoPessoa: HttpResponse<IAnexoRequeridoPessoa>) => {
          if (anexoRequeridoPessoa.body) {
            return of(anexoRequeridoPessoa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoRequeridoPessoaResolve;
