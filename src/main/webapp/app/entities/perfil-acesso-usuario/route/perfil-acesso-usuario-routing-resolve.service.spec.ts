import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';
import { PerfilAcessoUsuarioService } from '../service/perfil-acesso-usuario.service';

import perfilAcessoUsuarioResolve from './perfil-acesso-usuario-routing-resolve.service';

describe('PerfilAcessoUsuario routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PerfilAcessoUsuarioService;
  let resultPerfilAcessoUsuario: IPerfilAcessoUsuario | null | undefined;

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
    service = TestBed.inject(PerfilAcessoUsuarioService);
    resultPerfilAcessoUsuario = undefined;
  });

  describe('resolve', () => {
    it('should return IPerfilAcessoUsuario returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilAcessoUsuarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilAcessoUsuario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPerfilAcessoUsuario).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilAcessoUsuarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilAcessoUsuario = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPerfilAcessoUsuario).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPerfilAcessoUsuario>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilAcessoUsuarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilAcessoUsuario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPerfilAcessoUsuario).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
