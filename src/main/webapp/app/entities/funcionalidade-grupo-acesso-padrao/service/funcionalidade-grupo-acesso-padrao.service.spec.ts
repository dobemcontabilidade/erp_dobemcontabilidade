import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuncionalidadeGrupoAcessoPadrao } from '../funcionalidade-grupo-acesso-padrao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../funcionalidade-grupo-acesso-padrao.test-samples';

import { FuncionalidadeGrupoAcessoPadraoService, RestFuncionalidadeGrupoAcessoPadrao } from './funcionalidade-grupo-acesso-padrao.service';

const requireRestSample: RestFuncionalidadeGrupoAcessoPadrao = {
  ...sampleWithRequiredData,
  dataExpiracao: sampleWithRequiredData.dataExpiracao?.toJSON(),
  dataAtribuicao: sampleWithRequiredData.dataAtribuicao?.toJSON(),
};

describe('FuncionalidadeGrupoAcessoPadrao Service', () => {
  let service: FuncionalidadeGrupoAcessoPadraoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuncionalidadeGrupoAcessoPadrao | IFuncionalidadeGrupoAcessoPadrao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuncionalidadeGrupoAcessoPadraoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FuncionalidadeGrupoAcessoPadrao', () => {
      const funcionalidadeGrupoAcessoPadrao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(funcionalidadeGrupoAcessoPadrao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuncionalidadeGrupoAcessoPadrao', () => {
      const funcionalidadeGrupoAcessoPadrao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(funcionalidadeGrupoAcessoPadrao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuncionalidadeGrupoAcessoPadrao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuncionalidadeGrupoAcessoPadrao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuncionalidadeGrupoAcessoPadrao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing', () => {
      it('should add a FuncionalidadeGrupoAcessoPadrao to an empty array', () => {
        const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = sampleWithRequiredData;
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing([], funcionalidadeGrupoAcessoPadrao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoPadrao);
      });

      it('should not add a FuncionalidadeGrupoAcessoPadrao to an array that contains it', () => {
        const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = sampleWithRequiredData;
        const funcionalidadeGrupoAcessoPadraoCollection: IFuncionalidadeGrupoAcessoPadrao[] = [
          {
            ...funcionalidadeGrupoAcessoPadrao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing(
          funcionalidadeGrupoAcessoPadraoCollection,
          funcionalidadeGrupoAcessoPadrao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuncionalidadeGrupoAcessoPadrao to an array that doesn't contain it", () => {
        const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = sampleWithRequiredData;
        const funcionalidadeGrupoAcessoPadraoCollection: IFuncionalidadeGrupoAcessoPadrao[] = [sampleWithPartialData];
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing(
          funcionalidadeGrupoAcessoPadraoCollection,
          funcionalidadeGrupoAcessoPadrao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoPadrao);
      });

      it('should add only unique FuncionalidadeGrupoAcessoPadrao to an array', () => {
        const funcionalidadeGrupoAcessoPadraoArray: IFuncionalidadeGrupoAcessoPadrao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const funcionalidadeGrupoAcessoPadraoCollection: IFuncionalidadeGrupoAcessoPadrao[] = [sampleWithRequiredData];
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing(
          funcionalidadeGrupoAcessoPadraoCollection,
          ...funcionalidadeGrupoAcessoPadraoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = sampleWithRequiredData;
        const funcionalidadeGrupoAcessoPadrao2: IFuncionalidadeGrupoAcessoPadrao = sampleWithPartialData;
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing(
          [],
          funcionalidadeGrupoAcessoPadrao,
          funcionalidadeGrupoAcessoPadrao2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoPadrao);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoPadrao2);
      });

      it('should accept null and undefined values', () => {
        const funcionalidadeGrupoAcessoPadrao: IFuncionalidadeGrupoAcessoPadrao = sampleWithRequiredData;
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing(
          [],
          null,
          funcionalidadeGrupoAcessoPadrao,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoPadrao);
      });

      it('should return initial array if no FuncionalidadeGrupoAcessoPadrao is added', () => {
        const funcionalidadeGrupoAcessoPadraoCollection: IFuncionalidadeGrupoAcessoPadrao[] = [sampleWithRequiredData];
        expectedResult = service.addFuncionalidadeGrupoAcessoPadraoToCollectionIfMissing(
          funcionalidadeGrupoAcessoPadraoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(funcionalidadeGrupoAcessoPadraoCollection);
      });
    });

    describe('compareFuncionalidadeGrupoAcessoPadrao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuncionalidadeGrupoAcessoPadrao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFuncionalidadeGrupoAcessoPadrao(entity1, entity2);
        const compareResult2 = service.compareFuncionalidadeGrupoAcessoPadrao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFuncionalidadeGrupoAcessoPadrao(entity1, entity2);
        const compareResult2 = service.compareFuncionalidadeGrupoAcessoPadrao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFuncionalidadeGrupoAcessoPadrao(entity1, entity2);
        const compareResult2 = service.compareFuncionalidadeGrupoAcessoPadrao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
