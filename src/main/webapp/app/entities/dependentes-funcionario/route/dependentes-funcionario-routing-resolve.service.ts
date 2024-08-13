import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import { DependentesFuncionarioService } from '../service/dependentes-funcionario.service';

const dependentesFuncionarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IDependentesFuncionario> => {
  const id = route.params['id'];
  if (id) {
    return inject(DependentesFuncionarioService)
      .find(id)
      .pipe(
        mergeMap((dependentesFuncionario: HttpResponse<IDependentesFuncionario>) => {
          if (dependentesFuncionario.body) {
            return of(dependentesFuncionario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default dependentesFuncionarioResolve;
