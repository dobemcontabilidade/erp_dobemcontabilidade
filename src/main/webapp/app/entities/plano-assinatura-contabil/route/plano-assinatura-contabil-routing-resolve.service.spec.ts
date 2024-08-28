import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';
import { PlanoAssinaturaContabilService } from '../service/plano-assinatura-contabil.service';

import planoAssinaturaContabilResolve from './plano-assinatura-contabil-routing-resolve.service';

describe('PlanoAssinaturaContabil routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PlanoAssinaturaContabilService;
  let resultPlanoAssinaturaContabil: IPlanoAssinaturaContabil | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(PlanoAssinaturaContabilService);
    resultPlanoAssinaturaContabil = undefined;
  });

  describe('resolve', () => {
    it('should return IPlanoAssinaturaContabil returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        planoAssinaturaContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPlanoAssinaturaContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPlanoAssinaturaContabil).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        planoAssinaturaContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPlanoAssinaturaContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPlanoAssinaturaContabil).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPlanoAssinaturaContabil>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        planoAssinaturaContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPlanoAssinaturaContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPlanoAssinaturaContabil).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
