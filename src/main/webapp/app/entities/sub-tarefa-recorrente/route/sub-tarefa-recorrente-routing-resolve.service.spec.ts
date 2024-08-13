import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';
import { SubTarefaRecorrenteService } from '../service/sub-tarefa-recorrente.service';

import subTarefaRecorrenteResolve from './sub-tarefa-recorrente-routing-resolve.service';

describe('SubTarefaRecorrente routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SubTarefaRecorrenteService;
  let resultSubTarefaRecorrente: ISubTarefaRecorrente | null | undefined;

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
    service = TestBed.inject(SubTarefaRecorrenteService);
    resultSubTarefaRecorrente = undefined;
  });

  describe('resolve', () => {
    it('should return ISubTarefaRecorrente returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        subTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSubTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSubTarefaRecorrente).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        subTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSubTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubTarefaRecorrente).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISubTarefaRecorrente>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        subTarefaRecorrenteResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSubTarefaRecorrente = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSubTarefaRecorrente).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
