import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInstituicaoEnsino } from '../instituicao-ensino.model';
import { InstituicaoEnsinoService } from '../service/instituicao-ensino.service';

const instituicaoEnsinoResolve = (route: ActivatedRouteSnapshot): Observable<null | IInstituicaoEnsino> => {
  const id = route.params['id'];
  if (id) {
    return inject(InstituicaoEnsinoService)
      .find(id)
      .pipe(
        mergeMap((instituicaoEnsino: HttpResponse<IInstituicaoEnsino>) => {
          if (instituicaoEnsino.body) {
            return of(instituicaoEnsino.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default instituicaoEnsinoResolve;
