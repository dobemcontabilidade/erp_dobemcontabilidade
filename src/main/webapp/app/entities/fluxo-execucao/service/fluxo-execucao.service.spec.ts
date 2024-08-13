import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFluxoExecucao } from '../fluxo-execucao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../fluxo-execucao.test-samples';

import { FluxoExecucaoService } from './fluxo-execucao.service';

const requireRestSample: IFluxoExecucao = {
  ...sampleWithRequiredData,
};

describe('FluxoExecucao Service', () => {
  let service: FluxoExecucaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFluxoExecucao | IFluxoExecucao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FluxoExecucaoService);
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

    it('should create a FluxoExecucao', () => {
      const fluxoExecucao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fluxoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FluxoExecucao', () => {
      const fluxoExecucao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fluxoExecucao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FluxoExecucao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FluxoExecucao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FluxoExecucao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFluxoExecucaoToCollectionIfMissing', () => {
      it('should add a FluxoExecucao to an empty array', () => {
        const fluxoExecucao: IFluxoExecucao = sampleWithRequiredData;
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing([], fluxoExecucao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fluxoExecucao);
      });

      it('should not add a FluxoExecucao to an array that contains it', () => {
        const fluxoExecucao: IFluxoExecucao = sampleWithRequiredData;
        const fluxoExecucaoCollection: IFluxoExecucao[] = [
          {
            ...fluxoExecucao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing(fluxoExecucaoCollection, fluxoExecucao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FluxoExecucao to an array that doesn't contain it", () => {
        const fluxoExecucao: IFluxoExecucao = sampleWithRequiredData;
        const fluxoExecucaoCollection: IFluxoExecucao[] = [sampleWithPartialData];
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing(fluxoExecucaoCollection, fluxoExecucao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fluxoExecucao);
      });

      it('should add only unique FluxoExecucao to an array', () => {
        const fluxoExecucaoArray: IFluxoExecucao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fluxoExecucaoCollection: IFluxoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing(fluxoExecucaoCollection, ...fluxoExecucaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fluxoExecucao: IFluxoExecucao = sampleWithRequiredData;
        const fluxoExecucao2: IFluxoExecucao = sampleWithPartialData;
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing([], fluxoExecucao, fluxoExecucao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fluxoExecucao);
        expect(expectedResult).toContain(fluxoExecucao2);
      });

      it('should accept null and undefined values', () => {
        const fluxoExecucao: IFluxoExecucao = sampleWithRequiredData;
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing([], null, fluxoExecucao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fluxoExecucao);
      });

      it('should return initial array if no FluxoExecucao is added', () => {
        const fluxoExecucaoCollection: IFluxoExecucao[] = [sampleWithRequiredData];
        expectedResult = service.addFluxoExecucaoToCollectionIfMissing(fluxoExecucaoCollection, undefined, null);
        expect(expectedResult).toEqual(fluxoExecucaoCollection);
      });
    });

    describe('compareFluxoExecucao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFluxoExecucao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFluxoExecucao(entity1, entity2);
        const compareResult2 = service.compareFluxoExecucao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
