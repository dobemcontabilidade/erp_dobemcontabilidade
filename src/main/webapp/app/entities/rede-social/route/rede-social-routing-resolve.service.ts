import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRedeSocial } from '../rede-social.model';
import { RedeSocialService } from '../service/rede-social.service';

const redeSocialResolve = (route: ActivatedRouteSnapshot): Observable<null | IRedeSocial> => {
  const id = route.params['id'];
  if (id) {
    return inject(RedeSocialService)
      .find(id)
      .pipe(
        mergeMap((redeSocial: HttpResponse<IRedeSocial>) => {
          if (redeSocial.body) {
            return of(redeSocial.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default redeSocialResolve;
