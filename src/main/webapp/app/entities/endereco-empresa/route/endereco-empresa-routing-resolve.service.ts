import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnderecoEmpresa } from '../endereco-empresa.model';
import { EnderecoEmpresaService } from '../service/endereco-empresa.service';

const enderecoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IEnderecoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(EnderecoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((enderecoEmpresa: HttpResponse<IEnderecoEmpresa>) => {
          if (enderecoEmpresa.body) {
            return of(enderecoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default enderecoEmpresaResolve;
