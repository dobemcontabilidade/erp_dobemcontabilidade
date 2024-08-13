import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';
import { EtapaFluxoModeloService } from '../service/etapa-fluxo-modelo.service';

import etapaFluxoModeloResolve from './etapa-fluxo-modelo-routing-resolve.service';

describe('EtapaFluxoModelo routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: EtapaFluxoModeloService;
  let resultEtapaFluxoModelo: IEtapaFluxoModelo | null | undefined;

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
    service = TestBed.inject(EtapaFluxoModeloService);
    resultEtapaFluxoModelo = undefined;
  });

  describe('resolve', () => {
    it('should return IEtapaFluxoModelo returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        etapaFluxoModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEtapaFluxoModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEtapaFluxoModelo).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        etapaFluxoModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEtapaFluxoModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEtapaFluxoModelo).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEtapaFluxoModelo>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        etapaFluxoModeloResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEtapaFluxoModelo = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEtapaFluxoModelo).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
