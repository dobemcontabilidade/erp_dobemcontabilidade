import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-ordem-servico-execucao.test-samples';

import { AnexoOrdemServicoExecucaoService, RestAnexoOrdemServicoExecucao } from './anexo-ordem-servico-execucao.service';

const requireRestSample: RestAnexoOrdemServicoExecucao = {
  ...sampleWithRequiredData,
  dataHoraUpload: sampleWithRequiredData.dataHoraUpload?.toJSON(),
};

describe('AnexoOrdemServicoExecucao Service', () => {
  let service: AnexoOrdemServicoExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoOrdemServicoExecucao | IAnexoOrdemServicoExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoOrdemServicoExecucaoService);
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

    it('should create a AnexoOrdemServicoExecucao', () => {
      const anexoOrdemServicoExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoOrdemServicoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoOrdemServicoExecucao', () => {
      const anexoOrdemServicoExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoOrdemServicoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoOrdemServicoExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoOrdemServicoExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoOrdemServicoExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoOrdemServicoExecucaoToCollectionIfMissing', () => {
      it('should add a AnexoOrdemServicoExecucao to an empty array', () => {
        const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = sampleWithRequiredData;
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing([], anexoOrdemServicoExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoOrdemServicoExecucao);
      });

      it('should not add a AnexoOrdemServicoExecucao to an array that contains it', () => {
        const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = sampleWithRequiredData;
        const anexoOrdemServicoExecucaoCollection: IAnexoOrdemServicoExecucao[] = [
          {
            ...anexoOrdemServicoExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing(
          anexoOrdemServicoExecucaoCollection,
          anexoOrdemServicoExecucao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoOrdemServicoExecucao to an array that doesn't contain it", () => {
        const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = sampleWithRequiredData;
        const anexoOrdemServicoExecucaoCollection: IAnexoOrdemServicoExecucao[] = [sampleWithPartialData];
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing(
          anexoOrdemServicoExecucaoCollection,
          anexoOrdemServicoExecucao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoOrdemServicoExecucao);
      });

      it('should add only unique AnexoOrdemServicoExecucao to an array', () => {
        const anexoOrdemServicoExecucaoArray: IAnexoOrdemServicoExecucao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const anexoOrdemServicoExecucaoCollection: IAnexoOrdemServicoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing(
          anexoOrdemServicoExecucaoCollection,
          ...anexoOrdemServicoExecucaoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = sampleWithRequiredData;
        const anexoOrdemServicoExecucao2: IAnexoOrdemServicoExecucao = sampleWithPartialData;
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing(
          [],
          anexoOrdemServicoExecucao,
          anexoOrdemServicoExecucao2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoOrdemServicoExecucao);
        expect(expectedResult).toContain(anexoOrdemServicoExecucao2);
      });

      it('should accept null and undefined values', () => {
        const anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao = sampleWithRequiredData;
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing([], null, anexoOrdemServicoExecucao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoOrdemServicoExecucao);
      });

      it('should return initial array if no AnexoOrdemServicoExecucao is added', () => {
        const anexoOrdemServicoExecucaoCollection: IAnexoOrdemServicoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoOrdemServicoExecucaoToCollectionIfMissing(anexoOrdemServicoExecucaoCollection, undefined, null);
        expect(expectedResult).toEqual(anexoOrdemServicoExecucaoCollection);
      });
    });

    describe('compareAnexoOrdemServicoExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoOrdemServicoExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoOrdemServicoExecucao(entity1, entity2);
        const compareResult2 = service.compareAnexoOrdemServicoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoOrdemServicoExecucao(entity1, entity2);
        const compareResult2 = service.compareAnexoOrdemServicoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoOrdemServicoExecucao(entity1, entity2);
        const compareResult2 = service.compareAnexoOrdemServicoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
