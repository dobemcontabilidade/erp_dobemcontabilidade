import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../plano-assinatura-contabil.test-samples';

import { PlanoAssinaturaContabilService } from './plano-assinatura-contabil.service';

const requireRestSample: IPlanoAssinaturaContabil = {
  ...sampleWithRequiredData,
};

describe('PlanoAssinaturaContabil Service', () => {
  let service: PlanoAssinaturaContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoAssinaturaContabil | IPlanoAssinaturaContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoAssinaturaContabilService);
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

    it('should create a PlanoAssinaturaContabil', () => {
      const planoAssinaturaContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoAssinaturaContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoAssinaturaContabil', () => {
      const planoAssinaturaContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoAssinaturaContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoAssinaturaContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoAssinaturaContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoAssinaturaContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoAssinaturaContabilToCollectionIfMissing', () => {
      it('should add a PlanoAssinaturaContabil to an empty array', () => {
        const planoAssinaturaContabil: IPlanoAssinaturaContabil = sampleWithRequiredData;
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing([], planoAssinaturaContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoAssinaturaContabil);
      });

      it('should not add a PlanoAssinaturaContabil to an array that contains it', () => {
        const planoAssinaturaContabil: IPlanoAssinaturaContabil = sampleWithRequiredData;
        const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [
          {
            ...planoAssinaturaContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing(
          planoAssinaturaContabilCollection,
          planoAssinaturaContabil,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoAssinaturaContabil to an array that doesn't contain it", () => {
        const planoAssinaturaContabil: IPlanoAssinaturaContabil = sampleWithRequiredData;
        const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [sampleWithPartialData];
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing(
          planoAssinaturaContabilCollection,
          planoAssinaturaContabil,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoAssinaturaContabil);
      });

      it('should add only unique PlanoAssinaturaContabil to an array', () => {
        const planoAssinaturaContabilArray: IPlanoAssinaturaContabil[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing(
          planoAssinaturaContabilCollection,
          ...planoAssinaturaContabilArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoAssinaturaContabil: IPlanoAssinaturaContabil = sampleWithRequiredData;
        const planoAssinaturaContabil2: IPlanoAssinaturaContabil = sampleWithPartialData;
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing([], planoAssinaturaContabil, planoAssinaturaContabil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoAssinaturaContabil);
        expect(expectedResult).toContain(planoAssinaturaContabil2);
      });

      it('should accept null and undefined values', () => {
        const planoAssinaturaContabil: IPlanoAssinaturaContabil = sampleWithRequiredData;
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing([], null, planoAssinaturaContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoAssinaturaContabil);
      });

      it('should return initial array if no PlanoAssinaturaContabil is added', () => {
        const planoAssinaturaContabilCollection: IPlanoAssinaturaContabil[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoAssinaturaContabilToCollectionIfMissing(planoAssinaturaContabilCollection, undefined, null);
        expect(expectedResult).toEqual(planoAssinaturaContabilCollection);
      });
    });

    describe('comparePlanoAssinaturaContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoAssinaturaContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoAssinaturaContabil(entity1, entity2);
        const compareResult2 = service.comparePlanoAssinaturaContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoAssinaturaContabil(entity1, entity2);
        const compareResult2 = service.comparePlanoAssinaturaContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoAssinaturaContabil(entity1, entity2);
        const compareResult2 = service.comparePlanoAssinaturaContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
