import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../criterio-avaliacao-ator.test-samples';

import { CriterioAvaliacaoAtorService } from './criterio-avaliacao-ator.service';

const requireRestSample: ICriterioAvaliacaoAtor = {
  ...sampleWithRequiredData,
};

describe('CriterioAvaliacaoAtor Service', () => {
  let service: CriterioAvaliacaoAtorService;
  let httpMock: HttpTestingController;
  let expectedResult: ICriterioAvaliacaoAtor | ICriterioAvaliacaoAtor[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CriterioAvaliacaoAtorService);
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

    it('should create a CriterioAvaliacaoAtor', () => {
      const criterioAvaliacaoAtor = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(criterioAvaliacaoAtor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CriterioAvaliacaoAtor', () => {
      const criterioAvaliacaoAtor = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(criterioAvaliacaoAtor).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CriterioAvaliacaoAtor', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CriterioAvaliacaoAtor', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CriterioAvaliacaoAtor', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCriterioAvaliacaoAtorToCollectionIfMissing', () => {
      it('should add a CriterioAvaliacaoAtor to an empty array', () => {
        const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = sampleWithRequiredData;
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing([], criterioAvaliacaoAtor);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(criterioAvaliacaoAtor);
      });

      it('should not add a CriterioAvaliacaoAtor to an array that contains it', () => {
        const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = sampleWithRequiredData;
        const criterioAvaliacaoAtorCollection: ICriterioAvaliacaoAtor[] = [
          {
            ...criterioAvaliacaoAtor,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing(criterioAvaliacaoAtorCollection, criterioAvaliacaoAtor);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CriterioAvaliacaoAtor to an array that doesn't contain it", () => {
        const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = sampleWithRequiredData;
        const criterioAvaliacaoAtorCollection: ICriterioAvaliacaoAtor[] = [sampleWithPartialData];
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing(criterioAvaliacaoAtorCollection, criterioAvaliacaoAtor);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(criterioAvaliacaoAtor);
      });

      it('should add only unique CriterioAvaliacaoAtor to an array', () => {
        const criterioAvaliacaoAtorArray: ICriterioAvaliacaoAtor[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const criterioAvaliacaoAtorCollection: ICriterioAvaliacaoAtor[] = [sampleWithRequiredData];
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing(
          criterioAvaliacaoAtorCollection,
          ...criterioAvaliacaoAtorArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = sampleWithRequiredData;
        const criterioAvaliacaoAtor2: ICriterioAvaliacaoAtor = sampleWithPartialData;
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing([], criterioAvaliacaoAtor, criterioAvaliacaoAtor2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(criterioAvaliacaoAtor);
        expect(expectedResult).toContain(criterioAvaliacaoAtor2);
      });

      it('should accept null and undefined values', () => {
        const criterioAvaliacaoAtor: ICriterioAvaliacaoAtor = sampleWithRequiredData;
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing([], null, criterioAvaliacaoAtor, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(criterioAvaliacaoAtor);
      });

      it('should return initial array if no CriterioAvaliacaoAtor is added', () => {
        const criterioAvaliacaoAtorCollection: ICriterioAvaliacaoAtor[] = [sampleWithRequiredData];
        expectedResult = service.addCriterioAvaliacaoAtorToCollectionIfMissing(criterioAvaliacaoAtorCollection, undefined, null);
        expect(expectedResult).toEqual(criterioAvaliacaoAtorCollection);
      });
    });

    describe('compareCriterioAvaliacaoAtor', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCriterioAvaliacaoAtor(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCriterioAvaliacaoAtor(entity1, entity2);
        const compareResult2 = service.compareCriterioAvaliacaoAtor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCriterioAvaliacaoAtor(entity1, entity2);
        const compareResult2 = service.compareCriterioAvaliacaoAtor(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCriterioAvaliacaoAtor(entity1, entity2);
        const compareResult2 = service.compareCriterioAvaliacaoAtor(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
