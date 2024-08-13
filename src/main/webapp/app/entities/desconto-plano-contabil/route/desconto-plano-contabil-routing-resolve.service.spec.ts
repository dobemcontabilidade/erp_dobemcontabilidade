import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';
import { DescontoPlanoContabilService } from '../service/desconto-plano-contabil.service';

import descontoPlanoContabilResolve from './desconto-plano-contabil-routing-resolve.service';

describe('DescontoPlanoContabil routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DescontoPlanoContabilService;
  let resultDescontoPlanoContabil: IDescontoPlanoContabil | null | undefined;

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
    service = TestBed.inject(DescontoPlanoContabilService);
    resultDescontoPlanoContabil = undefined;
  });

  describe('resolve', () => {
    it('should return IDescontoPlanoContabil returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        descontoPlanoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDescontoPlanoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDescontoPlanoContabil).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        descontoPlanoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDescontoPlanoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDescontoPlanoContabil).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDescontoPlanoContabil>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        descontoPlanoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDescontoPlanoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDescontoPlanoContabil).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
