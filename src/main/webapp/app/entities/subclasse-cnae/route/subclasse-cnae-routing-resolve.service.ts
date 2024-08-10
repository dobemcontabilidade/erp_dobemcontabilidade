import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubclasseCnae } from '../subclasse-cnae.model';
import { SubclasseCnaeService } from '../service/subclasse-cnae.service';

const subclasseCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubclasseCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(SubclasseCnaeService)
      .find(id)
      .pipe(
        mergeMap((subclasseCnae: HttpResponse<ISubclasseCnae>) => {
          if (subclasseCnae.body) {
            return of(subclasseCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default subclasseCnaeResolve;
