import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITarefaRecorrente } from '../tarefa-recorrente.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tarefa-recorrente.test-samples';

import { TarefaRecorrenteService, RestTarefaRecorrente } from './tarefa-recorrente.service';

const requireRestSample: RestTarefaRecorrente = {
  ...sampleWithRequiredData,
  dataAdmin: sampleWithRequiredData.dataAdmin?.toJSON(),
};

describe('TarefaRecorrente Service', () => {
  let service: TarefaRecorrenteService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarefaRecorrente | ITarefaRecorrente[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TarefaRecorrenteService);
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

    it('should create a TarefaRecorrente', () => {
      const tarefaRecorrente = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TarefaRecorrente', () => {
      const tarefaRecorrente = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarefaRecorrente).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TarefaRecorrente', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TarefaRecorrente', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TarefaRecorrente', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTarefaRecorrenteToCollectionIfMissing', () => {
      it('should add a TarefaRecorrente to an empty array', () => {
        const tarefaRecorrente: ITarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing([], tarefaRecorrente);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaRecorrente);
      });

      it('should not add a TarefaRecorrente to an array that contains it', () => {
        const tarefaRecorrente: ITarefaRecorrente = sampleWithRequiredData;
        const tarefaRecorrenteCollection: ITarefaRecorrente[] = [
          {
            ...tarefaRecorrente,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing(tarefaRecorrenteCollection, tarefaRecorrente);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TarefaRecorrente to an array that doesn't contain it", () => {
        const tarefaRecorrente: ITarefaRecorrente = sampleWithRequiredData;
        const tarefaRecorrenteCollection: ITarefaRecorrente[] = [sampleWithPartialData];
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing(tarefaRecorrenteCollection, tarefaRecorrente);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaRecorrente);
      });

      it('should add only unique TarefaRecorrente to an array', () => {
        const tarefaRecorrenteArray: ITarefaRecorrente[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tarefaRecorrenteCollection: ITarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing(tarefaRecorrenteCollection, ...tarefaRecorrenteArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarefaRecorrente: ITarefaRecorrente = sampleWithRequiredData;
        const tarefaRecorrente2: ITarefaRecorrente = sampleWithPartialData;
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing([], tarefaRecorrente, tarefaRecorrente2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaRecorrente);
        expect(expectedResult).toContain(tarefaRecorrente2);
      });

      it('should accept null and undefined values', () => {
        const tarefaRecorrente: ITarefaRecorrente = sampleWithRequiredData;
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing([], null, tarefaRecorrente, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaRecorrente);
      });

      it('should return initial array if no TarefaRecorrente is added', () => {
        const tarefaRecorrenteCollection: ITarefaRecorrente[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaRecorrenteToCollectionIfMissing(tarefaRecorrenteCollection, undefined, null);
        expect(expectedResult).toEqual(tarefaRecorrenteCollection);
      });
    });

    describe('compareTarefaRecorrente', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarefaRecorrente(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarefaRecorrente(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrente(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
