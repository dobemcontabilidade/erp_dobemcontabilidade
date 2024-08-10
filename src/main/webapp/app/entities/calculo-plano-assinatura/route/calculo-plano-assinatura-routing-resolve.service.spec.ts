import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';
import { CalculoPlanoAssinaturaService } from '../service/calculo-plano-assinatura.service';

import calculoPlanoAssinaturaResolve from './calculo-plano-assinatura-routing-resolve.service';

describe('CalculoPlanoAssinatura routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CalculoPlanoAssinaturaService;
  let resultCalculoPlanoAssinatura: ICalculoPlanoAssinatura | null | undefined;

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
    service = TestBed.inject(CalculoPlanoAssinaturaService);
    resultCalculoPlanoAssinatura = undefined;
  });

  describe('resolve', () => {
    it('should return ICalculoPlanoAssinatura returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        calculoPlanoAssinaturaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCalculoPlanoAssinatura = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCalculoPlanoAssinatura).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        calculoPlanoAssinaturaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCalculoPlanoAssinatura = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCalculoPlanoAssinatura).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICalculoPlanoAssinatura>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        calculoPlanoAssinaturaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCalculoPlanoAssinatura = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCalculoPlanoAssinatura).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
