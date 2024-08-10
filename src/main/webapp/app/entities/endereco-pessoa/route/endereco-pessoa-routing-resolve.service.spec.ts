import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IEnderecoPessoa } from '../endereco-pessoa.model';
import { EnderecoPessoaService } from '../service/endereco-pessoa.service';

import enderecoPessoaResolve from './endereco-pessoa-routing-resolve.service';

describe('EnderecoPessoa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: EnderecoPessoaService;
  let resultEnderecoPessoa: IEnderecoPessoa | null | undefined;

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
    service = TestBed.inject(EnderecoPessoaService);
    resultEnderecoPessoa = undefined;
  });

  describe('resolve', () => {
    it('should return IEnderecoPessoa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        enderecoPessoaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEnderecoPessoa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEnderecoPessoa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        enderecoPessoaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEnderecoPessoa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEnderecoPessoa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEnderecoPessoa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        enderecoPessoaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultEnderecoPessoa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultEnderecoPessoa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
