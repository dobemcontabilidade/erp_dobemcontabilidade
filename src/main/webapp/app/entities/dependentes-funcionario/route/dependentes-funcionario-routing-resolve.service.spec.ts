import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import { DependentesFuncionarioService } from '../service/dependentes-funcionario.service';

import dependentesFuncionarioResolve from './dependentes-funcionario-routing-resolve.service';

describe('DependentesFuncionario routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DependentesFuncionarioService;
  let resultDependentesFuncionario: IDependentesFuncionario | null | undefined;

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
    service = TestBed.inject(DependentesFuncionarioService);
    resultDependentesFuncionario = undefined;
  });

  describe('resolve', () => {
    it('should return IDependentesFuncionario returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        dependentesFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDependentesFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDependentesFuncionario).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        dependentesFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDependentesFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDependentesFuncionario).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDependentesFuncionario>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        dependentesFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDependentesFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDependentesFuncionario).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
