import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../agenda-tarefa-recorrente-execucao.test-samples';

import { AgendaTarefaRecorrenteExecucaoService, RestAgendaTarefaRecorrenteExecucao } from './agenda-tarefa-recorrente-execucao.service';

const requireRestSample: RestAgendaTarefaRecorrenteExecucao = {
  ...sampleWithRequiredData,
  horaInicio: sampleWithRequiredData.horaInicio?.toJSON(),
  horaFim: sampleWithRequiredData.horaFim?.toJSON(),
};

describe('AgendaTarefaRecorrenteExecucao Service', () => {
  let service: AgendaTarefaRecorrenteExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgendaTarefaRecorrenteExecucao | IAgendaTarefaRecorrenteExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgendaTarefaRecorrenteExecucaoService);
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

    it('should create a AgendaTarefaRecorrenteExecucao', () => {
      const agendaTarefaRecorrenteExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agendaTarefaRecorrenteExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AgendaTarefaRecorrenteExecucao', () => {
      const agendaTarefaRecorrenteExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agendaTarefaRecorrenteExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AgendaTarefaRecorrenteExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AgendaTarefaRecorrenteExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AgendaTarefaRecorrenteExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing', () => {
      it('should add a AgendaTarefaRecorrenteExecucao to an empty array', () => {
        const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = sampleWithRequiredData;
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing([], agendaTarefaRecorrenteExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaTarefaRecorrenteExecucao);
      });

      it('should not add a AgendaTarefaRecorrenteExecucao to an array that contains it', () => {
        const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = sampleWithRequiredData;
        const agendaTarefaRecorrenteExecucaoCollection: IAgendaTarefaRecorrenteExecucao[] = [
          {
            ...agendaTarefaRecorrenteExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing(
          agendaTarefaRecorrenteExecucaoCollection,
          agendaTarefaRecorrenteExecucao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AgendaTarefaRecorrenteExecucao to an array that doesn't contain it", () => {
        const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = sampleWithRequiredData;
        const agendaTarefaRecorrenteExecucaoCollection: IAgendaTarefaRecorrenteExecucao[] = [sampleWithPartialData];
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing(
          agendaTarefaRecorrenteExecucaoCollection,
          agendaTarefaRecorrenteExecucao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaTarefaRecorrenteExecucao);
      });

      it('should add only unique AgendaTarefaRecorrenteExecucao to an array', () => {
        const agendaTarefaRecorrenteExecucaoArray: IAgendaTarefaRecorrenteExecucao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const agendaTarefaRecorrenteExecucaoCollection: IAgendaTarefaRecorrenteExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing(
          agendaTarefaRecorrenteExecucaoCollection,
          ...agendaTarefaRecorrenteExecucaoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = sampleWithRequiredData;
        const agendaTarefaRecorrenteExecucao2: IAgendaTarefaRecorrenteExecucao = sampleWithPartialData;
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing(
          [],
          agendaTarefaRecorrenteExecucao,
          agendaTarefaRecorrenteExecucao2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaTarefaRecorrenteExecucao);
        expect(expectedResult).toContain(agendaTarefaRecorrenteExecucao2);
      });

      it('should accept null and undefined values', () => {
        const agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao = sampleWithRequiredData;
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing(
          [],
          null,
          agendaTarefaRecorrenteExecucao,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaTarefaRecorrenteExecucao);
      });

      it('should return initial array if no AgendaTarefaRecorrenteExecucao is added', () => {
        const agendaTarefaRecorrenteExecucaoCollection: IAgendaTarefaRecorrenteExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing(
          agendaTarefaRecorrenteExecucaoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(agendaTarefaRecorrenteExecucaoCollection);
      });
    });

    describe('compareAgendaTarefaRecorrenteExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgendaTarefaRecorrenteExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgendaTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareAgendaTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgendaTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareAgendaTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgendaTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareAgendaTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
