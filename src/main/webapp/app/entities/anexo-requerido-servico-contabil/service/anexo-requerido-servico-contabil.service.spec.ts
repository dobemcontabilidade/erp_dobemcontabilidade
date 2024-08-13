import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-requerido-servico-contabil.test-samples';

import { AnexoRequeridoServicoContabilService } from './anexo-requerido-servico-contabil.service';

const requireRestSample: IAnexoRequeridoServicoContabil = {
  ...sampleWithRequiredData,
};

describe('AnexoRequeridoServicoContabil Service', () => {
  let service: AnexoRequeridoServicoContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoRequeridoServicoContabil | IAnexoRequeridoServicoContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoRequeridoServicoContabilService);
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

    it('should create a AnexoRequeridoServicoContabil', () => {
      const anexoRequeridoServicoContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoRequeridoServicoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoRequeridoServicoContabil', () => {
      const anexoRequeridoServicoContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoRequeridoServicoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoRequeridoServicoContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoRequeridoServicoContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoRequeridoServicoContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoRequeridoServicoContabilToCollectionIfMissing', () => {
      it('should add a AnexoRequeridoServicoContabil to an empty array', () => {
        const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing([], anexoRequeridoServicoContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoServicoContabil);
      });

      it('should not add a AnexoRequeridoServicoContabil to an array that contains it', () => {
        const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = sampleWithRequiredData;
        const anexoRequeridoServicoContabilCollection: IAnexoRequeridoServicoContabil[] = [
          {
            ...anexoRequeridoServicoContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing(
          anexoRequeridoServicoContabilCollection,
          anexoRequeridoServicoContabil,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoRequeridoServicoContabil to an array that doesn't contain it", () => {
        const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = sampleWithRequiredData;
        const anexoRequeridoServicoContabilCollection: IAnexoRequeridoServicoContabil[] = [sampleWithPartialData];
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing(
          anexoRequeridoServicoContabilCollection,
          anexoRequeridoServicoContabil,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoServicoContabil);
      });

      it('should add only unique AnexoRequeridoServicoContabil to an array', () => {
        const anexoRequeridoServicoContabilArray: IAnexoRequeridoServicoContabil[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const anexoRequeridoServicoContabilCollection: IAnexoRequeridoServicoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing(
          anexoRequeridoServicoContabilCollection,
          ...anexoRequeridoServicoContabilArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = sampleWithRequiredData;
        const anexoRequeridoServicoContabil2: IAnexoRequeridoServicoContabil = sampleWithPartialData;
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing(
          [],
          anexoRequeridoServicoContabil,
          anexoRequeridoServicoContabil2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoRequeridoServicoContabil);
        expect(expectedResult).toContain(anexoRequeridoServicoContabil2);
      });

      it('should accept null and undefined values', () => {
        const anexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil = sampleWithRequiredData;
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing([], null, anexoRequeridoServicoContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoRequeridoServicoContabil);
      });

      it('should return initial array if no AnexoRequeridoServicoContabil is added', () => {
        const anexoRequeridoServicoContabilCollection: IAnexoRequeridoServicoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoRequeridoServicoContabilToCollectionIfMissing(
          anexoRequeridoServicoContabilCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(anexoRequeridoServicoContabilCollection);
      });
    });

    describe('compareAnexoRequeridoServicoContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoRequeridoServicoContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoRequeridoServicoContabil(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoServicoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoRequeridoServicoContabil(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoServicoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoRequeridoServicoContabil(entity1, entity2);
        const compareResult2 = service.compareAnexoRequeridoServicoContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
