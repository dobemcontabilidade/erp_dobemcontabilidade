import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../perfil-contador-area-contabil.test-samples';

import { PerfilContadorAreaContabilService } from './perfil-contador-area-contabil.service';

const requireRestSample: IPerfilContadorAreaContabil = {
  ...sampleWithRequiredData,
};

describe('PerfilContadorAreaContabil Service', () => {
  let service: PerfilContadorAreaContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IPerfilContadorAreaContabil | IPerfilContadorAreaContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PerfilContadorAreaContabilService);
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

    it('should create a PerfilContadorAreaContabil', () => {
      const perfilContadorAreaContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(perfilContadorAreaContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PerfilContadorAreaContabil', () => {
      const perfilContadorAreaContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(perfilContadorAreaContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PerfilContadorAreaContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PerfilContadorAreaContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PerfilContadorAreaContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPerfilContadorAreaContabilToCollectionIfMissing', () => {
      it('should add a PerfilContadorAreaContabil to an empty array', () => {
        const perfilContadorAreaContabil: IPerfilContadorAreaContabil = sampleWithRequiredData;
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing([], perfilContadorAreaContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilContadorAreaContabil);
      });

      it('should not add a PerfilContadorAreaContabil to an array that contains it', () => {
        const perfilContadorAreaContabil: IPerfilContadorAreaContabil = sampleWithRequiredData;
        const perfilContadorAreaContabilCollection: IPerfilContadorAreaContabil[] = [
          {
            ...perfilContadorAreaContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing(
          perfilContadorAreaContabilCollection,
          perfilContadorAreaContabil,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PerfilContadorAreaContabil to an array that doesn't contain it", () => {
        const perfilContadorAreaContabil: IPerfilContadorAreaContabil = sampleWithRequiredData;
        const perfilContadorAreaContabilCollection: IPerfilContadorAreaContabil[] = [sampleWithPartialData];
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing(
          perfilContadorAreaContabilCollection,
          perfilContadorAreaContabil,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilContadorAreaContabil);
      });

      it('should add only unique PerfilContadorAreaContabil to an array', () => {
        const perfilContadorAreaContabilArray: IPerfilContadorAreaContabil[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const perfilContadorAreaContabilCollection: IPerfilContadorAreaContabil[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing(
          perfilContadorAreaContabilCollection,
          ...perfilContadorAreaContabilArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const perfilContadorAreaContabil: IPerfilContadorAreaContabil = sampleWithRequiredData;
        const perfilContadorAreaContabil2: IPerfilContadorAreaContabil = sampleWithPartialData;
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing(
          [],
          perfilContadorAreaContabil,
          perfilContadorAreaContabil2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(perfilContadorAreaContabil);
        expect(expectedResult).toContain(perfilContadorAreaContabil2);
      });

      it('should accept null and undefined values', () => {
        const perfilContadorAreaContabil: IPerfilContadorAreaContabil = sampleWithRequiredData;
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing([], null, perfilContadorAreaContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(perfilContadorAreaContabil);
      });

      it('should return initial array if no PerfilContadorAreaContabil is added', () => {
        const perfilContadorAreaContabilCollection: IPerfilContadorAreaContabil[] = [sampleWithRequiredData];
        expectedResult = service.addPerfilContadorAreaContabilToCollectionIfMissing(perfilContadorAreaContabilCollection, undefined, null);
        expect(expectedResult).toEqual(perfilContadorAreaContabilCollection);
      });
    });

    describe('comparePerfilContadorAreaContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePerfilContadorAreaContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePerfilContadorAreaContabil(entity1, entity2);
        const compareResult2 = service.comparePerfilContadorAreaContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePerfilContadorAreaContabil(entity1, entity2);
        const compareResult2 = service.comparePerfilContadorAreaContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePerfilContadorAreaContabil(entity1, entity2);
        const compareResult2 = service.comparePerfilContadorAreaContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
