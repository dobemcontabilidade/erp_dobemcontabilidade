import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from '../service/tarefa-ordem-servico-execucao.service';

import tarefaOrdemServicoExecucaoResolve from './tarefa-ordem-servico-execucao-routing-resolve.service';

describe('TarefaOrdemServicoExecucao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TarefaOrdemServicoExecucaoService;
  let resultTarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao | null | undefined;

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
    service = TestBed.inject(TarefaOrdemServicoExecucaoService);
    resultTarefaOrdemServicoExecucao = undefined;
  });

  describe('resolve', () => {
    it('should return ITarefaOrdemServicoExecucao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaOrdemServicoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaOrdemServicoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTarefaOrdemServicoExecucao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaOrdemServicoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaOrdemServicoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTarefaOrdemServicoExecucao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITarefaOrdemServicoExecucao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaOrdemServicoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaOrdemServicoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTarefaOrdemServicoExecucao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
