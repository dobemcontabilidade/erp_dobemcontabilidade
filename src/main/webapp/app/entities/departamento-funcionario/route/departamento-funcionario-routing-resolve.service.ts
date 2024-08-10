import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDepartamentoFuncionario } from '../departamento-funcionario.model';
import { DepartamentoFuncionarioService } from '../service/departamento-funcionario.service';

const departamentoFuncionarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IDepartamentoFuncionario> => {
  const id = route.params['id'];
  if (id) {
    return inject(DepartamentoFuncionarioService)
      .find(id)
      .pipe(
        mergeMap((departamentoFuncionario: HttpResponse<IDepartamentoFuncionario>) => {
          if (departamentoFuncionario.body) {
            return of(departamentoFuncionario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default departamentoFuncionarioResolve;
