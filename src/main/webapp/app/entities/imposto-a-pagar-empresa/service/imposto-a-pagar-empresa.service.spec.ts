import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../imposto-a-pagar-empresa.test-samples';

import { ImpostoAPagarEmpresaService, RestImpostoAPagarEmpresa } from './imposto-a-pagar-empresa.service';

const requireRestSample: RestImpostoAPagarEmpresa = {
  ...sampleWithRequiredData,
  dataVencimento: sampleWithRequiredData.dataVencimento?.toJSON(),
  dataPagamento: sampleWithRequiredData.dataPagamento?.toJSON(),
};

describe('ImpostoAPagarEmpresa Service', () => {
  let service: ImpostoAPagarEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IImpostoAPagarEmpresa | IImpostoAPagarEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ImpostoAPagarEmpresaService);
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

    it('should create a ImpostoAPagarEmpresa', () => {
      const impostoAPagarEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(impostoAPagarEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ImpostoAPagarEmpresa', () => {
      const impostoAPagarEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(impostoAPagarEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ImpostoAPagarEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ImpostoAPagarEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ImpostoAPagarEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addImpostoAPagarEmpresaToCollectionIfMissing', () => {
      it('should add a ImpostoAPagarEmpresa to an empty array', () => {
        const impostoAPagarEmpresa: IImpostoAPagarEmpresa = sampleWithRequiredData;
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing([], impostoAPagarEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoAPagarEmpresa);
      });

      it('should not add a ImpostoAPagarEmpresa to an array that contains it', () => {
        const impostoAPagarEmpresa: IImpostoAPagarEmpresa = sampleWithRequiredData;
        const impostoAPagarEmpresaCollection: IImpostoAPagarEmpresa[] = [
          {
            ...impostoAPagarEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing(impostoAPagarEmpresaCollection, impostoAPagarEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ImpostoAPagarEmpresa to an array that doesn't contain it", () => {
        const impostoAPagarEmpresa: IImpostoAPagarEmpresa = sampleWithRequiredData;
        const impostoAPagarEmpresaCollection: IImpostoAPagarEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing(impostoAPagarEmpresaCollection, impostoAPagarEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoAPagarEmpresa);
      });

      it('should add only unique ImpostoAPagarEmpresa to an array', () => {
        const impostoAPagarEmpresaArray: IImpostoAPagarEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const impostoAPagarEmpresaCollection: IImpostoAPagarEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing(impostoAPagarEmpresaCollection, ...impostoAPagarEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const impostoAPagarEmpresa: IImpostoAPagarEmpresa = sampleWithRequiredData;
        const impostoAPagarEmpresa2: IImpostoAPagarEmpresa = sampleWithPartialData;
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing([], impostoAPagarEmpresa, impostoAPagarEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(impostoAPagarEmpresa);
        expect(expectedResult).toContain(impostoAPagarEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const impostoAPagarEmpresa: IImpostoAPagarEmpresa = sampleWithRequiredData;
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing([], null, impostoAPagarEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(impostoAPagarEmpresa);
      });

      it('should return initial array if no ImpostoAPagarEmpresa is added', () => {
        const impostoAPagarEmpresaCollection: IImpostoAPagarEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addImpostoAPagarEmpresaToCollectionIfMissing(impostoAPagarEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(impostoAPagarEmpresaCollection);
      });
    });

    describe('compareImpostoAPagarEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareImpostoAPagarEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareImpostoAPagarEmpresa(entity1, entity2);
        const compareResult2 = service.compareImpostoAPagarEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareImpostoAPagarEmpresa(entity1, entity2);
        const compareResult2 = service.compareImpostoAPagarEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareImpostoAPagarEmpresa(entity1, entity2);
        const compareResult2 = service.compareImpostoAPagarEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
