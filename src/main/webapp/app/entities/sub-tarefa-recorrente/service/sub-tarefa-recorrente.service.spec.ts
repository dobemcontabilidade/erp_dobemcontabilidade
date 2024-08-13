import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../sub-tarefa-recorrente.test-samples';

import { SubTarefaRecorrenteService } from './sub-tarefa-recorrente.service';

const requireRestSample: ISubTarefaRecorrente = {
  ...sampleWithRequiredData,
};

describe('SubTarefaRecorrente Service', () => {
  let service: SubTarefaRecorrenteService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubTarefaRecorrente | ISubTarefaRecorrente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SubTarefaRecorrenteService);
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

    it('should create a SubTarefaRecorrente', () => {
      const subTarefaRecorrente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subTarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SubTarefaRecorrente', () => {
      const subTarefaRecorrente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subTarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SubTarefaRecorrente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SubTarefaRecorrente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SubTarefaRecorrente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubTarefaRecorrenteToCollectionIfMissing', () => {
      it('should add a SubTarefaRecorrente to an empty array', () => {
        const subTarefaRecorrente: ISubTarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing([], subTarefaRecorrente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subTarefaRecorrente);
      });

      it('should not add a SubTarefaRecorrente to an array that contains it', () => {
        const subTarefaRecorrente: ISubTarefaRecorrente = sampleWithRequiredData;
        const subTarefaRecorrenteCollection: ISubTarefaRecorrente[] = [
          {
            ...subTarefaRecorrente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing(subTarefaRecorrenteCollection, subTarefaRecorrente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SubTarefaRecorrente to an array that doesn't contain it", () => {
        const subTarefaRecorrente: ISubTarefaRecorrente = sampleWithRequiredData;
        const subTarefaRecorrenteCollection: ISubTarefaRecorrente[] = [sampleWithPartialData];
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing(subTarefaRecorrenteCollection, subTarefaRecorrente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subTarefaRecorrente);
      });

      it('should add only unique SubTarefaRecorrente to an array', () => {
        const subTarefaRecorrenteArray: ISubTarefaRecorrente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subTarefaRecorrenteCollection: ISubTarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing(subTarefaRecorrenteCollection, ...subTarefaRecorrenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subTarefaRecorrente: ISubTarefaRecorrente = sampleWithRequiredData;
        const subTarefaRecorrente2: ISubTarefaRecorrente = sampleWithPartialData;
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing([], subTarefaRecorrente, subTarefaRecorrente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subTarefaRecorrente);
        expect(expectedResult).toContain(subTarefaRecorrente2);
      });

      it('should accept null and undefined values', () => {
        const subTarefaRecorrente: ISubTarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing([], null, subTarefaRecorrente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subTarefaRecorrente);
      });

      it('should return initial array if no SubTarefaRecorrente is added', () => {
        const subTarefaRecorrenteCollection: ISubTarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addSubTarefaRecorrenteToCollectionIfMissing(subTarefaRecorrenteCollection, undefined, null);
        expect(expectedResult).toEqual(subTarefaRecorrenteCollection);
      });
    });

    describe('compareSubTarefaRecorrente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubTarefaRecorrente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareSubTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareSubTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareSubTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
