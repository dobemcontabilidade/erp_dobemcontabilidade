import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPeriodoPagamento } from '../periodo-pagamento.model';
import { PeriodoPagamentoService } from '../service/periodo-pagamento.service';

import periodoPagamentoResolve from './periodo-pagamento-routing-resolve.service';

describe('PeriodoPagamento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PeriodoPagamentoService;
  let resultPeriodoPagamento: IPeriodoPagamento | null | undefined;

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
    service = TestBed.inject(PeriodoPagamentoService);
    resultPeriodoPagamento = undefined;
  });

  describe('resolve', () => {
    it('should return IPeriodoPagamento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        periodoPagamentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPeriodoPagamento = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPeriodoPagamento).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        periodoPagamentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPeriodoPagamento = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPeriodoPagamento).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPeriodoPagamento>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        periodoPagamentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPeriodoPagamento = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPeriodoPagamento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
