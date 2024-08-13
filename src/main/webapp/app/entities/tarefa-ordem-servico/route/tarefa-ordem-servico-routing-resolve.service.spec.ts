import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from '../service/tarefa-ordem-servico.service';

import tarefaOrdemServicoResolve from './tarefa-ordem-servico-routing-resolve.service';

describe('TarefaOrdemServico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TarefaOrdemServicoService;
  let resultTarefaOrdemServico: ITarefaOrdemServico | null | undefined;

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
    service = TestBed.inject(TarefaOrdemServicoService);
    resultTarefaOrdemServico = undefined;
  });

  describe('resolve', () => {
    it('should return ITarefaOrdemServico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTarefaOrdemServico).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTarefaOrdemServico).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITarefaOrdemServico>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTarefaOrdemServico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
