import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';
import { ContadorResponsavelTarefaRecorrenteService } from '../service/contador-responsavel-tarefa-recorrente.service';

import contadorResponsavelTarefaRecorrenteResolve from './contador-responsavel-tarefa-recorrente-routing-resolve.service';

describe('ContadorResponsavelTarefaRecorrente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ContadorResponsavelTarefaRecorrenteService;
  let resultContadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente | null | undefined;

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
    service = TestBed.inject(ContadorResponsavelTarefaRecorrenteService);
    resultContadorResponsavelTarefaRecorrente = undefined;
  });

  describe('resolve', () => {
    it('should return IContadorResponsavelTarefaRecorrente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contadorResponsavelTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContadorResponsavelTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultContadorResponsavelTarefaRecorrente).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        contadorResponsavelTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContadorResponsavelTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultContadorResponsavelTarefaRecorrente).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IContadorResponsavelTarefaRecorrente>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contadorResponsavelTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContadorResponsavelTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultContadorResponsavelTarefaRecorrente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
