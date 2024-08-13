import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../etapa-fluxo-execucao.test-samples';

import { EtapaFluxoExecucaoService } from './etapa-fluxo-execucao.service';

const requireRestSample: IEtapaFluxoExecucao = {
  ...sampleWithRequiredData,
};

describe('EtapaFluxoExecucao Service', () => {
  let service: EtapaFluxoExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEtapaFluxoExecucao | IEtapaFluxoExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EtapaFluxoExecucaoService);
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

    it('should create a EtapaFluxoExecucao', () => {
      const etapaFluxoExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(etapaFluxoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EtapaFluxoExecucao', () => {
      const etapaFluxoExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(etapaFluxoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EtapaFluxoExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EtapaFluxoExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EtapaFluxoExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEtapaFluxoExecucaoToCollectionIfMissing', () => {
      it('should add a EtapaFluxoExecucao to an empty array', () => {
        const etapaFluxoExecucao: IEtapaFluxoExecucao = sampleWithRequiredData;
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing([], etapaFluxoExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(etapaFluxoExecucao);
      });

      it('should not add a EtapaFluxoExecucao to an array that contains it', () => {
        const etapaFluxoExecucao: IEtapaFluxoExecucao = sampleWithRequiredData;
        const etapaFluxoExecucaoCollection: IEtapaFluxoExecucao[] = [
          {
            ...etapaFluxoExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing(etapaFluxoExecucaoCollection, etapaFluxoExecucao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EtapaFluxoExecucao to an array that doesn't contain it", () => {
        const etapaFluxoExecucao: IEtapaFluxoExecucao = sampleWithRequiredData;
        const etapaFluxoExecucaoCollection: IEtapaFluxoExecucao[] = [sampleWithPartialData];
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing(etapaFluxoExecucaoCollection, etapaFluxoExecucao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(etapaFluxoExecucao);
      });

      it('should add only unique EtapaFluxoExecucao to an array', () => {
        const etapaFluxoExecucaoArray: IEtapaFluxoExecucao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const etapaFluxoExecucaoCollection: IEtapaFluxoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing(etapaFluxoExecucaoCollection, ...etapaFluxoExecucaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const etapaFluxoExecucao: IEtapaFluxoExecucao = sampleWithRequiredData;
        const etapaFluxoExecucao2: IEtapaFluxoExecucao = sampleWithPartialData;
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing([], etapaFluxoExecucao, etapaFluxoExecucao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(etapaFluxoExecucao);
        expect(expectedResult).toContain(etapaFluxoExecucao2);
      });

      it('should accept null and undefined values', () => {
        const etapaFluxoExecucao: IEtapaFluxoExecucao = sampleWithRequiredData;
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing([], null, etapaFluxoExecucao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(etapaFluxoExecucao);
      });

      it('should return initial array if no EtapaFluxoExecucao is added', () => {
        const etapaFluxoExecucaoCollection: IEtapaFluxoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addEtapaFluxoExecucaoToCollectionIfMissing(etapaFluxoExecucaoCollection, undefined, null);
        expect(expectedResult).toEqual(etapaFluxoExecucaoCollection);
      });
    });

    describe('compareEtapaFluxoExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEtapaFluxoExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEtapaFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareEtapaFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEtapaFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareEtapaFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEtapaFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareEtapaFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
