import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICompetencia } from '../competencia.model';
import { CompetenciaService } from '../service/competencia.service';

const competenciaResolve = (route: ActivatedRouteSnapshot): Observable<null | ICompetencia> => {
  const id = route.params['id'];
  if (id) {
    return inject(CompetenciaService)
      .find(id)
      .pipe(
        mergeMap((competencia: HttpResponse<ICompetencia>) => {
          if (competencia.body) {
            return of(competencia.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default competenciaResolve;
