import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoService } from '../service/agenda-tarefa-recorrente-execucao.service';

import agendaTarefaRecorrenteExecucaoResolve from './agenda-tarefa-recorrente-execucao-routing-resolve.service';

describe('AgendaTarefaRecorrenteExecucao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AgendaTarefaRecorrenteExecucaoService;
  let resultAgendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao | null | undefined;

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
    service = TestBed.inject(AgendaTarefaRecorrenteExecucaoService);
    resultAgendaTarefaRecorrenteExecucao = undefined;
  });

  describe('resolve', () => {
    it('should return IAgendaTarefaRecorrenteExecucao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agendaTarefaRecorrenteExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgendaTarefaRecorrenteExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAgendaTarefaRecorrenteExecucao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        agendaTarefaRecorrenteExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgendaTarefaRecorrenteExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAgendaTarefaRecorrenteExecucao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAgendaTarefaRecorrenteExecucao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        agendaTarefaRecorrenteExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAgendaTarefaRecorrenteExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAgendaTarefaRecorrenteExecucao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
