import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IClasseCnae } from '../classe-cnae.model';
import { ClasseCnaeService } from '../service/classe-cnae.service';

const classeCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | IClasseCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(ClasseCnaeService)
      .find(id)
      .pipe(
        mergeMap((classeCnae: HttpResponse<IClasseCnae>) => {
          if (classeCnae.body) {
            return of(classeCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default classeCnaeResolve;
