import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICriterioAvaliacao } from '../criterio-avaliacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../criterio-avaliacao.test-samples';

import { CriterioAvaliacaoService } from './criterio-avaliacao.service';

const requireRestSample: ICriterioAvaliacao = {
  ...sampleWithRequiredData,
};

describe('CriterioAvaliacao Service', () => {
  let service: CriterioAvaliacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICriterioAvaliacao | ICriterioAvaliacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CriterioAvaliacaoService);
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

    it('should create a CriterioAvaliacao', () => {
      const criterioAvaliacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(criterioAvaliacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CriterioAvaliacao', () => {
      const criterioAvaliacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(criterioAvaliacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CriterioAvaliacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CriterioAvaliacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CriterioAvaliacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCriterioAvaliacaoToCollectionIfMissing', () => {
      it('should add a CriterioAvaliacao to an empty array', () => {
        const criterioAvaliacao: ICriterioAvaliacao = sampleWithRequiredData;
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing([], criterioAvaliacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(criterioAvaliacao);
      });

      it('should not add a CriterioAvaliacao to an array that contains it', () => {
        const criterioAvaliacao: ICriterioAvaliacao = sampleWithRequiredData;
        const criterioAvaliacaoCollection: ICriterioAvaliacao[] = [
          {
            ...criterioAvaliacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing(criterioAvaliacaoCollection, criterioAvaliacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CriterioAvaliacao to an array that doesn't contain it", () => {
        const criterioAvaliacao: ICriterioAvaliacao = sampleWithRequiredData;
        const criterioAvaliacaoCollection: ICriterioAvaliacao[] = [sampleWithPartialData];
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing(criterioAvaliacaoCollection, criterioAvaliacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(criterioAvaliacao);
      });

      it('should add only unique CriterioAvaliacao to an array', () => {
        const criterioAvaliacaoArray: ICriterioAvaliacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const criterioAvaliacaoCollection: ICriterioAvaliacao[] = [sampleWithRequiredData];
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing(criterioAvaliacaoCollection, ...criterioAvaliacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const criterioAvaliacao: ICriterioAvaliacao = sampleWithRequiredData;
        const criterioAvaliacao2: ICriterioAvaliacao = sampleWithPartialData;
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing([], criterioAvaliacao, criterioAvaliacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(criterioAvaliacao);
        expect(expectedResult).toContain(criterioAvaliacao2);
      });

      it('should accept null and undefined values', () => {
        const criterioAvaliacao: ICriterioAvaliacao = sampleWithRequiredData;
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing([], null, criterioAvaliacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(criterioAvaliacao);
      });

      it('should return initial array if no CriterioAvaliacao is added', () => {
        const criterioAvaliacaoCollection: ICriterioAvaliacao[] = [sampleWithRequiredData];
        expectedResult = service.addCriterioAvaliacaoToCollectionIfMissing(criterioAvaliacaoCollection, undefined, null);
        expect(expectedResult).toEqual(criterioAvaliacaoCollection);
      });
    });

    describe('compareCriterioAvaliacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCriterioAvaliacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCriterioAvaliacao(entity1, entity2);
        const compareResult2 = service.compareCriterioAvaliacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCriterioAvaliacao(entity1, entity2);
        const compareResult2 = service.compareCriterioAvaliacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCriterioAvaliacao(entity1, entity2);
        const compareResult2 = service.compareCriterioAvaliacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
