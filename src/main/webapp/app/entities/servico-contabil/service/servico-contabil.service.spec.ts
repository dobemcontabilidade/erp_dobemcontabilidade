import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicoContabil } from '../servico-contabil.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../servico-contabil.test-samples';

import { ServicoContabilService } from './servico-contabil.service';

const requireRestSample: IServicoContabil = {
  ...sampleWithRequiredData,
};

describe('ServicoContabil Service', () => {
  let service: ServicoContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicoContabil | IServicoContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServicoContabilService);
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

    it('should create a ServicoContabil', () => {
      const servicoContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServicoContabil', () => {
      const servicoContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServicoContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServicoContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServicoContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServicoContabilToCollectionIfMissing', () => {
      it('should add a ServicoContabil to an empty array', () => {
        const servicoContabil: IServicoContabil = sampleWithRequiredData;
        expectedResult = service.addServicoContabilToCollectionIfMissing([], servicoContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabil);
      });

      it('should not add a ServicoContabil to an array that contains it', () => {
        const servicoContabil: IServicoContabil = sampleWithRequiredData;
        const servicoContabilCollection: IServicoContabil[] = [
          {
            ...servicoContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServicoContabilToCollectionIfMissing(servicoContabilCollection, servicoContabil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServicoContabil to an array that doesn't contain it", () => {
        const servicoContabil: IServicoContabil = sampleWithRequiredData;
        const servicoContabilCollection: IServicoContabil[] = [sampleWithPartialData];
        expectedResult = service.addServicoContabilToCollectionIfMissing(servicoContabilCollection, servicoContabil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabil);
      });

      it('should add only unique ServicoContabil to an array', () => {
        const servicoContabilArray: IServicoContabil[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const servicoContabilCollection: IServicoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilToCollectionIfMissing(servicoContabilCollection, ...servicoContabilArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicoContabil: IServicoContabil = sampleWithRequiredData;
        const servicoContabil2: IServicoContabil = sampleWithPartialData;
        expectedResult = service.addServicoContabilToCollectionIfMissing([], servicoContabil, servicoContabil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabil);
        expect(expectedResult).toContain(servicoContabil2);
      });

      it('should accept null and undefined values', () => {
        const servicoContabil: IServicoContabil = sampleWithRequiredData;
        expectedResult = service.addServicoContabilToCollectionIfMissing([], null, servicoContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabil);
      });

      it('should return initial array if no ServicoContabil is added', () => {
        const servicoContabilCollection: IServicoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilToCollectionIfMissing(servicoContabilCollection, undefined, null);
        expect(expectedResult).toEqual(servicoContabilCollection);
      });
    });

    describe('compareServicoContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicoContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicoContabil(entity1, entity2);
        const compareResult2 = service.compareServicoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicoContabil(entity1, entity2);
        const compareResult2 = service.compareServicoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicoContabil(entity1, entity2);
        const compareResult2 = service.compareServicoContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
