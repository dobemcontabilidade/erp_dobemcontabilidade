import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';
import { ServicoContabilEtapaFluxoExecucaoService } from '../service/servico-contabil-etapa-fluxo-execucao.service';

import servicoContabilEtapaFluxoExecucaoResolve from './servico-contabil-etapa-fluxo-execucao-routing-resolve.service';

describe('ServicoContabilEtapaFluxoExecucao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ServicoContabilEtapaFluxoExecucaoService;
  let resultServicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao | null | undefined;

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
    service = TestBed.inject(ServicoContabilEtapaFluxoExecucaoService);
    resultServicoContabilEtapaFluxoExecucao = undefined;
  });

  describe('resolve', () => {
    it('should return IServicoContabilEtapaFluxoExecucao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilEtapaFluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilEtapaFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilEtapaFluxoExecucao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilEtapaFluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilEtapaFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultServicoContabilEtapaFluxoExecucao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IServicoContabilEtapaFluxoExecucao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilEtapaFluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilEtapaFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilEtapaFluxoExecucao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
