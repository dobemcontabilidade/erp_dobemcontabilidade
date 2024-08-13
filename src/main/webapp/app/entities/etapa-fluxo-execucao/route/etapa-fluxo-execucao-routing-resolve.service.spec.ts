import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { EtapaFluxoExecucaoService } from '../service/etapa-fluxo-execucao.service';

import etapaFluxoExecucaoResolve from './etapa-fluxo-execucao-routing-resolve.service';

describe('EtapaFluxoExecucao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: EtapaFluxoExecucaoService;
  let resultEtapaFluxoExecucao: IEtapaFluxoExecucao | null | undefined;

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
    service = TestBed.inject(EtapaFluxoExecucaoService);
    resultEtapaFluxoExecucao = undefined;
  });

  describe('resolve', () => {
    it('should return IEtapaFluxoExecucao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        etapaFluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEtapaFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEtapaFluxoExecucao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        etapaFluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEtapaFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEtapaFluxoExecucao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEtapaFluxoExecucao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        etapaFluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEtapaFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEtapaFluxoExecucao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
