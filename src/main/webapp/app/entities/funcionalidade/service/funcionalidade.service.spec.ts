import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuncionalidade } from '../funcionalidade.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../funcionalidade.test-samples';

import { FuncionalidadeService } from './funcionalidade.service';

const requireRestSample: IFuncionalidade = {
  ...sampleWithRequiredData,
};

describe('Funcionalidade Service', () => {
  let service: FuncionalidadeService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuncionalidade | IFuncionalidade[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuncionalidadeService);
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

    it('should create a Funcionalidade', () => {
      const funcionalidade = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(funcionalidade).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Funcionalidade', () => {
      const funcionalidade = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(funcionalidade).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Funcionalidade', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Funcionalidade', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Funcionalidade', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuncionalidadeToCollectionIfMissing', () => {
      it('should add a Funcionalidade to an empty array', () => {
        const funcionalidade: IFuncionalidade = sampleWithRequiredData;
        expectedResult = service.addFuncionalidadeToCollectionIfMissing([], funcionalidade);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funcionalidade);
      });

      it('should not add a Funcionalidade to an array that contains it', () => {
        const funcionalidade: IFuncionalidade = sampleWithRequiredData;
        const funcionalidadeCollection: IFuncionalidade[] = [
          {
            ...funcionalidade,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuncionalidadeToCollectionIfMissing(funcionalidadeCollection, funcionalidade);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Funcionalidade to an array that doesn't contain it", () => {
        const funcionalidade: IFuncionalidade = sampleWithRequiredData;
        const funcionalidadeCollection: IFuncionalidade[] = [sampleWithPartialData];
        expectedResult = service.addFuncionalidadeToCollectionIfMissing(funcionalidadeCollection, funcionalidade);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funcionalidade);
      });

      it('should add only unique Funcionalidade to an array', () => {
        const funcionalidadeArray: IFuncionalidade[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const funcionalidadeCollection: IFuncionalidade[] = [sampleWithRequiredData];
        expectedResult = service.addFuncionalidadeToCollectionIfMissing(funcionalidadeCollection, ...funcionalidadeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const funcionalidade: IFuncionalidade = sampleWithRequiredData;
        const funcionalidade2: IFuncionalidade = sampleWithPartialData;
        expectedResult = service.addFuncionalidadeToCollectionIfMissing([], funcionalidade, funcionalidade2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funcionalidade);
        expect(expectedResult).toContain(funcionalidade2);
      });

      it('should accept null and undefined values', () => {
        const funcionalidade: IFuncionalidade = sampleWithRequiredData;
        expectedResult = service.addFuncionalidadeToCollectionIfMissing([], null, funcionalidade, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funcionalidade);
      });

      it('should return initial array if no Funcionalidade is added', () => {
        const funcionalidadeCollection: IFuncionalidade[] = [sampleWithRequiredData];
        expectedResult = service.addFuncionalidadeToCollectionIfMissing(funcionalidadeCollection, undefined, null);
        expect(expectedResult).toEqual(funcionalidadeCollection);
      });
    });

    describe('compareFuncionalidade', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuncionalidade(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFuncionalidade(entity1, entity2);
        const compareResult2 = service.compareFuncionalidade(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFuncionalidade(entity1, entity2);
        const compareResult2 = service.compareFuncionalidade(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFuncionalidade(entity1, entity2);
        const compareResult2 = service.compareFuncionalidade(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
