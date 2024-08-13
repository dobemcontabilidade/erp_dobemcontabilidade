import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-tarefa-recorrente-execucao.test-samples';

import { AnexoTarefaRecorrenteExecucaoService, RestAnexoTarefaRecorrenteExecucao } from './anexo-tarefa-recorrente-execucao.service';

const requireRestSample: RestAnexoTarefaRecorrenteExecucao = {
  ...sampleWithRequiredData,
  dataHoraUpload: sampleWithRequiredData.dataHoraUpload?.toJSON(),
};

describe('AnexoTarefaRecorrenteExecucao Service', () => {
  let service: AnexoTarefaRecorrenteExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoTarefaRecorrenteExecucao | IAnexoTarefaRecorrenteExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoTarefaRecorrenteExecucaoService);
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

    it('should create a AnexoTarefaRecorrenteExecucao', () => {
      const anexoTarefaRecorrenteExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoTarefaRecorrenteExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoTarefaRecorrenteExecucao', () => {
      const anexoTarefaRecorrenteExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoTarefaRecorrenteExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoTarefaRecorrenteExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoTarefaRecorrenteExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoTarefaRecorrenteExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing', () => {
      it('should add a AnexoTarefaRecorrenteExecucao to an empty array', () => {
        const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = sampleWithRequiredData;
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing([], anexoTarefaRecorrenteExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoTarefaRecorrenteExecucao);
      });

      it('should not add a AnexoTarefaRecorrenteExecucao to an array that contains it', () => {
        const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = sampleWithRequiredData;
        const anexoTarefaRecorrenteExecucaoCollection: IAnexoTarefaRecorrenteExecucao[] = [
          {
            ...anexoTarefaRecorrenteExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing(
          anexoTarefaRecorrenteExecucaoCollection,
          anexoTarefaRecorrenteExecucao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoTarefaRecorrenteExecucao to an array that doesn't contain it", () => {
        const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = sampleWithRequiredData;
        const anexoTarefaRecorrenteExecucaoCollection: IAnexoTarefaRecorrenteExecucao[] = [sampleWithPartialData];
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing(
          anexoTarefaRecorrenteExecucaoCollection,
          anexoTarefaRecorrenteExecucao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoTarefaRecorrenteExecucao);
      });

      it('should add only unique AnexoTarefaRecorrenteExecucao to an array', () => {
        const anexoTarefaRecorrenteExecucaoArray: IAnexoTarefaRecorrenteExecucao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const anexoTarefaRecorrenteExecucaoCollection: IAnexoTarefaRecorrenteExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing(
          anexoTarefaRecorrenteExecucaoCollection,
          ...anexoTarefaRecorrenteExecucaoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = sampleWithRequiredData;
        const anexoTarefaRecorrenteExecucao2: IAnexoTarefaRecorrenteExecucao = sampleWithPartialData;
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing(
          [],
          anexoTarefaRecorrenteExecucao,
          anexoTarefaRecorrenteExecucao2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoTarefaRecorrenteExecucao);
        expect(expectedResult).toContain(anexoTarefaRecorrenteExecucao2);
      });

      it('should accept null and undefined values', () => {
        const anexoTarefaRecorrenteExecucao: IAnexoTarefaRecorrenteExecucao = sampleWithRequiredData;
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing([], null, anexoTarefaRecorrenteExecucao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoTarefaRecorrenteExecucao);
      });

      it('should return initial array if no AnexoTarefaRecorrenteExecucao is added', () => {
        const anexoTarefaRecorrenteExecucaoCollection: IAnexoTarefaRecorrenteExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoTarefaRecorrenteExecucaoToCollectionIfMissing(
          anexoTarefaRecorrenteExecucaoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(anexoTarefaRecorrenteExecucaoCollection);
      });
    });

    describe('compareAnexoTarefaRecorrenteExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoTarefaRecorrenteExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareAnexoTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareAnexoTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoTarefaRecorrenteExecucao(entity1, entity2);
        const compareResult2 = service.compareAnexoTarefaRecorrenteExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
