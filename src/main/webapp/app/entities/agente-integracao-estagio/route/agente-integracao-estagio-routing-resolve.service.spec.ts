import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';
import { AgenteIntegracaoEstagioService } from '../service/agente-integracao-estagio.service';

import agenteIntegracaoEstagioResolve from './agente-integracao-estagio-routing-resolve.service';

describe('AgenteIntegracaoEstagio routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AgenteIntegracaoEstagioService;
  let resultAgenteIntegracaoEstagio: IAgenteIntegracaoEstagio | null | undefined;

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
    service = TestBed.inject(AgenteIntegracaoEstagioService);
    resultAgenteIntegracaoEstagio = undefined;
  });

  describe('resolve', () => {
    it('should return IAgenteIntegracaoEstagio returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agenteIntegracaoEstagioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgenteIntegracaoEstagio = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAgenteIntegracaoEstagio).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        agenteIntegracaoEstagioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgenteIntegracaoEstagio = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAgenteIntegracaoEstagio).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAgenteIntegracaoEstagio>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agenteIntegracaoEstagioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgenteIntegracaoEstagio = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAgenteIntegracaoEstagio).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
