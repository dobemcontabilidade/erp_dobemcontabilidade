import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITermoContratoContabil } from '../termo-contrato-contabil.model';
import { TermoContratoContabilService } from '../service/termo-contrato-contabil.service';

import termoContratoContabilResolve from './termo-contrato-contabil-routing-resolve.service';

describe('TermoContratoContabil routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TermoContratoContabilService;
  let resultTermoContratoContabil: ITermoContratoContabil | null | undefined;

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
    service = TestBed.inject(TermoContratoContabilService);
    resultTermoContratoContabil = undefined;
  });

  describe('resolve', () => {
    it('should return ITermoContratoContabil returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoContratoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoContratoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTermoContratoContabil).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoContratoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoContratoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTermoContratoContabil).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITermoContratoContabil>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoContratoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoContratoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTermoContratoContabil).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
