import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContratoFuncionario } from '../contrato-funcionario.model';
import { ContratoFuncionarioService } from '../service/contrato-funcionario.service';

const contratoFuncionarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IContratoFuncionario> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContratoFuncionarioService)
      .find(id)
      .pipe(
        mergeMap((contratoFuncionario: HttpResponse<IContratoFuncionario>) => {
          if (contratoFuncionario.body) {
            return of(contratoFuncionario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default contratoFuncionarioResolve;
