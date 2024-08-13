import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDescontoPlanoContabil } from '../desconto-plano-contabil.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../desconto-plano-contabil.test-samples';

import { DescontoPlanoContabilService } from './desconto-plano-contabil.service';

const requireRestSample: IDescontoPlanoContabil = {
  ...sampleWithRequiredData,
};

describe('DescontoPlanoContabil Service', () => {
  let service: DescontoPlanoContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IDescontoPlanoContabil | IDescontoPlanoContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DescontoPlanoContabilService);
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

    it('should create a DescontoPlanoContabil', () => {
      const descontoPlanoContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(descontoPlanoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DescontoPlanoContabil', () => {
      const descontoPlanoContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(descontoPlanoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DescontoPlanoContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DescontoPlanoContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DescontoPlanoContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDescontoPlanoContabilToCollectionIfMissing', () => {
      it('should add a DescontoPlanoContabil to an empty array', () => {
        const descontoPlanoContabil: IDescontoPlanoContabil = sampleWithRequiredData;
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing([], descontoPlanoContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(descontoPlanoContabil);
      });

      it('should not add a DescontoPlanoContabil to an array that contains it', () => {
        const descontoPlanoContabil: IDescontoPlanoContabil = sampleWithRequiredData;
        const descontoPlanoContabilCollection: IDescontoPlanoContabil[] = [
          {
            ...descontoPlanoContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing(descontoPlanoContabilCollection, descontoPlanoContabil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DescontoPlanoContabil to an array that doesn't contain it", () => {
        const descontoPlanoContabil: IDescontoPlanoContabil = sampleWithRequiredData;
        const descontoPlanoContabilCollection: IDescontoPlanoContabil[] = [sampleWithPartialData];
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing(descontoPlanoContabilCollection, descontoPlanoContabil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(descontoPlanoContabil);
      });

      it('should add only unique DescontoPlanoContabil to an array', () => {
        const descontoPlanoContabilArray: IDescontoPlanoContabil[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const descontoPlanoContabilCollection: IDescontoPlanoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing(
          descontoPlanoContabilCollection,
          ...descontoPlanoContabilArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const descontoPlanoContabil: IDescontoPlanoContabil = sampleWithRequiredData;
        const descontoPlanoContabil2: IDescontoPlanoContabil = sampleWithPartialData;
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing([], descontoPlanoContabil, descontoPlanoContabil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(descontoPlanoContabil);
        expect(expectedResult).toContain(descontoPlanoContabil2);
      });

      it('should accept null and undefined values', () => {
        const descontoPlanoContabil: IDescontoPlanoContabil = sampleWithRequiredData;
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing([], null, descontoPlanoContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(descontoPlanoContabil);
      });

      it('should return initial array if no DescontoPlanoContabil is added', () => {
        const descontoPlanoContabilCollection: IDescontoPlanoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addDescontoPlanoContabilToCollectionIfMissing(descontoPlanoContabilCollection, undefined, null);
        expect(expectedResult).toEqual(descontoPlanoContabilCollection);
      });
    });

    describe('compareDescontoPlanoContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDescontoPlanoContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDescontoPlanoContabil(entity1, entity2);
        const compareResult2 = service.compareDescontoPlanoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDescontoPlanoContabil(entity1, entity2);
        const compareResult2 = service.compareDescontoPlanoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDescontoPlanoContabil(entity1, entity2);
        const compareResult2 = service.compareDescontoPlanoContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
