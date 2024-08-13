import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../agenda-tarefa-ordem-servico-execucao.test-samples';

import {
  AgendaTarefaOrdemServicoExecucaoService,
  RestAgendaTarefaOrdemServicoExecucao,
} from './agenda-tarefa-ordem-servico-execucao.service';

const requireRestSample: RestAgendaTarefaOrdemServicoExecucao = {
  ...sampleWithRequiredData,
  horaInicio: sampleWithRequiredData.horaInicio?.toJSON(),
  horaFim: sampleWithRequiredData.horaFim?.toJSON(),
};

describe('AgendaTarefaOrdemServicoExecucao Service', () => {
  let service: AgendaTarefaOrdemServicoExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAgendaTarefaOrdemServicoExecucao | IAgendaTarefaOrdemServicoExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AgendaTarefaOrdemServicoExecucaoService);
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

    it('should create a AgendaTarefaOrdemServicoExecucao', () => {
      const agendaTarefaOrdemServicoExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(agendaTarefaOrdemServicoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AgendaTarefaOrdemServicoExecucao', () => {
      const agendaTarefaOrdemServicoExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(agendaTarefaOrdemServicoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AgendaTarefaOrdemServicoExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AgendaTarefaOrdemServicoExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AgendaTarefaOrdemServicoExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing', () => {
      it('should add a AgendaTarefaOrdemServicoExecucao to an empty array', () => {
        const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = sampleWithRequiredData;
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing([], agendaTarefaOrdemServicoExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaTarefaOrdemServicoExecucao);
      });

      it('should not add a AgendaTarefaOrdemServicoExecucao to an array that contains it', () => {
        const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = sampleWithRequiredData;
        const agendaTarefaOrdemServicoExecucaoCollection: IAgendaTarefaOrdemServicoExecucao[] = [
          {
            ...agendaTarefaOrdemServicoExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing(
          agendaTarefaOrdemServicoExecucaoCollection,
          agendaTarefaOrdemServicoExecucao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AgendaTarefaOrdemServicoExecucao to an array that doesn't contain it", () => {
        const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = sampleWithRequiredData;
        const agendaTarefaOrdemServicoExecucaoCollection: IAgendaTarefaOrdemServicoExecucao[] = [sampleWithPartialData];
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing(
          agendaTarefaOrdemServicoExecucaoCollection,
          agendaTarefaOrdemServicoExecucao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaTarefaOrdemServicoExecucao);
      });

      it('should add only unique AgendaTarefaOrdemServicoExecucao to an array', () => {
        const agendaTarefaOrdemServicoExecucaoArray: IAgendaTarefaOrdemServicoExecucao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const agendaTarefaOrdemServicoExecucaoCollection: IAgendaTarefaOrdemServicoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing(
          agendaTarefaOrdemServicoExecucaoCollection,
          ...agendaTarefaOrdemServicoExecucaoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = sampleWithRequiredData;
        const agendaTarefaOrdemServicoExecucao2: IAgendaTarefaOrdemServicoExecucao = sampleWithPartialData;
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing(
          [],
          agendaTarefaOrdemServicoExecucao,
          agendaTarefaOrdemServicoExecucao2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(agendaTarefaOrdemServicoExecucao);
        expect(expectedResult).toContain(agendaTarefaOrdemServicoExecucao2);
      });

      it('should accept null and undefined values', () => {
        const agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao = sampleWithRequiredData;
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing(
          [],
          null,
          agendaTarefaOrdemServicoExecucao,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(agendaTarefaOrdemServicoExecucao);
      });

      it('should return initial array if no AgendaTarefaOrdemServicoExecucao is added', () => {
        const agendaTarefaOrdemServicoExecucaoCollection: IAgendaTarefaOrdemServicoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing(
          agendaTarefaOrdemServicoExecucaoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(agendaTarefaOrdemServicoExecucaoCollection);
      });
    });

    describe('compareAgendaTarefaOrdemServicoExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAgendaTarefaOrdemServicoExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAgendaTarefaOrdemServicoExecucao(entity1, entity2);
        const compareResult2 = service.compareAgendaTarefaOrdemServicoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAgendaTarefaOrdemServicoExecucao(entity1, entity2);
        const compareResult2 = service.compareAgendaTarefaOrdemServicoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAgendaTarefaOrdemServicoExecucao(entity1, entity2);
        const compareResult2 = service.compareAgendaTarefaOrdemServicoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
