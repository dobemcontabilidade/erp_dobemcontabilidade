import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDependentesFuncionario } from '../dependentes-funcionario.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../dependentes-funcionario.test-samples';

import { DependentesFuncionarioService } from './dependentes-funcionario.service';

const requireRestSample: IDependentesFuncionario = {
  ...sampleWithRequiredData,
};

describe('DependentesFuncionario Service', () => {
  let service: DependentesFuncionarioService;
  let httpMock: HttpTestingController;
  let expectedResult: IDependentesFuncionario | IDependentesFuncionario[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DependentesFuncionarioService);
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

    it('should create a DependentesFuncionario', () => {
      const dependentesFuncionario = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(dependentesFuncionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DependentesFuncionario', () => {
      const dependentesFuncionario = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(dependentesFuncionario).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DependentesFuncionario', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DependentesFuncionario', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DependentesFuncionario', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDependentesFuncionarioToCollectionIfMissing', () => {
      it('should add a DependentesFuncionario to an empty array', () => {
        const dependentesFuncionario: IDependentesFuncionario = sampleWithRequiredData;
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing([], dependentesFuncionario);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dependentesFuncionario);
      });

      it('should not add a DependentesFuncionario to an array that contains it', () => {
        const dependentesFuncionario: IDependentesFuncionario = sampleWithRequiredData;
        const dependentesFuncionarioCollection: IDependentesFuncionario[] = [
          {
            ...dependentesFuncionario,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing(dependentesFuncionarioCollection, dependentesFuncionario);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DependentesFuncionario to an array that doesn't contain it", () => {
        const dependentesFuncionario: IDependentesFuncionario = sampleWithRequiredData;
        const dependentesFuncionarioCollection: IDependentesFuncionario[] = [sampleWithPartialData];
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing(dependentesFuncionarioCollection, dependentesFuncionario);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dependentesFuncionario);
      });

      it('should add only unique DependentesFuncionario to an array', () => {
        const dependentesFuncionarioArray: IDependentesFuncionario[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const dependentesFuncionarioCollection: IDependentesFuncionario[] = [sampleWithRequiredData];
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing(
          dependentesFuncionarioCollection,
          ...dependentesFuncionarioArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const dependentesFuncionario: IDependentesFuncionario = sampleWithRequiredData;
        const dependentesFuncionario2: IDependentesFuncionario = sampleWithPartialData;
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing([], dependentesFuncionario, dependentesFuncionario2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(dependentesFuncionario);
        expect(expectedResult).toContain(dependentesFuncionario2);
      });

      it('should accept null and undefined values', () => {
        const dependentesFuncionario: IDependentesFuncionario = sampleWithRequiredData;
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing([], null, dependentesFuncionario, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(dependentesFuncionario);
      });

      it('should return initial array if no DependentesFuncionario is added', () => {
        const dependentesFuncionarioCollection: IDependentesFuncionario[] = [sampleWithRequiredData];
        expectedResult = service.addDependentesFuncionarioToCollectionIfMissing(dependentesFuncionarioCollection, undefined, null);
        expect(expectedResult).toEqual(dependentesFuncionarioCollection);
      });
    });

    describe('compareDependentesFuncionario', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDependentesFuncionario(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDependentesFuncionario(entity1, entity2);
        const compareResult2 = service.compareDependentesFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDependentesFuncionario(entity1, entity2);
        const compareResult2 = service.compareDependentesFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDependentesFuncionario(entity1, entity2);
        const compareResult2 = service.compareDependentesFuncionario(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
