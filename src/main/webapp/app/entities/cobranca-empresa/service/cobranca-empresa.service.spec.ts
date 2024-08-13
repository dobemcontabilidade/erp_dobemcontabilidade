import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICobrancaEmpresa } from '../cobranca-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cobranca-empresa.test-samples';

import { CobrancaEmpresaService, RestCobrancaEmpresa } from './cobranca-empresa.service';

const requireRestSample: RestCobrancaEmpresa = {
  ...sampleWithRequiredData,
  dataCobranca: sampleWithRequiredData.dataCobranca?.toJSON(),
};

describe('CobrancaEmpresa Service', () => {
  let service: CobrancaEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: ICobrancaEmpresa | ICobrancaEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CobrancaEmpresaService);
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

    it('should create a CobrancaEmpresa', () => {
      const cobrancaEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cobrancaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CobrancaEmpresa', () => {
      const cobrancaEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cobrancaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CobrancaEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CobrancaEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CobrancaEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCobrancaEmpresaToCollectionIfMissing', () => {
      it('should add a CobrancaEmpresa to an empty array', () => {
        const cobrancaEmpresa: ICobrancaEmpresa = sampleWithRequiredData;
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing([], cobrancaEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cobrancaEmpresa);
      });

      it('should not add a CobrancaEmpresa to an array that contains it', () => {
        const cobrancaEmpresa: ICobrancaEmpresa = sampleWithRequiredData;
        const cobrancaEmpresaCollection: ICobrancaEmpresa[] = [
          {
            ...cobrancaEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing(cobrancaEmpresaCollection, cobrancaEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CobrancaEmpresa to an array that doesn't contain it", () => {
        const cobrancaEmpresa: ICobrancaEmpresa = sampleWithRequiredData;
        const cobrancaEmpresaCollection: ICobrancaEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing(cobrancaEmpresaCollection, cobrancaEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cobrancaEmpresa);
      });

      it('should add only unique CobrancaEmpresa to an array', () => {
        const cobrancaEmpresaArray: ICobrancaEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cobrancaEmpresaCollection: ICobrancaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing(cobrancaEmpresaCollection, ...cobrancaEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cobrancaEmpresa: ICobrancaEmpresa = sampleWithRequiredData;
        const cobrancaEmpresa2: ICobrancaEmpresa = sampleWithPartialData;
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing([], cobrancaEmpresa, cobrancaEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cobrancaEmpresa);
        expect(expectedResult).toContain(cobrancaEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const cobrancaEmpresa: ICobrancaEmpresa = sampleWithRequiredData;
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing([], null, cobrancaEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cobrancaEmpresa);
      });

      it('should return initial array if no CobrancaEmpresa is added', () => {
        const cobrancaEmpresaCollection: ICobrancaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addCobrancaEmpresaToCollectionIfMissing(cobrancaEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(cobrancaEmpresaCollection);
      });
    });

    describe('compareCobrancaEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCobrancaEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCobrancaEmpresa(entity1, entity2);
        const compareResult2 = service.compareCobrancaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCobrancaEmpresa(entity1, entity2);
        const compareResult2 = service.compareCobrancaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCobrancaEmpresa(entity1, entity2);
        const compareResult2 = service.compareCobrancaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
