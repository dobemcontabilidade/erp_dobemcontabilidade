import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';
import { AnexoRequeridoTarefaRecorrenteService } from '../service/anexo-requerido-tarefa-recorrente.service';

import anexoRequeridoTarefaRecorrenteResolve from './anexo-requerido-tarefa-recorrente-routing-resolve.service';

describe('AnexoRequeridoTarefaRecorrente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoRequeridoTarefaRecorrenteService;
  let resultAnexoRequeridoTarefaRecorrente: IAnexoRequeridoTarefaRecorrente | null | undefined;

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
    service = TestBed.inject(AnexoRequeridoTarefaRecorrenteService);
    resultAnexoRequeridoTarefaRecorrente = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoRequeridoTarefaRecorrente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoTarefaRecorrente).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoRequeridoTarefaRecorrente).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoRequeridoTarefaRecorrente>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoTarefaRecorrente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
