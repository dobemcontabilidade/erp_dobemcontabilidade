import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITarefaRecorrente } from '../tarefa-recorrente.model';
import { TarefaRecorrenteService } from '../service/tarefa-recorrente.service';

import tarefaRecorrenteResolve from './tarefa-recorrente-routing-resolve.service';

describe('TarefaRecorrente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TarefaRecorrenteService;
  let resultTarefaRecorrente: ITarefaRecorrente | null | undefined;

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
    service = TestBed.inject(TarefaRecorrenteService);
    resultTarefaRecorrente = undefined;
  });

  describe('resolve', () => {
    it('should return ITarefaRecorrente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTarefaRecorrente).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTarefaRecorrente).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITarefaRecorrente>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        tarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTarefaRecorrente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
