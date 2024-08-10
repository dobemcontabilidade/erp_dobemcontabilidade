import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlanoContaAzul } from '../plano-conta-azul.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plano-conta-azul.test-samples';

import { PlanoContaAzulService } from './plano-conta-azul.service';

const requireRestSample: IPlanoContaAzul = {
  ...sampleWithRequiredData,
};

describe('PlanoContaAzul Service', () => {
  let service: PlanoContaAzulService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoContaAzul | IPlanoContaAzul[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoContaAzulService);
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

    it('should create a PlanoContaAzul', () => {
      const planoContaAzul = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoContaAzul).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoContaAzul', () => {
      const planoContaAzul = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoContaAzul).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoContaAzul', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoContaAzul', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoContaAzul', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoContaAzulToCollectionIfMissing', () => {
      it('should add a PlanoContaAzul to an empty array', () => {
        const planoContaAzul: IPlanoContaAzul = sampleWithRequiredData;
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing([], planoContaAzul);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoContaAzul);
      });

      it('should not add a PlanoContaAzul to an array that contains it', () => {
        const planoContaAzul: IPlanoContaAzul = sampleWithRequiredData;
        const planoContaAzulCollection: IPlanoContaAzul[] = [
          {
            ...planoContaAzul,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing(planoContaAzulCollection, planoContaAzul);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoContaAzul to an array that doesn't contain it", () => {
        const planoContaAzul: IPlanoContaAzul = sampleWithRequiredData;
        const planoContaAzulCollection: IPlanoContaAzul[] = [sampleWithPartialData];
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing(planoContaAzulCollection, planoContaAzul);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoContaAzul);
      });

      it('should add only unique PlanoContaAzul to an array', () => {
        const planoContaAzulArray: IPlanoContaAzul[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoContaAzulCollection: IPlanoContaAzul[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing(planoContaAzulCollection, ...planoContaAzulArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoContaAzul: IPlanoContaAzul = sampleWithRequiredData;
        const planoContaAzul2: IPlanoContaAzul = sampleWithPartialData;
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing([], planoContaAzul, planoContaAzul2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoContaAzul);
        expect(expectedResult).toContain(planoContaAzul2);
      });

      it('should accept null and undefined values', () => {
        const planoContaAzul: IPlanoContaAzul = sampleWithRequiredData;
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing([], null, planoContaAzul, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoContaAzul);
      });

      it('should return initial array if no PlanoContaAzul is added', () => {
        const planoContaAzulCollection: IPlanoContaAzul[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoContaAzulToCollectionIfMissing(planoContaAzulCollection, undefined, null);
        expect(expectedResult).toEqual(planoContaAzulCollection);
      });
    });

    describe('comparePlanoContaAzul', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoContaAzul(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoContaAzul(entity1, entity2);
        const compareResult2 = service.comparePlanoContaAzul(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoContaAzul(entity1, entity2);
        const compareResult2 = service.comparePlanoContaAzul(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoContaAzul(entity1, entity2);
        const compareResult2 = service.comparePlanoContaAzul(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
