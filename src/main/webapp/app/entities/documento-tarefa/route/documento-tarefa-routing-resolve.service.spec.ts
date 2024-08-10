import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDocumentoTarefa } from '../documento-tarefa.model';
import { DocumentoTarefaService } from '../service/documento-tarefa.service';

import documentoTarefaResolve from './documento-tarefa-routing-resolve.service';

describe('DocumentoTarefa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DocumentoTarefaService;
  let resultDocumentoTarefa: IDocumentoTarefa | null | undefined;

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
    service = TestBed.inject(DocumentoTarefaService);
    resultDocumentoTarefa = undefined;
  });

  describe('resolve', () => {
    it('should return IDocumentoTarefa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        documentoTarefaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDocumentoTarefa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDocumentoTarefa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        documentoTarefaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDocumentoTarefa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDocumentoTarefa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDocumentoTarefa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        documentoTarefaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDocumentoTarefa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDocumentoTarefa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
