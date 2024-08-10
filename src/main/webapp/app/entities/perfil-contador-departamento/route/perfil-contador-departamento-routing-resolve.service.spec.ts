import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPerfilContadorDepartamento } from '../perfil-contador-departamento.model';
import { PerfilContadorDepartamentoService } from '../service/perfil-contador-departamento.service';

import perfilContadorDepartamentoResolve from './perfil-contador-departamento-routing-resolve.service';

describe('PerfilContadorDepartamento routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PerfilContadorDepartamentoService;
  let resultPerfilContadorDepartamento: IPerfilContadorDepartamento | null | undefined;

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
    service = TestBed.inject(PerfilContadorDepartamentoService);
    resultPerfilContadorDepartamento = undefined;
  });

  describe('resolve', () => {
    it('should return IPerfilContadorDepartamento returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilContadorDepartamentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilContadorDepartamento = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPerfilContadorDepartamento).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilContadorDepartamentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilContadorDepartamento = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPerfilContadorDepartamento).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPerfilContadorDepartamento>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilContadorDepartamentoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilContadorDepartamento = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPerfilContadorDepartamento).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
