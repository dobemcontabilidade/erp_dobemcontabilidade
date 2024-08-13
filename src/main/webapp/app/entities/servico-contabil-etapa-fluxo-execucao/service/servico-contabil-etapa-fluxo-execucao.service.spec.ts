import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../servico-contabil-etapa-fluxo-execucao.test-samples';

import { ServicoContabilEtapaFluxoExecucaoService } from './servico-contabil-etapa-fluxo-execucao.service';

const requireRestSample: IServicoContabilEtapaFluxoExecucao = {
  ...sampleWithRequiredData,
};

describe('ServicoContabilEtapaFluxoExecucao Service', () => {
  let service: ServicoContabilEtapaFluxoExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicoContabilEtapaFluxoExecucao | IServicoContabilEtapaFluxoExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServicoContabilEtapaFluxoExecucaoService);
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

    it('should create a ServicoContabilEtapaFluxoExecucao', () => {
      const servicoContabilEtapaFluxoExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicoContabilEtapaFluxoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServicoContabilEtapaFluxoExecucao', () => {
      const servicoContabilEtapaFluxoExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicoContabilEtapaFluxoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServicoContabilEtapaFluxoExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServicoContabilEtapaFluxoExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServicoContabilEtapaFluxoExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing', () => {
      it('should add a ServicoContabilEtapaFluxoExecucao to an empty array', () => {
        const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = sampleWithRequiredData;
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing([], servicoContabilEtapaFluxoExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoExecucao);
      });

      it('should not add a ServicoContabilEtapaFluxoExecucao to an array that contains it', () => {
        const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = sampleWithRequiredData;
        const servicoContabilEtapaFluxoExecucaoCollection: IServicoContabilEtapaFluxoExecucao[] = [
          {
            ...servicoContabilEtapaFluxoExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing(
          servicoContabilEtapaFluxoExecucaoCollection,
          servicoContabilEtapaFluxoExecucao,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServicoContabilEtapaFluxoExecucao to an array that doesn't contain it", () => {
        const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = sampleWithRequiredData;
        const servicoContabilEtapaFluxoExecucaoCollection: IServicoContabilEtapaFluxoExecucao[] = [sampleWithPartialData];
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing(
          servicoContabilEtapaFluxoExecucaoCollection,
          servicoContabilEtapaFluxoExecucao,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoExecucao);
      });

      it('should add only unique ServicoContabilEtapaFluxoExecucao to an array', () => {
        const servicoContabilEtapaFluxoExecucaoArray: IServicoContabilEtapaFluxoExecucao[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const servicoContabilEtapaFluxoExecucaoCollection: IServicoContabilEtapaFluxoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing(
          servicoContabilEtapaFluxoExecucaoCollection,
          ...servicoContabilEtapaFluxoExecucaoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = sampleWithRequiredData;
        const servicoContabilEtapaFluxoExecucao2: IServicoContabilEtapaFluxoExecucao = sampleWithPartialData;
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing(
          [],
          servicoContabilEtapaFluxoExecucao,
          servicoContabilEtapaFluxoExecucao2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoExecucao);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoExecucao2);
      });

      it('should accept null and undefined values', () => {
        const servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao = sampleWithRequiredData;
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing(
          [],
          null,
          servicoContabilEtapaFluxoExecucao,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilEtapaFluxoExecucao);
      });

      it('should return initial array if no ServicoContabilEtapaFluxoExecucao is added', () => {
        const servicoContabilEtapaFluxoExecucaoCollection: IServicoContabilEtapaFluxoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing(
          servicoContabilEtapaFluxoExecucaoCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(servicoContabilEtapaFluxoExecucaoCollection);
      });
    });

    describe('compareServicoContabilEtapaFluxoExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicoContabilEtapaFluxoExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicoContabilEtapaFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEtapaFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicoContabilEtapaFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEtapaFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicoContabilEtapaFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareServicoContabilEtapaFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
