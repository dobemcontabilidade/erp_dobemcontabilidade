import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITributacao } from '../tributacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tributacao.test-samples';

import { TributacaoService } from './tributacao.service';

const requireRestSample: ITributacao = {
  ...sampleWithRequiredData,
};

describe('Tributacao Service', () => {
  let service: TributacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITributacao | ITributacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TributacaoService);
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

    it('should create a Tributacao', () => {
      const tributacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tributacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tributacao', () => {
      const tributacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tributacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tributacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tributacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Tributacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTributacaoToCollectionIfMissing', () => {
      it('should add a Tributacao to an empty array', () => {
        const tributacao: ITributacao = sampleWithRequiredData;
        expectedResult = service.addTributacaoToCollectionIfMissing([], tributacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tributacao);
      });

      it('should not add a Tributacao to an array that contains it', () => {
        const tributacao: ITributacao = sampleWithRequiredData;
        const tributacaoCollection: ITributacao[] = [
          {
            ...tributacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTributacaoToCollectionIfMissing(tributacaoCollection, tributacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tributacao to an array that doesn't contain it", () => {
        const tributacao: ITributacao = sampleWithRequiredData;
        const tributacaoCollection: ITributacao[] = [sampleWithPartialData];
        expectedResult = service.addTributacaoToCollectionIfMissing(tributacaoCollection, tributacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tributacao);
      });

      it('should add only unique Tributacao to an array', () => {
        const tributacaoArray: ITributacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tributacaoCollection: ITributacao[] = [sampleWithRequiredData];
        expectedResult = service.addTributacaoToCollectionIfMissing(tributacaoCollection, ...tributacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tributacao: ITributacao = sampleWithRequiredData;
        const tributacao2: ITributacao = sampleWithPartialData;
        expectedResult = service.addTributacaoToCollectionIfMissing([], tributacao, tributacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tributacao);
        expect(expectedResult).toContain(tributacao2);
      });

      it('should accept null and undefined values', () => {
        const tributacao: ITributacao = sampleWithRequiredData;
        expectedResult = service.addTributacaoToCollectionIfMissing([], null, tributacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tributacao);
      });

      it('should return initial array if no Tributacao is added', () => {
        const tributacaoCollection: ITributacao[] = [sampleWithRequiredData];
        expectedResult = service.addTributacaoToCollectionIfMissing(tributacaoCollection, undefined, null);
        expect(expectedResult).toEqual(tributacaoCollection);
      });
    });

    describe('compareTributacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTributacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTributacao(entity1, entity2);
        const compareResult2 = service.compareTributacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTributacao(entity1, entity2);
        const compareResult2 = service.compareTributacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTributacao(entity1, entity2);
        const compareResult2 = service.compareTributacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
