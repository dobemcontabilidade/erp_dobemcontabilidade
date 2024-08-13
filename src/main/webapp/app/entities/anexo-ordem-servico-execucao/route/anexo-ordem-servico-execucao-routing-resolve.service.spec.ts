import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';
import { AnexoOrdemServicoExecucaoService } from '../service/anexo-ordem-servico-execucao.service';

import anexoOrdemServicoExecucaoResolve from './anexo-ordem-servico-execucao-routing-resolve.service';

describe('AnexoOrdemServicoExecucao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoOrdemServicoExecucaoService;
  let resultAnexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao | null | undefined;

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
    service = TestBed.inject(AnexoOrdemServicoExecucaoService);
    resultAnexoOrdemServicoExecucao = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoOrdemServicoExecucao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoOrdemServicoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoOrdemServicoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoOrdemServicoExecucao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoOrdemServicoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoOrdemServicoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoOrdemServicoExecucao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoOrdemServicoExecucao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoOrdemServicoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoOrdemServicoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoOrdemServicoExecucao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
