import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IOpcaoNomeFantasiaEmpresa } from '../opcao-nome-fantasia-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../opcao-nome-fantasia-empresa.test-samples';

import { OpcaoNomeFantasiaEmpresaService } from './opcao-nome-fantasia-empresa.service';

const requireRestSample: IOpcaoNomeFantasiaEmpresa = {
  ...sampleWithRequiredData,
};

describe('OpcaoNomeFantasiaEmpresa Service', () => {
  let service: OpcaoNomeFantasiaEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IOpcaoNomeFantasiaEmpresa | IOpcaoNomeFantasiaEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(OpcaoNomeFantasiaEmpresaService);
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

    it('should create a OpcaoNomeFantasiaEmpresa', () => {
      const opcaoNomeFantasiaEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(opcaoNomeFantasiaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a OpcaoNomeFantasiaEmpresa', () => {
      const opcaoNomeFantasiaEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(opcaoNomeFantasiaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a OpcaoNomeFantasiaEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of OpcaoNomeFantasiaEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a OpcaoNomeFantasiaEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addOpcaoNomeFantasiaEmpresaToCollectionIfMissing', () => {
      it('should add a OpcaoNomeFantasiaEmpresa to an empty array', () => {
        const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = sampleWithRequiredData;
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing([], opcaoNomeFantasiaEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(opcaoNomeFantasiaEmpresa);
      });

      it('should not add a OpcaoNomeFantasiaEmpresa to an array that contains it', () => {
        const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = sampleWithRequiredData;
        const opcaoNomeFantasiaEmpresaCollection: IOpcaoNomeFantasiaEmpresa[] = [
          {
            ...opcaoNomeFantasiaEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing(
          opcaoNomeFantasiaEmpresaCollection,
          opcaoNomeFantasiaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a OpcaoNomeFantasiaEmpresa to an array that doesn't contain it", () => {
        const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = sampleWithRequiredData;
        const opcaoNomeFantasiaEmpresaCollection: IOpcaoNomeFantasiaEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing(
          opcaoNomeFantasiaEmpresaCollection,
          opcaoNomeFantasiaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(opcaoNomeFantasiaEmpresa);
      });

      it('should add only unique OpcaoNomeFantasiaEmpresa to an array', () => {
        const opcaoNomeFantasiaEmpresaArray: IOpcaoNomeFantasiaEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const opcaoNomeFantasiaEmpresaCollection: IOpcaoNomeFantasiaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing(
          opcaoNomeFantasiaEmpresaCollection,
          ...opcaoNomeFantasiaEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = sampleWithRequiredData;
        const opcaoNomeFantasiaEmpresa2: IOpcaoNomeFantasiaEmpresa = sampleWithPartialData;
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing([], opcaoNomeFantasiaEmpresa, opcaoNomeFantasiaEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(opcaoNomeFantasiaEmpresa);
        expect(expectedResult).toContain(opcaoNomeFantasiaEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const opcaoNomeFantasiaEmpresa: IOpcaoNomeFantasiaEmpresa = sampleWithRequiredData;
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing([], null, opcaoNomeFantasiaEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(opcaoNomeFantasiaEmpresa);
      });

      it('should return initial array if no OpcaoNomeFantasiaEmpresa is added', () => {
        const opcaoNomeFantasiaEmpresaCollection: IOpcaoNomeFantasiaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addOpcaoNomeFantasiaEmpresaToCollectionIfMissing(opcaoNomeFantasiaEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(opcaoNomeFantasiaEmpresaCollection);
      });
    });

    describe('compareOpcaoNomeFantasiaEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareOpcaoNomeFantasiaEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareOpcaoNomeFantasiaEmpresa(entity1, entity2);
        const compareResult2 = service.compareOpcaoNomeFantasiaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareOpcaoNomeFantasiaEmpresa(entity1, entity2);
        const compareResult2 = service.compareOpcaoNomeFantasiaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareOpcaoNomeFantasiaEmpresa(entity1, entity2);
        const compareResult2 = service.compareOpcaoNomeFantasiaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
