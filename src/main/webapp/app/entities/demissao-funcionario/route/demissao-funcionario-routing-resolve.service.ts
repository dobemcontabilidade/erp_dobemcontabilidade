import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDemissaoFuncionario } from '../demissao-funcionario.model';
import { DemissaoFuncionarioService } from '../service/demissao-funcionario.service';

const demissaoFuncionarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IDemissaoFuncionario> => {
  const id = route.params['id'];
  if (id) {
    return inject(DemissaoFuncionarioService)
      .find(id)
      .pipe(
        mergeMap((demissaoFuncionario: HttpResponse<IDemissaoFuncionario>) => {
          if (demissaoFuncionario.body) {
            return of(demissaoFuncionario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default demissaoFuncionarioResolve;
