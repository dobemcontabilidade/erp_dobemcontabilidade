import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../tarefa-recorrente-execucao.test-samples';

import { TarefaRecorrenteExecucaoService, RestTarefaRecorrenteExecucao } from './tarefa-recorrente-execucao.service';

const requireRestSample: RestTarefaRecorrenteExecucao = {
  ...sampleWithRequiredData,
  dataEntrega: sampleWithRequiredData.dataEntrega?.toJSON(),
  dataAgendada: sampleWithRequiredData.dataAgendada?.toJSON(),
};

describe('TarefaRecorrenteExecucao Service', () => {
  let service: TarefaRecorrenteExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITarefaRecorrenteExecucao | ITarefaRecorrenteExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TarefaRecorrenteExecucaoService);
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

    it('should create a TarefaRecorrenteExecucao', () => {
      const tarefaRecorrenteExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tarefaRecorrenteExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TarefaRecorrenteExecucao', () => {
      const tarefaRecorrenteExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tarefaRecorrenteExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TarefaRecorrenteExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TarefaRecorrenteExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TarefaRecorrenteExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTarefaRecorrenteExecucaoToCollectionIfMissing', () => {
      it('should add a TarefaRecorrenteExecucao to an empty array', () => {
        const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = sampleWithRequiredData;
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing([], tarefaRecorrenteExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaRecorrenteExecucao);
      });

      it('should not add a TarefaRecorrenteExecucao to an array that contains it', () => {
        const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = sampleWithRequiredData;
        const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [
          {
            ...tarefaRecorrenteExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing(
          tarefaRecorrenteExecucaoCollection,
          tarefaRecorrenteExecucao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TarefaRecorrenteExecucao to an array that doesn't contain it", () => {
        const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = sampleWithRequiredData;
        const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [sampleWithPartialData];
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing(
          tarefaRecorrenteExecucaoCollection,
          tarefaRecorrenteExecucao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaRecorrenteExecucao);
      });

      it('should add only unique TarefaRecorrenteExecucao to an array', () => {
        const tarefaRecorrenteExecucaoArray: ITarefaRecorrenteExecucao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing(
          tarefaRecorrenteExecucaoCollection,
          ...tarefaRecorrenteExecucaoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = sampleWithRequiredData;
        const tarefaRecorrenteExecucao2: ITarefaRecorrenteExecucao = sampleWithPartialData;
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing([], tarefaRecorrenteExecucao, tarefaRecorrenteExecucao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tarefaRecorrenteExecucao);
        expect(expectedResult).toContain(tarefaRecorrenteExecucao2);
      });

      it('should accept null and undefined values', () => {
        const tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao = sampleWithRequiredData;
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing([], null, tarefaRecorrenteExecucao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tarefaRecorrenteExecucao);
      });

      it('should return initial array if no TarefaRecorrenteExecucao is added', () => {
        const tarefaRecorrenteExecucaoCollection: ITarefaRecorrenteExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addTarefaRecorrenteExecucaoToCollectionIfMissing(tarefaRecorrenteExecucaoCollection, undefined, null);
        expect(expectedResult).toEqual(tarefaRecorrenteExecucaoCollection);
      });
    });

    describe('compareTarefaRecorrenteExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTarefaRecorrenteExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
